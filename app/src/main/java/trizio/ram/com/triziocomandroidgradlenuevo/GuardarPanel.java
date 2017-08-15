package trizio.ram.com.triziocomandroidgradlenuevo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ScrollView;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GuardarPanel extends AppCompatActivity {
    String id = "";
    String motivo;
    String costo = "";
    String fpago = "";
    String precio = "";
    String num_grupo = "";
    String km = "";
    String esAutonext = "";
    String cod_empresa = "";
    String cod_sucursal = "";
    String cod_proceso = "";
    String cod_placa = "";
    String cod_usuario = "";
    String cod_ubicacion = "";
    String fec_proceso = "";
    String num_nit = "";
    String ind_estado = "O";
    String ip ;
    String[] arrayGrid ;
    String[] arrayNotas;
    String mecanico = "";
    ArrayList<String> columnas ;
    ArrayList<String> datos ;
    String cod_tecnico = "";
    String num_rombo;
    String numero = "";
    String bodega = "";
    String razon = "";
    private View Progress;
    private ScrollView Scroll;
    ProgressDialog mProgressDialog;
    String faltanDatos;
    String nombreTabla = "" ;
    String sql = "";
    String soat = "";
    String tecno = "";
    String promesa = "";
    String nuevoRegistro = "";
    String solicitud = "";
    String modelo = "";
    String encuentraPlaca = "";
    String encuentraNit = "";
    String descripcionModelo = "NNNN";
    String anoModelo = "";
    String marca = "";
    String eventoCita = "";
    String eventoRazon = "";
    String tipoVehiculo = "";
    String descPrecio = "";
    String notas = "";
    String email = "";
    String conexion = "";
    String proceso = "";
    ArrayList atributos;
    String fechaInicial = "";
    String fechaFinal = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_colibri);
        Button guardar = (Button) findViewById(R.id.button_guardar);
        // guardar.setVisibility(View.INVISIBLE);
        arrayGrid = new String[1];

        sql = "";

        columnas = new ArrayList<>();

        Scroll =(ScrollView) findViewById(R.id.ScrollLogin);
        Progress = findViewById(R.id.progressbar);
        Bundle bundle = this.getIntent().getExtras();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog= new ProgressDialog(GuardarPanel.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Guardando datos...");


        esAutonext = bundle.getString("esAutonext");
        id = bundle.getString("id");
        num_grupo = bundle.getString("num_grupo");
        conexion = bundle.getString("conexion");
        km = bundle.getString("km");
        encuentraNit = bundle.getString("encuentraNit");
        encuentraPlaca = bundle.getString("encuentraPlaca");
        tecno = bundle.getString("tecno");
        soat = bundle.getString("soat");
        motivo = bundle.getString("motivo");

        arrayNotas = (String[]) bundle.getSerializable("notas");
        email = bundle.getString("email");
        costo = bundle.getString("costo");
        marca = bundle.getString("marca");
        fpago = bundle.getString("fpago");
        notas = bundle.getString("notas");
        nuevoRegistro = bundle.getString("nuevoRegistro");
        descPrecio = bundle.getString("descPrecio");
        precio = bundle.getString("precio");
        eventoRazon = bundle.getString("eventoRazon");
        eventoCita = bundle.getString("eventoCita");
        descripcionModelo = bundle.getString("descripcionModelo");
        cod_empresa = bundle.getString("cod_empresa");
        solicitud = bundle.getString("solicitud");
        tipoVehiculo = bundle.getString("tipoVehiculo");
        cod_sucursal = bundle.getString("cod_sucursal");
        cod_proceso = bundle.getString("cod_proceso");
        cod_placa = bundle.getString("cod_placa");
        promesa = bundle.getString("promesa");
        cod_usuario = bundle.getString("cod_usuario");
        razon = bundle.getString("razon");
        cod_ubicacion = bundle.getString("cod_ubicacion");
        fec_proceso = bundle.getString("fec_proceso");
        proceso = fec_proceso = bundle.getString("proceso");
        num_nit = bundle.getString("num_nit");
        ind_estado = bundle.getString("ind_estado");
        ip = bundle.getString("ip");
        faltanDatos = bundle.getString("faltanDatos");
        atributos = bundle.getStringArrayList("atributos");
        columnas = bundle.getStringArrayList("columnas");
        datos = bundle.getStringArrayList("datos");
        cod_tecnico = bundle.getString("cod_tecnico");
        nombreTabla = bundle.getString("nombre_tabla");


        System.out.println("AQUI -------------------------" + nombreTabla);

        if (!proceso.trim().equals("0") && !proceso.trim().equals("56") && !proceso.trim().equals("57")){

        }else {

            columnas.add("COD_EMPRESA");
            columnas.add("COD_SUCURSAL");
            columnas.add("COD_USUARIO");

            datos.add(cod_empresa);
            datos.add(cod_sucursal);
            datos.add(cod_usuario);
        }


        for (int i = 0;i<columnas.size();i++){
            System.out.println("COLUMNA " + i +  " " + columnas.get(i));
        }

        for (int i = 0;i<datos.size();i++){
            System.out.println("DATO " + i +  " " + datos.get(i));
        }


        arrayGrid = new String [atributos.size()];
        for(int i = 0;i<atributos.size();i++){
            arrayGrid[i] = (atributos.get(i).toString());
        }


        GridView gridview = (GridView) findViewById(R.id.gridView5);// crear el
        // gridview a partir del elemento del xml gridview

        gridview.setAdapter(new CustomGridViewAdapter(getApplicationContext(), arrayGrid, "grd"));// con setAdapter se llena


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Toast para mostrar un mensaje. Escribe el nombre de tu clase
                // si no la llamaste MainActivity.


            }
        });

        for (int i = 0;i<arrayGrid.length-1;i++){
            if (arrayGrid[i].equals("")){

            }else{
                guardar.setVisibility(View.VISIBLE);
            }
        }


        fec_proceso = getCode();



        for (int i = 0;i<arrayGrid.length-1;i++){
            if (arrayGrid[i].equals("ROMBO :")){
                num_rombo = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("FECHA INICIAL :")){
                fechaInicial = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("FECHA FINAL :")){
                fechaFinal = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("OPERARIO :")){
                cod_tecnico = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("PLACA :")){
                cod_placa = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("UBICACION :")){
                cod_ubicacion = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("NIT :")){
                num_nit = arrayGrid[i+1];
            }
        }


        if (ind_estado.equals("S")){
            mProgressDialog.show();

            if (faltanDatos.equals("false")) {

                if (cod_proceso.trim().equals("1") || cod_proceso.trim().equals("99")) {
                    new GuardarMovimiento().execute("");

                }else{
                    if (cod_proceso.trim().equals("0") || cod_proceso.trim().equals("56")  ||  cod_proceso.trim().equals("58")){
                        guardarMovimiento();
                    }else {
                        if (cod_proceso.trim().equals("57")){
                            guardarMovimiento();
                        }else {
                            if (cod_proceso.trim().equals("123")) {
                                guardarMovimiento();
                            } else {

                                boolean atributo = false;
                                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                params.add(new BasicNameValuePair("sId", id));
                                params.add(new BasicNameValuePair("sCodAtributo", atributos.get(0).toString().replace(":", "").trim()));
                                params.add(new BasicNameValuePair("sParametro", "consultarColibri"));


                                String resultServer = getHttpPost(url, params);
                                System.out.println(resultServer);
                                try {

                                    JSONArray jArray = new JSONArray(resultServer);
                                    ArrayList<String> array = new ArrayList<String>();
                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject json = jArray.getJSONObject(i);
                                        array.add(json.getString("cod_valor"));
                                        atributo = true;
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                if (atributo && !nuevoRegistro.equals("SI")) {
                                    new ActualizarColibri().execute("");
                                } else {
                                    new GuardarColibri().execute("");
                                }
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(),"Hace faltan datos!",Toast.LENGTH_LONG).show();
            }

        }
    }

    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        return date;
    }


    public void prueba(View v){
        if (faltanDatos.equals("false")) {

            if (cod_proceso.trim().equals("1") || cod_proceso.trim().equals("99")) {
                new GuardarMovimiento().execute("");

            }else{
                if (cod_proceso.trim().equals("0") || cod_proceso.trim().equals("56")  ||  cod_proceso.trim().equals("58")){
                    guardarMovimiento();
                }else {
                    if (cod_proceso.trim().equals("57")){
                        guardarMovimiento();
                    }else {
                        if (cod_proceso.trim().equals("123")) {
                            guardarMovimiento();
                        } else {

                            boolean atributo = false;
                            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("sId", id));
                            params.add(new BasicNameValuePair("sCodAtributo", atributos.get(0).toString().replace(":", "").trim()));
                            params.add(new BasicNameValuePair("sParametro", "consultarColibri"));


                            String resultServer = getHttpPost(url, params);
                            System.out.println(resultServer);
                            try {

                                JSONArray jArray = new JSONArray(resultServer);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("cod_valor"));
                                    atributo = true;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (atributo && !nuevoRegistro.equals("SI")) {
                                new ActualizarColibri().execute("");
                            } else {
                                new GuardarColibri().execute("");
                            }
                        }
                    }
                }
            }
        }else{
            Toast.makeText(getApplicationContext(),"Hace faltan datos!",Toast.LENGTH_LONG).show();
        }

    }
    public void actualizarMovimiento(){
        if (faltanDatos.equals("false")) {
            new ActualizarMovimiento().execute("");
        }else{
            Toast.makeText(getApplicationContext(),"Hace faltan datos!",Toast.LENGTH_LONG).show();
        }
    }

    public void guardarMovimiento(){

        if (faltanDatos.equals("false")) {
            new GuardarEncabezado().execute("");
        }else{
            Toast.makeText(getApplicationContext(),"Hace faltan datos!",Toast.LENGTH_LONG).show();
        }

    }

    class ActualizarColibri extends AsyncTask<String, String, String> {

        String resultado;
        String user;
        @Override
        protected void onPreExecute(){
            mProgressDialog.show();


        }
        @Override
        protected String doInBackground(String... f_url) {
            ArrayList<Boolean> atributosBool = new ArrayList<>();


            if (num_grupo.trim().equals("")){
                for (int i = 0;i<atributos.size();i= i+2){
                    String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                    cod_proceso = "1";
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sId",id));
                    params.add(new BasicNameValuePair("sCodAtributo", atributos.get(i).toString().replace(":","")));
                    params.add(new BasicNameValuePair("sCodValor", atributos.get(i+1).toString()));
                    params.add(new BasicNameValuePair("sCodProceso", proceso));
                    params.add(new BasicNameValuePair("sParametro", "colibriActualizar"));


                    JSONObject c3;

                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);
                }
            }else{
                for (int i = 0;i<atributos.size();i= i+2){
                    String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                    cod_proceso = "1";
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sId",id));
                    params.add(new BasicNameValuePair("sCodAtributo", atributos.get(i).toString().replace(":","")));
                    params.add(new BasicNameValuePair("sCodValor", atributos.get(i+1).toString()));
                    params.add(new BasicNameValuePair("sCodProceso", proceso));
                    params.add(new BasicNameValuePair("sGrupo", num_grupo));
                    params.add(new BasicNameValuePair("sParametro", "colibriActualizarGrupo"));


                    JSONObject c3;

                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);
                }
            }



                String url3 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sRombo", num_rombo));
                params3.add(new BasicNameValuePair("sParametro", "desactivarRombo"));

                String resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);


                try {

                    String url2 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("sParametro", "aumentarConsecutivo"));

                    String resultServer2 = getHttpPost(url2, params2);
                    System.out.println(resultServer2);

                    /*Mail m = new Mail("davidrivera0218@gmail.com", "mamaydios11");

                    String[] toArr = {"davidrivera0218@gmail.com", "davidrivera0218@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("davidrivera0218@gmail.com");
                    m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                    m.setBody("Email body.");

                    try {
                        m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/ciclosplash.png");

                        if(m.send()) {
                            Toast.makeText(GuardarPanel.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(GuardarPanel.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                        }
                    } catch(Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }*/



                    guardarMovimiento();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            File dir = new  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/COLIBRI/");


            if (dir.isDirectory())
            {
                //obtiene un listado de los archivos contenidos en el directorio.
                String[] hijos = dir.list();
                //Elimina los archivos contenidos.
                for (int i = 0; i < hijos.length; i++)
                {
                    new File(dir, hijos[i]).delete();
                }
            }

            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                dataBaseHelper.borrarMovimiento();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            dataBaseHelper.close();

            mProgressDialog.dismiss();
            Intent data = new Intent();
            data.putExtra("parametro", "guardar");
            data.putExtra("valor", "bien");
            setResult(RESULT_OK, data);

            finish();
        }
    }

    class ActualizarMovimiento extends AsyncTask<String, String, String> {

        String resultado;
        String user;
        @Override
        protected void onPreExecute(){
            mProgressDialog.show();


        }
        @Override
        protected String doInBackground(String... f_url) {


            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                dataBaseHelper.borrarMovimiento();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            dataBaseHelper.close();

            mProgressDialog.dismiss();
            Intent data = new Intent();
            data.putExtra("parametro", "guardar");
            data.putExtra("valor", "bien");
            setResult(RESULT_OK, data);

            finish();
        }
    }

    class GuardarColibri extends AsyncTask<String, String, String> {

        String resultado;
        String user;
        boolean enviarCorreo = false;
        @Override
        protected void onPreExecute(){
            mProgressDialog.show();

            CheckBox checkCorreo = (CheckBox) findViewById(R.id.checkCorreo);

            if (checkCorreo.isChecked()) {
                enviarCorreo = true;
            }


        }
        @Override
        protected String doInBackground(String... f_url) {
            ArrayList<Boolean> atributosBool = new ArrayList<>();

            if (enviarCorreo){

                String correo = "";
                String url9 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params9 = new ArrayList<NameValuePair>();
                params9.add(new BasicNameValuePair("sPlaca", cod_placa));
                params9.add(new BasicNameValuePair("sParametro", "correo"));


                String resultServer9 = getHttpPost(url9, params9);
                System.out.println(resultServer9);
                try {

                    JSONArray jArray = new JSONArray(resultServer9);
                    ArrayList<String> array = new ArrayList<String>();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        correo = json.getString("mail").trim();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*Mail m = new Mail("davidrivera0218@gmail.com", "mamaydios11");

                String[] toArr = {correo, correo};
                m.setTo(toArr);
                m.setFrom("davidrivera0218@gmail.com");
                m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                m.setBody("Email body.");

                try {
                    m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/ciclosplash.jpg");

                    if(m.send()) {
                        Toast.makeText(GuardarPanel.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(GuardarPanel.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                    }
                } catch(Exception e) {
                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                    Log.e("MailApp", "Could not send email", e);
                }*/

                    /*try {
                        GMailSender sender = new GMailSender("lavaderocolibri@gmail.com", "1lavadero2");
                        sender.sendMail("Lavadero Ciclo Splash",
                                "Gracias por visitar Ciclo Splash , Gracias por usar nuestro servicio! si tiene alguna inquietud porfavor llame a este numero 3103703366",
                                email,
                                email);

                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }*/
            }

            String maximo = "0";

            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sId", id));
            params1.add(new BasicNameValuePair("sCodAtributo", atributos.get(0).toString().replace(":","")));
            params1.add(new BasicNameValuePair("sCodValor", atributos.get(1).toString()));
            params1.add(new BasicNameValuePair("sProceso", proceso));
            params1.add(new BasicNameValuePair("sParametro", "grupo"));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1);
            try {

                JSONArray jArray = new JSONArray(resultServer1);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    maximo = json.getString("max");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (maximo.trim().equals("null")){
                maximo = "0";
            }

            int j = 0;
            for (int i = 0;i<atributos.size();i= i+2){
                String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                cod_proceso = "1";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sId",id));
                params.add(new BasicNameValuePair("sCodAtributo", atributos.get(i).toString().replace(":","")));
                params.add(new BasicNameValuePair("sCodValor", atributos.get(i+1).toString()));
                params.add(new BasicNameValuePair("sCodProceso", proceso));
                params.add(new BasicNameValuePair("sGrupo", String.valueOf(Integer.parseInt(maximo) + 1)));
                try{
                    params.add(new BasicNameValuePair("sObs", arrayNotas[j].trim()));
                }catch (Exception e){
                    e.printStackTrace();
                    params.add(new BasicNameValuePair("sObs", ""));
                }
                params.add(new BasicNameValuePair("sParametro", "colibriGrupo"));


                JSONObject c3;

                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);
                j++;
            }


                String url3 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sRombo", num_rombo));
                params3.add(new BasicNameValuePair("sParametro", "desactivarRombo"));

                String resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);


                try {

                    String url2 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                    List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("sParametro", "aumentarConsecutivo"));

                    String resultServer2 = getHttpPost(url2, params2);
                    System.out.println(resultServer2);

                    /*Mail m = new Mail("davidrivera0218@gmail.com", "mamaydios11");

                    String[] toArr = {"davidrivera0218@gmail.com", "davidrivera0218@gmail.com"};
                    m.setTo(toArr);
                    m.setFrom("davidrivera0218@gmail.com");
                    m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                    m.setBody("Email body.");

                    try {
                        m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/ciclosplash.png");

                        if(m.send()) {
                            Toast.makeText(GuardarPanel.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(GuardarPanel.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                        }
                    } catch(Exception e) {
                        //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                        Log.e("MailApp", "Could not send email", e);
                    }*/



                    guardarMovimiento();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            File dir = new  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/COLIBRI/");


            if (dir.isDirectory())
            {
                //obtiene un listado de los archivos contenidos en el directorio.
                String[] hijos = dir.list();
                //Elimina los archivos contenidos.
                for (int i = 0; i < hijos.length; i++)
                {
                    new File(dir, hijos[i]).delete();
                }
            }


            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                dataBaseHelper.borrarMovimiento();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            dataBaseHelper.close();

            mProgressDialog.dismiss();
            Intent data = new Intent();
            data.putExtra("parametro", "guardar");
            data.putExtra("valor", "bien");
            setResult(RESULT_OK, data);
            finish();
        }
    }

    class GuardarMovimiento extends AsyncTask<String, String, String> {

        String resultado;
        String user;
        boolean enviarCorreo = false;
        @Override
        protected void onPreExecute(){
            mProgressDialog.show();

            CheckBox checkCorreo = (CheckBox) findViewById(R.id.checkCorreo);

            if (checkCorreo.isChecked()) {
                enviarCorreo = true;
            }

        }
        @Override
        protected String doInBackground(String... f_url) {
            ArrayList<Boolean> atributosBool = new ArrayList<>();


            for (int i = 0; i < arrayGrid.length - 1; i++) {
                if (arrayGrid[i].equals("ROMBO :")) {
                    num_rombo = arrayGrid[i + 1];
                }
                if (arrayGrid[i].equals("OPERARIO :")) {
                    cod_tecnico = arrayGrid[i + 1];
                }
                if (arrayGrid[i].equals("PLACA :")) {
                    cod_placa = arrayGrid[i + 1];
                }
                if (arrayGrid[i].equals("UBICACION :")) {
                    cod_ubicacion = arrayGrid[i + 1];
                }
                if (arrayGrid[i].equals("NIT :")) {
                    num_nit = arrayGrid[i + 1];
                }

            }


            if (!conexion.equals("local")) {

                if (cod_proceso.trim().equals("57")) {

                    String url10 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params10 = new ArrayList<NameValuePair>();
                    params10.add(new BasicNameValuePair("sNit", num_nit.trim()));
                    params10.add(new BasicNameValuePair("sParametro", "estadoCita"));


                    String resultServer10 = getHttpPost(url10, params10);
                    System.out.println(resultServer10);
                    String cita = "";
                    try {

                        JSONArray jArray = new JSONArray(resultServer10);
                        ArrayList<String> array = new ArrayList<String>();
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            cita = json.getString("num_nit").trim();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!cita.equals("") || !cita.equals("null")) {
                        String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("sNit", num_nit));
                        params.add(new BasicNameValuePair("sParametro", "actualizarEstadoCita"));

                        String resultServer = getHttpPost(url, params);
                        System.out.println(resultServer);
                    }

                }

                columnas.add("KILOMETRAJE");
                datos.add(km);

                columnas.add("KM_ANTERIOR");
                datos.add(km);

                columnas.add("BODEGA");
                datos.add(bodega);

                columnas.add("SERIE");
                datos.add(cod_placa);

                columnas.add("ROMBO_USADO");
                datos.add(num_rombo);

                columnas.add("ROMBO");
                datos.add(num_rombo);

                columnas.add("PROMESA_ENTREGA");
                datos.add("GETDATE()");

                columnas.add("NIT");
                datos.add(num_nit);

                String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sBodega", "1"));
                params1.add(new BasicNameValuePair("sParametro", "consecutivoOT"));


                String resultServer1 = getHttpPost(url1, params1);
                System.out.println(resultServer1);
                try {

                    JSONArray jArray = new JSONArray(resultServer1);
                    ArrayList<String> array = new ArrayList<String>();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array.add(json.getString("siguiente"));
                        datos.add(array.get(0));
                        numero = array.get(0);
                    }

                    columnas.add("NUMERO");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "http://" + ip + "/guardarMovimiento.php";


                sql = "INSERT INTO " + nombreTabla + " (";



                for (int i = 0;i<columnas.size();i++){
                    sql += columnas.get(i).trim() + ",";
                }

                sql = sql.substring(0,sql.length()-1);

                sql+= ") VALUES (";

                for (int i = 0;i<datos.size();i++){
                    if (datos.get(i).trim().equals("now()")){
                        sql +=  datos.get(i).trim() +  ",";
                    }else {
                        if (datos.get(i).trim().equals("convert(date,GETDATE())")){
                            sql +=  datos.get(i).trim() +  ",";
                        }else {
                            if (datos.get(i).trim().equals("")){
                                sql += "0" + ",";
                            }else {
                                if (datos.get(i).trim().equals("GETDATE()")){
                                    sql += "now()" + ",";
                                }else {
                                    sql += "'" + datos.get(i).trim() + "'" + ",";
                                }
                            }
                        }
                    }
                }


                sql = sql.substring(0,sql.length()-1);
                sql+= ");";


                System.out.println("ESTE ES EL SQL -> " + sql);

                url = "http://" + ip + "/guardarMovimientoColibriHydro.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sSQL", sql));
                params.add(new BasicNameValuePair("sParametro", "1"));

                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);

                url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sParametro", "CONSECUTIVO"));


                resultServer1 = getHttpPost(url1, params1);
                System.out.println("---------------------------------resultserver----------------");
                String consecutivo = "";
                try {

                    JSONArray jArray = new JSONArray(resultServer1);
                    ArrayList<String> array = new ArrayList<String>();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        consecutivo = json.getString("nume_cmo");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayList<String> rutas = new ArrayList<>();
                rutas.add(num_rombo);
                rutas.add(cod_ubicacion);
                rutas.add(cod_tecnico);

                url = "http://" + ip + "/guardarMovimientoColibriHydro.php";


                cod_proceso = "1";
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                params.add(new BasicNameValuePair("sCodUbicacion", cod_ubicacion));
                params.add(new BasicNameValuePair("sFecProceso", "2"));
                params.add(new BasicNameValuePair("sNumNit", num_nit));
                params.add(new BasicNameValuePair("sIndEstado", ind_estado));
                params.add(new BasicNameValuePair("sNumRombo", num_rombo));
                params.add(new BasicNameValuePair("sCodPlaca", cod_placa));
                params.add(new BasicNameValuePair("sCodTecnico", cod_tecnico));
                params.add(new BasicNameValuePair("sTipoVehiculo", tipoVehiculo));
                params.add(new BasicNameValuePair("sConsecutivo", consecutivo));
                params.add(new BasicNameValuePair("sMarca", marca));
                params.add(new BasicNameValuePair("sPrecio", precio));
                params.add(new BasicNameValuePair("sFpago", fpago));
                params.add(new BasicNameValuePair("sNotas", notas));
                params.add(new BasicNameValuePair("sMotivo", motivo));
                params.add(new BasicNameValuePair("sCosto", costo));
                params.add(new BasicNameValuePair("sDescPrecio", descPrecio));
                params.add(new BasicNameValuePair("sDescModelo", "1"));
                params.add(new BasicNameValuePair("sNumItem", "0"));
                params.add(new BasicNameValuePair("sNumTotal", "0"));
                params.add(new BasicNameValuePair("sCodCodigo", ""));
                params.add(new BasicNameValuePair("sCodUbicacionBodega", "1"));
                params.add(new BasicNameValuePair("sParametro", "traDetalleSimple"));


                JSONObject c3;

                resultServer = getHttpPost(url, params);
                System.out.println(resultServer);

                String url3 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                params3.add(new BasicNameValuePair("sRombo", num_rombo));
                params3.add(new BasicNameValuePair("sParametro", "desactivarRombo"));

                String resultServer3 = getHttpPost(url3, params3);
                System.out.println(resultServer3);

                String url2 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                params2.add(new BasicNameValuePair("sBodega", "1"));
                params2.add(new BasicNameValuePair("sParametro", "aumentarConsecutivoOT"));

                String resultServer2 = getHttpPost(url2, params2);
                System.out.println(resultServer2);


                try {

                    url2 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                    params2 = new ArrayList<NameValuePair>();
                    params2.add(new BasicNameValuePair("sParametro", "aumentarConsecutivo"));

                    resultServer2 = getHttpPost(url2, params2);
                    System.out.println(resultServer2);

                    if (enviarCorreo){

                        String correo = "";
                        String url9 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                        List<NameValuePair> params9 = new ArrayList<NameValuePair>();
                        params9.add(new BasicNameValuePair("sNit", num_nit));
                        params9.add(new BasicNameValuePair("sParametro", "correo"));


                        String resultServer9 = getHttpPost(url9, params9);
                        System.out.println("---------------------------------resultserver----------------");
                        try {

                            JSONArray jArray = new JSONArray(resultServer9);
                            ArrayList<String> array = new ArrayList<String>();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json = jArray.getJSONObject(i);
                                correo = json.getString("mail").trim();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /*Mail m = new Mail("davidrivera0218@gmail.com", "mamaydios11");

                        String[] toArr = {correo, correo};
                        m.setTo(toArr);
                        m.setFrom("davidrivera0218@gmail.com");
                        m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                        m.setBody("Email body.");

                        try {
                            m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/ciclosplash.png");

                            if(m.send()) {
                                Toast.makeText(GuardarPanel.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(GuardarPanel.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                            }
                        } catch(Exception e) {
                            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                            Log.e("MailApp", "Could not send email", e);
                        }*/

                    /*try {
                        GMailSender sender = new GMailSender("lavaderocolibri@gmail.com", "1lavadero2");
                        sender.sendMail("Lavadero Ciclo Splash",
                                "Gracias por visitar Ciclo Splash , Gracias por usar nuestro servicio! si tiene alguna inquietud porfavor llame a este numero 3103703366",
                                email,
                                email);

                    } catch (Exception e) {
                        Log.e("SendMail", e.getMessage(), e);
                    }*/
                    }


                    if (!cod_proceso.trim().equals("1")) {
                        guardarMovimiento();
                    }

                    /*mProgressDialog.dismiss();
                    Intent data = new Intent();
                    data.putExtra("parametro", "guardar");
                    data.putExtra("valor", "bien");
                    setResult(RESULT_OK, data);
                    finish();*/

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{


                if (cod_proceso.equals("1")) {



                    DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
                    try {
                        dataBaseHelper.createDataBase();
                        dataBaseHelper.openDataBase();
                        dataBaseHelper.guardarTraDetalle(cod_empresa, cod_sucursal, cod_usuario, cod_proceso, cod_ubicacion, "1", num_nit,
                                "A", num_rombo, cod_placa, cod_tecnico, tipoVehiculo, "22222", marca, precio, fpago,
                                notas, costo, descPrecio, descripcionModelo, "1", "1", "1", "1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    dataBaseHelper.close();
                }else{
                    guardarMovimiento();
                }

            }

            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                dataBaseHelper.borrarMovimiento();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            dataBaseHelper.close();

            mProgressDialog.dismiss();
            Intent data = new Intent();
            data.putExtra("parametro", "guardar");
            data.putExtra("valor", "bien");
            setResult(RESULT_OK, data);
            finish();
        }
    }

    class GuardarEncabezado extends AsyncTask<String, String, String> {

        String resultado;
        String user;
        String fecha = "";
        boolean consecutivo = false;
        @Override
        protected void onPreExecute(){
            mProgressDialog.show();


        }
        @Override
        protected String doInBackground(String... f_url) {


            if (cod_sucursal.trim().equals("51")) {
                bodega = "1";
            } else {
                if (cod_sucursal.trim().equals("33")) {
                    bodega = "2";
                } else {
                    if (cod_sucursal.trim().equals("10")) {
                        bodega = "3";
                    } else {
                        if (cod_sucursal.trim().equals("99")) {
                            bodega = "11";
                        }
                    }
                }
            }


            for (int i = 0; i < columnas.size(); i++) {
                System.out.println("COLUMNA " + i + " " + columnas.get(i));
            }

            for (int i = 0; i < datos.size(); i++) {
                System.out.println("DATO " + i + " " + datos.get(i));
            }

            if (!conexion.trim().equals("local")) {

                if (cod_proceso.trim().equals("58")){

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sUser", user));
                    params1.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                    params1.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                    params1.add(new BasicNameValuePair("sCodProceso", "2"));
                    params1.add(new BasicNameValuePair("sParametro", "citas"));

                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println(resultServer1 + " COD PROCESO ");

                    ArrayList<String> procesos = new ArrayList<>();
                    ArrayList<String> nomProceso = new ArrayList<>();
                    ArrayList<String> colores = new ArrayList<>();

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
                    params2.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                    params2.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
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

                    Intent intent = new Intent((Context) GuardarPanel.this, (Class) SeleccionarCitas.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("nombreTabla", nombreTabla);
                    bundle2.putStringArrayList("colores",colores);
                    bundle2.putStringArrayList("procesos",procesos);
                    bundle2.putStringArrayList("nomProcesos", nomProceso);
                    bundle2.putString("fechaInicial", fechaInicial);
                    bundle2.putString("fechaFinal", fechaFinal);
                    bundle2.putString("codAtributo", "");
                    bundle2.putString("nuevoRegistro", "NO");
                    bundle2.putString("esAutonext", "false");
                    bundle2.putString("user", user);
                    bundle2.putString("pass", "");
                    bundle2.putString("sucursal", cod_sucursal);
                    bundle2.putString("empresa", cod_empresa);
                    bundle2.putString("conexion", conexion);
                    bundle2.putSerializable("general", new String[][]{});
                    bundle2.putSerializable("generalAux", new String[][]{});
                    bundle2.putStringArrayList("columnas", columnas);
                    bundle2.putStringArrayList("datos", datos);
                    bundle2.putString("rombo", num_rombo);
                    bundle2.putSerializable("general", "");
                    bundle2.putString("ip", ip);
                    bundle2.putString("titulo", "");
                    bundle2.putString("proceso", "58");
                    bundle2.putStringArrayList("atributos", atributos);
                    bundle2.putStringArrayList("valores", new ArrayList<String>());
                    intent.putExtras(bundle2);
                    startActivity(intent);
                }else {
                    if (cod_proceso.trim().equals("57")){

                        String fecha = "";
                        String nit = "";

                        for (int i = 0;i<columnas.size();i++){
                            if (columnas.get(i).trim().equals("FEC_INICIAL")){
                                fecha = datos.get(i).trim();
                            }
                            if (columnas.get(i).trim().equals("NUM_NIT")){
                                nit = datos.get(i).trim();
                            }
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);

                        Date fechaFinal = null;
                        try {
                            fechaFinal = dateFormat.parse(fecha);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String day = (String) DateFormat.format("dd", fechaFinal);
                        String month = (String) DateFormat.format("MM", fechaFinal);
                        String year = (String) DateFormat.format("yyy", fechaFinal);
                        String hora = (String) DateFormat.format("HH:mm", fechaFinal);

                        String url32 = "http://" + ip + "/guardarMovimientoColibriHydro.php";
                        List<NameValuePair> params32 = new ArrayList<NameValuePair>();
                        params32.add(new BasicNameValuePair("sFecha", fecha));
                        params32.add(new BasicNameValuePair("sEmpresa", cod_empresa));
                        params32.add(new BasicNameValuePair("sSucursal", cod_sucursal));
                        params32.add(new BasicNameValuePair("sNit", nit));
                        params32.add(new BasicNameValuePair("sDia", day));
                        params32.add(new BasicNameValuePair("sMes",month));
                        params32.add(new BasicNameValuePair("sAno",year));
                        params32.add(new BasicNameValuePair("sHora",hora));
                        params32.add(new BasicNameValuePair("sProceso", "2"));
                        params32.add(new BasicNameValuePair("sParametro", "actualizarCita"));

                        String resultServer32 = getHttpPost(url32, params32);
                        System.out.println(resultServer32);

                    }else {
                        if (!cod_proceso.trim().equals("56")) {
                            String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";


                            sql = "INSERT INTO " + nombreTabla + " (";


                            String frecuencia = "";
                            String fechaInicial = "";
                            String fechaFinal = "";

                            if (cod_proceso.equals("0")){
                                columnas.add("NUM_ITEM");
                                columnas.add("COD_PLACA");
                                columnas.add("FEC_INICIAL");
                                columnas.add("FEC_FINAL");
                                columnas.add("FEC_LANZAMIENTO");
                                columnas.add("NUM_ROMBO");
                                columnas.add("COD_UBICACION");
                                datos.add("1");
                                datos.add("1");
                                datos.add("GETDATE()");
                                datos.add("GETDATE()");
                                datos.add("GETDATE()");
                                datos.add("1");
                                datos.add("1");

                                String nit = "";
                                for (int i = 0; i < columnas.size(); i++) {
                                    if (columnas.get(i).trim().equals("NUM_TIEMPO_PROCESO")) {
                                        frecuencia = datos.get(i).trim();
                                    }

                                    if (columnas.get(i).trim().equals("NUM_NIT")) {
                                        nit = datos.get(i).trim();
                                    }

                                    if (columnas.get(i).trim().equals("FEC_INICIAL")) {
                                        fechaInicial = datos.get(i).trim();
                                    }

                                    if (columnas.get(i).trim().equals("FEC_FINAL")) {
                                        fechaFinal = datos.get(i).trim();
                                    }
                                    sql += columnas.get(i) + ",";
                                }

                                columnas.add("NIT_CLIENTE");
                                datos.add(nit);

                                sql += columnas.get(columnas.size() - 1);
                                sql += ") VALUES (";

                                //datos.set(4,"LAT999");

                                for (int i = 0; i < datos.size() - 1; i++) {

                                    if (datos.get(i).trim().equals("GETDATE()")){
                                        sql += "" + datos.get(i) + "" + ",";
                                    }else {
                                        sql += "'" + datos.get(i) + "'" + ",";
                                    }
                                }


                                sql += "'" + datos.get(datos.size() - 1) + "'";
                                sql += ");";
                            }else {
                                if (cod_proceso.equals("123")){
                                    columnas.add("COD_EMPRESA");
                                    columnas.add("COD_SUCURSAL");
                                    datos.add(cod_empresa);
                                    datos.add(cod_sucursal);
                                }
                                for (int i = 0; i < columnas.size() - 1; i++) {

                                    if (columnas.get(i).trim().equals("NUM_TIEMPO_PROCESO")) {
                                        frecuencia = datos.get(i).trim();
                                    }

                                    if (columnas.get(i).trim().equals("FEC_INICIAL")) {
                                        fechaInicial = datos.get(i).trim();
                                    }

                                    if (columnas.get(i).trim().equals("FEC_FINAL")) {
                                        fechaFinal = datos.get(i).trim();
                                    }
                                    sql += columnas.get(i) + ",";
                                }

                                sql += columnas.get(columnas.size() - 1);
                                sql += ") VALUES (";

                                //datos.set(4,"LAT999");

                                for (int i = 0; i < datos.size() - 1; i++) {
                                    sql += "'" + datos.get(i) + "'" + ",";
                                }


                                sql += "'" + datos.get(datos.size() - 1) + "'";
                                sql += ");";
                            }


                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("sSQL", sql));
                            params.add(new BasicNameValuePair("sParametro", "1"));

                            String resultServer = getHttpPost(url, params);
                            System.out.println(resultServer);
                        } else {
                            String frecuencia = "";
                            String fechaInicial = "";
                            String fechaFinal = "";

                            for (int i = 0; i < columnas.size(); i++) {
                                if (columnas.get(i).trim().equals("NUM_TIEMPO_PROCESO")) {
                                    frecuencia = datos.get(i).trim();
                                }

                                if (columnas.get(i).trim().equals("FEC_INICIAL")) {
                                    fechaInicial = datos.get(i).trim();
                                }

                                if (columnas.get(i).trim().equals("FEC_FINAL")) {
                                    fechaFinal = datos.get(i).trim();
                                }

                            }

                            String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("sFechaInicial", fechaInicial));
                            params.add(new BasicNameValuePair("sFrecuencia", frecuencia));
                            params.add(new BasicNameValuePair("sFechaFinal", fechaFinal));
                            params.add(new BasicNameValuePair("sParametro", "cita"));

                            String resultServer = getHttpPost(url, params);
                            System.out.println(resultServer);


                        }
                    }

                }



            }else{

                sql = "INSERT INTO " + nombreTabla + " (";


                for (int i = 0; i < columnas.size() - 1; i++) {
                    sql += columnas.get(i) + ",";
                }

                sql += columnas.get(columnas.size() - 1);
                sql += ") VALUES (";

                for (int i = 0; i < datos.size() - 1; i++) {
                    if (datos.get(i).equals("GETDATE()")) {
                        sql += datos.get(i) + ",";
                    } else {
                        if (datos.get(i).equals("convert(date,GETDATE())")) {
                            sql += datos.get(i) + ",";
                        } else {
                            if (fecha.equals("dateadd")) {
                                sql += datos.get(i) + ",";
                            } else {
                                sql += "'" + datos.get(i) + "'" + ",";
                            }

                        }
                    }
                }


                sql += "'" + datos.get(datos.size() - 1) + "'";
                sql += ");";

                DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
                try {
                    dataBaseHelper.createDataBase();
                    dataBaseHelper.openDataBase();
                    dataBaseHelper.guardarSql(sql);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                dataBaseHelper.close();
            }


            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            DataBaseHelper dataBaseHelper = new DataBaseHelper(GuardarPanel.this);
            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
                dataBaseHelper.borrarMovimiento();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }

            dataBaseHelper.close();

            mProgressDialog.dismiss();
            Intent data = new Intent();
            data.putExtra("parametro", "guardar");
            data.putExtra("valor", "bien");
            setResult(RESULT_OK, data);
            finish();
        }
    }



    public void guardar (View v) {

        String num_rombo =" rombo ";
        ArrayList<Boolean> atributosBool = new ArrayList<>();
        for (int i = 0;i<arrayGrid.length-1;i++){
            if (arrayGrid[i].equals("ROMBO :")){
                num_rombo = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("MECANICO :")){
                cod_tecnico = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("PLACA :")){
                cod_placa = arrayGrid[i+1];
            }
            if (arrayGrid[i].equals("UBICACION :")){
                cod_ubicacion = arrayGrid[i+1];
            }

            if (arrayGrid[i].equals("NIT :")){
                num_nit = arrayGrid[i+1];
            }

        }

        for (int i =0;i<arrayGrid.length-1;i++) {

            System.out.println("ARRAYYYYYYYYYYYYYYYYYY->" + arrayGrid[i]);
        }

        /*String url4 = "http://" + ip + "/consultarPlaca.php";

        List<NameValuePair> params4 = new ArrayList<NameValuePair>();

        ArrayList<String> array = new ArrayList<String>();

        String resultServer4 = getHttpPost(url4, params4);
        try {

            JSONArray jArray = new JSONArray(resultServer4);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add(json.getString("cod_placa"));
                array.add(json.getString("num_rombo"));
            }

        }catch (JSONException e ){
            e.printStackTrace();
        }

        boolean placa;

        for (int i = 0;i<array.size();i++){
            if (array.equals(num))
        }
*/

        ArrayList<String> rutas = new ArrayList<>();
        rutas.add(num_rombo);
        rutas.add(cod_ubicacion);
        rutas.add(cod_tecnico);

        /*for (int i = 0;i<rutas.size();i++) {

            //showProgress(true);

            String url3 = "http://" + ip + "/desactivarRomUbic.php";

            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

            params3.add(new BasicNameValuePair("sValRecurso",rutas.get(i)));
            params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));


            String resultServer3 = getHttpPost(url3, params3);
            System.out.println(resultServer3);


            try {

            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }
        }*/

        String url1 = "http://" + ip + "/actualizarCita.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();

        params1.add(new BasicNameValuePair("sNumRombo", num_rombo));
        params1.add(new BasicNameValuePair("sCodUbicacion", cod_ubicacion));
        params1.add(new BasicNameValuePair("sCodPlaca", cod_placa));


        /** Get result from Server (Return the JSON Code)
         * StatusID = ? [0=Failed,1=Complete]
         * Error	= ?	[On case error return custom error message]
         *
         * Eg Save Failed = {"StatusID":"0","Error":"Email Exists!"}
         * Eg Save Complete = {"StatusID":"1","Error":""}
         */
        String resultServer1 = getHttpPost(url1, params1);
        System.out.println(resultServer1);


        try {

        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }

        boolean atributo = false;

       /* String url3 = "http://" + ip + "/consultarGeneral.php";

        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
        params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
        params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
        params3.add(new BasicNameValuePair("sCodPlaca", cod_placa.trim()));
        params3.add(new BasicNameValuePair("sNumRombo", num_rombo.trim()));
        params3.add(new BasicNameValuePair("sParametro", "detalle"));


        //showProgress(true);

        String resultServer3  = getHttpPost(url3,params3);
        System.out.println("---------------------------------resultserver----------------");
        System.out.println(resultServer3);
        try {

            JSONArray jArray = new JSONArray(resultServer3);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add(json.getString("num_rombo").trim());
                array.add(json.getString("cod_placa").trim());
            }

            for (int i = 0; i < array.size(); i= i+2) {
                if (array.get(i).equals(num_rombo) && array.get(i).equals(cod_placa)){
                    Toast.makeText(getApplicationContext(), "o se puede insertar, ya hay una placa igual!", Toast.LENGTH_SHORT).show();
                }else{*/
        String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";



        cod_proceso = "1";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
        params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
        params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
        params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
        params.add(new BasicNameValuePair("sCodUbicacion", cod_ubicacion));
        params.add(new BasicNameValuePair("sFecProceso", "2"));
        params.add(new BasicNameValuePair("sNumNit", num_nit));
        params.add(new BasicNameValuePair("sIndEstado", ind_estado));
        params.add(new BasicNameValuePair("sNumRombo", num_rombo));
        params.add(new BasicNameValuePair("sCodPlaca", cod_placa));
        params.add(new BasicNameValuePair("sCodTecnico", cod_tecnico));
        params.add(new BasicNameValuePair("sNumItem", "0"));
        params.add(new BasicNameValuePair("sNumTotal", "0"));
        params.add(new BasicNameValuePair("sCodCodigo", ""));
        params.add(new BasicNameValuePair("sCodUbicacionBodega", "1"));
        params.add(new BasicNameValuePair("sParametro", "2"));




        JSONObject c3;

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);


        try {
            /*String url2 = "http://" + ip + "/aumentarConsecutivo.php";

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            String resultServer2 = getHttpPost(url2, params2);
            System.out.println(resultServer2);


            try {

            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }*/
            Intent data = new Intent();
            data.putExtra("parametro", "guardar");
            data.putExtra("valor", "bien");
            setResult(RESULT_OK, data);
            finish();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"No se guardo el detalle" ,Toast.LENGTH_LONG).show();
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        // }
        //}

        /*}catch (JSONException e ){
            e.printStackTrace();
        }*/

        /*String url2 = "http://192.168.1.190:8080/guardarPrueba.php";



        cod_proceso = "1";
        List<NameValuePair> params2 = new ArrayList<NameValuePair>();

        String resultServer2 = getHttpPost(url2, params2);
        System.out.println(resultServer2);
        try {
            finish();
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }*/



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

    public void regresar(View v){
        Intent data = new Intent();
        data.putExtra("parametro", "guardar");
        data.putExtra("valor","regresar");
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        showProgress(show, this, Progress, Scroll);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show, Context ctx,
                                    final View mProgressView, final View mFormView) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = ctx.getResources()
                    .getInteger(android.R.integer.config_shortAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent data = new Intent();
            data.setData(Uri.parse("regresar"));
            setResult(RESULT_OK, data);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
