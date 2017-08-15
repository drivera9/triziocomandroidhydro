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

public class OT extends AppCompatActivity {
    String dirIP = "";
    String nombreTabla = "";
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
    ArrayList<String> arrayPlacas = new ArrayList<>();
    ProgressDialog mProgressDialog;
    String ip = "";
    String conexion = "";
    String cod_placa = "";
    String Id = "";
    ArrayList<Lista_entrada_panel> placas = new ArrayList();
    ArrayList ids = new ArrayList();
    ArrayList<String> atributos = new ArrayList();
    ArrayList<String> autoNextProcesos = new ArrayList<>();
    ArrayList<String> valores = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ot);
        Bundle bundle = this.getIntent().getExtras();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("MOVIMIENTOS");

        Progress = findViewById(R.id.progressbar);
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        sucursal = bundle.getString("sucursal");
        procesos = bundle.getStringArrayList("procesos");
        nomProceso = bundle.getStringArrayList("nomProcesos");
        autoNextProcesos = bundle.getStringArrayList("autoNextProcesos");
        System.out.println("sucursal ------------>" + sucursal);
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");

        procesoFinal = "";

        placas = new ArrayList<Lista_entrada_panel>();
        arrayPlacas = new ArrayList<>();

        mProgressDialog= new ProgressDialog(OT.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Consultando datos...");

        //new ActualizarLista().execute("");

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

    class ActualizarLista extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute(){
            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... f_url) {

            ArrayList<String> descripcion = new ArrayList<>();
            ArrayList<String> separador = new ArrayList<>();
            int contador = 0;

            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sVista", "OT"));
            params.add(new BasicNameValuePair("sParametro", "vista"));


            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);
            try {

                JSONArray jArray = new JSONArray(resultServer);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    descripcion.add(json.getString("cod_atributo").trim());
                    separador.add(json.getString("ind_separacion").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            procesoFinal = "";

            System.out.println("DESCRIPCIONNNN " + descripcion);

            placas = new ArrayList<Lista_entrada_panel>();
            arrayPlacas = new ArrayList<>();

            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sCodUsuario", user));
            params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
                params1.add(new BasicNameValuePair("sParametro", "spinnerPlaca"));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1);
            String desc = "";

            try {
                JSONArray jArray = new JSONArray(resultServer1);
                ArrayList<String> arrayAtributos = new ArrayList<String>();
                ArrayList<String> arrayValores= new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    desc = "";
                    for(int j = 0;j<descripcion.size();j++){
                        desc += json.getString(descripcion.get(j)).trim() + separador.get(j).trim();
                    }
                    placas.add(new Lista_entrada_panel(R.drawable.bike, desc.substring(0,desc.length()-1) ,"", Integer.parseInt(json.getString("Id").trim())));
                    arrayPlacas.add(json.getString("cod_placa").trim());
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }


            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            ListView listaPlacas = (ListView) findViewById(R.id.listaPlacas);
            listaPlacas.setAdapter(new Lista_adaptador_panel(getApplicationContext(), R.layout.entrada_movimientos, placas){
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

            mProgressDialog.dismiss();

            listaPlacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {


                    Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);
                    mProgressDialog= new ProgressDialog(OT.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sId", String.valueOf(elegido.get_proceso())));
                    params.add(new BasicNameValuePair("sParametro", "nombreTercero"));


                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);
                    String nombreTercero = "";
                    try {

                        JSONArray jArray = new JSONArray(resultServer);
                        ArrayList<String> array = new ArrayList<String>();
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            nombreTercero = json.getString("nombres").trim();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ProcesosPanel.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("nombreTercero", nombreTercero);
                    bundle.putString("conexion", conexion);
                    bundle.putStringArrayList("procesos", procesos);
                    bundle.putString("placa", arrayPlacas.get(posicion));
                    bundle.putString("id", String.valueOf(elegido.get_proceso()));
                    bundle.putStringArrayList("nomProcesos", nomProceso);
                    bundle.putStringArrayList("autoNextProcesos", autoNextProcesos);
                    bundle.putString("ip", ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    mProgressDialog.dismiss();


                }
            });


        }
    }

    @Override
    public void onResume(){
        super.onResume();

        new ActualizarLista().execute("");


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
