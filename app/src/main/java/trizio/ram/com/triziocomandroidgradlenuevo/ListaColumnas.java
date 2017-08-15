package trizio.ram.com.triziocomandroidgradlenuevo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class ListaColumnas extends AppCompatActivity {
    String ip = "";
    String tabla = "";
    MyCustomAdapter dataAdapter = null;
    ArrayList<Country> countryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_columnas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ip = getIntent().getExtras().getString("ip");
        tabla = getIntent().getExtras().getString("tabla");
    }

    public static String getHttpPost(String url, List<NameValuePair> params) {
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

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
        ListView lista = (ListView) findViewById(R.id.listaColumnas);

        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sTabla", tabla));
        params1.add(new BasicNameValuePair("sParametro", "columnas"));

        String resultServer1 = getHttpPost(url1, params1);
        System.out.println(resultServer1);

        ArrayList<String> vistas = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(resultServer1);
            final ArrayList<Integer> array = new ArrayList<Integer>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                vistas.add(json.getString("column_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        countryList = new ArrayList<>();

        for(int i = 0;i<vistas.size();i++){
            Country country = new Country("0",vistas.get(i).trim(),false);
            countryList.add(country);
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(ListaColumnas.this,
                R.layout.country_info, countryList);
        ListView listView = (ListView) findViewById(R.id.listaColumnas);
        // Assign adapter to ListView
        listView.setAdapter((ListAdapter) dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {


            }
        });

    }

    public void continuar(View v){

        ArrayList<String> columnasWhere = new ArrayList();
        ArrayList<String> columnas = new ArrayList<>();
        for(int i = 0;i<countryList.size();i++){
            if(countryList.get(i).isSelected()) {
                columnasWhere.add(countryList.get(i).getName());
            }
            columnas.add(countryList.get(i).getName());
        }



        Intent i = new Intent(ListaColumnas.this,GeneradorQuery.class);
        i.putExtra("ip",ip);
        i.putStringArrayListExtra("columnasWhere",columnasWhere);
        i.putStringArrayListExtra("columnas",columnas);
        i.putExtra("tabla",tabla);
        startActivity(i);
    }

    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);

                convertView.setTag(holder);

                MyCustomAdapter.ViewHolder finalHolder = holder;

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();

                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }



            Country country = countryList.get(position);
            holder.code.setText(country.getName());
            holder.name.setText("");
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            if (holder.name.isChecked()) {
                holder.code.setTextColor(Color.RED);
            }else{
                holder.code.setTextColor(Color.BLACK);
            }
            return convertView;

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
