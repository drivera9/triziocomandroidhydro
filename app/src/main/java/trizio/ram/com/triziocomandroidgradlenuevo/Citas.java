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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Citas extends AppCompatActivity {
    ArrayList<String> colores = new ArrayList<>();

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
        setContentView(R.layout.activity_citas);
        Bundle bundle = this.getIntent().getExtras();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        ListView citas = (ListView)findViewById(R.id.listaOpcionecCitas);

        ArrayList<Lista_entrada_panel> arrayAjustesCitas = new ArrayList<>();

        for (int i = 0;i<procesos.size();i++){

            try {

                if (procesos.get(i).trim().equals("55")) {
                    proceso = "55";
                    arrayAjustesCitas.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }
            try {

                if (procesos.get(i).trim().equals("56")) {
                    proceso = "56";
                    arrayAjustesCitas.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }
            try {

                if (procesos.get(i).trim().equals("57")) {
                    proceso = "57";
                    arrayAjustesCitas.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }


        }

        citas.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada, arrayAjustesCitas){
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

        citas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);

                if (String.valueOf(elegido.get_proceso()).equals("55")){
                    procesoFinal = "55";
                    new ConsultarLlamada().execute("");
                }

                if (String.valueOf(elegido.get_proceso()).equals("56")){
                    procesoFinal = "56";
                    new ConsultarGeneral().execute("");
                }

                if (String.valueOf(elegido.get_proceso()).equals("57")){
                    procesoFinal = "58";

                    new ConsultarGeneral().execute("");
                }
            }
        });
    }

    class ConsultarGeneral extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {


            columnas = new ArrayList<>();
            ArrayDatos = new ArrayList<>();


        }

        @Override
        protected String doInBackground(String... f_url) {

            String url11 = "http://" + ip + "/consultarRecursoColibriHydro.php";
            List<NameValuePair> params11 = new ArrayList<NameValuePair>();


            params11.add(new BasicNameValuePair("sCodEmpresa", empresa.trim()));
            params11.add(new BasicNameValuePair("sCodSucursal", sucursal.trim()));
            params11.add(new BasicNameValuePair("sParametro", "ROMBO"));


            String resultServer11 = getHttpPost(url11, params11);
            System.out.println("---------------------------------");
            System.out.println(resultServer11);
            try {
                JSONArray jArray = new JSONArray(resultServer11);
                ArrayList<String> array = new ArrayList<String>();
                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add((json.getString("val_recurso")));
                }

                num_rombo = array.get(0).trim();
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                        nombreTabla = json.getString("nom_tabla");
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

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            Date dateI = cal.getTime();

            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 23);
            Date dateF = cal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String currentDateandTime = sdf.format(dateI);


            general[0][5] = currentDateandTime;
            generalAux[0][5] = currentDateandTime;


            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentDateandTime = sdf.format(dateF);

            general[1][5] = currentDateandTime;
            generalAux[1][5] = currentDateandTime;


            Intent intent = new Intent((Context) Citas.this, (Class) RecepcionPanel.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("nombreTabla", nombreTabla);
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

    class ConsultarCitas extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected String doInBackground(String... f_url) {


            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sUser", user));
            params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params1.add(new BasicNameValuePair("sCodProceso", "2"));
            params1.add(new BasicNameValuePair("sParametro", "citas"));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1 + " COD PROCESO ");

            procesos = new ArrayList<>();
            nomProceso = new ArrayList<>();
            colores = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(resultServer1);
                final ArrayList<Integer> array = new ArrayList<Integer>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    procesos.add(json.getString("id"));
                    nomProceso.add(json.getString("fec_inicial"));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
            params2.add(new BasicNameValuePair("sUser", user));
            params2.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params2.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params2.add(new BasicNameValuePair("sCodProceso", "3"));
            params2.add(new BasicNameValuePair("sParametro", "citas"));

            String resultServer2 = getHttpPost(url2, params2);
            System.out.println(resultServer2 + " COD PROCESO ");

            colores = new ArrayList<>();

            try {
                JSONArray jArray = new JSONArray(resultServer2);
                final ArrayList<Integer> array = new ArrayList<Integer>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    colores.add(json.getString("fec_inicial"));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            Intent intent = new Intent((Context) Citas.this, (Class) SeleccionarCitas.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("nombreTabla", nombreTabla);
            bundle2.putStringArrayList("colores", colores);
            bundle2.putStringArrayList("procesos", procesos);
            bundle2.putStringArrayList("nomProcesos", nomProceso);
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

    class ConsultarLlamada extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {


            columnas = new ArrayList<>();
            ArrayDatos = new ArrayList<>();


        }

        @Override
        protected String doInBackground(String... f_url) {

            String url11 = "http://" + ip + "/consultarRecursoColibriHydro.php";
            List<NameValuePair> params11 = new ArrayList<NameValuePair>();


            params11.add(new BasicNameValuePair("sCodEmpresa", empresa.trim()));
            params11.add(new BasicNameValuePair("sCodSucursal", sucursal.trim()));
            params11.add(new BasicNameValuePair("sParametro", "ROMBO"));


            String resultServer11 = getHttpPost(url11, params11);
            System.out.println("---------------------------------");
            System.out.println(resultServer11);
            try {
                JSONArray jArray = new JSONArray(resultServer11);
                ArrayList<String> array = new ArrayList<String>();
                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add((json.getString("val_recurso")));
                }

                num_rombo = array.get(0).trim();
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                        nombreTabla = json.getString("nom_tabla");
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


            /*Intent intent = new Intent((Context) Citas.this, (Class) RecepcionLlamada.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("nombreTabla", nombreTabla);
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
            startActivity(intent);*/

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
