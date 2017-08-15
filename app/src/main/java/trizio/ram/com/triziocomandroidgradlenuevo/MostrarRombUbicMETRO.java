package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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


public class MostrarRombUbicMETRO extends Activity {
    String cod_empresa = "";
    String cod_sucursal = "";
    String[] procesos;
    String parametro = "";
    String ip = "";
    String nit = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_romb_ubic);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        final GridView gridview = (GridView) findViewById(R.id.gridView4);
        Bundle bundle = this.getIntent().getExtras();
        parametro = bundle.getString("parametro");
        ip = bundle.getString("ip");
        nit = bundle.getString("nit");
        System.out.println("IP---" + ip);
        String url = "";

        if (parametro.equals("rombo")) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("sParametro", "romboDisponible"));
            url = "http://" + ip + "/consultarGeneralCotHydro.php";


            String resultServer = getHttpPost(url, params);
            System.out.println("---------------------------------");
            System.out.println(resultServer);
            try {
                JSONArray jArray = new JSONArray(resultServer);
                ArrayList<String> array = new ArrayList<String>();
                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add((json.getString("rombo")));
                }
                procesos = new String[array.size()];
                for (int i = 0; i < array.size(); i++) {
                    procesos[i] = array.get(i);
                }
                gridview.setAdapter(new CustomGridViewAdapter(getApplicationContext(), procesos, "gri"));// con setAdapter se llena

                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    public void onItemClick(AdapterView<?> parent, View v,
                                            int position, long id) {


                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result", procesos[position]);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();


                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            if ( parametro.equals("cot")){
                List<NameValuePair> params = new ArrayList<NameValuePair>();


                params.add(new BasicNameValuePair("sCodEmpresa", "METRO"));
                params.add(new BasicNameValuePair("sCodSucursal", "10"));
                params.add(new BasicNameValuePair("sNit", nit));
                params.add(new BasicNameValuePair("sParametro", "COT"));
                url = "http://" + ip + "/consultarRecursoHydro.php";


                String resultServer = getHttpPost(url, params);
                System.out.println("---------------------------------");
                System.out.println(resultServer);
                try {
                    JSONArray jArray = new JSONArray(resultServer);
                    ArrayList<String> array = new ArrayList<String>();
                    //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array.add((json.getString("numero")));
                    }
                    procesos = new String[array.size()];
                    for (int i = 0; i < array.size(); i++) {
                        procesos[i] = array.get(i);
                    }
                    gridview.setAdapter(new CustomGridViewAdapter(getApplicationContext(), procesos, "gri"));// con setAdapter se llena

                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {


                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("result_cot", procesos[position]);
                            setResult(2, returnIntent);
                            finish();


                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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




    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.setData(Uri.parse("regresar"));
        setResult(RESULT_OK, data);
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
