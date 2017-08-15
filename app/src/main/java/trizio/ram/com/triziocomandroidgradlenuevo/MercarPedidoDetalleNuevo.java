package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MercarPedidoDetalleNuevo extends Activity {

    ProgressDialog mProgressDialog;
    String ip = "";
    String[] cantUpdates ;
    MyListAdapter myListAdapter = null;
    ArrayList<ItemCheck> countryList;
    ArrayList<String> codes;
    String ind_movimiento = "";
    String ind_lista = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercar_pedido_detalle_nuevo);

        ip = getIntent().getExtras().getString("ip");
        codes = getIntent().getExtras().getStringArrayList("codes");
        ind_movimiento = getIntent().getExtras().getString("ind_movimiento");
        ind_lista = getIntent().getExtras().getString("ind_lista");

        mProgressDialog = new ProgressDialog(MercarPedidoDetalleNuevo.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Consultando datos...");


        if (ind_movimiento.equals("N")) {
            AutoCompleteTextView producto = (AutoCompleteTextView) findViewById(R.id.editProducto);
            Button añadir = (Button) findViewById(R.id.addItem);

            producto.setVisibility(View.INVISIBLE);
            añadir.setVisibility(View.INVISIBLE);
        }


        new ConsultarDatos().execute("");
        new ConsultarProductos().execute("");
    }

    class ConsultarProductos extends AsyncTask<String, String, String> {


        ItemCheck country;
        ArrayList<String> array = new ArrayList<>();
        ArrayList< String[]> arrayPrices = new ArrayList<>();
        String[] prices;
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... f_url) {


            //for (int i = 0; i < codes.size(); i++) {
                String url3 = "http://" + ip + "/consultarGeneralHydro.php";

                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sParametro", "consultarProductos"));
                String resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);

                try {

                    JSONArray jArray2 = new JSONArray(resultServer3);

                    for (int j = 0; j < jArray2.length(); j++) {
                        JSONObject json2 = jArray2.getJSONObject(j);

                       array.add(json2.getString("codigo").trim() + " - " + json2.getString("descripcion").trim());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            //}


            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            mProgressDialog.dismiss();
            //create an ArrayAdaptar from the String Array
            final String[] countries = new String[array.size()];
            for (int i = 0;i<array.size();i++){
                countries[i] = array.get(i).trim();
            }
            final AutoCompleteTextView producto = (AutoCompleteTextView) findViewById(R.id.editProducto);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (getApplicationContext(),android.R.layout.simple_list_item_1,countries);
            producto.setAdapter(adapter);


            producto.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    //Toast.makeText(getApplicationContext(), countries[position] , Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    public void addItem(View v){
        final AutoCompleteTextView producto = (AutoCompleteTextView) findViewById(R.id.editProducto);
        String[] data = producto.getText().toString().split("-");
        //Toast.makeText(getApplicationContext(), Arrays.toString(data), Toast.LENGTH_SHORT).show();

        countryList.add(new ItemCheck(data[0], data[1], true, "1",
                "1","0", new String[]{"Lista.."}));
        myListAdapter.notifyDataSetChanged();

        producto.setText("");

        Toast.makeText(getApplicationContext(), "Producto Añadido ..", Toast.LENGTH_SHORT).show();
    }

    class ConsultarDatos extends AsyncTask<String, String, String> {


        ItemCheck country;

        @Override
        protected void onPreExecute() {
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... f_url) {

            countryList = new ArrayList<ItemCheck>();

            for (int i = 0; i < codes.size(); i++) {
                String url3 = "http://" + ip + "/consultarGeneralHydro.php";

                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sParametro", "consultarPlantillasLin"));
                params3.add(new BasicNameValuePair("sId", codes.get(i)));
                String resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);
                String[] prices = new String[0];
                try {

                    JSONArray jArray2 = new JSONArray(resultServer3);

                    for (int j = 0; j < jArray2.length(); j++) {
                        JSONObject json2 = jArray2.getJSONObject(j);

                        String url4 = "http://" + ip + "/consultarGeneralHydro.php";

                        List<NameValuePair> params4 = new ArrayList<NameValuePair>();
                        params4.add(new BasicNameValuePair("sParametro", "consultarPlantillasLinPrices"));
                        params4.add(new BasicNameValuePair("sProductoId", json2.getString("Producto_Id").trim()));
                        String resultServer4 = getHttpPost(url4, params4);
                        System.out.println(resultServer4);

                        try {

                            JSONArray jArray4 = new JSONArray(resultServer4);
                            prices = new String[11];
                            for (int k = 0; k < jArray4.length(); k++) {
                                JSONObject json = jArray4.getJSONObject(k);
                                prices[k] = ("Lista..");
                                if (json.getString("precio_1").trim().equals("null")){
                                    prices[k + 1] = "0";
                                }else{
                                    prices[k + 1] = (json.getString("precio_1").trim());
                                }
                                if (json.getString("precio_2").trim().equals("null")){
                                    prices[k + 2] = "0";
                                }else{
                                    prices[k + 2] = (json.getString("precio_2").trim());
                                }
                                if (json.getString("precio_3").trim().equals("null")){
                                    prices[k + 3] = "0";
                                }else{
                                    prices[k + 3] = (json.getString("precio_3").trim());
                                }
                                if (json.getString("precio_4").trim().equals("null")){
                                    prices[k + 4] = "0";
                                }else{
                                    prices[k + 4] = (json.getString("precio_4").trim());
                                }
                                if (json.getString("precio_5").trim().equals("null")){
                                    prices[k + 5] = "0";
                                }else{
                                    prices[k + 5] = (json.getString("precio_5").trim());
                                }
                                if (json.getString("precio_6").trim().equals("null")){
                                    prices[k + 6] = "0";
                                }else{
                                    prices[k + 6] = (json.getString("precio_6").trim());
                                }
                                if (json.getString("precio_7").trim().equals("null")){
                                    prices[k + 7] = "0";
                                }else{
                                    prices[k + 7] = (json.getString("precio_7").trim());
                                }
                                if (json.getString("precio_8").trim().equals("null")){
                                    prices[k + 8] = "0";
                                }else{
                                    prices[k + 8] = (json.getString("precio_8").trim());
                                }
                                if (json.getString("precio_9").trim().equals("null")){
                                    prices[k + 9] = "0";
                                }else{
                                    prices[k + 9] = (json.getString("precio_9").trim());
                                }
                                if (json.getString("precio_10").trim().equals("null")){
                                    prices[k + 10] = "0";
                                }else{
                                    prices[k + 10] = (json.getString("precio_10").trim());
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        country = new ItemCheck(json2.getString("Producto_Id").trim(), json2.getString("descripcion").trim(), true, json2.getString("Cantidad").trim(),
                                json2.getString("precio").trim(),String.valueOf(Integer.parseInt(json2.getString("Cantidad_saldo").trim()) + Integer.parseInt(json2.getString("Cantidad_entrada").trim())
                                - Integer.parseInt(json2.getString("Cantidad_salida").trim())),
                                prices);
                        countryList.add(country);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            //create an ArrayAdaptar from the String Array

            ListView listView = (ListView) findViewById(R.id.listChequeo);
            // Assign adapter to ListView

            myListAdapter = new MyListAdapter();
            listView.setAdapter(myListAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    ItemCheck country = (ItemCheck) parent.getItemAtPosition(position);

                }
            });


        }
    }

        private class MyListAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                if (countryList != null && countryList.size() != 0) {
                    return countryList.size();
                }
                return 0;
            }

            @Override
            public Object getItem(int position) {
                // TODO Auto-generated method stub
                return countryList.get(position);
            }

            @Override
            public long getItemId(int position) {
                // TODO Auto-generated method stub
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {


                //ViewHolder holder = null;
                final ViewHolder holder;
                if (convertView == null) {

                    holder = new ViewHolder();
                    LayoutInflater inflater = MercarPedidoDetalleNuevo.this.getLayoutInflater();
                    convertView = inflater.inflate(R.layout.item_check, null);
                    holder.cant = (EditText) convertView.findViewById(R.id.editCant);
                    holder.val = (EditText) convertView.findViewById(R.id.editValor);
                    holder.saldo = (TextView) convertView.findViewById(R.id.saldo);
                    holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                    holder.prices = (Spinner) convertView.findViewById(R.id.spinnerPrices);

                    convertView.setTag(holder);

                    if (ind_lista.equals("N")){
                        holder.val.setKeyListener(null);
                    }



                } else {

                    holder = (ViewHolder) convertView.getTag();
                }

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;

                        countryList.get(position).setSelected(cb.isChecked());
                    }
                });

                holder.name.setText(countryList.get(position).getName());
                holder.name.setChecked(countryList.get(position).isSelected());
                //holder.name.setTag(country);

                ArrayAdapter<String> adaptador =
                        new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.spinner_item, countryList.get(position).getPrices());
                adaptador.setDropDownViewResource(
                        R.layout.spinner_item_drop);
                holder.prices.setAdapter(adaptador);

                holder.prices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                        if (!adapter.getItemAtPosition(i).toString().equals("Lista..")) {
                            countryList.get(position).setVal(adapter.getItemAtPosition(i).toString());
                            holder.val.setText(adapter.getItemAtPosition(i).toString());
                        }
                        //country.setVal(adapter.getItemAtPosition(i).toString());
                        //holder.val.setText(country.getVal());
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView)
                    {

                    }
                });

                holder.index = position;

                holder.saldo.setText(countryList.get(position).getSaldo());
                holder.cant.setText(countryList.get(position).getCant());
                holder.cant.getBackground().mutate().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                holder.val.setText(countryList.get(position).getVal());
                holder.val.getBackground().mutate().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
                holder.val.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        v.setFocusable(true);
                        if (!hasFocus){
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            countryList.get(holder.index).setVal(Caption.getText().toString());
                        }
                    }
                });

                holder.cant.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus){
                            final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            countryList.get(holder.index).setCant(Caption.getText().toString());
                        }
                    }
                });

                /*holder.val.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                        countryList.get(holder.index).setVal(arg0.toString());
                    }
                });*/

                return convertView;
            }

            private class ViewHolder {
                TextView code;
                CheckBox name;
                EditText cant;
                EditText val;
                Spinner prices;
                TextView saldo;
                int index;
            }
        }


    public String getHttpPost(String url, List<NameValuePair> params) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse response = client.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Status OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public void aceptar(View v){

        ArrayList<ArrayList> codes = new ArrayList<>();

        ArrayList<String> products = new ArrayList<>();



        for(int j=0;j<countryList.size();j++){
            ItemCheck country = countryList.get(j);
            products = new ArrayList<>();
            products.add(country.getCode());
            products.add(country.getCant());
            products.add(country.getVal());
            products.add(String.valueOf(country.getListaPrice()));
            if(country.isSelected()){
                codes.add(products);
            }
        }

        Intent intent = new Intent();
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",codes);
        intent.putExtra("codes",args);
        setResult(1, intent);
        finish();

    }

    public void cancelar(View v){
        finish();
    }
}
