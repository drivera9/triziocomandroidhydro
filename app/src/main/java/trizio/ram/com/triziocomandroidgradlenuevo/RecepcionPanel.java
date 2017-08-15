package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class RecepcionPanel extends AppCompatActivity {
    String existeTercero = "SI";
    String procesoOriginal = "";
    String cod_atributo = "";
    String codAtributo = "";
    String email = "";
    String num_grupo = "";
    String costo = "";
    String fpago = "E";
    String marca = "";
    String notas = "NN";
    String descPrecio = "";
    String precio = "";
    String tipoVehiculo = "";
    int indice =0;
    String extintor = "";
    private final String ruta_fotos = "/storage/sdcard0/METRO/" ;
    private File file = new File(ruta_fotos);
    private Button boton;
    static String conexion = "";
    String tecnico = "";
    String encuentraPlaca = "";
    String encuentraNit = "";
    String nombreTabla = "";
    String ip = "" ;
    String placa = "";
    String vin = "";
    String Cedula = "";
    String nombre1 = "";
    String nombre2 = "";
    String apellido1 = "";
    String apellido2 = "";
    String nombre = "";
    String cod_placa = "";
    String num_rombo= "";
    String cod_ubicacion = "";
    String fec_proceso = "";
    String num_nit = "";
    String promesa = "";
    String solicitud = "";
    String sucursal;
    int request_code = 1;
    ArrayList  atributos ;
    String titulo = "";
    String empresa;
    EditText texto;
    String [][] generalAux;
    String [][] general;
    String [][] generalAuxNueva;
    String [][] generalNueva;
    String [] arrayNotas;
    String proceso = "";
    int date ;
    String fechaFoto;
    String dia ;
    String dirFoto;
    int longitud;
    String tiempoPromesa;
    String kilometraje = "";
    String sInformativo = "";
    String parametro = "";
    String pass;
    String user;
    String km = "";
    ProgressDialog mProgressDialog;
    String km_anterior = "";
    String razon = "";
    String descripcion = "";
    String soat = "";
    String tecno = "";
    String modelo = "";
    String cita = "";
    String horacita = "";
    String descripcionModelo = "";
    String motivo;
    String eventoCita = "";
    String eventoRazon = "";
    String id = "";
    String esAutonext = "";
    String nombreProceso = "";
    String nuevoRegistro = "";
    private static final int CAMERA_REQUEST = 1888;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    ArrayList<String> columnas = new ArrayList<>();
    ArrayList<String> datos = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepcion_panel);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mProgressDialog= new ProgressDialog(RecepcionPanel.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Subiendo imagen...");

        ArrayList<String> atributosD1 = new ArrayList<>();
        ArrayList<String> valoresD1 = new ArrayList<>();

        Bundle bundle = this.getIntent().getExtras();
        Object[] objectArray = (Object[]) bundle.getSerializable("general");
        if(objectArray!=null){
            general = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                general[i]=(String[]) objectArray[i];
            }
        }


        objectArray = (Object[]) bundle.getSerializable("generalAux");
        if(objectArray!=null){
            generalAux = new String[objectArray.length][];
            for(int i=0;i<objectArray.length;i++){
                generalAux[i]=(String[]) objectArray[i];
            }
        }

        arrayNotas = new String[objectArray.length];

        datos = bundle.getStringArrayList("datos");
        columnas = bundle.getStringArrayList("columnas");
        atributosD1 = bundle.getStringArrayList("atributos");
        valoresD1 = bundle.getStringArrayList("valores");
        num_rombo = bundle.getString("rombo");
        num_grupo = bundle.getString("num_grupo");
        id = bundle.getString("id");
        nombreProceso = bundle.getString("nombreProceso");
        esAutonext = bundle.getString("esAutonext");
        codAtributo = bundle.getString("codAtributo");
        placa = bundle.getString("placa");
        cod_placa = bundle.getString("placa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        conexion = bundle.getString("conexion");
        titulo = bundle.getString("titulo");
        ip = bundle.getString("ip");
        proceso = bundle.getString("proceso");
        procesoOriginal = proceso ;
        if (proceso.equals("11")){
            num_rombo = bundle.getString("num_rombo");
        }
        nombreTabla = bundle.getString("nombreTabla");
        nuevoRegistro = bundle.getString("nuevoRegistro");
        sucursal = bundle.getString("sucursal");
        empresa = bundle.getString("empresa");
        System.out.println("sucursal y empresa ------------>" + sucursal + empresa);

        fechaFoto = getCode();

        if (nuevoRegistro.equals("SI")){
            if (atributosD1.size() > 0) {
                for (int i = 0; i < atributosD1.size(); i++) {
                    for (int j = 0; j < general.length; j++) {
                        if (general[j][4].trim().equals(atributosD1.get(i).trim())) {
                            general[j][5] = "";
                        }
                    }
                }
            }
        }else {

            if (atributosD1.size() > 0) {
                for (int i = 0; i < atributosD1.size(); i++) {
                    for (int j = 0; j < general.length; j++) {
                        if (general[j][4].trim().equals(atributosD1.get(i).trim())) {
                            general[j][5] = valoresD1.get(i).trim();
                        }
                    }
                }
            }
        }

        Button buscar = (Button) findViewById(R.id.button_buscar);
        Button anadir = (Button) findViewById(R.id.button_anadir);

        buscar.setVisibility(View.INVISIBLE);
        anadir.setVisibility(View.INVISIBLE);

        /*if (!proceso.equals("1")){
            ScrollView layout = (ScrollView) findViewById(R.id.ScrollRecepcion);
            layout.setBackgroundResource(R.drawable.background);
        }*/

        setTitle(titulo);

        toolbar.setTitle(Html.fromHtml("<font color='#ff0000'>ActionBartitle </font>"));

        texto = (EditText) findViewById(R.id.editText_texto);
        TextView atributo = (TextView) findViewById(R.id.textView_atributo);

        if (atributo.getText().equals("ROMBO")) {
            texto.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        texto.requestFocus();

        if (conexion.equals("local")) {
            final DataBaseHelper dataBaseHelper = new DataBaseHelper((Context) this);
            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            generalAux = dataBaseHelper.consultarProceso(pass, empresa, sucursal, proceso,1);

            int contador = 0;

            for (int i = 0;i<generalAux.length;i++){
                for (int j = 0;j<generalAux[i].length;j++){
                    if (generalAux[i][17].trim().equals("I")){
                        contador++;
                    }
                }
            }
            contador = contador/21;
            System.out.println("CONTADOR - " + contador);

            if (contador == 0 ){
                general = new String[generalAux.length][22];

                for (int i = 0; i < generalAux.length; i++) {
                    for (int j = 0; j < generalAux[i].length; j++) {
                        if (generalAux[i][j].equals("null")){
                            generalAux[i][j] = "";
                        }

                        general[i][j] = generalAux[i][j];

                    }


                    if (generalAux[i][17].equals("I") && generalAux[i][15].equals(generalAux[i][3])){
                        //columnas.add(generalAux[i][18]);
                        //datos.add(generalAux[i][5]);
                    }



                }
            }else{
                general = new String[generalAux.length-contador][22];
                System.out.println("LONGITUD ->" + (generalAux.length-contador));

                int k=0;
                for (int i = 0; i < general.length; i++) {
                    for ( int j = 0; j < generalAux[i].length; j++) {
                        if (generalAux[k][j].equals("null")) {
                            generalAux[k][j] = "";
                        }

                        if (!generalAux[k][17].equals("I")) {
                            general[i][j] = generalAux[k][j];

                        } else {
                            if (generalAux[k][18].trim().equals(generalAux[k][3].trim())) {
                                //columnas.add(generalAux[k][20].trim());
                                //datos.add(generalAux[k][5].trim());
                            }
                            k++;
                            general[i][j] = generalAux[k][j];
                        }
                    }
                    k++;
                }
            }

            for (int i = 0;i<generalAux.length;i++){
                for (int j = 0;j<generalAux[i].length;j++){
                    System.out.println("GENERAL AUX - " + generalAux[i][j]);
                }
            }

            for (int i = 0;i<general.length;i++){
                for (int j = 0;j<general[i].length;j++){
                    System.out.println("GENERAL - " + general[i][j]);
                }
            }


            actualizar(general, indice, conexion);

            final String cod_empresa = general[0][0];
            final String cod_sucursal = general[0][1];
            final String cod_proceso = general[0][3];
            final String cod_usuario = general[0][2];
            final String ind_estado = general[0][17];

            final Button buttonOk = (Button) findViewById(R.id.button_OK);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ok(cod_empresa,cod_sucursal,cod_proceso,cod_usuario,ind_estado,general);
                }
            });

            final ImageButton button = (ImageButton) findViewById(R.id.imageButton8);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (indice == general.length - 1) {
                    } else {
                        indice++;
                        actualizar(general, indice, conexion);
                        texto.requestFocus();
                    }
                }
            });


            final ImageButton button2 = (ImageButton) findViewById(R.id.imageButton7);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (indice == 0) {
                    } else {
                        indice--;
                        actualizar(general, indice,conexion);
                        texto.requestFocus();
                    }
                }
            });

            final ImageButton button3 = (ImageButton) findViewById(R.id.imageButton6);
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    indice = 0;
                    actualizar(general, indice,conexion);
                    texto.requestFocus();
                }
            });

            final ImageButton button4 = (ImageButton) findViewById(R.id.imageButton9);
            button4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    indice = general.length - 1;
                    actualizar(general, indice, conexion);
                    texto.requestFocus();
                }
            });

            texto.requestFocus();




        }else{


            try {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);
                ArrayList arrayAtributo = new ArrayList();
                ArrayList arrayValor = new ArrayList();
                try {
                    dataBaseHelper.createDataBase();
                    dataBaseHelper.openDataBase();
                    arrayValor = dataBaseHelper.consultarValor();
                    arrayAtributo = dataBaseHelper.consultarAtributo();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                dataBaseHelper.close();


                for (int i = 0; i < arrayAtributo.size(); i++) {
                    System.out.println(" DATOS DE SQLITE ------------------------------------------------");
                    System.out.println(arrayAtributo.get(i) + " " + arrayValor.get(i));
                }



                for (int i = 0; i < arrayAtributo.size(); i++) {
                    if (arrayAtributo.get(i).equals("ROMBO")) {
                        num_rombo = arrayValor.get(i).toString().trim();
                    }
                    if (arrayAtributo.get(i).equals("PLACA")) {
                        cod_placa = arrayValor.get(i).toString().trim();
                    }
                    if (arrayAtributo.get(i).equals("UBICACION")) {
                        cod_ubicacion = arrayValor.get(i).toString().trim();
                    }
                    if (arrayAtributo.get(i).equals("NIT")) {
                        num_nit = arrayValor.get(i).toString().trim();
                    }
                    if (arrayAtributo.get(i).equals("KM")) {
                        kilometraje = arrayValor.get(i).toString().trim();
                    }
                    if (arrayAtributo.get(i).equals("RAZON")) {
                        razon = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("SOT")) {
                        soat = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("EXTINTOR")) {
                        extintor = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("TECNICOECANICA")) {
                        tecno = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("SOLICITUD")) {
                        solicitud = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("OPERARIO")) {
                        tecnico = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("PROMESA")) {
                        promesa = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("VEHICULO")) {
                        tipoVehiculo = arrayValor.get(i).toString().trim();
                    }

                    if (arrayAtributo.get(i).equals("LISTA")) {
                        precio = arrayValor.get(i).toString().trim();
                    }


                }
            }catch (Throwable e){
                e.printStackTrace();
            }

            actualizar(general, indice, conexion);

            final String cod_empresa = general[0][0];
            final String cod_sucursal = general[0][1];
            final String cod_proceso = general[0][3];
            final String cod_usuario = general[0][2];
            final String ind_estado = general[0][17];

            final Button buttonOk = (Button) findViewById(R.id.button_OK);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ok(cod_empresa,cod_sucursal,cod_proceso,cod_usuario,ind_estado,general);
                }
            });

            final ImageButton button = (ImageButton) findViewById(R.id.imageButton8);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (indice == general.length - 1) {
                    } else {
                        indice++;
                        actualizar(general, indice, conexion);
                        texto.requestFocus();
                    }
                }
            });


            final ImageButton button2 = (ImageButton) findViewById(R.id.imageButton7);
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (indice == 0) {
                    } else {
                        indice--;
                        actualizar(general, indice,conexion);
                        texto.requestFocus();
                    }
                }
            });

            final ImageButton button3 = (ImageButton) findViewById(R.id.imageButton6);
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    indice = 0;
                    actualizar(general, indice,conexion);
                    texto.requestFocus();
                }
            });

            final ImageButton button4 = (ImageButton) findViewById(R.id.imageButton9);
            button4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    indice = general.length - 1;
                    actualizar(general, indice, conexion);
                    texto.requestFocus();
                }
            });

        }

        texto.requestFocus();

        System.out.println("NOMBRE DE LA TABLA " + nombreTabla);

        for (int i = 0;i<columnas.size();i++){
            System.out.println("COLUMNA -" + columnas.get(i));
            System.out.println("DATO -" + datos.get(i));
        }


    }

    public void enter (View v){

        final TextView error = (TextView) findViewById(R.id.textView_error);

                        /*if (texto.getText().toString().matches("") && (general[indice][5].equals("ROMBO") || general[indice][5].equals("UBICACION") || general[indice][5].equals("MECANICO"))) {
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("conexion", conexion);
                            bundle2.putString("ip", ip);
                            bundle2.putString("atributo", cod_atributo);
                            intent.putExtras(bundle2);
                            RecepcionPanel.this.startActivityForResult(intent, request_code);
                            Toast.makeText(getApplicationContext(), resultado.get(4) + "  ATRIBUTO ", Toast.LENGTH_SHORT).show();

                        } else {*/
                            /*String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                            params2.add(new BasicNameValuePair("sCodUser", general[indice][2].toString()));
                            params2.add(new BasicNameValuePair("sCodEmpresa", general[indice][0].toString()));
                            params2.add(new BasicNameValuePair("sCodAtributo", general[indice][4].toString()));
                            params2.add(new BasicNameValuePair("sCodSucursal",sucursal));
                            params2.add(new BasicNameValuePair("sParametro", "actualizar"));


                            String resultServer2 = getHttpPost(url2, params2);
                            System.out.println("---------------------------------");
                            System.out.println(resultServer2);
                            ArrayList<String> array = new ArrayList<String>();
                            try {
                                JSONArray jArray = new JSONArray(resultServer2);

                                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add((json.getString("cod_valor")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (array.size()>0) {
                                String url4 = "http://" + ip + "/updateGeneral.php";

                                List<NameValuePair> params4 = new ArrayList<NameValuePair>();


                                params4.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                params4.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                params4.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                params4.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                params4.add(new BasicNameValuePair("sParametro", "update3"));

                                String resultServer4 = getHttpPost(url4, params4);
                                System.out.println(resultServer4);


                                try {
                                    Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    pasar(conexion, general);
                                }catch (Exception e){
                                    Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                }
                            } else {*/





                                /*final AlertDialog.Builder ad = new AlertDialog.Builder(RecepcionPanel.this);

                                ad.setTitle("Error! ");
                                ad.setIcon(android.R.drawable.btn_star_big_on);
                                ad.setPositiveButton("Close", null);
                                InputStream isr = null;
                                String url = "http://" + finalIp + "/update.php";

                                List<NameValuePair> params = new ArrayList<NameValuePair>();


                                params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                params.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                params.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                params.add(new BasicNameValuePair("sNumOrden", num_orden));

                                String resultServer = getHttpPost(url, params);
                                System.out.println(resultServer);


                                JSONObject c;
                                try {
                                    Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    pasar(conexion, general);
                                }catch(Exception e){
                                    Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                }*/

        String atributo = texto.getText().toString().trim();
        error.setTextColor(Color.RED);

        String file = "storage/sdcard0/METRO/" + nombre;
        File mi_foto = new File(file);
        String file2 = "storage/sdcard0/METRO/";
        File mi_foto2 = new File(file2);
        mi_foto.renameTo(mi_foto2);

        EditText texto1 = (EditText) findViewById(R.id.editText_texto);

        if (general[indice][4].trim().equals("ROMBO")) {
            error.setText("");
            num_rombo = texto.getText().toString().trim();
        }
        if (general[indice][4].trim().equals("PLACA")) {
            cod_placa = texto.getText().toString().trim();

        }
        if (general[indice][4].trim().equals("PLACA") && encuentraPlaca.equals("false")) {
            error.setText("Placa no existe, se creara!");
        }
        if (general[indice][4].trim().equals("UBICACION")) {
            cod_ubicacion = texto.getText().toString().trim();
        }
        if (general[indice][4].trim().equals("NIT")) {
            num_nit = texto.getText().toString().trim();
        }
        if (general[indice][4].trim().equals("KM")) {
            kilometraje = texto.getText().toString().trim();
        }
        if (general[indice][4].trim().equals("RAZON")) {
            if (razon.equals("61") || razon.equals("62")){
                eventoRazon = "G";
            }
            razon = texto.getText().toString().trim();
            error.setText(descripcion);
        }

        if (general[indice][4].trim().equals("SOT")) {
            soat = texto.getText().toString().trim();
        }

        if (general[indice][4].trim().equals("EXTINRTOR")) {
            extintor = texto.getText().toString().trim();
        }

        if (general[indice][4].trim().equals("TECNICOECANICA")) {
            tecno = texto.getText().toString().trim();
        }

        if (general[indice][4].trim().equals("CEDULA")) {
            num_nit = texto.getText().toString().trim();
        }


        if (general[indice][4].trim().equals("PLACA")) {

            cita = new String();
            horacita = new String();

            String url4 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params4 = new ArrayList<NameValuePair>();

            params4.add(new BasicNameValuePair("sParametro", "placanitLavadero"));
            params4.add(new BasicNameValuePair("sPlaca", cod_placa));

            String resultServer4 = getHttpPost(url4, params4);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer4);

            try {

                JSONArray jArray = new JSONArray(resultServer4);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("nit"));
                }
                if (array.get(0).equals("null")){

                }else {
                    num_nit = array.get(0).trim();
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                    try {

                        guardarMovimiento("NIT", num_nit);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dataBaseHelper.close();
                }
            } catch (JSONException e) {

                error.setText("");
                //informativo.setTextColor(Color.BLUE);
                e.printStackTrace();
            }

            String url3 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

            params3.add(new BasicNameValuePair("sParametro", "CITA"));
            params3.add(new BasicNameValuePair("sPlaca", cod_placa));

            String resultServer3 = getHttpPost(url3, params3);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer3);

            try {

                JSONArray jArray = new JSONArray(resultServer3);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("placa"));
                    array.add(json.getString("fecha"));
                }
                cita = array.get(0);
                horacita = array.get(1);
                eventoCita = "C";
            } catch (JSONException e) {

                error.setText("");
                //informativo.setTextColor(Color.BLUE);
                e.printStackTrace();
            }

            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

            params1.add(new BasicNameValuePair("sParametro", "MODELO"));
            params1.add(new BasicNameValuePair("sPlaca", cod_placa));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer1);
            try {

                JSONArray jArray = new JSONArray(resultServer1);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("modelo"));
                }

                modelo = array.get(0);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                try {

                    guardarMovimiento("MODELO", modelo);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dataBaseHelper.close();

            } catch (JSONException e) {

                error.setText("");
                //informativo.setTextColor(Color.BLUE);
                e.printStackTrace();
            }

            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("sParametro", "placa"));
            params.add(new BasicNameValuePair("sPlaca", cod_placa));

            String resultServer = getHttpPost(url, params);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer);
            String placa = "";
            try {

                JSONArray jArray = new JSONArray(resultServer);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("placa"));
                    placa = array.get(0);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

            params2.add(new BasicNameValuePair("sParametro", "fechas"));
            params2.add(new BasicNameValuePair("sPlaca", cod_placa));

            String resultServer2 = getHttpPost(url2, params2);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer2);
            try {

                JSONArray jArray = new JSONArray(resultServer2);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("soat"));
                    array.add(json.getString("tecno"));
                }


                soat = array.get(0);

                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                try {

                    guardarMovimiento("SOAT", soat);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dataBaseHelper.close();

                tecno = array.get(1);


                try {

                    guardarMovimiento("TECNICOMECANICA", tecno);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dataBaseHelper.close();


            } catch (JSONException e) {
                error.setText("");
                e.printStackTrace();
            }

            if (cod_placa.trim().equals(placa)) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                try {

                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                dataBaseHelper.close();
                general[indice][5] = texto.getText().toString().trim();
                pasar(conexion, general);
                error.setText("");
                encuentraPlaca = "true";

            } else {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                try {

                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                dataBaseHelper.close();
                general[indice][5] = texto.getText().toString().trim();
                pasar(conexion, general);
                error.setText("");
                //error.setText("No existe la placa!");
                encuentraPlaca = "false";
            }
        } else {
            if (general[indice][4].trim().equals("CEDULA")) {
                String cedula = texto.getText().toString().replaceAll("\n", "");


                try {
                    Cedula = cedula.substring(47, 58);
                    nombre1 = cedula.substring(95, 116);
                    nombre2 = cedula.substring(117, 140);
                    apellido1 = cedula.substring(58, 71);
                    apellido2 = cedula.substring(72, 94);
                    if (Cedula.substring(1, 2).equals("00")) {
                        Cedula = cedula.substring(50, 58);
                    }


                    if (general[indice + 1][4].trim().equals("NOMBRES")) {
                        general[indice + 1][5] = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;

                    }

                    texto.setText(Cedula);
                    general[indice][5] = texto.getText().toString().trim();
                    pasar(conexion, general);
                    error.setText("");
                } catch (Exception e) {
                    general[indice][5] = texto.getText().toString().trim();
                    pasar(conexion, general);
                    error.setText("");
                    e.printStackTrace();
                }
            } else {

                if (general[indice][4].trim().equals("NOMBRES")) {
                    String cedula = texto.getText().toString().replaceAll("\n", "");


                    try {
                        Cedula = cedula.substring(47, 58);
                        nombre1 = cedula.substring(95, 116);
                        nombre2 = cedula.substring(117, 140);
                        apellido1 = cedula.substring(58, 71);
                        apellido2 = cedula.substring(72, 94);
                        if (Cedula.substring(1, 3).equals("00")) {
                            Cedula = cedula.substring(50, 58);
                        }

                        if (general[indice + 1][4].trim().equals("CEDULA")) {
                            general[indice + 1][5] = Cedula;
                        }

                        for (int i = 0; i < general.length; i++) {
                            if (general[i][4].trim().equals("NIT")) {
                                general[i][5] = Cedula;
                            }
                        }

                        texto.setText(remove(nombre1) + " " + remove(nombre2) + " " + remove(apellido1) + " " + remove(apellido2));
                        general[indice][5] = remove(nombre1) + " " + remove(nombre2) + " " + remove(apellido1) + " " + remove(apellido2);
                        pasar(conexion, general);
                        error.setText("");
                    } catch (Exception e) {
                        general[indice][5] = texto.getText().toString().trim();
                        pasar(conexion, general);
                        error.setText("");
                        e.printStackTrace();
                    }
                } else {


                    if (general[indice][4].trim().equals("KM")) {

                        String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                        params.add(new BasicNameValuePair("sParametro", "km_anterior"));
                        params.add(new BasicNameValuePair("sPlaca", cod_placa));

                        String resultServer = getHttpPost(url, params);
                        System.out.println("---------------------------------resultserver----------------");
                        System.out.println(resultServer);
                        try {

                            JSONArray jArray = new JSONArray(resultServer);
                            ArrayList<String> array = new ArrayList<String>();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json = jArray.getJSONObject(i);
                                array.add(json.getString("km_anterior"));
                                km_anterior = array.get(0);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (km_anterior.trim().equals("null"))
                            km_anterior = "1";

                        if (Integer.parseInt(km_anterior) < Integer.parseInt(texto.getText().toString())) {

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                            try {

                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                            dataBaseHelper.close();
                            general[indice][5] = texto.getText().toString().trim();
                            pasar(conexion, general);
                            error.setText("");

                        } else {
                            //informativo.setTextColor(Color.BLUE);
                            error.setText("Anterior : " + km_anterior + ", debe ser mayor!");


                        }
                    } else {

                        if (general[indice][4].trim().equals("NIT")) {

                            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                            params.add(new BasicNameValuePair("sParametro", "nit"));
                            params.add(new BasicNameValuePair("sNit", num_nit));

                            String resultServer = getHttpPost(url, params);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer);
                            String nit = "";
                            try {

                                JSONArray jArray = new JSONArray(resultServer);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("nit"));
                                    array.add(json.getString("mail"));
                                    nit = array.get(0);
                                    email = array.get(1);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (num_nit.trim().equals(nit)) {
                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                dataBaseHelper.close();
                                general[indice][5] = texto.getText().toString().trim();
                                pasar(conexion, general);
                                error.setText("");
                                encuentraNit = "true";
                                existeTercero = "SI";
                            } else {
                                existeTercero = "NO";
                                Intent i =  new Intent(RecepcionPanel.this,CrearCliente.class);
                                i.putExtra("nit",texto.getText().toString().trim());
                                i.putExtra("ip",ip.trim());
                                startActivityForResult(i,request_code);
                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                dataBaseHelper.close();

                                general[indice][5] = texto.getText().toString().trim();
                                error.setText("");
                                //error.setText("No existe el nit!");
                                //general[indice][5] = texto.getText().toString().trim();
                                pasar(conexion, general);

                            }
                        } else {
                            if (general[indice][4].trim().equals("PLACA")) {
                                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                List<NameValuePair> params = new ArrayList<NameValuePair>();

                                params.add(new BasicNameValuePair("sParametro", "PLACA"));
                                params.add(new BasicNameValuePair("sPlaca", cod_placa));

                                String resultServer = getHttpPost(url, params);
                                System.out.println("---------------------------------resultserver----------------");
                                System.out.println(resultServer);
                                String nombre = "";
                                try {

                                    JSONArray jArray = new JSONArray(resultServer);
                                    ArrayList<String> array = new ArrayList<String>();
                                    for (int i = 0; i < jArray.length(); i++) {
                                        JSONObject json = jArray.getJSONObject(i);
                                        array.add(json.getString("nombre"));
                                        sInformativo = sInformativo + "\n" + array.get(0);
                                    }

                                } catch (JSONException e) {

                                    error.setText("");
                                    error.setText("No existe la placa!");
                                    //informativo.setTextColor(Color.BLUE);
                                    e.printStackTrace();
                                }




                                if (sInformativo.equals("null") || sInformativo.equals("")) {

                                    error.setText("No existe la placa!");
                                } else {
                                    error.setText(sInformativo);
                                    //informativo.setTextColor(Color.BLUE);
                                    general[indice][5] = texto.getText().toString().trim();
                                    pasar(conexion, general);
                                    error.setText("");
                                }
                            } else {
                                if (general[indice][4].trim().equals("RAZON")) {
                                    final String[] grid2 = {"GENERAL"
                                            ,"VENTA TALLERES MOSTRADOR"
                                            ,"MAIL-CAMPAÑA"
                                            ,"CALL CENTER-CAMPAÑA"
                                            ,"CAMPAÑA DE REDES SOCIALES"
                                            ,"AUTOSURA-CITA CARRO TALLER"
                                            ,"AUTOSURA-CLIENTE"
                                            ,"FONDOS Y FERIAS"
                                            ,"CARRERAS CALI"
                                            ,"CARRO TALLER"
                                            ,"CONVENIOS INSTITUCIONALES"
                                            ,"AUTOSURA-BONO"
                                            ,"CLIENTE UNE MAS"
                                            ,"SUCURSAL  AUTOSURA"
                                            ,"CUPON CLIENTE UNE MAS"
                                            ,"PROMOCIONES Y PUBLICACIONES EN REDES SOCIALES"
                                            ,"CLIENTE DE GERENCIA"
                                            ,"BONO DE CORTESIA"
                                            ,"AGENDA UNIVERSITARIA"
                                            ,"FLYER O VOLANTE"
                                            ,"CONVENIO ALTO NIVEL"
                                            ,"REVISTA AGENDA AUTOMOTRIZ"
                                            ,"AVISO DIAGNOSTICO GRATIS"
                                            ,"EXPOMOTRIZ"
                                            ,"CITA REVISION PREVENTIVA"
                                            ,"MAILING CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                            ,"VOLANTE CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                            ,"L A W , CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                            ,"AVISOS FACHADA VACACIONES SIN PREOCUPACIONES"
                                            ,"CIRCUITO SABANETA"
                                            ,"RASPA Y GANA VACACIONE SIN PREOCUPACIONES"
                                            ,"COMERCIAL BLU RADIO"
                                            ,"CITAS POR PAGINA WEB INTERNET"
                                            ,"EVENTO STA MARIA DE LOS ANGELES"
                                            ,"BLU RADIO"
                                            ,"RECORDATORIO DE KILOMETRAJE"
                                            ,"LLAMADA DEL CLIENTE"
                                            ,"REFERIDOS"
                                            ,"EVENTO DIA DE CAMPO JARDIN BOTANICO",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "",
                                            "GENERAL-FIME",
                                            "CITA - FIME"};

                                    try {

                                        general[indice][5] = texto.getText().toString().trim();
                                        if (grid2[Integer.parseInt(general[indice][5]) - 1].equals("")){
                                            Toast.makeText(RecepcionPanel.this, "No existe la razon!", Toast.LENGTH_SHORT).show();
                                            general[indice][5] = "";
                                            error.setText("");
                                        }else{
                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                            try {

                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            dataBaseHelper.close();
                                            error.setText(grid2[Integer.parseInt(general[indice][5]) - 1]);
                                            pasar(conexion, general);
                                            descripcion = grid2[Integer.parseInt(general[indice][5]) - 1];
                                            if (razon.equals("61") || razon.equals("62")){
                                                eventoRazon = "G";
                                            }
                                            razon = general[indice][5];

                                        }

                                    }catch (Throwable e){
                                        e.printStackTrace();
                                        general[indice][5] = "";
                                        error.setText("");
                                        Toast.makeText(RecepcionPanel.this, "No existe la razon!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (general[indice][4].trim().equals("PROMESA")) {
                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                        try {

                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        dataBaseHelper.close();
                                        promesa = texto.getText().toString().trim();
                                        general[indice][5] = texto.getText().toString().trim();
                                        pasar(conexion, general);
                                        error.setText("");
                                    } else {
                                        if (general[indice][4].trim().equals("SOT")) {
                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                            try {

                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            dataBaseHelper.close();
                                            soat = texto.getText().toString().trim();
                                            general[indice][5] = texto.getText().toString().trim();
                                            pasar(conexion, general);
                                            error.setText("");
                                        } else {
                                            if (general[indice][4].trim().equals("TECNICOECANICA")) {
                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                try {

                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                dataBaseHelper.close();
                                                tecno = texto.getText().toString().trim();
                                                general[indice][5] = texto.getText().toString().trim();
                                                pasar(conexion, general);
                                                error.setText("");
                                            } else {
                                                if (general[indice][4].trim().equals("SOLICITUD")) {
                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                    try {

                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                    dataBaseHelper.close();
                                                    solicitud = texto.getText().toString().trim();
                                                    general[indice][5] = texto.getText().toString().trim();
                                                    pasar(conexion, general);
                                                    error.setText("");
                                                } else {
                                                    if (general[indice][4].trim().equals("VEHICULO")) {
                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                        try {

                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        } catch (SQLException e) {
                                                            e.printStackTrace();
                                                        }

                                                        if (texto.getText().toString().trim().equals("A")
                                                                || texto.getText().toString().trim().equals("M")
                                                                || texto.getText().toString().trim().equals("B")
                                                                || texto.getText().toString().trim().equals("K")
                                                                || texto.getText().toString().trim().equals("C")
                                                                || texto.getText().toString().trim().equals("T")) {
                                                            dataBaseHelper.close();
                                                            tipoVehiculo = texto.getText().toString().trim();
                                                            general[indice][5] = texto.getText().toString().trim();
                                                            pasar(conexion, general);
                                                            error.setText("");
                                                        }else{
                                                            error.setText("No es un tipo de vehiculo!");
                                                            texto.setText("");
                                                        }

                                                    } else {
                                                        if (general[indice][4].trim().equals("UBICACION")) {
                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                            try {

                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                            dataBaseHelper.close();
                                                            cod_ubicacion = texto.getText().toString().trim();
                                                            general[indice][5] = texto.getText().toString().trim();
                                                            pasar(conexion, general);
                                                            error.setText("");

                                                        } else {
                                                            if (general[indice][4].trim().equals("OPERARIO")) {
                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                try {

                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                } catch (SQLException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                dataBaseHelper.close();
                                                                tecnico = texto.getText().toString().trim();
                                                                general[indice][5] = texto.getText().toString().trim();
                                                                pasar(conexion, general);
                                                                error.setText("");
                                                            } else {
                                                                if (general[indice][4].trim().equals("NOTAS")) {
                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                    try {

                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    } catch (SQLException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    dataBaseHelper.close();
                                                                    notas = texto.getText().toString().trim();
                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                    pasar(conexion, general);
                                                                    error.setText("");
                                                                } else {
                                                                    if (general[indice][4].trim().equals("LISTA")) {
                                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                        try {

                                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                        } catch (IOException e) {
                                                                            e.printStackTrace();
                                                                        } catch (SQLException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        dataBaseHelper.close();
                                                                        precio = texto.getText().toString().trim();
                                                                        general[indice][5] = texto.getText().toString().trim();
                                                                        pasar(conexion, general);
                                                                        error.setText("");
                                                                    } else {
                                                                        if (general[indice][4].trim().equals("MARCA")) {
                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                            try {

                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            } catch (SQLException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            dataBaseHelper.close();
                                                                            marca = texto.getText().toString().trim();
                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                            pasar(conexion, general);
                                                                            error.setText("");
                                                                        } else {
                                                                            if (general[indice][4].trim().equals("FORMA PAGO")) {
                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                try {

                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                } catch (IOException e) {
                                                                                    e.printStackTrace();
                                                                                } catch (SQLException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                dataBaseHelper.close();
                                                                                fpago = texto.getText().toString().trim();
                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                pasar(conexion, general);
                                                                                error.setText("");
                                                                            } else {
                                                                                if (general[indice][9].trim().equals("E")) {
                                                                                    if (isNumeric(atributo)) {
                                                                                        if (Integer.parseInt(atributo) >= Double.parseDouble(general[indice][10]) && Integer.parseInt(atributo) <= Double.parseDouble(general[indice][11])) {
                                                                                            if (general[indice][4].trim().equals("ROMBO")) {

                                                                                                String url = "";
                                                                                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                                                                                boolean ocupado = false;

                                                                                                String bodega = "";

                                                                                                if (sucursal.trim().equals("51")) {
                                                                                                    bodega = "1";
                                                                                                } else {
                                                                                                    if (sucursal.trim().equals("33")) {
                                                                                                        bodega = "2";
                                                                                                    } else {
                                                                                                        if (sucursal.trim().equals("10")) {
                                                                                                            bodega = "3";
                                                                                                        } else {
                                                                                                            if (sucursal.trim().equals("99")) {
                                                                                                                bodega = "11";
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }

                                                                                                params.add(new BasicNameValuePair("sCodEmpresa", empresa.trim()));
                                                                                                params.add(new BasicNameValuePair("sCodSucursal", bodega.trim()));
                                                                                                url = "http://" + ip + "/consultarRecurso.php";
                                                                                                params.add(new BasicNameValuePair("sParametro", "R"));
                                                                                                String resultServer = getHttpPost(url, params);
                                                                                                System.out.println("---------------------------------");
                                                                                                System.out.println(resultServer);
                                                                                                try {
                                                                                                    JSONArray jArray = new JSONArray(resultServer);
                                                                                                    ArrayList<String> array = new ArrayList<String>();
                                                                                                    for (int i = 0; i < jArray.length(); i++) {
                                                                                                        JSONObject json = jArray.getJSONObject(i);
                                                                                                        array.add((json.getString("rombo")));
                                                                                                        if (texto.getText().toString().equals(array.get(i).trim())) {
                                                                                                            ocupado = true;
                                                                                                            break;
                                                                                                        } else {
                                                                                                            ocupado = false;
                                                                                                        }
                                                                                                    }


                                                                                                } catch (JSONException e) {
                                                                                                    e.printStackTrace();
                                                                                                }


                                                                                                if (ocupado) {
                                                                                                    error.setText("El rombo no esta disponible");
                                                                                                } else {
                                                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                    try {

                                                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                    } catch (IOException e) {
                                                                                                        e.printStackTrace();
                                                                                                    } catch (SQLException e) {
                                                                                                        e.printStackTrace();
                                                                                                    }
                                                                                                    dataBaseHelper.close();
                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                    pasar(conexion, general);
                                                                                                    error.setText("");
                                                                                                }
                                                                                            } else {
                                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                try {

                                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                } catch (IOException e) {
                                                                                                    e.printStackTrace();
                                                                                                } catch (SQLException e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                                dataBaseHelper.close();
                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                pasar(conexion, general);
                                                                                                error.setText("");
                                                                                            }

                                                                                        } else {
                                                                                            error.setText("No esta en el rango!");
                                                                                        }
                                                                                    } else {
                                                                                        error.setText("No es numero!");
                                                                                    }
                                                                                } else {
                                                                                    if (general[indice][9].trim().equals("C")) {
                                                                                        if ((texto.getText().toString().trim().length() >= Double.parseDouble(general[indice][10])) && (texto.getText().toString().trim().length() <= Double.parseDouble(general[indice][11]))) {
                                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                                            pasar(conexion, general);
                                                                                            error.setText("");

                                                                                        } else {
                                                                                            if (Double.parseDouble(general[indice][10]) == Double.parseDouble(general[indice][11]) && texto.getText().toString().trim().length() == Double.parseDouble(general[indice][10])) {
                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                pasar(conexion, general);
                                                                                                error.setText("");
                                                                                            } else {
                                                                                                error.setText("No esta en el rango!");
                                                                                            }
                                                                                            error.setText("No esta en el rango!");
                                                                                        }
                                                                                    } else {
                                                                                        if (general[indice][9].trim().equals("F")) {
                                                                                            if (general[indice][18].trim().equals("PROMESA_ENTREGA")) {

                                                                                                date = getHora();
                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                pasar(conexion, general);
                                                                                                error.setText("");
                                                                                            } else {
                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                pasar(conexion, general);
                                                                                                error.setText("");
                                                                                            }
                                                                                        } else {
                                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                            try {

                                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                            } catch (IOException e) {
                                                                                                e.printStackTrace();
                                                                                            } catch (SQLException e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                            dataBaseHelper.close();
                                                                                        }
                                                                                    }
                                                                                }


                                                                                // params.add(new BasicNameValuePair("sNumOrden", num_orden));


                                /*if (resultado.get(4).equals("NIT")) {
                                    String cedula = texto.getText().toString().replaceAll("\n", "");
                                    String cedulafinal = "";


                                    String url3 = "http://" + finalIp + ":8080/update2.php";
                                    String resultServer3;
                                    try {

                                        Cedula = cedula.substring(47, 58);
                                        nombre1 = cedula.substring(95, 116);
                                        nombre2 = cedula.substring(117, 140);
                                        apellido1 = cedula.substring(58, 71);
                                        apellido2 = cedula.substring(72, 94);
                                        if (Cedula.substring(1, 2).equals("00")) {
                                            Cedula = cedula.substring(50, 58);
                                        }


                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("sNit", Cedula));
                                        params3.add(new BasicNameValuePair("sApellido1", apellido1));
                                        params3.add(new BasicNameValuePair("sApellido2", apellido2));
                                        params3.add(new BasicNameValuePair("sNombre1", nombre1));
                                        params3.add(new BasicNameValuePair("sNombre2", nombre2));
                                        resultServer3 = getHttpPost(url3, params3);
                                        System.out.println(resultServer3);

                                        String strStatusID = "0";
                                        String strError = "Unknow Status! 3";

                                        JSONObject c3;
                                        try {
                                            c3 = new JSONObject(resultServer3);
                                            strStatusID = c3.getString("StatusID");
                                            strError = c3.getString("Error");
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        if (strStatusID.equals("0")) {
                                            ad.setMessage(strError);
                                            ad.show();
                                            return false;
                                        } else {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }
                                    } catch (Exception e) {
                                        url3 = "http://" + finalIp + "/update.php";
                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                        params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                        params3.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                        params3.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                        params3.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                        params3.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                        params3.add(new BasicNameValuePair("sNumOrden", num_orden));
                                        resultServer3 = getHttpPost(url3, params3);
                                        String strStatusID = "0";
                                        String strError = "Unknow Status! 3";

                                        JSONObject c3;
                                        try {
                                            c3 = new JSONObject(resultServer3);
                                            strStatusID = c3.getString("StatusID");
                                            strError = c3.getString("Error");
                                        } catch (JSONException ex) {
                                            // TODO Auto-generated catch block
                                            ex.printStackTrace();
                                        }
                                        if (strStatusID.equals("0")) {
                                            ad.setMessage(strError);
                                            ad.show();
                                            return false;
                                        } else {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }
                                    }


                                } else {
                                    if (resultado.get(4).equals("MATRICULA")) {
                                        String matricula = texto.getText().toString().replaceAll("\n", "");
                                        matricula = matricula.replaceAll(" ", "");
                                        System.out.println("LONGITUD : " + matricula.length());
                                        System.out.println(texto.getText().toString());
                                        String matriculafinal = "";


                                        try {
                                            placa = matricula.substring(55, 61);
                                            vin = matricula.substring(61, 78);

                                            texto.setText("");

                                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarDatosMatricula.class);
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putString("placa", placa);
                                            bundle2.putString("vin", vin);
                                            intent.putExtras(bundle2);
                                            RecepcionPanel.this.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                            ad.setTitle("Error! ");
                                            ad.setIcon(android.R.drawable.btn_star_big_on);
                                            ad.setPositiveButton("Close", null);
                                            String url5 = "http://" + finalIp + "/update.php";

                                            List<NameValuePair> params5 = new ArrayList<NameValuePair>();


                                            params5.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                            params5.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                            params5.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                            params5.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                            params5.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                            params5.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                            params5.add(new BasicNameValuePair("sNumOrden", num_orden));
                                            String resultServer = getHttpPost(url, params);
                                            System.out.println(resultServer);

                                            String strStatusID = "0";
                                            String strError = "Unknow Status!";

                                            JSONObject c;
                                            try {
                                                Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                                pasar(conexion, general);
                                            }catch(Exception e1){
                                                Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                    } else {
                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);


                                        JSONObject c;
                                        try {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }catch(Exception e){
                                            Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }*/


                                                                                //TALL_ENCABEZA_ORDEN
                                                /*)}
                                        }


                                    }
                                }
                            }*/


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
                                    }
                                }
                            }
                        }
                    }
                }
                // }


            }
        }
    }


    public void ampliar(View v){
        nombre = new String();

        String strPath = "";
        if (procesoOriginal.trim().equals("88")) {
            if(general[indice][4].trim().equals("PLACA")) {

                nombre = id + "-" + placa + "-" + general[indice][6].trim();
                strPath = dirFoto + nombre + ".jpg";
            }else{
                nombre = general[indice][4].trim();
                //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
                strPath = dirFoto + nombre + ".jpg";
            }
        }else{
            nombre = general[indice][4].trim();
            //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
            strPath = dirFoto + nombre + ".jpg";
        }

        Bundle bundle = new Bundle();
        Intent intent = new Intent((Context) this, (Class) AmpliarImagen.class);
        bundle.putString("nombre", nombre);
        bundle.putString("path", strPath);
        intent.putExtras(bundle);
        this.startActivity(intent);
    }

    class ConsultarDatos extends AsyncTask<String, String, String> {

        String resultado;
        String user;
        String imgString = "";
        String resultServer1;
        @Override
        protected void onPreExecute(){
            try {
                mProgressDialog.dismiss();
                mProgressDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(String... f_url) {

            String url4 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params4 = new ArrayList<NameValuePair>();

            params4.add(new BasicNameValuePair("sParametro", "rutaFoto"));
            params4.add(new BasicNameValuePair("sAtributo", general[indice][4].trim()));
            params4.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params4.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params4.add(new BasicNameValuePair("sCodProceso", proceso));

            String resultServer4 = getHttpPost(url4, params4);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer4);

            String nom_ruta = "";
            String num_ip = "";
            try {

                JSONArray jArray = new JSONArray(resultServer4);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    nom_ruta = json.getString("nom_ruta");
                    num_ip = json.getString("num_ip");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            String strPath = "";
            if (procesoOriginal.trim().equals("88")) {
                if(general[indice][4].trim().equals("PLACA")) {

                    nombre = id + "-" + placa + "-" + general[indice][6].trim();
                    strPath = dirFoto + nombre + ".jpg";
                }else{
                    nombre = general[indice][4].trim() + "-" +  general[indice][5].trim();
                    //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
                    strPath = dirFoto + nombre + ".jpg";
                }
            }else{
                nombre = general[indice][4].trim() + "-" +  general[indice][5].trim();
            }

            //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
            String imagen =  dirFoto + nombre +  ".jpg";

            String url = "http://" + num_ip + "/uploadHydro.php";
            System.out.println(url);
            HttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
            HttpPost httpPost = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            FileBody fileBody = new FileBody(new File(imagen));


            builder.addPart("uploaded", fileBody);
            builder.addTextBody("id", id);
            builder.addTextBody("ruta", nom_ruta.trim());

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            try{
                httpClient.execute(httpPost);
                httpClient.getConnectionManager().shutdown();
            }catch (ClientProtocolException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }

            FileInputStream in = null;

            try {
                in = new FileInputStream(imagen);

                BufferedInputStream buf = new BufferedInputStream(in);
                byte[] bMapArray= new byte[buf.available()];
                buf.read(bMapArray);
                Bitmap bMap = BitmapFactory.decodeByteArray(bMapArray, 0, bMapArray.length);

                // get the base 64 string
                imgString = Base64.encodeToString(getBytesFromBitmap(bMap),
                        Base64.NO_WRAP);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String url1 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sAtributo", general[indice][4].trim()));
            params1.add(new BasicNameValuePair("sValor", general[indice][5].trim()));
            params1.add(new BasicNameValuePair("sImagen", imgString));
            params1.add(new BasicNameValuePair("sProceso", proceso));
            params1.add(new BasicNameValuePair("sId", id));
            params1.add(new BasicNameValuePair("sParametro", "guardarImagen"));



            resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1 );

            File imgFile = new  File(nombre);
            imgFile.delete();


            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {

            //addTextToFile(imgString);
            //System.out.println(imgString);

            mProgressDialog.dismiss();
            generateNoteOnSD(getApplicationContext(),id  +  general[indice][4].trim() + ".txt", imgString.trim());


        }
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void camara(View v) {

        //Si no existe crea la carpeta donde se guardaran las fotos
        //file.mkdirs();
        //String file = ruta_fotos + getCode() + ".jpg";
        TextView texto = (TextView) findViewById(R.id.textView_atributo);




        for (int i = 0; i < general.length; i++) {
            for (int j = 0; j < general[i].length; j++) {
                if (general[i][j].equals("null")){
                    general[i][j] = "";
                }
                System.out.println("GENERAL->" + general[i][j]);
            }
        }

        String strPath = "";
        if (procesoOriginal.trim().equals("88")) {
            if(general[indice][4].trim().equals("PLACA")) {

                nombre = id + "-" + placa + "-" + general[indice][6].trim();
                strPath = dirFoto + nombre + ".jpg";
            }else{
                nombre = general[indice][4].trim() + "-" + general[indice][5].trim();
                //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
                strPath = dirFoto + nombre + ".jpg";
            }
        }else{
            nombre = general[indice][4].trim() + "-" + general[indice][5].trim();
            //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
            strPath = dirFoto + nombre + ".jpg";

        }

        System.out.println("ESTE ES EL NOMBRE -> " + nombre + " general -> " +general[indice][4].trim());



        dirFoto =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/COLIBRI/" ;

        //String file = "/storage/E6E7-2A20/METRO/" + nombre;

        System.out.println(dirFoto);

        File fecha = new File(dirFoto);

        fecha.mkdirs();



        String file = dirFoto + nombre +  ".jpg";


        File mi_foto = new File(file);

        try {
            mi_foto.createNewFile();

            //
            Uri uri = Uri.fromFile(mi_foto);
            //Abre la camara para tomar la foto
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            //Retorna a la actividad
            startActivityForResult(cameraIntent,  CAMERA_REQUEST);
            ImageView myImage = (ImageView) findViewById(R.id.imageView);
            //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
            //String strPath = "/storage/E6E7-2A20/METRO/" + nombre;
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            Bitmap bm = BitmapFactory.decodeFile(strPath,options);

            myImage.setImageBitmap(bm);
            myImage.invalidate();

            general[indice][13] = file;


            //mostrarImagen();

        } catch (IOException ex) {
            Log.e("ERROR ", "Error:" + ex);
        }
    }

    public void uploadFoto(View v){
        new ConsultarDatos().execute("");
    }

    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = dateFormat.format(new Date());
        return date;
    }

    private String getDia() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String date = dateFormat.format(new Date());
        return date;
    }

    public void mostrarImagen(){
        ImageView myImage = (ImageView) findViewById(R.id.imageView);
        String strPath = "";
        if (!procesoOriginal.trim().equals("88")) {
            nombre = general[indice][4].trim();
            //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
            strPath = dirFoto + nombre + ".jpg";
        }else{
            if(general[indice][4].trim().equals("PLACA")) {

                nombre = id + "-" + placa + "-" + general[indice][6].trim();
                strPath = dirFoto + nombre + ".jpg";
            }else{
                nombre = general[indice][4].trim();
                //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
                strPath = dirFoto + nombre + ".jpg";
            }

        }
        //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
        System.out.println(strPath);
        //String strPath = "/storage/E6E7-2A20/METRO/" + nombre;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;




        Bitmap bm = BitmapFactory.decodeFile(strPath, options);



        if (bm==null) {
            myImage.setImageResource(R.drawable.camera);
        }else{
            myImage.setImageBitmap(bm);
        }





        /*try {
            if (imgFile.exists()) {
                ImageView myImage = (ImageView) findViewById(R.id.imageView);
                myImage.setImageURI(Uri.fromFile(imgFile));

            }
        }catch (Exception e){
        }*/
    }



    public void siguiente(View v){


        final DataBaseHelper dataBaseHelper = new DataBaseHelper((Context)this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        general = dataBaseHelper.consultarProceso("123", "METRO", sucursal, "1",1);
        if (indice==general.length-1){

        }else {
            indice++;
            actualizar(general, indice, conexion);
        }
    }





    public void cancelar(View v){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Saliendo...")
                .setMessage("Esta Seguro que desea dejar de recibir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void guardar(View v){
        final DataBaseHelper dataBaseHelper = new DataBaseHelper((Context)this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String [][] general;
        general = dataBaseHelper.consultarProceso("123", "METRO", sucursal, "01",1);
       /* for (int j = 0;j<Integer.parseInt(resultado.get(0).toString());j++) {
            for (int i = 0; i < resultado.size()-18; i++) {
                general[j][i] = resultado.get(i).toString();
            }
        }*/

        final ArrayList resultado = new ArrayList();



        /*if (Integer.parseInt(resultado.get(0).toString()) > 0){
            int cont = 0;

        }*/

        final String cod_empresa = resultado.get(0).toString();
        final String cod_sucursal = resultado.get(1).toString();
        final String cod_usuario = resultado.get(2).toString();
        final String cod_proceso = resultado.get(3).toString();
        final String cod_atributo = resultado.get(4).toString();
        final String cod_valor = resultado.get(5).toString();
        final String num_orden = resultado.get(7).toString();

        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(Integer.parseInt(resultado.get(6).toString()));
        dataBaseHelper.guardarProceso(cod_empresa, cod_sucursal, cod_usuario, cod_proceso, cod_atributo, texto.getText().toString(), num_orden);
        Toast.makeText(RecepcionPanel.this, (CharSequence) "se guardo correctamente!", Toast.LENGTH_SHORT).show();
        dataBaseHelper.close();
        texto.setText("");
    }

    public void atras(View v){

        final DataBaseHelper dataBaseHelper = new DataBaseHelper((Context)this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String [][] general;
        general = dataBaseHelper.consultarProceso("123", "METRO", sucursal, "01",1);
        if(indice == 0){

        }else{
            indice--;
            actualizar(general,indice,conexion);
        }
    }

    public void anteriorUltimo(View v){
        final DataBaseHelper dataBaseHelper = new DataBaseHelper((Context)this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String [][] general;
        general = dataBaseHelper.consultarProceso("123", "METRO", sucursal, "01",1);
        indice = general.length-1;
        actualizar(general, indice, conexion);
    }

    public void prueba (View v){
        TextView atributo = (TextView) findViewById(R.id.textView_atributo);
        atributo.setText("cambio");
    }

    public void anteriorPrimero(View v){
        final DataBaseHelper dataBaseHelper = new DataBaseHelper((Context)this);
        try {
            dataBaseHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            dataBaseHelper.openDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String [][] general;
        general = dataBaseHelper.consultarProceso("123", "METRO", sucursal, "01",1);
        indice= 0;
        actualizar(general, indice, conexion);
    }


    public void actualizar(final String[][] general, final int indice, final String conexion){




        final TextView error = (TextView) findViewById(R.id.textView_error);
        //final TextView informativo = (TextView) findViewById(R.id.textView_informativo);
        //.setTextColor(Color.rgb(21,130,125));
        error.setText("");
        //informativo.setText("");

        final ArrayList resultado = new ArrayList();
        final TextView atributo = (TextView) findViewById(R.id.textView_atributo);
        final ImageView imagen = (ImageView) findViewById(R.id.imageView);

        TextView atributo1 = (TextView) findViewById(R.id.textView_atributo);
        atributo1.setBackgroundColor(Color.WHITE);
        atributo1.setTextColor(Color.BLACK);

        try {
            texto.setText(general[indice][5].trim());
        }catch (Exception e){
            e.printStackTrace();
        }

        Button buscar = (Button) findViewById(R.id.button_buscar);
        Button anadir = (Button) findViewById(R.id.button_anadir);

        if ( general[indice][15].trim().equals(general[indice][3].trim())) {
            buscar.setVisibility(View.INVISIBLE);
            anadir.setVisibility(View.INVISIBLE);
        }else {
            imagen.setImageResource(R.drawable.add);
            buscar.setVisibility(View.VISIBLE);
            anadir.setVisibility(View.VISIBLE);
        }

        if (general[indice][4].trim().equals("RAZON")) {
            error.setTextColor(Color.RED);
            error.setText(descripcion);
        }

        if (general[indice][4].trim().equals("SOT")) {
            texto.setText(soat);
            general[indice][5] = soat;
        }

        if (general[indice][4].trim().equals("PLACA")) {
            texto.setText(cod_placa);
            general[indice][5] = cod_placa;
        }

        if (general[indice][4].trim().equals("ROMBO")) {
            texto.setText(num_rombo);
            general[indice][5] = num_rombo;
        }

        if (general[indice][4].trim().equals("UBICACION")) {
            texto.setText(cod_ubicacion);
            general[indice][5] = cod_ubicacion;
        }

        if (general[indice][4].trim().equals("SOLICITUD")) {
            texto.setText(solicitud);
            general[indice][5] = solicitud;
        }

        if (general[indice][4].trim().equals("OPERARIO")) {
            tecnico = general[indice][5].trim();
            texto.setText(tecnico);
            general[indice][5] = tecnico;
        }

        if (general[indice][4].trim().equals("NOTAS")) {
            texto.setText(notas);
            general[indice][5] = notas;
        }

        if (general[indice][4].trim().equals("MARCA")) {
            marca = general[indice][5].trim();
            texto.setText(marca);
            general[indice][5] = marca;
        }

        if (general[indice][4].trim().equals("FORMA PAGO")) {
            texto.setText(fpago);
            general[indice][5] = fpago;
        }

        if (general[indice][4].trim().equals("PROMESA")) {
            texto.setText(promesa);
            general[indice][5] = promesa;
        }

        if (general[indice][4].trim().equals("RAZON")) {
            texto.setText(razon);
            general[indice][5] = razon;
        }



        if (general[indice][4].trim().equals("KM")) {
            texto.setText(kilometraje);
            general[indice][5] = kilometraje;
        }

        if (general[indice][4].trim().equals("NIT")) {
            texto.setText(num_nit);
            general[indice][5] = num_nit;
        }

        if (general[indice][4].trim().equals("PLACA") && encuentraPlaca.equals("false")) {
            error.setText("Placa no existe, se creara!");
        }

        if (general[indice][4].trim().equals("NIT") && encuentraNit.equals("false")) {
            error.setText("Nit no existe, se creara!");
        }

        if (general[indice][4].trim().equals("VEHICULO")) {
            if (tipoVehiculo.equals("null")){
                tipoVehiculo = "";
            }
            tipoVehiculo = general[indice][5].trim();
            texto.setText(tipoVehiculo);
            general[indice][5] = tipoVehiculo;
        }

        if (general[indice][4].trim().equals("TECNICOECANICA")) {
            texto.setText(tecno);
            general[indice][5] = tecno;
        }

        if (general[indice][4].trim().equals("LISTA")) {
            texto.setText(precio);
            general[indice][5] = precio;
        }



        if (general[indice][4].trim().equals("ROMBO")) {
            if ( cita.trim().equals("") || cita.trim().equals("null")){
                atributo1.setBackgroundColor(Color.BLUE);
                atributo1.setTextColor(Color.WHITE);
            }else{
                error.setText("Hora cita : " + horacita.substring(0,5));
                atributo1.setBackgroundColor(Color.YELLOW);
                atributo1.setTextColor(Color.BLUE);
                eventoCita = "C";
            }
        }



        //for(int j = 0;j<general[0].length;j++){
        //    resultado.add(general[indice][j]);
        //}


       /* String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sCodUser", resultado.get(2).toString().trim()));
        params.add(new BasicNameValuePair("sCodEmpresa", resultado.get(0).toString().trim()));
        params.add(new BasicNameValuePair("sCodAtributo", resultado.get(4).toString().trim()));
        params.add(new BasicNameValuePair("sCodSucursal",sucursal));
        params.add(new BasicNameValuePair("sParametro", "actualizar"));

        final String resultServer  = getHttpPost(url, params);
        System.out.println("-----------ACTUALIZAR------------");
        System.out.println("coduser - " + resultado.get(2).toString());
        System.out.println("codempresa - " + resultado.get(0).toString());
        System.out.println("codatributo + " + resultado.get(4).toString());
        System.out.println(resultServer);
        try {
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add((json.getString("cod_valor")));
            }
            texto.setText(array.get(0));
        }catch (JSONException e){
            e.printStackTrace();
        }*/

        //texto.setText(general[indice][5].trim());


        atributo.setText(general[indice][4].trim());
        //atributo.setText(resultado.get(7) + ". " + resultado.get(4).toString().trim());


        //if (resultado.get(13).equals("ROMBO.JPG")) imagen.setImageResource(R.drawable.rombo1);
        //if (resultado.get(13).equals("PLACA.JPG")) imagen.setImageResource(R.drawable.placa6);
        //if (resultado.get(13).equals("NIT.JPG")) imagen.setImageResource(R.drawable.cedula1);
        /*if (Integer.parseInt(resultado.get(0).toString()) > 0){
            int cont = 0;

        }*/

        mostrarImagen();

        texto.requestFocus();

        final String cod_empresa = general[indice][0];
        final String cod_sucursal =general[indice][1];
        final String cod_usuario = general[indice][2];
        final String[] cod_proceso = {general[indice][3]};
        final String[] cod_atributo = {general[indice][4]};
        final String cod_valor = general[indice][5];
        final String num_orden = general[indice][7];

        /*final String cod_empresa = resultado.get(0).toString();
        final String cod_sucursal = resultado.get(1).toString();
        final String cod_usuario = resultado.get(2).toString();
        final String cod_proceso = resultado.get(3).toString();
        final String cod_atributo = resultado.get(4).toString();
        final String cod_valor = resultado.get(5).toString();
        final String num_orden = resultado.get(7).toString();*/


        if (conexion.equals("local")) {


            final String finalIp = ip;

            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
                public boolean onDoubleTap(MotionEvent e) {
                    if (texto.getText().toString().matches("") && (cod_atributo[0].trim().equals("ROMBO")
                            || cod_atributo[0].trim().equals("UBICACION") || cod_atributo[0].trim().equals("OPERARIO")
                            || cod_atributo[0].trim().equals("NUMERO OT")) || cod_atributo[0].trim().equals("PROMESA")
                            || cod_atributo[0].trim().equals("RAZON2") || cod_atributo[0].trim().equals("RAZON")) {

                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("conexion", conexion);
                        bundle2.putString("ip", ip);
                        bundle2.putString("atributo", cod_atributo[0].trim());
                        bundle2.putString("empresa", cod_empresa);
                        bundle2.putString("sucursal", cod_sucursal);
                        intent.putExtras(bundle2);
                        RecepcionPanel.this.startActivityForResult(intent, request_code);
                    }else{
                        if (general[indice][4].trim().equals("SOAT") || general[indice][4].trim().equals("TECNICOMECANICA") ) {
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) CalendarioSoat.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("parametro",general[indice][4].trim());
                            intent.putExtras(bundle2);
                            startActivityForResult(intent, request_code);
                        }//else{
                    }
                       /* if (general[indice][9].toString().equals("F")){
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) Calendario.class);
                            RecepcionPanel.this.startActivityForResult(intent,request_code);
                        }
                    }*/

                        /*String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                        params2.add(new BasicNameValuePair("sCodUser", resultado.get(2).toString()));
                        params2.add(new BasicNameValuePair("sCodEmpresa", resultado.get(0).toString()));
                        params2.add(new BasicNameValuePair("sCodAtributo", resultado.get(4).toString()));
                        params2.add(new BasicNameValuePair("sCodSucursal",sucursal));
                        params2.add(new BasicNameValuePair("sParametro", "actualizar"));


                        String resultServer2 = getHttpPost(url2, params2);
                        System.out.println("---------------------------------");
                        System.out.println(resultServer2);
                        ArrayList<String> array = new ArrayList<String>();
                        try {
                            JSONArray jArray = new JSONArray(resultServer2);

                            //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json = jArray.getJSONObject(i);
                                array.add((json.getString("cod_valor")));
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        if (array.size()>0) {
                            String url4 = "http://" + ip + "/updateGeneral.php";

                            List<NameValuePair> params4 = new ArrayList<NameValuePair>();


                            params4.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                            params4.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                            params4.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                            params4.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                            params4.add(new BasicNameValuePair("sParametro", "update3"));

                            String resultServer4 = getHttpPost(url4, params4);

                            JSONObject c4;
                            try {

                                Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                pasar(conexion, general);
                            }catch(Exception e1){
                                Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } else {





                            final AlertDialog.Builder ad = new AlertDialog.Builder(RecepcionPanel.this);

                            ad.setTitle("Error! ");
                            ad.setIcon(android.R.drawable.btn_star_big_on);
                            ad.setPositiveButton("Close", null);
                            InputStream isr = null;
                            String url = "http://" + finalIp + "/update.php";

                            List<NameValuePair> params = new ArrayList<NameValuePair>();


                            params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                            params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                            params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                            params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                            params.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                            params.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                            params.add(new BasicNameValuePair("sNumOrden", num_orden));

                            */

                    // params.add(new BasicNameValuePair("sNumOrden", num_orden));


                            /*if (resultado.get(4).equals("NIT")) {
                                String cedula = texto.getText().toString().replaceAll("\n", "");
                                String cedulafinal = "";

                                String url3 = "http://" + finalIp + ":8080/update2.php";
                                String resultServer3;
                                try {

                                    Cedula = cedula.substring(47, 58);
                                    nombre1 = cedula.substring(95, 116);
                                    nombre2 = cedula.substring(117, 140);
                                    apellido1 = cedula.substring(58, 71);
                                    apellido2 = cedula.substring(72, 94);
                                    if (Cedula.substring(1, 2).equals("00")) {
                                        Cedula = cedula.substring(50, 58);
                                    }


                                    List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                    params3.add(new BasicNameValuePair("sNit", Cedula));
                                    params3.add(new BasicNameValuePair("sApellido1", apellido1));
                                    params3.add(new BasicNameValuePair("sApellido2", apellido2));
                                    params3.add(new BasicNameValuePair("sNombre1", nombre1));
                                    params3.add(new BasicNameValuePair("sNombre2", nombre2));
                                    resultServer3 = getHttpPost(url3, params3);
                                    System.out.println(resultServer3);

                                    String strStatusID = "0";
                                    String strError = "Unknow Status! 3";

                                    JSONObject c3;
                                    try {
                                        c3 = new JSONObject(resultServer3);
                                        strStatusID = c3.getString("StatusID");
                                        strError = c3.getString("Error");
                                    } catch (JSONException ex) {
                                        // TODO Auto-generated catch block
                                        ex.printStackTrace();
                                    }
                                    if (strStatusID.equals("0")) {
                                        ad.setMessage(strError);
                                        ad.show();
                                        return false;
                                    } else {
                                        Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        pasar(conexion, general);
                                    }
                                } catch (Exception ec) {
                                    url3 = "http://" + finalIp + "/update.php";
                                    List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                    params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                    params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                    params3.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                    params3.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                    params3.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                    params3.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                    params3.add(new BasicNameValuePair("sNumOrden", num_orden));
                                    resultServer3 = getHttpPost(url3, params3);
                                    String strStatusID = "0";
                                    String strError = "Unknow Status! 3";

                                    JSONObject c3;
                                    try {
                                        c3 = new JSONObject(resultServer3);
                                        strStatusID = c3.getString("StatusID");
                                        strError = c3.getString("Error");
                                    } catch (JSONException ex) {
                                        // TODO Auto-generated catch block
                                        ex.printStackTrace();
                                    }
                                    if (strStatusID.equals("0")) {
                                        ad.setMessage(strError);
                                        ad.show();
                                        return false;
                                    } else {
                                        Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        pasar(conexion, general);
                                    }
                                }


                            } else {
                                if (resultado.get(4).equals("MATRICULA")) {
                                    String matricula = texto.getText().toString().replaceAll("\n", "");
                                    matricula = matricula.replaceAll(" ", "");
                                    System.out.println("LONGITUD : " + matricula.length());
                                    System.out.println(texto.getText().toString());
                                    String matriculafinal = "";


                                Character  numAux = cedula.charAt(50);
                                String num = numAux.toString();
                                while (num.charAt(0)>='0' && num.charAt(0)<='9'){
                                    cedulafinal = cedulafinal + num.charAt(0);
                                }

                                    try {
                                        placa = matricula.substring(55, 61);
                                        vin = matricula.substring(61, 78);

                                        texto.setText("");

                                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarDatosMatricula.class);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString("placa", placa);
                                        bundle2.putString("vin", vin);
                                        intent.putExtras(bundle2);
                                        RecepcionPanel.this.startActivity(intent);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                        ad.setTitle("Error! ");
                                        ad.setIcon(android.R.drawable.btn_star_big_on);
                                        ad.setPositiveButton("Close", null);
                                        String url5 = "http://" + finalIp + "/update.php";

                                        List<NameValuePair> params5 = new ArrayList<NameValuePair>();


                                        params5.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                        params5.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                        params5.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                        params5.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                        params5.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                        params5.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                        params5.add(new BasicNameValuePair("sNumOrden", num_orden));
                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);


                                        JSONObject c;
                                        try {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }catch(Exception e1){
                                            Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                } else {
                                    String resultServer = getHttpPost(url, params);
                                    System.out.println(resultServer);

                                    String strStatusID = "0";
                                    String strError = "Unknow Status!";

                                    JSONObject c;
                                    try {

                                        Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();

                                        pasar(conexion, general);
                                    }catch(Exception e1){
                                        Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }*/


                            /*String file = "storage/sdcard0/METRO/" + nombre;
                            File mi_foto = new File(file);
                            String file2 = "storage/sdcard0/METRO/";
                            File mi_foto2 = new File(file2);
                            mi_foto.renameTo(mi_foto2);*/
                    //else{
                    //    Toast.makeText(getApplicationContext()," Este registro ya esta en la base de datos !" , Toast.LENGTH_SHORT).show();
                    //}
                    // }

                    return true;
                }
            });

            if ( !(general[indice][18].trim().equals(general[indice][3].trim()))) {




                Button BotonBuscar = (Button) findViewById(R.id.button_buscar);
                BotonBuscar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if (general[indice][4].trim().equals("NIT")) {

                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarCliente.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("conexion", conexion);
                            bundle2.putString("ip", ip);
                            bundle2.putString("atributo", texto.getText().toString().trim());
                            bundle2.putString("empresa", cod_empresa);
                            bundle2.putString("sucursal", sucursal);
                            bundle2.putString("parametro", parametro);
                            intent.putExtras(bundle2);
                            RecepcionPanel.this.startActivityForResult(intent, request_code);
                            error.setText("");
                        }else{

                            if (texto.getText().toString().matches("") && (cod_atributo[0].trim().equals("ROMBO")
                                    || cod_atributo[0].trim().equals("UBICACION") || cod_atributo[0].trim().equals("OPERARIO")
                                    || cod_atributo[0].trim().equals("NUMERO OT")) || cod_atributo[0].trim().equals("PROMESA")
                                    || cod_atributo[0].trim().equals("RAZON2") || cod_atributo[0].trim().equals("RAZON")) {

                                Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("conexion", conexion);
                                bundle2.putString("ip", ip);
                                bundle2.putString("atributo", cod_atributo[0].trim());
                                bundle2.putString("empresa", cod_empresa);
                                bundle2.putString("sucursal", cod_sucursal);
                                intent.putExtras(bundle2);
                                RecepcionPanel.this.startActivityForResult(intent, request_code);
                            }else{
                                if (general[indice][4].trim().equals("VEHIC")){
                                    Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarModelo.class);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("placa", cod_placa);
                                    bundle2.putString("ip", ip);
                                    intent.putExtras(bundle2);
                                    startActivityForResult(intent, request_code);
                                }else{
                                    if (general[indice][4].trim().equals("SOAT") || general[indice][4].trim().equals("TECNICOMECANICA")
                                            || general[indice][4].trim().equals("EXTINTOR")) {
                                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) CalendarioSoat.class);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString("parametro",general[indice][4].trim());
                                        intent.putExtras(bundle2);
                                        startActivityForResult(intent, request_code);
                                    }else{
                                        if (general[indice][4].trim().equals("LISTA")) {

                                            if (tipoVehiculo.equals("")){
                                                Toast.makeText(RecepcionPanel.this, "Seleccione un tipo de vehiculo!", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarLista.class);
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putString("conexion", conexion);
                                                bundle2.putString("parametro", general[indice][4].trim());
                                                bundle2.putString("tipoVehiculo",tipoVehiculo);
                                                bundle2.putString("ip", ip);
                                                bundle2.putString("atributo", cod_atributo[0].trim());
                                                bundle2.putString("empresa", cod_empresa);
                                                bundle2.putString("sucursal", cod_sucursal);
                                                intent.putExtras(bundle2);
                                                RecepcionPanel.this.startActivityForResult(intent, request_code);
                                            }
                                        }else{
                                            if (general[indice][4].trim().equals("FECHA INICIAL")) {
                                                final View dialogView = View.inflate(RecepcionPanel.this, R.layout.date_time_picker, null);
                                                final AlertDialog alertDialog = new AlertDialog.Builder(RecepcionPanel.this).create();

                                                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                                                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                                                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                                                datePicker.getMonth(),
                                                                datePicker.getDayOfMonth(),
                                                                timePicker.getCurrentHour(),
                                                                timePicker.getCurrentMinute());

                                                        general[indice][5] = String.valueOf(calendar.getTimeInMillis());
                                                        alertDialog.dismiss();
                                                    }});
                                                alertDialog.setView(dialogView);
                                                alertDialog.show();
                                            }else {
                                                Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putString("conexion", conexion);
                                                bundle2.putString("ip", ip);
                                                bundle2.putString("atributo", cod_atributo[0].trim());
                                                bundle2.putString("empresa", cod_empresa);
                                                bundle2.putString("sucursal", cod_sucursal);
                                                intent.putExtras(bundle2);
                                                RecepcionPanel.this.startActivityForResult(intent, request_code);
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                });

                Button BotonAnadir = (Button) findViewById(R.id.button_anadir);
                BotonAnadir.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        proceso = general[indice][18].trim();
                        titulo = general[indice][4].trim();

                        cod_atributo[0] = general[indice][4].trim();

                        nombreTabla = generalAux[indice][19].trim();

                        System.out.println("TABLAAAAA " +  nombreTabla +  " " + cod_atributo[0]);

                        System.out.println(user + " " + pass + " " + proceso);
                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) RecepcionPanel.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("user", user);
                        bundle2.putString("esAutonext", "false");
                        bundle2.putString("pass", pass);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("empresa", empresa);
                        bundle2.putString("conexion", conexion);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("nuevoRegistro", "NO");
                        bundle2.putString("ip", ip);
                        bundle2.putString("titulo", titulo);
                        bundle2.putString("proceso", "123");
                        bundle2.putString("nombreTabla",nombreTabla);
                        bundle2.putString("codAtributo",cod_atributo[0].trim());

                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }
                });



            }else {
            }

            texto.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
            texto.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {


                        /*if (texto.getText().toString().matches("") && (general[indice][5].equals("ROMBO") || general[indice][5].equals("UBICACION") || general[indice][5].equals("MECANICO"))) {
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("conexion", conexion);
                            bundle2.putString("ip", ip);
                            bundle2.putString("atributo", cod_atributo);
                            intent.putExtras(bundle2);
                            RecepcionPanel.this.startActivityForResult(intent, request_code);
                            Toast.makeText(getApplicationContext(), resultado.get(4) + "  ATRIBUTO ", Toast.LENGTH_SHORT).show();

                        } else {*/
                            /*String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                            params2.add(new BasicNameValuePair("sCodUser", general[indice][2].toString()));
                            params2.add(new BasicNameValuePair("sCodEmpresa", general[indice][0].toString()));
                            params2.add(new BasicNameValuePair("sCodAtributo", general[indice][4].toString()));
                            params2.add(new BasicNameValuePair("sCodSucursal",sucursal));
                            params2.add(new BasicNameValuePair("sParametro", "actualizar"));


                            String resultServer2 = getHttpPost(url2, params2);
                            System.out.println("---------------------------------");
                            System.out.println(resultServer2);
                            ArrayList<String> array = new ArrayList<String>();
                            try {
                                JSONArray jArray = new JSONArray(resultServer2);

                                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add((json.getString("cod_valor")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (array.size()>0) {
                                String url4 = "http://" + ip + "/updateGeneral.php";

                                List<NameValuePair> params4 = new ArrayList<NameValuePair>();


                                params4.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                params4.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                params4.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                params4.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                params4.add(new BasicNameValuePair("sParametro", "update3"));

                                String resultServer4 = getHttpPost(url4, params4);
                                System.out.println(resultServer4);


                                try {
                                    Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    pasar(conexion, general);
                                }catch (Exception e){
                                    Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                }
                            } else {*/





                                /*final AlertDialog.Builder ad = new AlertDialog.Builder(RecepcionPanel.this);

                                ad.setTitle("Error! ");
                                ad.setIcon(android.R.drawable.btn_star_big_on);
                                ad.setPositiveButton("Close", null);
                                InputStream isr = null;
                                String url = "http://" + finalIp + "/update.php";

                                List<NameValuePair> params = new ArrayList<NameValuePair>();


                                params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                params.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                params.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                params.add(new BasicNameValuePair("sNumOrden", num_orden));

                                String resultServer = getHttpPost(url, params);
                                System.out.println(resultServer);


                                JSONObject c;
                                try {
                                    Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    pasar(conexion, general);
                                }catch(Exception e){
                                    Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                }*/

                        String atributo = texto.getText().toString().trim();
                        error.setTextColor(Color.RED);

                        String file = "storage/sdcard0/METRO/" + nombre;
                        File mi_foto = new File(file);
                        String file2 = "storage/sdcard0/METRO/";
                        File mi_foto2 = new File(file2);
                        mi_foto.renameTo(mi_foto2);

                        EditText texto1 = (EditText) findViewById(R.id.editText_texto);

                        if (general[indice][4].trim().equals("ROMBO")) {
                            error.setText("");
                            num_rombo = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("PLACA")) {
                            cod_placa = texto.getText().toString().trim();

                        }
                        if (general[indice][4].trim().equals("PLACA") && encuentraPlaca.equals("false")) {
                            error.setText("Placa no existe, se creara!");
                        }
                        if (general[indice][4].trim().equals("UBICACION")) {
                            cod_ubicacion = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("NIT")) {
                            num_nit = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("KM")) {
                            kilometraje = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("RAZON")) {
                            if (razon.equals("61") || razon.equals("62")){
                                eventoRazon = "G";
                            }
                            razon = texto.getText().toString().trim();
                            error.setText(descripcion);
                        }

                        if (general[indice][4].trim().equals("SOT")) {
                            soat = texto.getText().toString().trim();
                        }

                        if (general[indice][4].trim().equals("TECNICOECANICA")) {
                            tecno = texto.getText().toString().trim();
                        }

                        if (general[indice][4].trim().equals("CEDULA")) {
                            num_nit = texto.getText().toString().trim();
                        }


                        if (general[indice][4].trim().equals("PLACA")) {

                            /*

                            cita = new String();
                            horacita = new String();

                            String url4 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params4 = new ArrayList<NameValuePair>();

                            params4.add(new BasicNameValuePair("sParametro", "placanit"));
                            params4.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer4 = getHttpPost(url4, params4);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer4);

                            try {

                                JSONArray jArray = new JSONArray(resultServer4);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("nit"));
                                }
                                if (array.get(0).equals("null")){

                                }else {
                                    num_nit = array.get(0);
                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                    try {

                                        guardarMovimiento("NIT", num_nit);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    dataBaseHelper.close();
                                }
                            } catch (JSONException e) {

                                error.setText("");
                                //informativo.setTextColor(Color.BLUE);
                                e.printStackTrace();
                            }



                            String url3 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

                            params3.add(new BasicNameValuePair("sParametro", "CITA"));
                            params3.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer3 = getHttpPost(url3, params3);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer3);

                            try {

                                JSONArray jArray = new JSONArray(resultServer3);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("placa"));
                                    array.add(json.getString("fecha"));
                                }
                                cita = array.get(0);
                                horacita = array.get(1);
                                eventoCita = "C";
                            } catch (JSONException e) {

                                error.setText("");
                                //informativo.setTextColor(Color.BLUE);
                                e.printStackTrace();
                            }

                            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

                            params1.add(new BasicNameValuePair("sParametro", "MODELO"));
                            params1.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer1 = getHttpPost(url1, params1);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer1);
                            try {

                                JSONArray jArray = new JSONArray(resultServer1);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("modelo"));
                                }

                                modelo = array.get(0);

                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento("MODELO", modelo);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();

                            } catch (JSONException e) {

                                error.setText("");
                                //informativo.setTextColor(Color.BLUE);
                                e.printStackTrace();
                            }

                            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                            params.add(new BasicNameValuePair("sParametro", "placa"));
                            params.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer = getHttpPost(url, params);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer);
                            String placa = "";
                            try {

                                JSONArray jArray = new JSONArray(resultServer);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("placa"));
                                    placa = array.get(0);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                            params2.add(new BasicNameValuePair("sParametro", "fechas"));
                            params2.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer2 = getHttpPost(url2, params2);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer2);
                            try {

                                JSONArray jArray = new JSONArray(resultServer2);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("soat"));
                                    array.add(json.getString("tecno"));
                                }


                                soat = array.get(0);

                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento("SOAT", soat);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();

                                tecno = array.get(1);


                                try {

                                    guardarMovimiento("TECNICOMECANICA", tecno);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();


                            } catch (JSONException e) {
                                error.setText("");
                                e.printStackTrace();
                            }
                            */

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                            try {

                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                            dataBaseHelper.close();
                            general[indice][5] = texto.getText().toString().trim();
                            pasar(conexion, general);
                            error.setText("");
                            //error.setText("No existe la placa!");
                            encuentraPlaca = "false";

                        } else {
                            if (general[indice][4].trim().equals("CEDULA")) {
                                String cedula = texto.getText().toString().replaceAll("\n", "");


                                try {
                                    Cedula = cedula.substring(47, 58);
                                    nombre1 = cedula.substring(95, 116);
                                    nombre2 = cedula.substring(117, 140);
                                    apellido1 = cedula.substring(58, 71);
                                    apellido2 = cedula.substring(72, 94);
                                    if (Cedula.substring(1, 2).equals("00")) {
                                        Cedula = cedula.substring(50, 58);
                                    }


                                    if (general[indice + 1][4].trim().equals("NOMBRES")) {
                                        general[indice + 1][5] = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;

                                    }

                                    texto.setText(Cedula);
                                    general[indice][5] = texto.getText().toString().trim();
                                    pasar(conexion, general);
                                    error.setText("");
                                } catch (Exception e) {
                                    general[indice][5] = texto.getText().toString().trim();
                                    pasar(conexion, general);
                                    error.setText("");
                                    e.printStackTrace();
                                }
                            } else {

                                if (general[indice][4].trim().equals("NOMBRES")) {
                                    String cedula = texto.getText().toString().replaceAll("\n", "");


                                    try {
                                        Cedula = cedula.substring(47, 58);
                                        nombre1 = cedula.substring(95, 116);
                                        nombre2 = cedula.substring(117, 140);
                                        apellido1 = cedula.substring(58, 71);
                                        apellido2 = cedula.substring(72, 94);
                                        if (Cedula.substring(1, 3).equals("00")) {
                                            Cedula = cedula.substring(50, 58);
                                        }

                                        if (general[indice + 1][4].trim().equals("CEDULA")) {
                                            general[indice + 1][5] = Cedula;
                                        }

                                        for (int i = 0; i < general.length; i++) {
                                            if (general[i][4].trim().equals("NIT")) {
                                                general[i][5] = Cedula;
                                            }
                                        }

                                        texto.setText(remove(nombre1) + " " + remove(nombre2) + " " + remove(apellido1) + " " + remove(apellido2));
                                        general[indice][5] = remove(nombre1) + " " + remove(nombre2) + " " + remove(apellido1) + " " + remove(apellido2);
                                        pasar(conexion, general);
                                        error.setText("");
                                    } catch (Exception e) {
                                        general[indice][5] = texto.getText().toString().trim();
                                        pasar(conexion, general);
                                        error.setText("");
                                        e.printStackTrace();
                                    }
                                } else {


                                    if (general[indice][4].trim().equals("KM")) {

                                        /*String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                                        params.add(new BasicNameValuePair("sParametro", "km_anterior"));
                                        params.add(new BasicNameValuePair("sPlaca", cod_placa));

                                        String resultServer = getHttpPost(url, params);
                                        System.out.println("---------------------------------resultserver----------------");
                                        System.out.println(resultServer);
                                        try {

                                            JSONArray jArray = new JSONArray(resultServer);
                                            ArrayList<String> array = new ArrayList<String>();
                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json = jArray.getJSONObject(i);
                                                array.add(json.getString("km_anterior"));
                                                km_anterior = array.get(0);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if (km_anterior.trim().equals("null"))
                                            km_anterior = "1";

                                        if (Integer.parseInt(km_anterior.toString()) < Integer.parseInt(texto.getText().toString())) {
                                        */
                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                        try {

                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                        dataBaseHelper.close();
                                        general[indice][5] = texto.getText().toString().trim();
                                        pasar(conexion, general);
                                        error.setText("");

                                        /*} else {
                                            //informativo.setTextColor(Color.BLUE);
                                            error.setText("Anterior : " + km_anterior + ", debe ser mayor!");


                                        }*/
                                    } else {

                                        if (general[indice][4].trim().equals("NIT")) {

                                            /*String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                                            params.add(new BasicNameValuePair("sParametro", "nit"));
                                            params.add(new BasicNameValuePair("sNit", num_nit));

                                            String resultServer = getHttpPost(url, params);
                                            System.out.println("---------------------------------resultserver----------------");
                                            System.out.println(resultServer);
                                            String nit = "";
                                            try {

                                                JSONArray jArray = new JSONArray(resultServer);
                                                ArrayList<String> array = new ArrayList<String>();
                                                for (int i = 0; i < jArray.length(); i++) {
                                                    JSONObject json = jArray.getJSONObject(i);
                                                    array.add(json.getString("nit"));
                                                    array.add(json.getString("mail"));
                                                    nit = array.get(0);
                                                    email = array.get(1);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }*/
                                            //if (num_nit.trim().equals(nit)) {
                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                            try {

                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                            dataBaseHelper.close();
                                            general[indice][5] = texto.getText().toString().trim();
                                            pasar(conexion, general);
                                            error.setText("");
                                            encuentraNit = "true";
                                            /*} else {

                                                Intent i =  new Intent(RecepcionPanel.this,CrearCliente.class);
                                                i.putExtra("nit",texto.getText().toString().trim());
                                                i.putExtra("ip",ip.trim());
                                                startActivity(i);
                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                try {

                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                                dataBaseHelper.close();

                                                general[indice][5] = texto.getText().toString().trim();
                                                error.setText("");
                                                //error.setText("No existe el nit!");
                                                //general[indice][5] = texto.getText().toString().trim();
                                                pasar(conexion, general);

                                            }*/
                                        } else {
                                            if (general[indice][4].trim().equals("PLACA")) {
                                                /*String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                                List<NameValuePair> params = new ArrayList<NameValuePair>();

                                                params.add(new BasicNameValuePair("sParametro", "PLACA"));
                                                params.add(new BasicNameValuePair("sPlaca", cod_placa));

                                                String resultServer = getHttpPost(url, params);
                                                System.out.println("---------------------------------resultserver----------------");
                                                System.out.println(resultServer);
                                                String nombre = "";
                                                try {

                                                    JSONArray jArray = new JSONArray(resultServer);
                                                    ArrayList<String> array = new ArrayList<String>();
                                                    for (int i = 0; i < jArray.length(); i++) {
                                                        JSONObject json = jArray.getJSONObject(i);
                                                        array.add(json.getString("nombre"));
                                                        sInformativo = sInformativo + "\n" + array.get(0);
                                                    }

                                                } catch (JSONException e) {

                                                    error.setText("");
                                                    error.setText("No existe la placa!");
                                                    //informativo.setTextColor(Color.BLUE);
                                                    e.printStackTrace();
                                                }




                                                if (sInformativo.equals("null") || sInformativo.equals("")) {

                                                    error.setText("No existe la placa!");
                                                } else {*/
                                                error.setText(sInformativo);
                                                //informativo.setTextColor(Color.BLUE);
                                                general[indice][5] = texto.getText().toString().trim();
                                                pasar(conexion, general);
                                                error.setText("");
                                                //}
                                            } else {
                                                if (general[indice][4].trim().equals("RAZON")) {
                                                    final String[] grid2 = {"GENERAL"
                                                            ,"VENTA TALLERES MOSTRADOR"
                                                            ,"MAIL-CAMPAÑA"
                                                            ,"CALL CENTER-CAMPAÑA"
                                                            ,"CAMPAÑA DE REDES SOCIALES"
                                                            ,"AUTOSURA-CITA CARRO TALLER"
                                                            ,"AUTOSURA-CLIENTE"
                                                            ,"FONDOS Y FERIAS"
                                                            ,"CARRERAS CALI"
                                                            ,"CARRO TALLER"
                                                            ,"CONVENIOS INSTITUCIONALES"
                                                            ,"AUTOSURA-BONO"
                                                            ,"CLIENTE UNE MAS"
                                                            ,"SUCURSAL  AUTOSURA"
                                                            ,"CUPON CLIENTE UNE MAS"
                                                            ,"PROMOCIONES Y PUBLICACIONES EN REDES SOCIALES"
                                                            ,"CLIENTE DE GERENCIA"
                                                            ,"BONO DE CORTESIA"
                                                            ,"AGENDA UNIVERSITARIA"
                                                            ,"FLYER O VOLANTE"
                                                            ,"CONVENIO ALTO NIVEL"
                                                            ,"REVISTA AGENDA AUTOMOTRIZ"
                                                            ,"AVISO DIAGNOSTICO GRATIS"
                                                            ,"EXPOMOTRIZ"
                                                            ,"CITA REVISION PREVENTIVA"
                                                            ,"MAILING CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                                            ,"VOLANTE CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                                            ,"L A W , CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                                            ,"AVISOS FACHADA VACACIONES SIN PREOCUPACIONES"
                                                            ,"CIRCUITO SABANETA"
                                                            ,"RASPA Y GANA VACACIONE SIN PREOCUPACIONES"
                                                            ,"COMERCIAL BLU RADIO"
                                                            ,"CITAS POR PAGINA WEB INTERNET"
                                                            ,"EVENTO STA MARIA DE LOS ANGELES"
                                                            ,"BLU RADIO"
                                                            ,"RECORDATORIO DE KILOMETRAJE"
                                                            ,"LLAMADA DEL CLIENTE"
                                                            ,"REFERIDOS"
                                                            ,"EVENTO DIA DE CAMPO JARDIN BOTANICO",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "GENERAL-FIME",
                                                            "CITA - FIME"};

                                                    try {

                                                        general[indice][5] = texto.getText().toString().trim();
                                                        if (grid2[Integer.parseInt(general[indice][5]) - 1].equals("")){
                                                            Toast.makeText(RecepcionPanel.this, "No existe la razon!", Toast.LENGTH_SHORT).show();
                                                            general[indice][5] = "";
                                                            error.setText("");
                                                        }else{
                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                            try {

                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                            dataBaseHelper.close();
                                                            error.setText(grid2[Integer.parseInt(general[indice][5]) - 1]);
                                                            pasar(conexion, general);
                                                            descripcion = grid2[Integer.parseInt(general[indice][5]) - 1];
                                                            if (razon.equals("61") || razon.equals("62")){
                                                                eventoRazon = "G";
                                                            }
                                                            razon = general[indice][5];

                                                        }

                                                    }catch (Throwable e){
                                                        e.printStackTrace();
                                                        general[indice][5] = "";
                                                        error.setText("");
                                                        Toast.makeText(RecepcionPanel.this, "No existe la razon!", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    if (general[indice][4].trim().equals("PROMESA")) {
                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                        try {

                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        } catch (SQLException e) {
                                                            e.printStackTrace();
                                                        }
                                                        dataBaseHelper.close();
                                                        promesa = texto.getText().toString().trim();
                                                        general[indice][5] = texto.getText().toString().trim();
                                                        pasar(conexion, general);
                                                        error.setText("");
                                                    } else {
                                                        if (general[indice][4].trim().equals("SOT")) {
                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                            try {

                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                            dataBaseHelper.close();
                                                            soat = texto.getText().toString().trim();
                                                            general[indice][5] = texto.getText().toString().trim();
                                                            pasar(conexion, general);
                                                            error.setText("");
                                                        } else {
                                                            if (general[indice][4].trim().equals("TECNICOECANICA")) {
                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                try {

                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                } catch (SQLException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                dataBaseHelper.close();
                                                                tecno = texto.getText().toString().trim();
                                                                general[indice][5] = texto.getText().toString().trim();
                                                                pasar(conexion, general);
                                                                error.setText("");
                                                            } else {
                                                                if (general[indice][4].trim().equals("SOLICITUD")) {
                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                    try {

                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    } catch (SQLException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    dataBaseHelper.close();
                                                                    solicitud = texto.getText().toString().trim();
                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                    pasar(conexion, general);
                                                                    error.setText("");
                                                                } else {
                                                                    if (general[indice][4].trim().equals("VEHICULO")) {
                                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                        try {

                                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                        } catch (IOException e) {
                                                                            e.printStackTrace();
                                                                        } catch (SQLException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        if (texto.getText().toString().trim().equals("A")
                                                                                || texto.getText().toString().trim().equals("M")
                                                                                || texto.getText().toString().trim().equals("B")
                                                                                || texto.getText().toString().trim().equals("K")
                                                                                || texto.getText().toString().trim().equals("C")
                                                                                || texto.getText().toString().trim().equals("T")) {
                                                                            dataBaseHelper.close();
                                                                            tipoVehiculo = texto.getText().toString().trim();
                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                            pasar(conexion, general);
                                                                            error.setText("");
                                                                        }else{
                                                                            error.setText("No es un tipo de vehiculo!");
                                                                            texto.setText("");
                                                                        }

                                                                    } else {
                                                                        if (general[indice][4].trim().equals("UBICACION")) {
                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                            try {

                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            } catch (SQLException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            dataBaseHelper.close();
                                                                            cod_ubicacion = texto.getText().toString().trim();
                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                            pasar(conexion, general);
                                                                            error.setText("");

                                                                        } else {
                                                                            if (general[indice][4].trim().equals("OPERARIO")) {
                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                try {

                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                } catch (IOException e) {
                                                                                    e.printStackTrace();
                                                                                } catch (SQLException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                dataBaseHelper.close();
                                                                                tecnico = texto.getText().toString().trim();
                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                pasar(conexion, general);
                                                                                error.setText("");
                                                                            } else {
                                                                                if (general[indice][4].trim().equals("NOTAS")) {
                                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                    try {

                                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                    } catch (IOException e) {
                                                                                        e.printStackTrace();
                                                                                    } catch (SQLException e) {
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                    dataBaseHelper.close();
                                                                                    notas = texto.getText().toString().trim();
                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                    pasar(conexion, general);
                                                                                    error.setText("");
                                                                                } else {
                                                                                    if (general[indice][4].trim().equals("LISTA")) {
                                                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                        try {

                                                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                        } catch (IOException e) {
                                                                                            e.printStackTrace();
                                                                                        } catch (SQLException e) {
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                        dataBaseHelper.close();
                                                                                        precio = texto.getText().toString().trim();
                                                                                        general[indice][5] = texto.getText().toString().trim();
                                                                                        pasar(conexion, general);
                                                                                        error.setText("");
                                                                                    } else {
                                                                                        if (general[indice][4].trim().equals("MARCA")) {
                                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                            try {

                                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                            } catch (IOException e) {
                                                                                                e.printStackTrace();
                                                                                            } catch (SQLException e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                            dataBaseHelper.close();
                                                                                            marca = texto.getText().toString().trim();
                                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                                            pasar(conexion, general);
                                                                                            error.setText("");
                                                                                        } else {
                                                                                            if (general[indice][4].trim().equals("FORMA PAGO")) {
                                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                try {

                                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                } catch (IOException e) {
                                                                                                    e.printStackTrace();
                                                                                                } catch (SQLException e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                                dataBaseHelper.close();
                                                                                                fpago = texto.getText().toString().trim();
                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                pasar(conexion, general);
                                                                                                error.setText("");
                                                                                            } else {
                                                                                                if (general[indice][9].trim().equals("E")) {
                                                                                                    if (isNumeric(atributo)) {
                                                                                                        if (Integer.parseInt(atributo) >= Double.parseDouble(general[indice][10]) && Integer.parseInt(atributo) <= Double.parseDouble(general[indice][11])) {
                                                                                                            if (general[indice][4].trim().equals("ROMBO")) {

                                                                                                                String url = "";
                                                                                                                List<NameValuePair> params = new ArrayList<NameValuePair>();
                                                                                                                boolean ocupado = false;

                                                                                                                String bodega = "";

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

                                                                                                                params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa.trim()));
                                                                                                                params.add(new BasicNameValuePair("sCodSucursal", bodega.trim()));
                                                                                                                url = "http://" + ip + "/consultarRecursoColibriHydro.php";
                                                                                                                params.add(new BasicNameValuePair("sParametro", "R"));
                                                                                                                String resultServer = getHttpPost(url, params);
                                                                                                                System.out.println("---------------------------------");
                                                                                                                System.out.println(resultServer);
                                                                                                                try {
                                                                                                                    JSONArray jArray = new JSONArray(resultServer);
                                                                                                                    ArrayList<String> array = new ArrayList<String>();
                                                                                                                    for (int i = 0; i < jArray.length(); i++) {
                                                                                                                        JSONObject json = jArray.getJSONObject(i);
                                                                                                                        array.add((json.getString("rombo")));
                                                                                                                        if (texto.getText().toString().equals(array.get(i).trim())) {
                                                                                                                            ocupado = true;
                                                                                                                            break;
                                                                                                                        } else {
                                                                                                                            ocupado = false;
                                                                                                                        }
                                                                                                                    }


                                                                                                                } catch (JSONException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                }


                                                                                                                if (ocupado) {
                                                                                                                    error.setText("El rombo no esta disponible");
                                                                                                                } else {
                                                                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                                    try {

                                                                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                                    } catch (IOException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                    } catch (SQLException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                    }
                                                                                                                    dataBaseHelper.close();
                                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                                    pasar(conexion, general);
                                                                                                                    error.setText("");
                                                                                                                }
                                                                                                            } else {
                                                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                                try {

                                                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                                } catch (IOException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                } catch (SQLException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                }
                                                                                                                dataBaseHelper.close();
                                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                                pasar(conexion, general);
                                                                                                                error.setText("");
                                                                                                            }

                                                                                                        } else {
                                                                                                            error.setText("No esta en el rango!");
                                                                                                        }
                                                                                                    } else {
                                                                                                        error.setText("No es numero!");
                                                                                                    }
                                                                                                } else {
                                                                                                    if (general[indice][9].trim().equals("C")) {
                                                                                                        if ((texto.getText().toString().trim().length() >= Double.parseDouble(general[indice][10])) && (texto.getText().toString().trim().length() <= Double.parseDouble(general[indice][11]))) {
                                                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                                                            pasar(conexion, general);
                                                                                                            error.setText("");

                                                                                                        } else {
                                                                                                            if (Double.parseDouble(general[indice][10]) == Double.parseDouble(general[indice][11]) && texto.getText().toString().trim().length() == Double.parseDouble(general[indice][10])) {
                                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                                pasar(conexion, general);
                                                                                                                error.setText("");
                                                                                                            } else {
                                                                                                                error.setText("No esta en el rango!");
                                                                                                            }
                                                                                                            error.setText("No esta en el rango!");
                                                                                                        }
                                                                                                    } else {
                                                                                                        if (general[indice][9].trim().equals("F")) {
                                                                                                            if (general[indice][18].trim().equals("PROMESA_ENTREGA")) {

                                                                                                                date = getHora();
                                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                                pasar(conexion, general);
                                                                                                                error.setText("");
                                                                                                            } else {
                                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                                pasar(conexion, general);
                                                                                                                error.setText("");
                                                                                                            }
                                                                                                        } else {
                                                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                            try {

                                                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                            } catch (IOException e) {
                                                                                                                e.printStackTrace();
                                                                                                            } catch (SQLException e) {
                                                                                                                e.printStackTrace();
                                                                                                            }
                                                                                                            dataBaseHelper.close();
                                                                                                        }
                                                                                                    }
                                                                                                }


                                                                                                // params.add(new BasicNameValuePair("sNumOrden", num_orden));


                                /*if (resultado.get(4).equals("NIT")) {
                                    String cedula = texto.getText().toString().replaceAll("\n", "");
                                    String cedulafinal = "";


                                    String url3 = "http://" + finalIp + ":8080/update2.php";
                                    String resultServer3;
                                    try {

                                        Cedula = cedula.substring(47, 58);
                                        nombre1 = cedula.substring(95, 116);
                                        nombre2 = cedula.substring(117, 140);
                                        apellido1 = cedula.substring(58, 71);
                                        apellido2 = cedula.substring(72, 94);
                                        if (Cedula.substring(1, 2).equals("00")) {
                                            Cedula = cedula.substring(50, 58);
                                        }


                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("sNit", Cedula));
                                        params3.add(new BasicNameValuePair("sApellido1", apellido1));
                                        params3.add(new BasicNameValuePair("sApellido2", apellido2));
                                        params3.add(new BasicNameValuePair("sNombre1", nombre1));
                                        params3.add(new BasicNameValuePair("sNombre2", nombre2));
                                        resultServer3 = getHttpPost(url3, params3);
                                        System.out.println(resultServer3);

                                        String strStatusID = "0";
                                        String strError = "Unknow Status! 3";

                                        JSONObject c3;
                                        try {
                                            c3 = new JSONObject(resultServer3);
                                            strStatusID = c3.getString("StatusID");
                                            strError = c3.getString("Error");
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        if (strStatusID.equals("0")) {
                                            ad.setMessage(strError);
                                            ad.show();
                                            return false;
                                        } else {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }
                                    } catch (Exception e) {
                                        url3 = "http://" + finalIp + "/update.php";
                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                        params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                        params3.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                        params3.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                        params3.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                        params3.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                        params3.add(new BasicNameValuePair("sNumOrden", num_orden));
                                        resultServer3 = getHttpPost(url3, params3);
                                        String strStatusID = "0";
                                        String strError = "Unknow Status! 3";

                                        JSONObject c3;
                                        try {
                                            c3 = new JSONObject(resultServer3);
                                            strStatusID = c3.getString("StatusID");
                                            strError = c3.getString("Error");
                                        } catch (JSONException ex) {
                                            // TODO Auto-generated catch block
                                            ex.printStackTrace();
                                        }
                                        if (strStatusID.equals("0")) {
                                            ad.setMessage(strError);
                                            ad.show();
                                            return false;
                                        } else {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }
                                    }


                                } else {
                                    if (resultado.get(4).equals("MATRICULA")) {
                                        String matricula = texto.getText().toString().replaceAll("\n", "");
                                        matricula = matricula.replaceAll(" ", "");
                                        System.out.println("LONGITUD : " + matricula.length());
                                        System.out.println(texto.getText().toString());
                                        String matriculafinal = "";


                                        try {
                                            placa = matricula.substring(55, 61);
                                            vin = matricula.substring(61, 78);

                                            texto.setText("");

                                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarDatosMatricula.class);
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putString("placa", placa);
                                            bundle2.putString("vin", vin);
                                            intent.putExtras(bundle2);
                                            RecepcionPanel.this.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                            ad.setTitle("Error! ");
                                            ad.setIcon(android.R.drawable.btn_star_big_on);
                                            ad.setPositiveButton("Close", null);
                                            String url5 = "http://" + finalIp + "/update.php";

                                            List<NameValuePair> params5 = new ArrayList<NameValuePair>();


                                            params5.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                            params5.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                            params5.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                            params5.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                            params5.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                            params5.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                            params5.add(new BasicNameValuePair("sNumOrden", num_orden));
                                            String resultServer = getHttpPost(url, params);
                                            System.out.println(resultServer);

                                            String strStatusID = "0";
                                            String strError = "Unknow Status!";

                                            JSONObject c;
                                            try {
                                                Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                                pasar(conexion, general);
                                            }catch(Exception e1){
                                                Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                    } else {
                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);


                                        JSONObject c;
                                        try {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }catch(Exception e){
                                            Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }*/


                                                                                                //TALL_ENCABEZA_ORDEN
                                                /*)}
                                        }


                                    }
                                }
                            }*/


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
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                // }


                            }
                        }


                    }
                    return false;
                }

            });

        } else {



            final String finalIp = ip;

            final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
                public boolean onDoubleTap(MotionEvent e) {
                    if (texto.getText().toString().matches("") && (cod_atributo[0].trim().equals("ROMBO")
                            || cod_atributo[0].trim().equals("UBICACION") || cod_atributo[0].trim().equals("OPERARIO")
                            || cod_atributo[0].trim().equals("NUMERO OT")) || cod_atributo[0].trim().equals("PROMESA")
                            || cod_atributo[0].trim().equals("RAZON2") || cod_atributo[0].trim().equals("RAZON")) {

                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("conexion", conexion);
                        bundle2.putString("ip", ip);
                        bundle2.putString("atributo", cod_atributo[0].trim());
                        bundle2.putString("empresa", cod_empresa);
                        bundle2.putString("sucursal", cod_sucursal);
                        intent.putExtras(bundle2);
                        RecepcionPanel.this.startActivityForResult(intent, request_code);
                    }else{
                        if (general[indice][4].trim().equals("SOAT") || general[indice][4].trim().equals("TECNICOMECANICA") ) {
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) CalendarioSoat.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("parametro",general[indice][4].trim());
                            intent.putExtras(bundle2);
                            startActivityForResult(intent, request_code);
                        }//else{
                    }
                       /* if (general[indice][9].toString().equals("F")){
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) Calendario.class);
                            RecepcionPanel.this.startActivityForResult(intent,request_code);
                        }
                    }*/

                        /*String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                        params2.add(new BasicNameValuePair("sCodUser", resultado.get(2).toString()));
                        params2.add(new BasicNameValuePair("sCodEmpresa", resultado.get(0).toString()));
                        params2.add(new BasicNameValuePair("sCodAtributo", resultado.get(4).toString()));
                        params2.add(new BasicNameValuePair("sCodSucursal",sucursal));
                        params2.add(new BasicNameValuePair("sParametro", "actualizar"));


                        String resultServer2 = getHttpPost(url2, params2);
                        System.out.println("---------------------------------");
                        System.out.println(resultServer2);
                        ArrayList<String> array = new ArrayList<String>();
                        try {
                            JSONArray jArray = new JSONArray(resultServer2);

                            //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json = jArray.getJSONObject(i);
                                array.add((json.getString("cod_valor")));
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        if (array.size()>0) {
                            String url4 = "http://" + ip + "/updateGeneral.php";

                            List<NameValuePair> params4 = new ArrayList<NameValuePair>();


                            params4.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                            params4.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                            params4.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                            params4.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                            params4.add(new BasicNameValuePair("sParametro", "update3"));

                            String resultServer4 = getHttpPost(url4, params4);

                            JSONObject c4;
                            try {

                                Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                pasar(conexion, general);
                            }catch(Exception e1){
                                Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                            }
                        } else {





                            final AlertDialog.Builder ad = new AlertDialog.Builder(RecepcionPanel.this);

                            ad.setTitle("Error! ");
                            ad.setIcon(android.R.drawable.btn_star_big_on);
                            ad.setPositiveButton("Close", null);
                            InputStream isr = null;
                            String url = "http://" + finalIp + "/update.php";

                            List<NameValuePair> params = new ArrayList<NameValuePair>();


                            params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                            params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                            params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                            params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                            params.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                            params.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                            params.add(new BasicNameValuePair("sNumOrden", num_orden));

                            */

                    // params.add(new BasicNameValuePair("sNumOrden", num_orden));


                            /*if (resultado.get(4).equals("NIT")) {
                                String cedula = texto.getText().toString().replaceAll("\n", "");
                                String cedulafinal = "";

                                String url3 = "http://" + finalIp + ":8080/update2.php";
                                String resultServer3;
                                try {

                                    Cedula = cedula.substring(47, 58);
                                    nombre1 = cedula.substring(95, 116);
                                    nombre2 = cedula.substring(117, 140);
                                    apellido1 = cedula.substring(58, 71);
                                    apellido2 = cedula.substring(72, 94);
                                    if (Cedula.substring(1, 2).equals("00")) {
                                        Cedula = cedula.substring(50, 58);
                                    }


                                    List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                    params3.add(new BasicNameValuePair("sNit", Cedula));
                                    params3.add(new BasicNameValuePair("sApellido1", apellido1));
                                    params3.add(new BasicNameValuePair("sApellido2", apellido2));
                                    params3.add(new BasicNameValuePair("sNombre1", nombre1));
                                    params3.add(new BasicNameValuePair("sNombre2", nombre2));
                                    resultServer3 = getHttpPost(url3, params3);
                                    System.out.println(resultServer3);

                                    String strStatusID = "0";
                                    String strError = "Unknow Status! 3";

                                    JSONObject c3;
                                    try {
                                        c3 = new JSONObject(resultServer3);
                                        strStatusID = c3.getString("StatusID");
                                        strError = c3.getString("Error");
                                    } catch (JSONException ex) {
                                        // TODO Auto-generated catch block
                                        ex.printStackTrace();
                                    }
                                    if (strStatusID.equals("0")) {
                                        ad.setMessage(strError);
                                        ad.show();
                                        return false;
                                    } else {
                                        Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        pasar(conexion, general);
                                    }
                                } catch (Exception ec) {
                                    url3 = "http://" + finalIp + "/update.php";
                                    List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                    params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                    params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                    params3.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                    params3.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                    params3.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                    params3.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                    params3.add(new BasicNameValuePair("sNumOrden", num_orden));
                                    resultServer3 = getHttpPost(url3, params3);
                                    String strStatusID = "0";
                                    String strError = "Unknow Status! 3";

                                    JSONObject c3;
                                    try {
                                        c3 = new JSONObject(resultServer3);
                                        strStatusID = c3.getString("StatusID");
                                        strError = c3.getString("Error");
                                    } catch (JSONException ex) {
                                        // TODO Auto-generated catch block
                                        ex.printStackTrace();
                                    }
                                    if (strStatusID.equals("0")) {
                                        ad.setMessage(strError);
                                        ad.show();
                                        return false;
                                    } else {
                                        Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        pasar(conexion, general);
                                    }
                                }


                            } else {
                                if (resultado.get(4).equals("MATRICULA")) {
                                    String matricula = texto.getText().toString().replaceAll("\n", "");
                                    matricula = matricula.replaceAll(" ", "");
                                    System.out.println("LONGITUD : " + matricula.length());
                                    System.out.println(texto.getText().toString());
                                    String matriculafinal = "";


                                Character  numAux = cedula.charAt(50);
                                String num = numAux.toString();
                                while (num.charAt(0)>='0' && num.charAt(0)<='9'){
                                    cedulafinal = cedulafinal + num.charAt(0);
                                }

                                    try {
                                        placa = matricula.substring(55, 61);
                                        vin = matricula.substring(61, 78);

                                        texto.setText("");

                                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarDatosMatricula.class);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString("placa", placa);
                                        bundle2.putString("vin", vin);
                                        intent.putExtras(bundle2);
                                        RecepcionPanel.this.startActivity(intent);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();

                                        ad.setTitle("Error! ");
                                        ad.setIcon(android.R.drawable.btn_star_big_on);
                                        ad.setPositiveButton("Close", null);
                                        String url5 = "http://" + finalIp + "/update.php";

                                        List<NameValuePair> params5 = new ArrayList<NameValuePair>();


                                        params5.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                        params5.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                        params5.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                        params5.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                        params5.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                        params5.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                        params5.add(new BasicNameValuePair("sNumOrden", num_orden));
                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);


                                        JSONObject c;
                                        try {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }catch(Exception e1){
                                            Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                } else {
                                    String resultServer = getHttpPost(url, params);
                                    System.out.println(resultServer);

                                    String strStatusID = "0";
                                    String strError = "Unknow Status!";

                                    JSONObject c;
                                    try {

                                        Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();

                                        pasar(conexion, general);
                                    }catch(Exception e1){
                                        Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }*/


                            /*String file = "storage/sdcard0/METRO/" + nombre;
                            File mi_foto = new File(file);
                            String file2 = "storage/sdcard0/METRO/";
                            File mi_foto2 = new File(file2);
                            mi_foto.renameTo(mi_foto2);*/
                    //else{
                    //    Toast.makeText(getApplicationContext()," Este registro ya esta en la base de datos !" , Toast.LENGTH_SHORT).show();
                    //}
                    // }

                    return true;
                }
            });

            if ( !(general[indice][18].trim().equals(general[indice][3].trim()))) {




                Button BotonBuscar = (Button) findViewById(R.id.button_buscar);
                BotonBuscar.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        if (general[indice][4].trim().equals("NIT")) {

                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarCliente.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("conexion", conexion);
                            bundle2.putString("ip", ip);
                            bundle2.putString("atributo", texto.getText().toString().trim());
                            bundle2.putString("empresa", cod_empresa);
                            bundle2.putString("sucursal", sucursal);
                            bundle2.putString("parametro", parametro);
                            intent.putExtras(bundle2);
                            RecepcionPanel.this.startActivityForResult(intent, request_code);
                            error.setText("");
                        }else{

                            if (texto.getText().toString().matches("") && (cod_atributo[0].trim().equals("ROMBO")
                                    || cod_atributo[0].trim().equals("UBICACION") || cod_atributo[0].trim().equals("OPERARIO")
                                    || cod_atributo[0].trim().equals("NUMERO OT")) || cod_atributo[0].trim().equals("PROMESA")
                                    || cod_atributo[0].trim().equals("RAZON2") || cod_atributo[0].trim().equals("RAZON")) {

                                Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                                Bundle bundle2 = new Bundle();
                                bundle2.putString("conexion", conexion);
                                bundle2.putString("ip", ip);
                                bundle2.putString("atributo", cod_atributo[0].trim());
                                bundle2.putString("empresa", cod_empresa);
                                bundle2.putString("sucursal", cod_sucursal);
                                intent.putExtras(bundle2);
                                RecepcionPanel.this.startActivityForResult(intent, request_code);
                            }else{
                                if (general[indice][4].trim().equals("VEHIC")){
                                    Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarModelo.class);
                                    Bundle bundle2 = new Bundle();
                                    bundle2.putString("placa", cod_placa);
                                    bundle2.putString("ip", ip);
                                    intent.putExtras(bundle2);
                                    startActivityForResult(intent, request_code);
                                }else{
                                    if (general[indice][4].trim().equals("SOAT") || general[indice][4].trim().equals("TECNICOMECANICA")
                                            || general[indice][4].trim().equals("EXTINTOR")) {
                                        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) CalendarioSoat.class);
                                        Bundle bundle2 = new Bundle();
                                        bundle2.putString("parametro",general[indice][4].trim());
                                        intent.putExtras(bundle2);
                                        startActivityForResult(intent, request_code);
                                    }else{
                                        if (general[indice][4].trim().equals("LISTA")) {

                                            if (tipoVehiculo.equals("")){
                                                Toast.makeText(RecepcionPanel.this, "Seleccione un tipo de vehiculo!", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarLista.class);
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putString("conexion", conexion);
                                                bundle2.putString("parametro", general[indice][4].trim());
                                                bundle2.putString("tipoVehiculo",tipoVehiculo);
                                                bundle2.putString("ip", ip);
                                                bundle2.putString("atributo", cod_atributo[0].trim());
                                                bundle2.putString("empresa", cod_empresa);
                                                bundle2.putString("sucursal", cod_sucursal);
                                                intent.putExtras(bundle2);
                                                RecepcionPanel.this.startActivityForResult(intent, request_code);
                                            }
                                        }else{
                                            if (general[indice][4].trim().equals("FECHA INICIAL") || general[indice][4].trim().equals("FECHA FINAL")) {
                                                final View dialogView = View.inflate(RecepcionPanel.this, R.layout.date_time_picker, null);
                                                final AlertDialog alertDialog = new AlertDialog.Builder(RecepcionPanel.this).create();

                                                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                                                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);


                                                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                                                datePicker.getMonth(),
                                                                datePicker.getDayOfMonth(),
                                                                timePicker.getCurrentHour(),
                                                                timePicker.getCurrentMinute());

                                                        general[indice][5] = String.valueOf(calendar.get(Calendar.YEAR) + "-" +
                                                                String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) +
                                                                " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));

                                                        pasar(conexion,general);
                                                        alertDialog.dismiss();
                                                    }});
                                                alertDialog.setView(dialogView);
                                                alertDialog.show();
                                            }else {
                                                Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                                                Bundle bundle2 = new Bundle();
                                                bundle2.putString("conexion", conexion);
                                                bundle2.putString("ip", ip);
                                                bundle2.putString("atributo", cod_atributo[0].trim());
                                                bundle2.putString("empresa", cod_empresa);
                                                bundle2.putString("sucursal", cod_sucursal);
                                                intent.putExtras(bundle2);
                                                RecepcionPanel.this.startActivityForResult(intent, request_code);
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                });

                Button BotonAnadir = (Button) findViewById(R.id.button_anadir);
                BotonAnadir.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        new ConsultarGeneral().execute("");
                    }
                });



            }else {
            }

            texto.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
            texto.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {


                        /*if (texto.getText().toString().matches("") && (general[indice][5].equals("ROMBO") || general[indice][5].equals("UBICACION") || general[indice][5].equals("MECANICO"))) {
                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarRombUbicPanel.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putString("conexion", conexion);
                            bundle2.putString("ip", ip);
                            bundle2.putString("atributo", cod_atributo);
                            intent.putExtras(bundle2);
                            RecepcionPanel.this.startActivityForResult(intent, request_code);
                            Toast.makeText(getApplicationContext(), resultado.get(4) + "  ATRIBUTO ", Toast.LENGTH_SHORT).show();

                        } else {*/
                            /*String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                            params2.add(new BasicNameValuePair("sCodUser", general[indice][2].toString()));
                            params2.add(new BasicNameValuePair("sCodEmpresa", general[indice][0].toString()));
                            params2.add(new BasicNameValuePair("sCodAtributo", general[indice][4].toString()));
                            params2.add(new BasicNameValuePair("sCodSucursal",sucursal));
                            params2.add(new BasicNameValuePair("sParametro", "actualizar"));


                            String resultServer2 = getHttpPost(url2, params2);
                            System.out.println("---------------------------------");
                            System.out.println(resultServer2);
                            ArrayList<String> array = new ArrayList<String>();
                            try {
                                JSONArray jArray = new JSONArray(resultServer2);

                                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add((json.getString("cod_valor")));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (array.size()>0) {
                                String url4 = "http://" + ip + "/updateGeneral.php";

                                List<NameValuePair> params4 = new ArrayList<NameValuePair>();


                                params4.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                params4.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                params4.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                params4.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                params4.add(new BasicNameValuePair("sParametro", "update3"));

                                String resultServer4 = getHttpPost(url4, params4);
                                System.out.println(resultServer4);


                                try {
                                    Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    pasar(conexion, general);
                                }catch (Exception e){
                                    Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                }
                            } else {*/





                                /*final AlertDialog.Builder ad = new AlertDialog.Builder(RecepcionPanel.this);

                                ad.setTitle("Error! ");
                                ad.setIcon(android.R.drawable.btn_star_big_on);
                                ad.setPositiveButton("Close", null);
                                InputStream isr = null;
                                String url = "http://" + finalIp + "/update.php";

                                List<NameValuePair> params = new ArrayList<NameValuePair>();


                                params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                params.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                params.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                params.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                params.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                params.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                params.add(new BasicNameValuePair("sNumOrden", num_orden));

                                String resultServer = getHttpPost(url, params);
                                System.out.println(resultServer);


                                JSONObject c;
                                try {
                                    Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                    pasar(conexion, general);
                                }catch(Exception e){
                                    Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                }*/

                        String atributo = texto.getText().toString().trim();
                        error.setTextColor(Color.RED);

                        String file = "storage/sdcard0/METRO/" + nombre;
                        File mi_foto = new File(file);
                        String file2 = "storage/sdcard0/METRO/";
                        File mi_foto2 = new File(file2);
                        mi_foto.renameTo(mi_foto2);

                        EditText texto1 = (EditText) findViewById(R.id.editText_texto);

                        if (general[indice][4].trim().equals("ROMBO")) {
                            error.setText("");
                            num_rombo = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("PLACA")) {
                            cod_placa = texto.getText().toString().trim();

                        }
                        if (general[indice][4].trim().equals("PLACA") && encuentraPlaca.equals("false")) {
                            error.setText("Placa no existe, se creara!");
                        }
                        if (general[indice][4].trim().equals("UBICACION")) {
                            cod_ubicacion = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("NIT")) {
                            num_nit = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("KM")) {
                            kilometraje = texto.getText().toString().trim();
                        }
                        if (general[indice][4].trim().equals("RAZON")) {
                            if (razon.equals("61") || razon.equals("62")){
                                eventoRazon = "G";
                            }
                            razon = texto.getText().toString().trim();
                            error.setText(descripcion);
                        }

                        if (general[indice][4].trim().equals("SOT")) {
                            soat = texto.getText().toString().trim();
                        }

                        if (general[indice][4].trim().equals("TECNICOECANICA")) {
                            tecno = texto.getText().toString().trim();
                        }

                        if (general[indice][4].trim().equals("CEDULA")) {
                            num_nit = texto.getText().toString().trim();
                        }

                        if (general[indice][4].trim().equals("MOTIVO")) {
                            motivo = texto.getText().toString().trim();
                        }

                        if (general[indice][4].trim().equals("PLACA")) {

                            cita = new String();
                            horacita = new String();

                            String url4 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params4 = new ArrayList<NameValuePair>();

                            params4.add(new BasicNameValuePair("sParametro", "placanitLavadero"));
                            params4.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer4 = getHttpPost(url4, params4);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer4);

                            try {

                                JSONArray jArray = new JSONArray(resultServer4);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("nit"));
                                }
                                if (array.get(0).equals("null")){

                                }else {
                                    num_nit = array.get(0).trim();
                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                    try {

                                        guardarMovimiento("NIT", num_nit);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    dataBaseHelper.close();
                                }
                            } catch (JSONException e) {

                                error.setText("");
                                //informativo.setTextColor(Color.BLUE);
                                e.printStackTrace();
                            }

                            String url3 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params3 = new ArrayList<NameValuePair>();

                            params3.add(new BasicNameValuePair("sParametro", "CITA"));
                            params3.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer3 = getHttpPost(url3, params3);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer3);

                            try {

                                JSONArray jArray = new JSONArray(resultServer3);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("placa"));
                                    array.add(json.getString("fecha"));
                                }
                                cita = array.get(0);
                                horacita = array.get(1);
                                eventoCita = "C";
                            } catch (JSONException e) {

                                error.setText("");
                                //informativo.setTextColor(Color.BLUE);
                                e.printStackTrace();
                            }

                            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params1 = new ArrayList<NameValuePair>();

                            params1.add(new BasicNameValuePair("sParametro", "MODELO"));
                            params1.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer1 = getHttpPost(url1, params1);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer1);
                            try {

                                JSONArray jArray = new JSONArray(resultServer1);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("modelo"));
                                }

                                modelo = array.get(0);

                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento("MODELO", modelo);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();

                            } catch (JSONException e) {

                                error.setText("");
                                //informativo.setTextColor(Color.BLUE);
                                e.printStackTrace();
                            }

                            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                            params.add(new BasicNameValuePair("sParametro", "placa"));
                            params.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer = getHttpPost(url, params);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer);
                            String placa = "";
                            try {

                                JSONArray jArray = new JSONArray(resultServer);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("placa"));
                                    placa = array.get(0);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                            List<NameValuePair> params2 = new ArrayList<NameValuePair>();

                            params2.add(new BasicNameValuePair("sParametro", "fechas"));
                            params2.add(new BasicNameValuePair("sPlaca", cod_placa));

                            String resultServer2 = getHttpPost(url2, params2);
                            System.out.println("---------------------------------resultserver----------------");
                            System.out.println(resultServer2);
                            try {

                                JSONArray jArray = new JSONArray(resultServer2);
                                ArrayList<String> array = new ArrayList<String>();
                                for (int i = 0; i < jArray.length(); i++) {
                                    JSONObject json = jArray.getJSONObject(i);
                                    array.add(json.getString("soat"));
                                    array.add(json.getString("tecno"));
                                }


                                soat = array.get(0);

                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento("SOAT", soat);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();

                                tecno = array.get(1);


                                try {

                                    guardarMovimiento("TECNICOMECANICA", tecno);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();


                            } catch (JSONException e) {
                                error.setText("");
                                e.printStackTrace();
                            }

                            if (cod_placa.trim().equals(placa)) {
                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                dataBaseHelper.close();
                                general[indice][5] = texto.getText().toString().trim();
                                pasar(conexion, general);
                                error.setText("");
                                encuentraPlaca = "true";

                            } else {
                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                try {

                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                dataBaseHelper.close();
                                general[indice][5] = texto.getText().toString().trim();
                                pasar(conexion, general);
                                error.setText("");
                                //error.setText("No existe la placa!");
                                encuentraPlaca = "false";
                            }
                        } else {
                            if (general[indice][4].trim().equals("CEDULA")) {
                                String cedula = texto.getText().toString().replaceAll("\n", "");


                                try {
                                    Cedula = cedula.substring(47, 58);
                                    nombre1 = cedula.substring(95, 116);
                                    nombre2 = cedula.substring(117, 140);
                                    apellido1 = cedula.substring(58, 71);
                                    apellido2 = cedula.substring(72, 94);
                                    if (Cedula.substring(1, 2).equals("00")) {
                                        Cedula = cedula.substring(50, 58);
                                    }


                                    if (general[indice + 1][4].trim().equals("NOMBRES")) {
                                        general[indice + 1][5] = nombre1 + " " + nombre2 + " " + apellido1 + " " + apellido2;

                                    }

                                    texto.setText(Cedula);
                                    general[indice][5] = texto.getText().toString().trim();
                                    pasar(conexion, general);
                                    error.setText("");
                                } catch (Exception e) {
                                    general[indice][5] = texto.getText().toString().trim();
                                    pasar(conexion, general);
                                    error.setText("");
                                    e.printStackTrace();
                                }
                            } else {

                                if (general[indice][4].trim().equals("NOMBRES")) {
                                    String cedula = texto.getText().toString().replaceAll("\n", "");


                                    try {
                                        Cedula = cedula.substring(47, 58);
                                        nombre1 = cedula.substring(95, 116);
                                        nombre2 = cedula.substring(117, 140);
                                        apellido1 = cedula.substring(58, 71);
                                        apellido2 = cedula.substring(72, 94);
                                        if (Cedula.substring(1, 3).equals("00")) {
                                            Cedula = cedula.substring(50, 58);
                                        }

                                        if (general[indice + 1][4].trim().equals("CEDULA")) {
                                            general[indice + 1][5] = Cedula;
                                        }

                                        for (int i = 0; i < general.length; i++) {
                                            if (general[i][4].trim().equals("NIT")) {
                                                general[i][5] = Cedula;
                                            }
                                        }

                                        texto.setText(remove(nombre1) + " " + remove(nombre2) + " " + remove(apellido1) + " " + remove(apellido2));
                                        general[indice][5] = remove(nombre1) + " " + remove(nombre2) + " " + remove(apellido1) + " " + remove(apellido2);
                                        pasar(conexion, general);
                                        error.setText("");
                                    } catch (Exception e) {
                                        general[indice][5] = texto.getText().toString().trim();
                                        pasar(conexion, general);
                                        error.setText("");
                                        e.printStackTrace();
                                    }
                                } else {


                                    if (general[indice][4].trim().equals("KM")) {

                                        String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                        List<NameValuePair> params = new ArrayList<NameValuePair>();

                                        params.add(new BasicNameValuePair("sParametro", "km_anterior"));
                                        params.add(new BasicNameValuePair("sPlaca", cod_placa));

                                        String resultServer = getHttpPost(url, params);
                                        System.out.println("---------------------------------resultserver----------------");
                                        System.out.println(resultServer);
                                        try {

                                            JSONArray jArray = new JSONArray(resultServer);
                                            ArrayList<String> array = new ArrayList<String>();
                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json = jArray.getJSONObject(i);
                                                array.add(json.getString("km_anterior"));
                                                km_anterior = array.get(0);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if (km_anterior.trim().equals("null"))
                                            km_anterior = "1";

                                        if (Integer.parseInt(km_anterior.toString()) < Integer.parseInt(texto.getText().toString())) {

                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                            try {

                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                            dataBaseHelper.close();
                                            general[indice][5] = texto.getText().toString().trim();
                                            pasar(conexion, general);
                                            error.setText("");

                                        } else {
                                            //informativo.setTextColor(Color.BLUE);
                                            error.setText("Anterior : " + km_anterior + ", debe ser mayor!");


                                        }
                                    } else {

                                        if (general[indice][4].trim().equals("NIT")) {

                                            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                            List<NameValuePair> params = new ArrayList<NameValuePair>();

                                            params.add(new BasicNameValuePair("sParametro", "nit"));
                                            params.add(new BasicNameValuePair("sNit", num_nit));

                                            String resultServer = getHttpPost(url, params);
                                            System.out.println("---------------------------------resultserver----------------");
                                            System.out.println(resultServer);
                                            String nit = "";
                                            try {

                                                JSONArray jArray = new JSONArray(resultServer);
                                                ArrayList<String> array = new ArrayList<String>();
                                                for (int i = 0; i < jArray.length(); i++) {
                                                    JSONObject json = jArray.getJSONObject(i);
                                                    array.add(json.getString("nit"));
                                                    array.add(json.getString("mail"));
                                                    nit = array.get(0);
                                                    email = array.get(1);

                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            if (num_nit.trim().equals(nit)) {
                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                try {

                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                                dataBaseHelper.close();
                                                general[indice][5] = texto.getText().toString().trim();
                                                pasar(conexion, general);
                                                error.setText("");
                                                encuentraNit = "true";
                                                existeTercero = "SI";
                                            } else {
                                                existeTercero = "NO";
                                                Intent i =  new Intent(RecepcionPanel.this,CrearCliente.class);
                                                i.putExtra("nit",texto.getText().toString().trim());
                                                i.putExtra("ip",ip.trim());
                                                startActivity(i);
                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                try {

                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                //ArrayList arrayList = dataBaseHelper.getConsultas(string);
                                                dataBaseHelper.close();

                                                general[indice][5] = texto.getText().toString().trim();
                                                error.setText("");
                                                //error.setText("No existe el nit!");
                                                //general[indice][5] = texto.getText().toString().trim();
                                                pasar(conexion, general);

                                            }
                                        } else {
                                            if (general[indice][4].trim().equals("PLACA")) {
                                                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                                                List<NameValuePair> params = new ArrayList<NameValuePair>();

                                                params.add(new BasicNameValuePair("sParametro", "PLACA"));
                                                params.add(new BasicNameValuePair("sPlaca", cod_placa));

                                                String resultServer = getHttpPost(url, params);
                                                System.out.println("---------------------------------resultserver----------------");
                                                System.out.println(resultServer);
                                                String nombre = "";
                                                try {

                                                    JSONArray jArray = new JSONArray(resultServer);
                                                    ArrayList<String> array = new ArrayList<String>();
                                                    for (int i = 0; i < jArray.length(); i++) {
                                                        JSONObject json = jArray.getJSONObject(i);
                                                        array.add(json.getString("nombre"));
                                                        sInformativo = sInformativo + "\n" + array.get(0);
                                                    }

                                                } catch (JSONException e) {

                                                    error.setText("");
                                                    error.setText("No existe la placa!");
                                                    //informativo.setTextColor(Color.BLUE);
                                                    e.printStackTrace();
                                                }




                                                if (sInformativo.equals("null") || sInformativo.equals("")) {

                                                    error.setText("No existe la placa!");
                                                } else {
                                                    error.setText(sInformativo);
                                                    //informativo.setTextColor(Color.BLUE);
                                                    general[indice][5] = texto.getText().toString().trim();
                                                    pasar(conexion, general);
                                                    error.setText("");
                                                }
                                            } else {
                                                if (general[indice][4].trim().equals("RAZON")) {
                                                    final String[] grid2 = {"GENERAL"
                                                            ,"VENTA TALLERES MOSTRADOR"
                                                            ,"MAIL-CAMPAÑA"
                                                            ,"CALL CENTER-CAMPAÑA"
                                                            ,"CAMPAÑA DE REDES SOCIALES"
                                                            ,"AUTOSURA-CITA CARRO TALLER"
                                                            ,"AUTOSURA-CLIENTE"
                                                            ,"FONDOS Y FERIAS"
                                                            ,"CARRERAS CALI"
                                                            ,"CARRO TALLER"
                                                            ,"CONVENIOS INSTITUCIONALES"
                                                            ,"AUTOSURA-BONO"
                                                            ,"CLIENTE UNE MAS"
                                                            ,"SUCURSAL  AUTOSURA"
                                                            ,"CUPON CLIENTE UNE MAS"
                                                            ,"PROMOCIONES Y PUBLICACIONES EN REDES SOCIALES"
                                                            ,"CLIENTE DE GERENCIA"
                                                            ,"BONO DE CORTESIA"
                                                            ,"AGENDA UNIVERSITARIA"
                                                            ,"FLYER O VOLANTE"
                                                            ,"CONVENIO ALTO NIVEL"
                                                            ,"REVISTA AGENDA AUTOMOTRIZ"
                                                            ,"AVISO DIAGNOSTICO GRATIS"
                                                            ,"EXPOMOTRIZ"
                                                            ,"CITA REVISION PREVENTIVA"
                                                            ,"MAILING CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                                            ,"VOLANTE CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                                            ,"L A W , CAMPAÑA VACACIONES SIN PREOCUPACIONES"
                                                            ,"AVISOS FACHADA VACACIONES SIN PREOCUPACIONES"
                                                            ,"CIRCUITO SABANETA"
                                                            ,"RASPA Y GANA VACACIONE SIN PREOCUPACIONES"
                                                            ,"COMERCIAL BLU RADIO"
                                                            ,"CITAS POR PAGINA WEB INTERNET"
                                                            ,"EVENTO STA MARIA DE LOS ANGELES"
                                                            ,"BLU RADIO"
                                                            ,"RECORDATORIO DE KILOMETRAJE"
                                                            ,"LLAMADA DEL CLIENTE"
                                                            ,"REFERIDOS"
                                                            ,"EVENTO DIA DE CAMPO JARDIN BOTANICO",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "",
                                                            "GENERAL-FIME",
                                                            "CITA - FIME"};

                                                    try {

                                                        general[indice][5] = texto.getText().toString().trim();
                                                        if (grid2[Integer.parseInt(general[indice][5]) - 1].equals("")){
                                                            Toast.makeText(RecepcionPanel.this, "No existe la razon!", Toast.LENGTH_SHORT).show();
                                                            general[indice][5] = "";
                                                            error.setText("");
                                                        }else{
                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                            try {

                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                            dataBaseHelper.close();
                                                            error.setText(grid2[Integer.parseInt(general[indice][5]) - 1]);
                                                            pasar(conexion, general);
                                                            descripcion = grid2[Integer.parseInt(general[indice][5]) - 1];
                                                            if (razon.equals("61") || razon.equals("62")){
                                                                eventoRazon = "G";
                                                            }
                                                            razon = general[indice][5];

                                                        }

                                                    }catch (Throwable e){
                                                        e.printStackTrace();
                                                        general[indice][5] = "";
                                                        error.setText("");
                                                        Toast.makeText(RecepcionPanel.this, "No existe la razon!", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    if (general[indice][4].trim().equals("PROMESA")) {
                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                        try {

                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        } catch (SQLException e) {
                                                            e.printStackTrace();
                                                        }
                                                        dataBaseHelper.close();
                                                        promesa = texto.getText().toString().trim();
                                                        general[indice][5] = texto.getText().toString().trim();
                                                        pasar(conexion, general);
                                                        error.setText("");
                                                    } else {
                                                        if (general[indice][4].trim().equals("SOT")) {
                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                            try {

                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }
                                                            dataBaseHelper.close();
                                                            soat = texto.getText().toString().trim();
                                                            general[indice][5] = texto.getText().toString().trim();
                                                            pasar(conexion, general);
                                                            error.setText("");
                                                        } else {
                                                            if (general[indice][4].trim().equals("TECNICOECANICA")) {
                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                try {

                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                } catch (SQLException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                dataBaseHelper.close();
                                                                tecno = texto.getText().toString().trim();
                                                                general[indice][5] = texto.getText().toString().trim();
                                                                pasar(conexion, general);
                                                                error.setText("");
                                                            } else {
                                                                if (general[indice][4].trim().equals("SOLICITUD")) {
                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                    try {

                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    } catch (SQLException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    dataBaseHelper.close();
                                                                    solicitud = texto.getText().toString().trim();
                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                    pasar(conexion, general);
                                                                    error.setText("");
                                                                } else {
                                                                    if (general[indice][4].trim().equals("VEHICULO")) {
                                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                        try {

                                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                        } catch (IOException e) {
                                                                            e.printStackTrace();
                                                                        } catch (SQLException e) {
                                                                            e.printStackTrace();
                                                                        }

                                                                        if (texto.getText().toString().trim().equals("A")
                                                                                || texto.getText().toString().trim().equals("M")
                                                                                || texto.getText().toString().trim().equals("B")
                                                                                || texto.getText().toString().trim().equals("K")
                                                                                || texto.getText().toString().trim().equals("C")
                                                                                || texto.getText().toString().trim().equals("T")) {
                                                                            dataBaseHelper.close();
                                                                            tipoVehiculo = texto.getText().toString().trim();
                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                            pasar(conexion, general);
                                                                            error.setText("");
                                                                        }else{
                                                                            error.setText("No es un tipo de vehiculo!");
                                                                            texto.setText("");
                                                                        }

                                                                    } else {
                                                                        if (general[indice][4].trim().equals("UBICACION")) {
                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                            try {

                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                            } catch (IOException e) {
                                                                                e.printStackTrace();
                                                                            } catch (SQLException e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                            dataBaseHelper.close();
                                                                            cod_ubicacion = texto.getText().toString().trim();
                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                            pasar(conexion, general);
                                                                            error.setText("");

                                                                        } else {
                                                                            if (general[indice][4].trim().equals("OPERARIO")) {
                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                try {

                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                } catch (IOException e) {
                                                                                    e.printStackTrace();
                                                                                } catch (SQLException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                                dataBaseHelper.close();
                                                                                tecnico = texto.getText().toString().trim();
                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                pasar(conexion, general);
                                                                                error.setText("");
                                                                            } else {
                                                                                if (general[indice][4].trim().equals("NOTAS")) {
                                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                    try {

                                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                    } catch (IOException e) {
                                                                                        e.printStackTrace();
                                                                                    } catch (SQLException e) {
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                    dataBaseHelper.close();
                                                                                    notas = texto.getText().toString().trim();
                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                    pasar(conexion, general);
                                                                                    error.setText("");
                                                                                } else {
                                                                                    if (general[indice][4].trim().equals("LISTA")) {
                                                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                        try {

                                                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                        } catch (IOException e) {
                                                                                            e.printStackTrace();
                                                                                        } catch (SQLException e) {
                                                                                            e.printStackTrace();
                                                                                        }
                                                                                        dataBaseHelper.close();
                                                                                        precio = texto.getText().toString().trim();
                                                                                        general[indice][5] = texto.getText().toString().trim();
                                                                                        pasar(conexion, general);
                                                                                        error.setText("");
                                                                                    } else {
                                                                                        if (general[indice][4].trim().equals("MARCA")) {
                                                                                            DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                            try {

                                                                                                guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                            } catch (IOException e) {
                                                                                                e.printStackTrace();
                                                                                            } catch (SQLException e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                            dataBaseHelper.close();
                                                                                            marca = texto.getText().toString().trim();
                                                                                            general[indice][5] = texto.getText().toString().trim();
                                                                                            pasar(conexion, general);
                                                                                            error.setText("");
                                                                                        } else {
                                                                                            if (general[indice][4].trim().equals("FORMA PAGO")) {
                                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                try {

                                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                } catch (IOException e) {
                                                                                                    e.printStackTrace();
                                                                                                } catch (SQLException e) {
                                                                                                    e.printStackTrace();
                                                                                                }
                                                                                                dataBaseHelper.close();
                                                                                                fpago = texto.getText().toString().trim();
                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                pasar(conexion, general);
                                                                                                error.setText("");
                                                                                            } else {
                                                                                                if (general[indice][4].trim().equals("MOTIVO")) {
                                                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                    try {

                                                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                    } catch (IOException e) {
                                                                                                        e.printStackTrace();
                                                                                                    } catch (SQLException e) {
                                                                                                        e.printStackTrace();
                                                                                                    }
                                                                                                    dataBaseHelper.close();
                                                                                                    motivo = texto.getText().toString().trim();
                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                    pasar(conexion, general);
                                                                                                    error.setText("");
                                                                                                }else{
                                                                                                    if (general[indice][9].trim().equals("E")) {
                                                                                                        if (isNumeric(atributo)) {
                                                                                                            if (Integer.parseInt(atributo) >= Double.parseDouble(general[indice][10]) && Integer.parseInt(atributo) <= Double.parseDouble(general[indice][11])) {
                                                                                                                if (general[indice][4].trim().equals("ROMBO")) {

                                                                                                                    String url = "";
                                                                                                                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                                                                                                                    boolean ocupado = false;

                                                                                                                    String bodega = "";

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

                                                                                                                    params.add(new BasicNameValuePair("sCodEmpresa", cod_empresa.trim()));
                                                                                                                    params.add(new BasicNameValuePair("sCodSucursal", bodega.trim()));
                                                                                                                    url = "http://" + ip + "/consultarRecursoColibriHydro.php";
                                                                                                                    params.add(new BasicNameValuePair("sParametro", "R"));
                                                                                                                    String resultServer = getHttpPost(url, params);
                                                                                                                    System.out.println("---------------------------------");
                                                                                                                    System.out.println(resultServer);
                                                                                                                    try {
                                                                                                                        JSONArray jArray = new JSONArray(resultServer);
                                                                                                                        ArrayList<String> array = new ArrayList<String>();
                                                                                                                        for (int i = 0; i < jArray.length(); i++) {
                                                                                                                            JSONObject json = jArray.getJSONObject(i);
                                                                                                                            array.add((json.getString("rombo")));
                                                                                                                            if (texto.getText().toString().equals(array.get(i).trim())) {
                                                                                                                                ocupado = true;
                                                                                                                                break;
                                                                                                                            } else {
                                                                                                                                ocupado = false;
                                                                                                                            }
                                                                                                                        }


                                                                                                                    } catch (JSONException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                    }


                                                                                                                    if (ocupado) {
                                                                                                                        error.setText("El rombo no esta disponible");
                                                                                                                    } else {
                                                                                                                        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                                        try {

                                                                                                                            guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                                        } catch (IOException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                        } catch (SQLException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                        }
                                                                                                                        dataBaseHelper.close();
                                                                                                                        general[indice][5] = texto.getText().toString().trim();
                                                                                                                        pasar(conexion, general);
                                                                                                                        error.setText("");
                                                                                                                    }
                                                                                                                } else {
                                                                                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                                    try {

                                                                                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                                    } catch (IOException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                    } catch (SQLException e) {
                                                                                                                        e.printStackTrace();
                                                                                                                    }
                                                                                                                    dataBaseHelper.close();
                                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                                    pasar(conexion, general);
                                                                                                                    error.setText("");
                                                                                                                }

                                                                                                            } else {
                                                                                                                error.setText("No esta en el rango!");
                                                                                                            }
                                                                                                        } else {
                                                                                                            error.setText("No es numero!");
                                                                                                        }
                                                                                                    } else {
                                                                                                        if (general[indice][9].trim().equals("C")) {
                                                                                                            if ((texto.getText().toString().trim().length() >= Double.parseDouble(general[indice][10])) && (texto.getText().toString().trim().length() <= Double.parseDouble(general[indice][11]))) {
                                                                                                                general[indice][5] = texto.getText().toString().trim();
                                                                                                                pasar(conexion, general);
                                                                                                                error.setText("");

                                                                                                            } else {
                                                                                                                if (Double.parseDouble(general[indice][10]) == Double.parseDouble(general[indice][11]) && texto.getText().toString().trim().length() == Double.parseDouble(general[indice][10])) {
                                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                                    pasar(conexion, general);
                                                                                                                    error.setText("");
                                                                                                                } else {
                                                                                                                    error.setText("No esta en el rango!");
                                                                                                                }
                                                                                                                error.setText("No esta en el rango!");
                                                                                                            }
                                                                                                        } else {
                                                                                                            if (general[indice][9].trim().equals("F")) {
                                                                                                                if (general[indice][18].trim().equals("PROMESA_ENTREGA")) {

                                                                                                                    date = getHora();
                                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                                    pasar(conexion, general);
                                                                                                                    error.setText("");
                                                                                                                } else {
                                                                                                                    general[indice][5] = texto.getText().toString().trim();
                                                                                                                    pasar(conexion, general);
                                                                                                                    error.setText("");
                                                                                                                }
                                                                                                            } else {
                                                                                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                                                                                try {

                                                                                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                                                                                } catch (IOException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                } catch (SQLException e) {
                                                                                                                    e.printStackTrace();
                                                                                                                }
                                                                                                                dataBaseHelper.close();
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }

                                                                                                // params.add(new BasicNameValuePair("sNumOrden", num_orden));


                                /*if (resultado.get(4).equals("NIT")) {
                                    String cedula = texto.getText().toString().replaceAll("\n", "");
                                    String cedulafinal = "";


                                    String url3 = "http://" + finalIp + ":8080/update2.php";
                                    String resultServer3;
                                    try {

                                        Cedula = cedula.substring(47, 58);
                                        nombre1 = cedula.substring(95, 116);
                                        nombre2 = cedula.substring(117, 140);
                                        apellido1 = cedula.substring(58, 71);
                                        apellido2 = cedula.substring(72, 94);
                                        if (Cedula.substring(1, 2).equals("00")) {
                                            Cedula = cedula.substring(50, 58);
                                        }


                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("sNit", Cedula));
                                        params3.add(new BasicNameValuePair("sApellido1", apellido1));
                                        params3.add(new BasicNameValuePair("sApellido2", apellido2));
                                        params3.add(new BasicNameValuePair("sNombre1", nombre1));
                                        params3.add(new BasicNameValuePair("sNombre2", nombre2));
                                        resultServer3 = getHttpPost(url3, params3);
                                        System.out.println(resultServer3);

                                        String strStatusID = "0";
                                        String strError = "Unknow Status! 3";

                                        JSONObject c3;
                                        try {
                                            c3 = new JSONObject(resultServer3);
                                            strStatusID = c3.getString("StatusID");
                                            strError = c3.getString("Error");
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        if (strStatusID.equals("0")) {
                                            ad.setMessage(strError);
                                            ad.show();
                                            return false;
                                        } else {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }
                                    } catch (Exception e) {
                                        url3 = "http://" + finalIp + "/update.php";
                                        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
                                        params3.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                        params3.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                        params3.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                        params3.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                        params3.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                        params3.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                        params3.add(new BasicNameValuePair("sNumOrden", num_orden));
                                        resultServer3 = getHttpPost(url3, params3);
                                        String strStatusID = "0";
                                        String strError = "Unknow Status! 3";

                                        JSONObject c3;
                                        try {
                                            c3 = new JSONObject(resultServer3);
                                            strStatusID = c3.getString("StatusID");
                                            strError = c3.getString("Error");
                                        } catch (JSONException ex) {
                                            // TODO Auto-generated catch block
                                            ex.printStackTrace();
                                        }
                                        if (strStatusID.equals("0")) {
                                            ad.setMessage(strError);
                                            ad.show();
                                            return false;
                                        } else {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }
                                    }


                                } else {
                                    if (resultado.get(4).equals("MATRICULA")) {
                                        String matricula = texto.getText().toString().replaceAll("\n", "");
                                        matricula = matricula.replaceAll(" ", "");
                                        System.out.println("LONGITUD : " + matricula.length());
                                        System.out.println(texto.getText().toString());
                                        String matriculafinal = "";


                                        try {
                                            placa = matricula.substring(55, 61);
                                            vin = matricula.substring(61, 78);

                                            texto.setText("");

                                            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarDatosMatricula.class);
                                            Bundle bundle2 = new Bundle();
                                            bundle2.putString("placa", placa);
                                            bundle2.putString("vin", vin);
                                            intent.putExtras(bundle2);
                                            RecepcionPanel.this.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();

                                            ad.setTitle("Error! ");
                                            ad.setIcon(android.R.drawable.btn_star_big_on);
                                            ad.setPositiveButton("Close", null);
                                            String url5 = "http://" + finalIp + "/update.php";

                                            List<NameValuePair> params5 = new ArrayList<NameValuePair>();


                                            params5.add(new BasicNameValuePair("sCodEmpresa", cod_empresa));
                                            params5.add(new BasicNameValuePair("sCodSucursal", cod_sucursal));
                                            params5.add(new BasicNameValuePair("sCodUsuario", cod_usuario));
                                            params5.add(new BasicNameValuePair("sCodProceso", cod_proceso));
                                            params5.add(new BasicNameValuePair("sCodAtributo", cod_atributo));
                                            params5.add(new BasicNameValuePair("sCodValor", texto.getText().toString()));
                                            params5.add(new BasicNameValuePair("sNumOrden", num_orden));
                                            String resultServer = getHttpPost(url, params);
                                            System.out.println(resultServer);

                                            String strStatusID = "0";
                                            String strError = "Unknow Status!";

                                            JSONObject c;
                                            try {
                                                Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                                pasar(conexion, general);
                                            }catch(Exception e1){
                                                Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                    } else {
                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);


                                        JSONObject c;
                                        try {
                                            Toast.makeText(RecepcionPanel.this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                                            pasar(conexion, general);
                                        }catch(Exception e){
                                            Toast.makeText(RecepcionPanel.this, "Dont Update Data Successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }*/


                                                                                                //TALL_ENCABEZA_ORDEN
                                                /*)}
                                        }


                                    }
                                }
                            }*/


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
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                // }


                            }
                        }


                    }
                    return false;
                }

            });
        }
    }

    public void ver (View v){
        Intent intent = new Intent((Context) RecepcionPanel.this, (Class) MostrarDatosNit.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("cedula", Cedula);
        bundle2.putString("nombre1", nombre1);
        bundle2.putString("nombre2", nombre2);
        bundle2.putString("apellido1",apellido1);
        bundle2.putString("apellido2", apellido2);
        intent.putExtras(bundle2);
        RecepcionPanel.this.startActivity(intent);
    }

    public void pasar(String conexion,final String[][] general){



        if (indice == general.length - 1) {
            if (general[indice][16].trim().equals("S")){
                indice = 0;
                actualizar(general, indice, conexion);
                ok(empresa,sucursal,proceso,user,"S",general);
            }else{
                indice = 0;
                actualizar(general, indice, conexion);
                ok(empresa,sucursal,proceso,user,"A",general);
            }

        } else {
            indice++;
            actualizar(general, indice, conexion);
        }

    }

    public void pasarPrimero(String conexion,final String[][] general){

        indice = 0;
        actualizar(general, indice, conexion);

    }

    public void ok(String cod_empresa,String cod_sucursal,String cod_proceso
            ,String cod_usuario,
                   String ind_estado, String[][] general){



        if (!proceso.trim().equals("1") && !proceso.trim().equals("0") && !proceso.trim().equals("56") && !proceso.trim().equals("57")){
            columnas.add("COD_RECURSO");
            datos.add(codAtributo);
        }
        atributos = new ArrayList<>();

        if (conexion.equals("remoto")) {
            if (!proceso.trim().equals("1")) {

                for (int i = 0; i < (general.length); i++) {
                    System.out.println("general FINALES " + general[i][5]);
                }

                for (int i = 0; i < (general.length); i++) {

                    if (columnas.get(columnas.size() - 1).equals(general[i][18].trim())) {
                        columnas.set(columnas.size() - 1, general[i][18]);
                    } else {
                        columnas.add(general[i][18]);
                    }

                }

                for (int i = 0; i < (general.length); i++) {

                    if (columnas.size() <= datos.size()) {
                        datos.set(datos.size() - 1, general[i][5]);
                    } else {
                        datos.add(general[i][5]);
                    }

                }

            }

        }else{
            for (int i = 0; i < (general.length); i++) {
                columnas.add(general[i][20]);
            }

            for (int i = 0; i < (general.length); i++) {
                datos.add(general[i][5]);
            }
        }



        for (int i = 0;i<general.length;i++) {
            if (general[i][4].trim().equals("SOT")){
                soat = general[i][5].trim();
            }else{
                if (general[i][4].trim().equals("TECNICOECANICA")){
                    tecno = general[i][5].trim();
                }
            }
            atributos.add(general[i][4].trim() + " :");
            atributos.add(general[i][5].trim());


        }


        /*for (int i = 0;i<(generalAux.length-general.length);i++) {
            columnas.add(generalAux[general.length + i][18].trim());
            System.out.println(generalAux[general.length + i][18] + "COLUMNA AUXXXXXX");
        }

        for (int i = 0;i<(generalAux.length-general.length);i++) {
            datos.add(generalAux[general.length + i][5].trim());
            System.out.println(generalAux[general.length + i][5] + "DATO AUXXXXXX");
        }*/



        String bodega = "";

        if (cod_sucursal.trim().equals("51")){
            bodega = "1";
        }else{
            if (cod_sucursal.trim().equals("33")){
                bodega = "2";
            }else{
                if (cod_sucursal.trim().equals("10")){
                    bodega = "3";
                }else{
                    if (cod_sucursal.trim().equals("99")){
                        bodega = "11";
                    }
                }
            }
        }


        /*if (cod_proceso.trim().equals("1")) {


            columnas.add("KILOMETRAJE");
            datos.add(kilometraje);

            columnas.add("KM_ANTERIOR");
            datos.add(km_anterior);

            columnas.add("BODEGA");
            datos.add(bodega);

            columnas.add("SERIE");
            datos.add(cod_placa);

            columnas.add("ROMBO_USADO");
            datos.add(num_rombo);

            columnas.add("ROMBO");
            datos.add(num_rombo);

       }*/

        String faltanDatos = "false";

        for (int i =0;i<atributos.size();i=i+2){
            if (atributos.get(i+1).equals("")){
                faltanDatos ="true";
                break;
            }
        }

        if (existeTercero.equals("SI")) {

            if (ind_estado.equals("S")) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent((Context) this, (Class) GuardarPanel.class);
                bundle.putString("id", id);
                bundle.putString("conexion", conexion);
                bundle.putString("esAutonext", esAutonext);
                bundle.putString("nuevoRegistro", nuevoRegistro);
                bundle.putString("motivo", motivo);
                bundle.putString("num_grupo", num_grupo);
                bundle.putString("marca", marca);
                bundle.putString("notas", notas);
                bundle.putString("eventoRazon", eventoRazon);
                bundle.putString("eventoCita", eventoCita);
                bundle.putString("descripcionModelo", descripcionModelo);
                bundle.putString("km", kilometraje);
                bundle.putString("encuentraNit", encuentraNit);
                bundle.putString("encuentraPlaca", encuentraPlaca);
                bundle.putString("solicitud", solicitud);
                bundle.putString("tipoVehiculo", tipoVehiculo);
                bundle.putString("costo", costo);
                bundle.putString("fpago", fpago);
                bundle.putString("precio", precio);
                bundle.putString("descPrecio", descPrecio);
                bundle.putString("promesa", promesa);
                bundle.putString("tecno", tecno);
                bundle.putString("email", email);
                bundle.putString("soat", soat);
                bundle.putString("cod_empresa", cod_empresa);
                bundle.putString("cod_sucursal", cod_sucursal);
                bundle.putString("cod_proceso", cod_proceso);
                bundle.putString("cod_placa", placa);
                bundle.putString("num_rombo", num_rombo);
                bundle.putString("cod_usuario", cod_usuario);
                bundle.putString("cod_ubicacion", cod_ubicacion);
                bundle.putString("fec_proceso", fec_proceso);
                bundle.putString("num_nit", num_nit);
                bundle.putString("ind_estado", ind_estado);
                bundle.putStringArrayList("atributos", atributos);
                bundle.putStringArrayList("columnas", columnas);
                bundle.putStringArrayList("datos", datos);
                bundle.putString("ip", ip);
                bundle.putString("faltanDatos", faltanDatos);
                bundle.putString("nombre_tabla", nombreTabla);
                bundle.putString("razon", razon);
                bundle.putString("proceso", procesoOriginal);
                bundle.putSerializable("notas",arrayNotas);
                intent.putExtras(bundle);
                this.startActivityForResult(intent, request_code);
            } else {
                Bundle bundle = new Bundle();
                Intent intent = new Intent((Context) this, (Class) GuardarPanel.class);
                bundle.putString("id", id);
                bundle.putString("conexion", conexion);
                bundle.putString("esAutonext", esAutonext);
                bundle.putString("nuevoRegistro", nuevoRegistro);
                bundle.putString("motivo", motivo);
                bundle.putString("num_grupo", num_grupo);
                bundle.putString("marca", marca);
                bundle.putString("notas", notas);
                bundle.putString("eventoRazon", eventoRazon);
                bundle.putString("eventoCita", eventoCita);
                bundle.putString("descripcionModelo", descripcionModelo);
                bundle.putString("km", kilometraje);
                bundle.putString("encuentraNit", encuentraNit);
                bundle.putString("encuentraPlaca", encuentraPlaca);
                bundle.putString("solicitud", solicitud);
                bundle.putString("tipoVehiculo", tipoVehiculo);
                bundle.putString("costo", costo);
                bundle.putString("fpago", fpago);
                bundle.putString("precio", precio);
                bundle.putString("descPrecio", descPrecio);
                bundle.putString("promesa", promesa);
                bundle.putString("tecno", tecno);
                bundle.putString("email", email);
                bundle.putString("soat", soat);
                bundle.putString("cod_empresa", cod_empresa);
                bundle.putString("cod_sucursal", cod_sucursal);
                bundle.putString("cod_proceso", cod_proceso);
                bundle.putString("cod_placa", placa);
                bundle.putString("num_rombo", num_rombo);
                bundle.putString("cod_usuario", cod_usuario);
                bundle.putString("cod_ubicacion", cod_ubicacion);
                bundle.putString("fec_proceso", fec_proceso);
                bundle.putString("num_nit", num_nit);
                bundle.putString("ind_estado", "A");
                bundle.putStringArrayList("atributos", atributos);
                bundle.putStringArrayList("columnas", columnas);
                bundle.putStringArrayList("datos", datos);
                bundle.putString("ip", ip);
                bundle.putString("faltanDatos", faltanDatos);
                bundle.putString("nombre_tabla", nombreTabla);
                bundle.putString("razon", razon);
                bundle.putString("proceso", procesoOriginal);
                bundle.putSerializable("notas",arrayNotas);
                intent.putExtras(bundle);
                this.startActivityForResult(intent, request_code);
            }
        }
    }

    class ConsultarGeneral extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            mProgressDialog= new ProgressDialog(RecepcionPanel.this);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Espere...");
            mProgressDialog.show();

            columnas = new ArrayList<>();
            datos = new ArrayList<>();
            cod_atributo = general[indice][4].trim();


        }
        @Override
        protected String doInBackground(String... f_url) {



            proceso = general[indice][15].trim();
            if (general[indice][15].trim().equals("1")){
                titulo = "CLIENTES";

            }
            if (proceso.trim().equals("2")){
                titulo = "USUARIO";
            }
            if (general[indice][15].trim().equals("3")){
                titulo = "CIUDADES";
            }
            if (general[indice][15].trim().equals("4")){
                titulo = "IMPUESTOS";
            }


            nombreTabla = generalAux[indice][19].trim();

            System.out.println("TABLAAAAA " +  nombreTabla);

            proceso =general[indice][15].trim();

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
            params.add(new BasicNameValuePair("sCodProceso", proceso));
            params.add(new BasicNameValuePair("sParametro", "consultarProceso"));

            String resultServer  = getHttpPost(url,params);
            System.out.println(resultServer);
            try {
                JSONArray jArray = new JSONArray(resultServer);
                generalAuxNueva = new String[jArray.length()][21];

                int contador = 0;
                int j =0;
                longitud = jArray.length();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    generalAuxNueva[i][j] = json.getString("cod_empresa");
                    generalAuxNueva[i][j+1] = json.getString("cod_sucursal");
                    generalAuxNueva[i][j+2] = json.getString("cod_usuario");
                    generalAuxNueva[i][j+3] = json.getString("cod_proceso");
                    generalAuxNueva[i][j+4] = json.getString("cod_atributo");
                    generalAuxNueva[i][j+5] = json.getString("cod_valor");
                    generalAuxNueva[i][j+6] = json.getString("num_secuencia");
                    generalAuxNueva[i][j+7] = json.getString("num_orden");
                    generalAuxNueva[i][j+8] = json.getString("ind_requerido");
                    generalAuxNueva[i][j+9] = json.getString("ind_tipo");
                    generalAuxNueva[i][j+10] = json.getString("val_minimo");
                    generalAuxNueva[i][j+11] = json.getString("val_maximo");
                    generalAuxNueva[i][j+12] = json.getString("num_longitud");
                    generalAuxNueva[i][j+13] = json.getString("nom_ruta");
                    generalAuxNueva[i][j+14] = json.getString("val_defecto");
                    generalAuxNueva[i][j+15] = json.getString("cod_proceso_padre");
                    generalAuxNueva[i][j+16] = json.getString("ind_autonext");
                    generalAuxNueva[i][j+17] = json.getString("ind_estado");
                    if (generalAuxNueva[i][j+17].equals("I")){
                        contador++;
                    }
                    generalAuxNueva[i][j+18] = json.getString("nom_columna");
                    generalAuxNueva[i][j+19] = json.getString("nom_tabla");
                    generalAuxNueva[i][j+20] = json.getString("idx_foto");
                    j=0;
                }

                for (int i = 0; i < generalAuxNueva.length; i++) {
                    for (j = 0; j < generalAuxNueva[i].length; j++) {
                        System.out.println(" AUXILIAR  " + i + " " + generalAuxNueva[i][j]);
                    }
                }

                if (contador == 0 ){
                    generalNueva = new String[generalAuxNueva.length][21];

                    for (int i = 0; i < generalAuxNueva.length; i++) {
                        for (j = 0; j < generalAuxNueva[i].length; j++) {
                            System.out.println(" AUXILIAR  " + i + " " + generalAuxNueva[i][j]);
                            if (generalAuxNueva[i][j].equals("null")){
                                generalAuxNueva[i][j] = "";
                            }

                            generalNueva[i][j] = generalAuxNueva[i][j];

                        }


                        if (generalAuxNueva[i][17].equals("I") && generalAuxNueva[i][15].equals(generalAuxNueva[i][3])){
                            //columnas.add(generalAuxNueva[i][18]);
                            //datos.add(generalAuxNueva[i][5]);
                        }
                    }
                }else{
                    generalNueva = new String[generalAuxNueva.length-contador][21];
                    System.out.println("LONGITUD ->" + (generalAuxNueva.length-contador));

                    int k=0;
                    for (int i = 0; i < generalNueva.length; i++) {
                        for (j = 0; j < generalAuxNueva[i].length; j++) {
                            if (generalAuxNueva[k][j].equals("null")) {
                                generalAuxNueva[k][j] = "";
                            }

                            if (!generalAuxNueva[k][17].equals("I")) {
                                generalNueva[i][j] = generalAuxNueva[k][j];
                            } else {
                                if (generalAuxNueva[k][15].equals(generalAuxNueva[k][3])) {
                                    //columnas.add(generalAuxNueva[k][18].trim());
                                    //datos.add(generalAuxNueva[k][5].trim());
                                }
                                k++;
                                generalNueva[i][j] = generalAuxNueva[k][j];
                            }
                        }
                        k++;
                    }
                }

            }catch (JSONException e ){
                e.printStackTrace();
            }

            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            ArrayList<String> atributos = new ArrayList<>();
            ArrayList<String> valores = new ArrayList<>();
            Intent intent = new Intent((Context) RecepcionPanel.this, (Class) RecepcionPanel.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("nuevoRegistro", "NO");
            bundle2.putString("esAutonext", "false");
            bundle2.putString("nombreTabla", "");
            bundle2.putString("codAtributo", "");
            bundle2.putString("user", user);
            bundle2.putString("pass", pass);
            bundle2.putString("sucursal", sucursal);
            bundle2.putString("empresa", empresa);
            bundle2.putString("conexion", conexion);
            bundle2.putString("sucursal", sucursal);
            bundle2.putSerializable("general" , generalNueva);
            bundle2.putSerializable("generalAux" , generalAuxNueva);
            bundle2.putStringArrayList("columnas" , columnas);
            bundle2.putStringArrayList("datos" , datos);
            bundle2.putStringArrayList("atributos" , atributos);
            bundle2.putStringArrayList("valores" , valores);
            bundle2.putString("rombo" , num_rombo);
            bundle2.putString("ip", ip);
            bundle2.putString("titulo", titulo);
            bundle2.putString("proceso", "123");
            bundle2.putString("nombreTabla",nombreTabla);
            bundle2.putString("codAtributo",cod_atributo.trim());

            intent.putExtras(bundle2);
            startActivity(intent);


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

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public void refrescar(View v){
        mostrarImagen();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_default, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap ) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {

            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

                if (procesoOriginal.trim().equals("88")) {
                    if(general[indice][4].trim().equals("PLACA")) {

                        nombre = id + "-" + placa + "-" + general[indice][6].trim();
                    }else{
                        nombre = general[indice][4].trim() + "-" + general[indice][5].trim();
                    }
                }else{
                    nombre = general[indice][4].trim()+ "-" + general[indice][5].trim();

                }
                dirFoto =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)  + "/COLIBRI/" ;

                String file = dirFoto + nombre +  ".jpg";

                Bitmap b= BitmapFactory.decodeFile(file);
                Bitmap out = Bitmap.createScaledBitmap(b, 480, 854, false);

                FileOutputStream fOut;

                File mi_foto = new File(file);

                try {
                    fOut = new FileOutputStream(mi_foto);
                    out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    b.recycle();
                    out.recycle();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new ConsultarDatos().execute("");

                ImageView myImage = (ImageView) findViewById(R.id.imageView);
                String strPath = "";
                if (!procesoOriginal.trim().equals("88")) {
                    nombre = general[indice][4].trim() + "-" +  general[indice][5].trim();
                    //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
                    strPath = dirFoto + nombre + ".jpg";
                }else{
                    if(general[indice][4].trim().equals("PLACA")) {

                        nombre = id + "-" + placa + "-" + general[indice][6].trim();
                        strPath = dirFoto + nombre + ".jpg";
                    }else{
                        nombre = general[indice][4].trim() + "-" +  general[indice][5].trim();
                        //File imgFile = new  File(ruta_fotos + getCode() + ".jpg");
                        strPath = dirFoto + nombre + ".jpg";
                    }

                }
                System.out.println(strPath);

                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;

                Bitmap bm = BitmapFactory.decodeFile(strPath, options);

                if (bm==null) {
                    myImage.setImageResource(R.drawable.camera);
                }else{
                    myImage.setImageBitmap(bm);
                }

            }

            if (requestCode == 1) {
                if (resultCode == Activity.RESULT_OK) {
                    String parametro = data.getStringExtra("parametro");
                    String valor = data.getStringExtra("valor");
                    TextView error = (TextView) findViewById(R.id.textView_error);
                    if (parametro.equals("razon")) {
                        descripcion = data.getStringExtra("descripcion");
                        error.setText(descripcion);
                        razon = valor.trim();
                        if (razon.equals("61") || razon.equals("62")) {
                            eventoRazon = "G";
                        }
                        texto.setText(razon);
                        general[indice][5] = texto.getText().toString().trim();
                        pasar(conexion, general);

                    } else {
                        if (parametro.equals("nota")) {
                            arrayNotas[indice] = data.getStringExtra("nota");
                            texto.setText(valor.trim());
                            general[indice][5] = texto.getText().toString().trim();

                        } else {
                            if (parametro.equals("razon2")) {
                                km = data.getDataString();
                                texto.setText(data.getDataString());
                                general[indice][5] = texto.getText().toString().trim();
                                pasar(conexion, general);
                            } else {
                                if (parametro.equals("rombo")) {
                                    num_rombo = valor.trim();
                                    texto.setText(num_rombo);
                                    general[indice][5] = texto.getText().toString().trim();
                                    pasar(conexion, general);
                                } else {
                                    if (parametro.equals("guardar")) {
                                        if (valor.equals("bien")) {
                                            if (!esAutonext.equals("true")) {

                                                finish();
                                            } else {
                                                if (nuevoRegistro.equals("NO")) {
                                                    finish();
                                                } else {
                                                    for (int i = 0; i < general.length; i++) {
                                                        for (int j = 0; j < general[i].length; j++) {
                                                            //if (general[j][4].trim().equals(atributosD1.get(i).trim())) {
                                                            general[j][5] = "";
                                                            //}
                                                            actualizar(general, 0, conexion);
                                                        }
                                                    }
                                                }
                                            }

                                        } else {

                                        }
                                    } else {
                                        if (parametro.equals("fecha")) {
                                            String par = data.getStringExtra("parametro2");
                                            if (par.trim().equals("SOT")) {


                                                texto.setText(valor);
                                                DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                try {

                                                    guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                } catch (SQLException e) {
                                                    e.printStackTrace();
                                                }
                                                dataBaseHelper.close();
                                                soat = valor;
                                                general[indice][5] = texto.getText().toString().trim();
                                                pasar(conexion, general);
                                            } else {
                                                if (par.trim().equals("EXTINTOR")) {
                                                    texto.setText(valor);
                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                    try {

                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                    dataBaseHelper.close();
                                                    extintor = valor;
                                                    general[indice][5] = texto.getText().toString().trim();
                                                    pasar(conexion, general);
                                                } else {
                                                    texto.setText(valor);
                                                    DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);

                                                    try {

                                                        guardarMovimiento(general[indice][4].trim().toString(), texto.getText().toString().trim());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    } catch (SQLException e) {
                                                        e.printStackTrace();
                                                    }
                                                    dataBaseHelper.close();
                                                    tecno = valor;
                                                    general[indice][5] = texto.getText().toString().trim();
                                                    pasar(conexion, general);
                                                }
                                            }
                                        } else {
                                            if (parametro.equals("cliente")) {
                                                if (valor.equals("guardar")) {
                                                    general[indice][5] = texto.getText().toString().trim();
                                                } else {
                                                    general[indice][5] = texto.getText().toString().trim();
                                                }
                                            } else {
                                                if (parametro.equals("guardarModelo")) {
                                                    descripcionModelo = data.getStringExtra("desc");
                                                    texto.setText(valor);
                                                    modelo = texto.getText().toString().trim();
                                                    general[indice][5] = texto.getText().toString().trim();
                                                } else {
                                                    if (parametro.equals("lista")) {
                                                        String valor2 = data.getStringExtra("valor2");
                                                        String valor3 = data.getStringExtra("valor3");
                                                        precio = valor.trim();
                                                        descPrecio = valor2.trim();
                                                        costo = valor3.trim();
                                                        texto.setText(valor.trim());
                                                        general[indice][5] = texto.getText().toString().trim();
                                                        pasar(conexion, general);

                                                    } else {
                                                        if (parametro.equals("clienteNuevo")) {
                                                            existeTercero = "SI";
                                                            num_nit = valor.trim();
                                                            texto.setText(valor.trim());
                                                            general[indice][5] = texto.getText().toString().trim();
                                                            //pasar(conexion, general);

                                                        } else {
                                                            texto.setText(valor.trim());
                                                            general[indice][5] = texto.getText().toString().trim();
                                                            //pasar(conexion, general);
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
                }
                // }
            }
            /*if (parametro.equals("regresar")) {

            } else {
                if (parametro.equals("razon2")) {
                    finish();
                } else {
                    if (parametro.equals("razon")) {
                        //razon = bundle.getString("razon");
                        texto.setText(razon);
                        general[indice][5] = texto.getText().toString().trim();
                        pasar(conexion, general);
                    }else{
                        km = data.getDataString();
                        texto.setText(data.getDataString());
                        general[indice][5] = texto.getText().toString().trim();
                        pasar(conexion, general);
                    }
                }
            }*/
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public void guardarMovimiento(String atributo,String valor) throws IOException, SQLException {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(RecepcionPanel.this);
        dataBaseHelper.createDataBase();
        dataBaseHelper.openDataBase();
        dataBaseHelper.guardarMovimiento(atributo,valor);
        dataBaseHelper.close();
    }

    public int getHora() {
        Calendar calendar = Calendar.getInstance();
        Calendar c = new GregorianCalendar();
        int mMinute = c.get(Calendar.MINUTE);
        return mMinute; // Devuelve el objeto Date con las nuevas horas a?adidas
    }

    public static String remove(String input) {
        String output = "";

        for (int i =0;i<input.length()-1;i++){
            if (Character.isLetter(input.charAt(i))){
                output += input.charAt(i);
            }
        }
        return output;
    }

    public void onDestroy() {
        super.onDestroy();

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Saliendo...")
                .setMessage("Esta seguro que desea dejar de recibir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        Toast.makeText(this, "SI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
