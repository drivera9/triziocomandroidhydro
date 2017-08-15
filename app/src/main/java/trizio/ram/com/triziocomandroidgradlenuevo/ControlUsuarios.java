package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class ControlUsuarios extends AppCompatActivity {
    String user = "";
    String pass = "";
    String proceso = "";
    String sucursal;
    String empresa;
    String ip = "";
    String conexion = "";
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog = new ProgressDialog(ControlUsuarios.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Espere...");


        Bundle bundle = this.getIntent().getExtras();
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        sucursal = bundle.getString("sucursal");
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");

        new ConsultarDatos().execute("");
    }

    class ConsultarDatos extends AsyncTask<String, String, String> {
        ListView ajustesProcesos;
        ArrayList<Lista_entrada_panel> arrayAjustesProcesos;
        ArrayList<String> datos;
        protected void onPreExecute(){
            mProgressDialog.show();

            ajustesProcesos = (ListView) findViewById(R.id.listaAjustes);

            arrayAjustesProcesos = new ArrayList<>();

            datos = new ArrayList<>();
        }
        @Override
        protected String doInBackground(String... f_url) {
            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sUser", user));
            params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params1.add(new BasicNameValuePair("sParametro", "procesosJoin"));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1 + " COD PROCESO ");

            try {
                JSONArray jArray = new JSONArray(resultServer1);
                final ArrayList<Integer> array = new ArrayList<Integer>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.report,json.getString("nom_proceso").trim(),"",
                            Integer.parseInt(json.getString("cod_proceso").trim())));



                }
            } catch (JSONException e) {
                e.printStackTrace();

            }



            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            ajustesProcesos.setAdapter(new Lista_adaptador_panel(getApplicationContext(), R.layout.entrada, arrayAjustesProcesos) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                        if (texto_superior_entrada != null)
                            texto_superior_entrada.setText(((Lista_entrada_panel) entrada).get_textoEncima());

                        TextView texto_debajo_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                        if (texto_debajo_entrada != null)
                            texto_debajo_entrada.setText(((Lista_entrada_panel) entrada).get_textoDebajo());

                        ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                        if (imagen_entrada != null)
                            imagen_entrada.setImageResource(((Lista_entrada_panel) entrada).get_idImagen());
                    }
                }
            });

            ajustesProcesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ActualizarUsuarios.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putString("ip", ip);
                    bundle.putString("proceso", String.valueOf(elegido.get_proceso()));
                    bundle.putString("nomProceso", String.valueOf(elegido.get_textoEncima()));
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            });

            mProgressDialog.dismiss();
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
