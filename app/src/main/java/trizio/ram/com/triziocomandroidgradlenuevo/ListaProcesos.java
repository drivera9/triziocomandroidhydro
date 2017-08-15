package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
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

public class ListaProcesos extends AppCompatActivity {
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages = 2;
    String nombreProceso = "";
    String hayAtributos = "";
    boolean hayDetalle = false;
    String esAutonext = "";
    String nomproc = "";

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
    ArrayList<String> autoNextProcesos = new ArrayList<>();
    ProgressDialog mProgressDialog;
    String ip = "";
    String conexion = "";
    String placa = "";
    String Id = "";
    ArrayList<String> atributos = new ArrayList();
    ArrayList<String> valores = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_procesos);
        Bundle bundle = this.getIntent().getExtras();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("VER ATRIBUTOS");


        layout = (LinearLayout) findViewById(R.id.LayoutRelativeProcesos);
        Progress = findViewById(R.id.progressbar);
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        Id = bundle.getString("id");
        sucursal = bundle.getString("sucursal");
        placa = bundle.getString("placa");
        procesos = bundle.getStringArrayList("procesos");
        nomProceso = bundle.getStringArrayList("nomProcesos");
        autoNextProcesos = bundle.getStringArrayList("autoNextProcesos");
        proceso = bundle.getString("cod_proceso");

        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");

        ArrayList<Lista_entrada_panel> datos = new ArrayList<Lista_entrada_panel>();
        ListView lista = (ListView) findViewById(R.id.listView2);

        procesos = new ArrayList<>();
        nomProceso = new ArrayList<>();
        autoNextProcesos = new ArrayList<>();
        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sUser", user));
        params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
        params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
        params1.add(new BasicNameValuePair("sParametro", "procesosGeneralJoin"));

        String resultServer1 = getHttpPost(url1, params1);
        System.out.println(resultServer1 + " COD PROCESO ");

        try {
            JSONArray jArray = new JSONArray(resultServer1);
            final ArrayList<Integer> array = new ArrayList<Integer>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                procesos.add(json.getString("cod_proceso"));
                nomProceso.add(json.getString("nom_proceso"));
                if (json.getString("cod_proceso").trim().equals(json.getString("cod_proceso_cola").trim()) &&
                        json.getString("cod_proceso_cabeza").trim().equals(json.getString("cod_proceso").trim())){
                    autoNextProcesos.add("true");
                }else{
                    autoNextProcesos.add("false");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


        ArrayList<String> estaId = new ArrayList();

        String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sId", Id));
        params.add(new BasicNameValuePair("sParametro", "validarEstadoJoin"));


        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);
        try {

            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int j = 0; j < jArray.length(); j++) {
                JSONObject json = jArray.getJSONObject(j);
                estaId.add(json.getString("cod_proceso"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0;i<procesos.size();i++){


            if(!procesos.get(i).trim().equals("200")) {
                if (!procesos.get(i).trim().equals("201")) {
                    if (!procesos.get(i).trim().equals("0")) {
                        if (!procesos.get(i).trim().equals("14")) {
                            if (!procesos.get(i).trim().equals("55")) {
                                if (!procesos.get(i).trim().equals("56")) {
                                    if (!procesos.get(i).trim().equals("57")) {
                                        if (estaId.size() > 0) {

                                            try {
                                                if (autoNextProcesos.get(i).trim().equals("true")) {
                                                    datos.add(new Lista_entrada_panel(R.drawable.close, "   " + nomProceso.get(i).trim(), "detalle", Integer.parseInt(procesos.get(i).trim())));
                                                    continue;
                                                } else {
                                                    datos.add(new Lista_entrada_panel(R.drawable.close, "   " + nomProceso.get(i).trim(), "", Integer.parseInt(procesos.get(i).trim())));
                                                    continue;
                                                }
                                                //}
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            try {
                                                if (autoNextProcesos.get(i).trim().equals("true")) {
                                                    datos.add(new Lista_entrada_panel(R.drawable.close, "   " + nomProceso.get(i).trim(), "detalle", Integer.parseInt(procesos.get(i).trim())));
                                                    continue;
                                                } else {
                                                    datos.add(new Lista_entrada_panel(R.drawable.close, "   " + nomProceso.get(i).trim(), "", Integer.parseInt(procesos.get(i).trim())));
                                                    continue;
                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        for (int i = 0;i< estaId.size();i++){
            for (int j = 0;j<procesos.size();j++){
                if (String.valueOf(datos.get(j).get_proceso()).equals(estaId.get(i))){
                    datos.get(j).set_idImagen(R.drawable.validacion);
                }
            }
        }

        lista.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada, datos) {
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

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);


                if (proceso.equals("14")){
                    mProgressDialog = new ProgressDialog(ListaProcesos.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    procesoFinal = String.valueOf(elegido.get_proceso());

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ListDCaptura.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putString("ip", ip);
                    bundle.putString("id", Id);
                    bundle.putString("proceso", String.valueOf(elegido.get_proceso()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    mProgressDialog.dismiss();
                }else {

                    mProgressDialog = new ProgressDialog(ListaProcesos.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    procesoFinal = String.valueOf(elegido.get_proceso());

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) CrearAtributo.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putString("ip", ip);
                    bundle.putString("id", Id);
                    bundle.putString("proceso", procesoFinal);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    mProgressDialog.dismiss();
                }
            }
        });
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

    class ConsultarGeneral extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {


            columnas = new ArrayList<>();
            ArrayDatos = new ArrayList<>();


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
            params.add(new BasicNameValuePair("sParametro", "consultarProceso"));

            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);
            ArrayList<String> datos = new ArrayList<>();
            try {
                JSONArray jArray = new JSONArray(resultServer);
                generalAux = new String[jArray.length()][21];

                int contador = 0;
                int j = 0;
                longitud = jArray.length();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    generalAux[i][j] = json.getString("cod_empresa");
                    generalAux[i][j + 1] = json.getString("cod_sucursal");
                    generalAux[i][j + 2] = json.getString("cod_usuario");
                    generalAux[i][j + 3] = json.getString("cod_proceso");
                    generalAux[i][j + 4] = json.getString("cod_atributo");
                    generalAux[i][j + 5] = json.getString("cod_valor");
                    generalAux[i][j + 6] = json.getString("num_secuencia");
                    generalAux[i][j + 7] = json.getString("num_orden");
                    generalAux[i][j + 8] = json.getString("ind_requerido");
                    generalAux[i][j + 9] = json.getString("ind_tipo");
                    generalAux[i][j + 10] = json.getString("val_minimo");
                    generalAux[i][j + 11] = json.getString("val_maximo");
                    generalAux[i][j + 12] = json.getString("num_longitud");
                    generalAux[i][j + 13] = json.getString("nom_ruta");
                    generalAux[i][j + 14] = json.getString("val_defecto");
                    generalAux[i][j + 15] = json.getString("cod_proceso_padre");
                    generalAux[i][j + 16] = json.getString("ind_autonext");
                    generalAux[i][j + 17] = json.getString("ind_estado");
                    if (generalAux[i][j + 17].equals("I")) {
                        contador++;
                        columnas.add(json.getString("nom_columna"));
                        ArrayDatos.add(json.getString("cod_valor"));
                        //nombreTabla = json.getString("nom_tabla");
                    }
                    generalAux[i][j + 18] = json.getString("nom_columna");
                    generalAux[i][j + 19] = json.getString("nom_tabla");
                    generalAux[i][j + 20] = json.getString("idx_foto");

                    j = 0;
                }

                for (int i = 0; i < generalAux.length; i++) {
                    for (j = 0; j < generalAux[i].length; j++) {
                        System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                    }
                }

                if (contador == 0) {
                    general = new String[generalAux.length][21];

                    for (int i = 0; i < generalAux.length; i++) {
                        for (j = 0; j < generalAux[i].length; j++) {
                            System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                            if (generalAux[i][j].equals("null")) {
                                generalAux[i][j] = "";
                            }

                            general[i][j] = generalAux[i][j];

                        }


                        if (generalAux[i][17].equals("I") && generalAux[i][15].equals(generalAux[i][3])) {
                            //columnas.add(generalAux[i][18]);
                            //ArrayDatos.add(generalAux[i][5]);
                        }
                    }
                } else {
                    general = new String[generalAux.length - contador][21];
                    System.out.println("LONGITUD ->" + (generalAux.length - contador));

                    int k = 0;
                    for (int i = 0; i < general.length; i++) {
                        for (j = 0; j < generalAux[i].length; j++) {
                            if (generalAux[k][j].equals("null")) {
                                generalAux[k][j] = "";
                            }

                            if (!generalAux[k][17].equals("I")) {
                                general[i][j] = generalAux[k][j];
                            } else {
                                if (generalAux[k][15].equals(generalAux[k][3])) {
                                    //columnas.add(generalAux[k][18].trim());
                                    //ArrayDatos.add(generalAux[k][5].trim());
                                }
                                k++;
                                general[i][j] = generalAux[k][j];
                            }
                        }
                        k++;
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            for(int i = 0;i<columnas.size();i++){
                System.out.println("COLUMNASS " + columnas.get(i));
            }
            ArrayList<String> atributos = new ArrayList();
            ArrayList<String> valores = new ArrayList();


            Intent intent = new Intent((Context) ListaProcesos.this, (Class) Recepcion.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("nombreTabla", "");
            bundle2.putString("codAtributo", "");
            bundle2.putString("nuevoRegistro", "NO");
            bundle2.putString("esAutonext", "false");
            bundle2.putString("user", user);
            bundle2.putString("pass", pass);
            bundle2.putString("sucursal", sucursal);
            bundle2.putString("empresa", empresa);
            bundle2.putString("conexion", conexion);
            bundle2.putString("sucursal", sucursal);
            bundle2.putSerializable("general", general);
            bundle2.putSerializable("generalAux", generalAux);
            bundle2.putStringArrayList("columnas", columnas);
            bundle2.putStringArrayList("datos", ArrayDatos);
            bundle2.putString("rombo", num_rombo);
            bundle2.putSerializable("general", general);
            bundle2.putString("ip", ip);
            bundle2.putString("titulo", titulo + "/Rec");
            bundle2.putString("proceso", procesoFinal);
            bundle2.putStringArrayList("atributos", atributos);
            bundle2.putStringArrayList("valores", valores);
            intent.putExtras(bundle2);
            startActivity(intent);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
