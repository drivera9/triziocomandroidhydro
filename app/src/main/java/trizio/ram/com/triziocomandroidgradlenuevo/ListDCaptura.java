package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class ListDCaptura extends AppCompatActivity {
    String atributo = "";
    boolean esta = false;
    String dirIP = "";
    String procesoFinal = "";
    ArrayList<String> columnas;
    ArrayList<String> ArrayDatos;
    int longitud = 0;
    String [][] generalAux;
    String [][] general;
    String titulo = "";
    String user = "";
    String pass = "";
    String proceso = "";
    String sucursal;
    String empresa;
    String num_rombo = "";
    private LinearLayout layout;
    private View Progress;
    ArrayList<String> procesos = new ArrayList<>();
    ArrayList<String> nomProceso = new ArrayList<>();
    ProgressDialog mProgressDialog;
    String ip = "";
    String conexion = "";
    String placa = "";
    String Id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dcaptura);
        Bundle bundle = this.getIntent().getExtras();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        procesoFinal = getIntent().getExtras().getString("proceso");
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        sucursal = bundle.getString("sucursal");
        Id = getIntent().getExtras().getString("id");
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");
        proceso = bundle.getString("proceso");

    }

    class ConsultarGeneral extends AsyncTask<String, String, String> {

        ListView atributos = (ListView) findViewById(R.id.listaAtributos);

        ArrayList<Lista_entrada_panel> arrayAjustesAtributos = new ArrayList<>();

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... f_url) {

            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sCodUser", pass));
            params.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params.add(new BasicNameValuePair("sIndEstado", "A"));
            params.add(new BasicNameValuePair("sCodProceso", proceso));
            params.add(new BasicNameValuePair("sParametro", "consultarAtributo"));


            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);
            try {

                JSONArray jArray = new JSONArray(resultServer);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo"));
                    arrayAjustesAtributos.add(new Lista_entrada_panel(R.drawable.recepcion, json.getString("cod_atributo").trim() ,
                            "" ,Integer.parseInt(json.getString("num_orden").trim())));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            atributos.setAdapter(new Lista_adaptador_panel(getApplicationContext(), R.layout.entrada, arrayAjustesAtributos){
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                        TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                        if (texto_superior_entrada != null)
                            texto_superior_entrada.setText(((Lista_entrada_panel) entrada).get_textoEncima());

                        if (texto_inferior_entrada != null)
                            texto_inferior_entrada.setText(((Lista_entrada_panel) entrada).get_textoDebajo());


                        ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                        if (imagen_entrada != null)
                            imagen_entrada.setImageResource(((Lista_entrada_panel) entrada).get_idImagen());
                    }
                }
            });

            atributos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {

                    Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);

                    atributo = elegido.get_textoEncima();

                    new ConsultarDetalles().execute("");
                }
            });
        }
    }

    class ConsultarDetalles extends AsyncTask<String, String, String> {

        ArrayList<String> atributos = new ArrayList<>();
        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... f_url) {

            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sCodUser", pass));
            params.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params.add(new BasicNameValuePair("sIndEstado", "A"));
            params.add(new BasicNameValuePair("sCodProceso", procesoFinal));
            params.add(new BasicNameValuePair("sAtributo", atributo));
            params.add(new BasicNameValuePair("sParametro", "consultarProcesoUnico"));

            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);
            try {
                JSONArray jArray = new JSONArray(resultServer);
                generalAux = new String[jArray.length()][21];

                int contador = 0;
                int j = 0;
                longitud = jArray.length();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    atributos.add(json.getString("cod_atributo"));
                    atributos.add(json.getString("cod_valor"));
                    atributos.add(json.getString("num_secuencia"));
                    atributos.add(json.getString("ind_autonext"));
                    atributos.add(json.getString("ind_estado"));
                    atributos.add(json.getString("nom_tabla"));
                    atributos.add(json.getString("nom_columna"));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            Intent intent = new Intent((Context) ListDCaptura.this, (Class) ActualizarCaptura.class);
            intent.putExtra("proceso",procesoFinal);
            intent.putExtra("atributo",atributo);
            intent.putExtra("ip",ip);
            intent.putExtra("empresa",empresa);
            intent.putExtra("sucursal",sucursal);
            intent.putStringArrayListExtra("atributosArray",atributos);
            startActivity(intent);

        }
    }

    public String getHttpPost(String url,List<NameValuePair> params) {
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
    public void onResume(){
        super.onResume();

        new ConsultarGeneral().execute("");

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
