package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class MercarPedido extends Activity {
    MyCustomAdapter dataAdapter = null;
    ProgressDialog mProgressDialog;
    String ip = "";
    String proyecto = "";
    String empresa = "";
    int request_code = 1;
    String cod_empresa = "";
    String cod_sucursal = "";
    String ind_contrato = "";
    String ind_movimiento = "";
    String ind_lista = "";
    ArrayList<ArrayList> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercar_pedido);

        ip = getIntent().getExtras().getString("ip");
        empresa = getIntent().getExtras().getString("empresa");
        proyecto = getIntent().getExtras().getString("proyecto");
        cod_empresa = getIntent().getExtras().getString("cod_empresa");
        cod_sucursal = getIntent().getExtras().getString("cod_sucursal");

        mProgressDialog= new ProgressDialog(MercarPedido.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Consultando datos...");

        new ConsultarDatos().execute("");

    }

    class ConsultarDatos extends AsyncTask<String, String, String> {

        ArrayList<ItemCheck> countryList = new ArrayList<ItemCheck>();
        ItemCheck country;

        @Override
        protected void onPreExecute(){
            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... f_url) {


            String url3 = "http://" + ip + "/consultarGeneralHydro.php";

            List<NameValuePair> params3 = new ArrayList<NameValuePair>();
            params3.add(new BasicNameValuePair("sParametro", "consultarConfig"));
            params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
            params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
            String resultServer3 = getHttpPost(url3, params3);
            System.out.println(resultServer3);

            try {

                JSONArray jArray2 = new JSONArray(resultServer3);

                for (int j = 0; j < jArray2.length(); j++) {
                    JSONObject json2 = jArray2.getJSONObject(j);
                    ind_contrato = json2.getString("ind_contrato_solamente").trim();
                    ind_movimiento = json2.getString("ind_contrato_movimiento").trim();
                    ind_lista = json2.getString("ind_lista_precios").trim();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (ind_contrato.equals("S") && ind_movimiento.equals("S")){
                url3 = "http://" + ip + "/consultarGeneralHydro.php";

                params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sParametro", "consultarPlantillas"));
                params3.add(new BasicNameValuePair("sIdEmpresa", empresa));
                params3.add(new BasicNameValuePair("sIdProyecto", proyecto));
                params3.add(new BasicNameValuePair("sWhere", "tra_plantillas_tipos.tipo = 'C' OR tra_plantillas_tipos.tipo = 'M'"));
                resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);

                try {

                    JSONArray jArray2 = new JSONArray(resultServer3);

                    for (int j = 0; j < jArray2.length(); j++) {
                        JSONObject json2 = jArray2.getJSONObject(j);
                        final String[] datos =
                                new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
                        country = new ItemCheck(json2.getString("id").trim(),json2.getString("nombre").trim(),false,"0","0","0",datos);
                        countryList.add(country);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                url3 = "http://" + ip + "/consultarGeneralHydro.php";

                params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sParametro", "consultarPlantillasNueva"));
                resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);

                try {

                    JSONArray jArray2 = new JSONArray(resultServer3);

                    for (int j = 0; j < jArray2.length(); j++) {
                        JSONObject json2 = jArray2.getJSONObject(j);
                        final String[] datos =
                                new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
                        country = new ItemCheck(json2.getString("id").trim(),json2.getString("nombre").trim(),false,"0","0","0",datos);
                        countryList.add(country);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                if (ind_contrato.equals("N") && ind_movimiento.equals("S")){

                    url3 = "http://" + ip + "/consultarGeneralHydro.php";

                    params3 = new ArrayList<NameValuePair>();
                    params3.add(new BasicNameValuePair("sParametro", "consultarPlantillas"));
                    params3.add(new BasicNameValuePair("sIdEmpresa", empresa));
                    params3.add(new BasicNameValuePair("sIdProyecto", proyecto));
                    params3.add(new BasicNameValuePair("sWhere", "tra_plantillas_tipos.tipo = 'M'"));
                    resultServer3 = getHttpPost(url3, params3);
                    System.out.println(resultServer3);

                    try {

                        JSONArray jArray2 = new JSONArray(resultServer3);

                        for (int j = 0; j < jArray2.length(); j++) {
                            JSONObject json2 = jArray2.getJSONObject(j);
                            final String[] datos =
                                    new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
                            country = new ItemCheck(json2.getString("id").trim(),json2.getString("nombre").trim(),false,"0","0","0",datos);
                            countryList.add(country);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    url3 = "http://" + ip + "/consultarGeneralHydro.php";

                    params3 = new ArrayList<NameValuePair>();
                    params3.add(new BasicNameValuePair("sParametro", "consultarPlantillasNueva"));
                    resultServer3 = getHttpPost(url3, params3);
                    System.out.println(resultServer3);

                    try {

                        JSONArray jArray2 = new JSONArray(resultServer3);

                        for (int j = 0; j < jArray2.length(); j++) {
                            JSONObject json2 = jArray2.getJSONObject(j);
                            final String[] datos =
                                    new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
                            country = new ItemCheck(json2.getString("id").trim(),json2.getString("nombre").trim(),false,"0","0","0",datos);
                            countryList.add(country);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    if (ind_contrato.equals("S") && ind_movimiento.equals("N")){
                        url3 = "http://" + ip + "/consultarGeneralHydro.php";

                        params3 = new ArrayList<NameValuePair>();
                        params3.add(new BasicNameValuePair("sParametro", "consultarPlantillas"));
                        params3.add(new BasicNameValuePair("sIdEmpresa", empresa));
                        params3.add(new BasicNameValuePair("sIdProyecto", proyecto));
                        params3.add(new BasicNameValuePair("sWhere", "tra_plantillas_tipos.tipo = 'C' "));
                        resultServer3 = getHttpPost(url3, params3);
                        System.out.println(resultServer3);

                        try {

                            JSONArray jArray2 = new JSONArray(resultServer3);

                            for (int j = 0; j < jArray2.length(); j++) {
                                JSONObject json2 = jArray2.getJSONObject(j);
                                final String[] datos =
                                        new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"};
                                country = new ItemCheck(json2.getString("id").trim(),json2.getString("nombre").trim(),false,"0","0","0",datos);
                                countryList.add(country);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }



            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            mProgressDialog.dismiss();
            //create an ArrayAdaptar from the String Array
            dataAdapter = new MyCustomAdapter(getApplicationContext(),
                    R.layout.item_check, countryList);
            ListView listView = (ListView) findViewById(R.id.listChequeo);
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    ItemCheck country = (ItemCheck) parent.getItemAtPosition(position);

                }
            });

            checkButtonClick();
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



    private class MyCustomAdapter extends ArrayAdapter<ItemCheck> {

        private ArrayList<ItemCheck> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<ItemCheck> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<ItemCheck>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
            EditText cant;
            EditText val;
            TextView saldo;
            TextView textSaldo;
            TextView textCant;
            TextView textValor;
            Spinner prices;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.item_check, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.cant = (EditText) convertView.findViewById(R.id.editCant);
                holder.val = (EditText) convertView.findViewById(R.id.editValor);
                holder.saldo = (TextView) convertView.findViewById(R.id.saldo);
                holder.textSaldo = (TextView) convertView.findViewById(R.id.textViewSaldo);
                holder.textCant = (TextView) convertView.findViewById(R.id.textViewCantidad);
                holder.textValor = (TextView) convertView.findViewById(R.id.textViewValor);
                holder.prices = (Spinner) convertView.findViewById(R.id.spinnerPrices);

                holder.cant.setVisibility(View.GONE);
                holder.val.setVisibility(View.GONE);
                holder.prices.setVisibility(View.GONE);
                holder.textSaldo.setVisibility(View.GONE);
                holder.textCant.setVisibility(View.GONE);
                holder.textValor.setVisibility(View.GONE);
                holder.saldo.setVisibility(View.GONE);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        ItemCheck country = (ItemCheck) cb.getTag();

                        country.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ItemCheck country = countryList.get(position);
            holder.code.setText(" (" + country.getCode() + ")");
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<ItemCheck> countryList = dataAdapter.countryList;
                for(int i=0;i<countryList.size();i++){
                    ItemCheck country = countryList.get(i);
                    if(country.isSelected()){
                        responseText.append("\n" + country.getName());
                    }
                }



            }
        });

    }

    public void aceptar(View v){

        Intent i = new Intent(MercarPedido.this,MercarPedidoDetalleNuevo.class);
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<ItemCheck> countryList = dataAdapter.countryList;
        for(int j=0;j<countryList.size();j++){
            ItemCheck country = countryList.get(j);
            if(country.isSelected()){
                codes.add(country.getCode());
            }
        }

        i.putStringArrayListExtra("codes",codes);
        i.putExtra("ip",ip);
        i.putExtra("ind_movimiento",ind_movimiento);
        i.putExtra("ind_lista",ind_lista);
        startActivityForResult(i,request_code);
    }

    public void cancelar(View v){
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {

            if (requestCode == 1) {
                Intent intent = new Intent();
                Bundle args = data.getBundleExtra("codes");
                //products = (ArrayList<ArrayList>) args.getSerializable("codes");
                intent.putExtra("codes",args);
                setResult(2, intent);
                finish();


            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

