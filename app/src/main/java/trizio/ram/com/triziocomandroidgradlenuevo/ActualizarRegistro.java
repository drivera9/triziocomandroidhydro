package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ActualizarRegistro extends AppCompatActivity {
    static String tamano = "";
    static ArrayList<Lista_entrada_panel> datosAct;
    static ArrayList<String> columnas = new ArrayList<>();
    ArrayList<String> datos = new ArrayList<>();
    private static Context context;
    static ListView lista ;
    String procesoOriginal = "";
    String cod_atributo = "";
    String codAtributo = "";
    String email = "";
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
    static String nomproc = "";
    static String ip = "" ;
    static String placa = "";
    String vin = "";
    String Cedula = "";
    String nombre1 = "";
    String nombre2 = "";
    String apellido1 = "";
    String apellido2 = "";
    String nombre = "";
    String cod_placa = "";
    static String num_rombo= "";
    String cod_ubicacion = "";
    String fec_proceso = "";
    String num_nit = "";
    String promesa = "";
    String solicitud = "";
    static String sucursal;
    int request_code = 1;

    String titulo = "";
    static String empresa;
    EditText texto;
    static String [][] generalAux;
    static String [][] general;
    String [][] generalAuxNueva;
    String [][] generalNueva;
    static String proceso = "";
    int date ;
    String fechaFoto;
    String dia ;
    String dirFoto;
    static int longitud;
    String tiempoPromesa;
    String kilometraje = "";
    String sInformativo = "";
    String parametro = "";
    static String pass;
    static String user;
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
    static String Id = "";
    static String esAutonext = "";
    static String nombreProceso = "";
    String nuevoRegistro = "";
    String nombreTercero = "";
    GridView grid;
    static ArrayList<String> atributosD1;
    static ArrayList<String> valoresD1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_registro);
        ip = getIntent().getExtras().getString("ip");
        proceso = getIntent().getExtras().getString("proceso");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActualizarRegistro.context = getApplicationContext();

        atributosD1 = new ArrayList<>();
        valoresD1 = new ArrayList<>();

        lista = (ListView) findViewById(R.id.lista);
        Bundle bundle = this.getIntent().getExtras();
        final Object[] objectArray = (Object[]) bundle.getSerializable("general");

        final Object[] objectArray2 = (Object[]) bundle.getSerializable("generalAux");

        datos = bundle.getStringArrayList("datos");
        columnas = bundle.getStringArrayList("columnas");
        atributosD1 = bundle.getStringArrayList("atributos");
        valoresD1 = bundle.getStringArrayList("valores");
        num_rombo = bundle.getString("rombo");
        nombreTercero = bundle.getString("nombreTercero");
        Id = bundle.getString("id");
        nomproc = bundle.getString("nomproc");
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

        TextView Titulo = (TextView) findViewById(R.id.titulo);
        setTitle(nomproc.trim() + " de " + nombreTercero);


    }


    public void nuevo(View v){

        ArrayList valores = new ArrayList();
        ArrayList atributos = new ArrayList();
        ArrayList ArrayDatos = new ArrayList();

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
            generalAux = new String[jArray.length()][21];

            int contador = 0;
            int j =0;
            longitud = jArray.length();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                generalAux[i][j] = json.getString("cod_empresa");
                generalAux[i][j+1] = json.getString("cod_sucursal");
                generalAux[i][j+2] = json.getString("cod_usuario");
                generalAux[i][j+3] = json.getString("cod_proceso");
                generalAux[i][j+4] = json.getString("cod_atributo");
                if (proceso.trim().equals("9")){
                    generalAux[i][j+5] = Id + "-" + placa + "-" + json.getString("num_secuencia");
                }else{
                    generalAux[i][j+5] = json.getString("cod_valor");
                }
                generalAux[i][j+6] = json.getString("num_secuencia");
                generalAux[i][j+7] = json.getString("num_orden");
                generalAux[i][j+8] = json.getString("ind_requerido");
                generalAux[i][j+9] = json.getString("ind_tipo");
                generalAux[i][j+10] = json.getString("val_minimo");
                generalAux[i][j+11] = json.getString("val_maximo");
                generalAux[i][j+12] = json.getString("num_longitud");
                generalAux[i][j+13] = json.getString("nom_ruta");
                generalAux[i][j+14] = json.getString("val_defecto");
                generalAux[i][j+15] = json.getString("cod_proceso_padre");
                generalAux[i][j+16] = json.getString("ind_autonext");
                generalAux[i][j+17] = json.getString("ind_estado");
                if (generalAux[i][j+17].equals("I")){
                    contador++;
                }
                generalAux[i][j+18] = json.getString("nom_columna");
                generalAux[i][j+19] = json.getString("nom_tabla");
                generalAux[i][j+20] = json.getString("idx_foto");
                j=0;
            }

            for (int i = 0; i < generalAux.length; i++) {
                for (j = 0; j < generalAux[i].length; j++) {
                    System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                }
            }

            if (contador == 0 ){
                general = new String[generalAux.length][21];

                for (int i = 0; i < generalAux.length; i++) {
                    for (j = 0; j < generalAux[i].length; j++) {
                        System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                        if (generalAux[i][j].equals("null")){
                            generalAux[i][j] = "";
                        }

                        general[i][j] = generalAux[i][j];

                    }


                    if (generalAux[i][17].equals("I") && generalAux[i][15].equals(generalAux[i][3])){
                        columnas.add(generalAux[i][18]);
                        ArrayDatos.add(generalAux[i][5]);
                    }
                }
            }else{
                general = new String[generalAux.length-contador][21];
                System.out.println("LONGITUD ->" + (generalAux.length-contador));

                int k=0;
                for (int i = 0; i < general.length; i++) {
                    for (j = 0; j < generalAux[i].length; j++) {
                        if (generalAux[k][j].equals("null")) {
                            generalAux[k][j] = "";
                        }

                        if (!generalAux[k][17].equals("I")) {
                            general[i][j] = generalAux[k][j];
                        } else {
                            if (generalAux[k][15].equals(generalAux[k][3])) {
                                columnas.add(generalAux[k][18].trim());
                                ArrayDatos.add(generalAux[k][5].trim());
                            }
                            k++;
                            general[i][j] = generalAux[k][j];
                        }
                    }
                    k++;
                }
            }
        }catch (JSONException e ){
            e.printStackTrace();
        }

        Intent intent = new Intent((Context) ActualizarRegistro.this, (Class) RecepcionPanel.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("nombreTabla", "");
        bundle2.putString("codAtributo", "");
        bundle2.putString("nuevoRegistro", "SI");
        bundle2.putString("user", user);
        bundle2.putString("nombreProceso", nombreProceso);
        bundle2.putString("esAutonext", esAutonext);
        bundle2.putString("pass", pass);
        bundle2.putString("sucursal", sucursal);
        bundle2.putString("empresa", empresa);
        bundle2.putString("conexion", conexion);
        bundle2.putString("sucursal", sucursal);
        bundle2.putString("placa", placa);
        bundle2.putSerializable("general", general);
        bundle2.putSerializable("generalAux", generalAux);
        bundle2.putStringArrayList("columnas", columnas);
        bundle2.putStringArrayList("datos", ArrayDatos);
        bundle2.putString("rombo", num_rombo);
        bundle2.putSerializable("general", general);
        bundle2.putString("ip", ip);
        bundle2.putString("titulo", titulo + "/Rec");
        bundle2.putString("proceso", proceso);
        bundle2.putStringArrayList("atributos", atributos);
        bundle2.putString("id", Id);
        bundle2.putStringArrayList("valores", valores);
        intent.putExtras(bundle2);
        startActivity(intent);
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
    public void onResume(){
        super.onResume();

        String url3 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params3 = new ArrayList<NameValuePair>();
        params3.add(new BasicNameValuePair("sId", Id));
        params3.add(new BasicNameValuePair("sProceso", proceso));
        params3.add(new BasicNameValuePair("sEmpresa", empresa));
        params3.add(new BasicNameValuePair("sSucursal", sucursal));
        params3.add(new BasicNameValuePair("sParametro", "grid"));

        ArrayList<String> atributos = new ArrayList<String>();
        ArrayList<String> valores = new ArrayList<String>();
        String resultServer3 = getHttpPost(url3, params3);
        System.out.println(resultServer3);
        try {

            JSONArray jArray = new JSONArray(resultServer3);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                atributos.add(json.getString("cod_atributo").trim());
                valores.add(json.getString("val_valor").trim());

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        EditText cant = (EditText) findViewById(R.id.editCantidad);
        EditText val = (EditText) findViewById(R.id.editValor);



        String[] web;
        if (atributos.size()>0) {
            web = new String[atributos.size()*2];
            int j = 0;
            for (int i = 0;i<atributos.size();i++){

                web[j] = atributos.get(i).trim();
                web[j+1] = valores.get(i).trim();
                j=j+2;
            }

        }else{
            web = new String[] {"CANTIDAD" , "0" , "VALORES" , "0"} ;
        }

        GridView grid;


        CustomGrid adapter = new CustomGrid(ActualizarRegistro.this, web);
        grid=(GridView)findViewById(R.id.grid_text);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });



        String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sId", Id));
        params.add(new BasicNameValuePair("sProceso", proceso));
        params.add(new BasicNameValuePair("sParametro", "actualizarRegistro"));

        ArrayList<String> array = new ArrayList<String>();
        String resultServer = getHttpPost(url, params);
        System.out.println("---------------------------------resultserver----------------");
        try {

            JSONArray jArray = new JSONArray(resultServer);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add(json.getString("cod_proceso"));
                array.add(json.getString("cod_atributo"));
                array.add(json.getString("cod_valor"));
                array.add(json.getString("ind_estado"));
                array.add(json.getString("id"));
                array.add(json.getString("num_secuencia"));
                array.add(json.getString("ind_foto"));
                array.add(json.getString("num_grupo"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sProceso", proceso));
        params1.add(new BasicNameValuePair("sParametro", "tamano"));

        ArrayList<String> array1 = new ArrayList<String>();
        String resultServer1 = getHttpPost(url1, params1);
        System.out.println("---------------------------------resultserver----------------");
        try {

            JSONArray jArray = new JSONArray(resultServer1);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);

                array1.add(json.getString("cod_atributo"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tamano = String.valueOf(array1.size());



        ArrayList<String> descripcion = new ArrayList<>();
        ArrayList<String> separador = new ArrayList<>();
        int contador = 0;

        String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
        params2.add(new BasicNameValuePair("sVista", "ActualizarRegistro"));
        params2.add(new BasicNameValuePair("sParametro", "vista"));


        String resultServer2 = getHttpPost(url2, params2);
        System.out.println(resultServer2);
        try {

            JSONArray jArray = new JSONArray(resultServer2);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                descripcion.add(json.getString("cod_atributo").trim());
                separador.add(json.getString("ind_separacion").trim());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        datosAct = new ArrayList<Lista_entrada_panel>();
        for (int i = 0;i<array.size();i=i+(8*Integer.parseInt(tamano))){
            String desc = "";
            for(int j = 0;j<descripcion.size();j++){
                if (descripcion.get(j).equals("cod_proceso")){
                    desc += array.get(i).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("cod_atributo")){
                    desc += array.get(i+1).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("cod_valor")){
                    desc += array.get(i+2).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("ind_estado")){
                    desc += array.get(i+3).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("id")){
                    desc += array.get(i+4).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("num_secuencia")){
                    desc += array.get(i+5).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("ind_foto")){
                    desc += array.get(i+6).trim() + separador.get(j);
                }
                if (descripcion.get(j).equals("num_grupo")){
                    desc += array.get(i+7).trim() + separador.get(j);
                }
            }

            datosAct.add(new Lista_entrada_panel(R.drawable.close, desc.substring(0,desc.length()-1)  ,"", Integer.parseInt(array.get(i+7).trim())));
        }

        System.out.println(datosAct.size() + " SIZE");

        Button reg = (Button) findViewById(R.id.texto);

        reg.setText(String.valueOf(datosAct.size()));

        lista.setAdapter(new Lista_adaptador_panel(ActualizarRegistro.this, R.layout.entrada, datosAct){
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
                final Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);

                AlertDialog.Builder dialog = new AlertDialog.Builder(ActualizarRegistro.this);
                dialog.setIcon(android.R.drawable.ic_dialog_alert);
                dialog.setTitle("CONFIRMACION");
                dialog.setMessage("QUE DESEA HACER?");
                dialog.setPositiveButton("ACTUALIZAR REGISTRO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ArrayList valores = new ArrayList();
                        ArrayList atributos = new ArrayList();
                        ArrayList ArrayDatos = new ArrayList();



                        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("sSecuencia", String.valueOf(elegido.get_proceso())));
                        params1.add(new BasicNameValuePair("sTamano", tamano));
                        params1.add(new BasicNameValuePair("sParametro", "valores"));

                        ArrayList<String> array1 = new ArrayList<String>();
                        String resultServer1 = getHttpPost(url1, params1);
                        System.out.println(resultServer1);

                        try {

                            JSONArray jArray = new JSONArray(resultServer1);

                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json = jArray.getJSONObject(i);
                                atributos.add(json.getString("cod_atributo"));
                                valores.add(json.getString("cod_valor"));
                            }

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
                            generalAux = new String[jArray.length()][21];

                            int contador = 0;
                            int j =0;
                            longitud = jArray.length();
                            for (int i = 0; i < jArray.length(); i++) {
                                JSONObject json = jArray.getJSONObject(i);
                                generalAux[i][j] = json.getString("cod_empresa");
                                generalAux[i][j+1] = json.getString("cod_sucursal");
                                generalAux[i][j+2] = json.getString("cod_usuario");
                                generalAux[i][j+3] = json.getString("cod_proceso");
                                generalAux[i][j+4] = json.getString("cod_atributo");
                                if (proceso.trim().equals("9")){
                                    generalAux[i][j+5] = Id + "-" + placa + "-" + json.getString("num_secuencia");
                                }else{
                                    generalAux[i][j+5] = json.getString("cod_valor");
                                }
                                generalAux[i][j+6] = json.getString("num_secuencia");
                                generalAux[i][j+7] = json.getString("num_orden");
                                generalAux[i][j+8] = json.getString("ind_requerido");
                                generalAux[i][j+9] = json.getString("ind_tipo");
                                generalAux[i][j+10] = json.getString("val_minimo");
                                generalAux[i][j+11] = json.getString("val_maximo");
                                generalAux[i][j+12] = json.getString("num_longitud");
                                generalAux[i][j+13] = json.getString("nom_ruta");
                                generalAux[i][j+14] = json.getString("val_defecto");
                                generalAux[i][j+15] = json.getString("cod_proceso_padre");
                                generalAux[i][j+16] = json.getString("ind_autonext");
                                generalAux[i][j+17] = json.getString("ind_estado");
                                if (generalAux[i][j+17].equals("I")){
                                    contador++;
                                }
                                generalAux[i][j+18] = json.getString("nom_columna");
                                generalAux[i][j+19] = json.getString("nom_tabla");
                                generalAux[i][j+20] = json.getString("idx_foto");
                                j=0;
                            }

                            for (int i = 0; i < generalAux.length; i++) {
                                for (j = 0; j < generalAux[i].length; j++) {
                                    System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                                }
                            }

                            if (contador == 0 ){
                                general = new String[generalAux.length][21];

                                for (int i = 0; i < generalAux.length; i++) {
                                    for (j = 0; j < generalAux[i].length; j++) {
                                        System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                                        if (generalAux[i][j].equals("null")){
                                            generalAux[i][j] = "";
                                        }

                                        general[i][j] = generalAux[i][j];

                                    }


                                    if (generalAux[i][17].equals("I") && generalAux[i][15].equals(generalAux[i][3])){
                                        columnas.add(generalAux[i][18]);
                                        ArrayDatos.add(generalAux[i][5]);
                                    }
                                }
                            }else{
                                general = new String[generalAux.length-contador][21];
                                System.out.println("LONGITUD ->" + (generalAux.length-contador));

                                int k=0;
                                for (int i = 0; i < general.length; i++) {
                                    for (j = 0; j < generalAux[i].length; j++) {
                                        if (generalAux[k][j].equals("null")) {
                                            generalAux[k][j] = "";
                                        }

                                        if (!generalAux[k][17].equals("I")) {
                                            general[i][j] = generalAux[k][j];
                                        } else {
                                            if (generalAux[k][15].equals(generalAux[k][3])) {
                                                columnas.add(generalAux[k][18].trim());
                                                ArrayDatos.add(generalAux[k][5].trim());
                                            }
                                            k++;
                                            general[i][j] = generalAux[k][j];
                                        }
                                    }
                                    k++;
                                }
                            }
                        }catch (JSONException e ){
                            e.printStackTrace();
                        }
                        Intent intent = new Intent((Context) ActualizarRegistro.this, (Class) RecepcionPanel.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("nombreTabla", "");
                        bundle2.putString("codAtributo", "");
                        bundle2.putString("nuevoRegistro", "NO");
                        bundle2.putString("num_grupo", String.valueOf(elegido.get_proceso()));
                        bundle2.putString("user", user);
                        bundle2.putString("nombreProceso", nombreProceso);
                        bundle2.putString("esAutonext", esAutonext);
                        bundle2.putString("pass", pass);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("empresa", empresa);
                        bundle2.putString("conexion", conexion);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("placa", placa);
                        bundle2.putSerializable("general", general);
                        bundle2.putSerializable("generalAux", generalAux);
                        bundle2.putStringArrayList("columnas", columnas);
                        bundle2.putStringArrayList("datos", ArrayDatos);
                        bundle2.putString("rombo", num_rombo);
                        bundle2.putSerializable("general", general);
                        bundle2.putString("ip", ip);
                        bundle2.putString("titulo", titulo + "/Rec");
                        bundle2.putString("proceso", proceso);
                        bundle2.putStringArrayList("atributos", atributos);
                        bundle2.putString("id", Id);
                        bundle2.putStringArrayList("valores", valores);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }

                });
                dialog.setNegativeButton("BORRAR REGISTRO", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String url1 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("sProceso", proceso));
                        params1.add(new BasicNameValuePair("sGrupo", String.valueOf(elegido.get_proceso())));
                        params1.add(new BasicNameValuePair("sParametro", "eliminarGrupo"));

                        ArrayList<String> array1 = new ArrayList<String>();
                        String resultServer1 = getHttpPost(url1, params1);
                        System.out.println(resultServer1);

                        actualizarLista();
                    }

                });
                AlertDialog dialogo = dialog.create();
                dialogo.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation; //style id
                //dialogo.show();

                CustomDialogClassPanel cdd=new CustomDialogClassPanel(ActualizarRegistro.this,String.valueOf(elegido.get_proceso()),
                        proceso,tamano,ip,empresa,sucursal,pass,generalAux,general,Id,placa,columnas,user,nombreProceso,esAutonext,
                        conexion,num_rombo,elegido.get_textoEncima(),atributosD1,valoresD1);
                cdd.show();


            }
        });
    }

    public static void actualizarLista(){
        String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sId", Id));
        params.add(new BasicNameValuePair("sProceso", proceso));
        params.add(new BasicNameValuePair("sParametro", "actualizarRegistro"));

        ArrayList<String> array = new ArrayList<String>();
        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);
        try {

            JSONArray jArray = new JSONArray(resultServer);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add(json.getString("cod_proceso"));
                array.add(json.getString("cod_atributo"));
                array.add(json.getString("cod_valor"));
                array.add(json.getString("ind_estado"));
                array.add(json.getString("id"));
                array.add(json.getString("num_grupo"));
                array.add(json.getString("ind_foto"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sProceso", proceso));
        params1.add(new BasicNameValuePair("sParametro", "tamano"));

        ArrayList<String> array1 = new ArrayList<String>();
        String resultServer1 = getHttpPost(url1, params1);
        System.out.println("---------------------------------resultserver----------------");
        try {

            JSONArray jArray = new JSONArray(resultServer1);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array1.add(json.getString("cod_atributo"));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        tamano = String.valueOf(array1.size());




        datosAct = new ArrayList<Lista_entrada_panel>();
        for (int i = 0;i<array.size();i=(i+(7*Integer.parseInt(tamano)))){
            datosAct.add(new Lista_entrada_panel(R.drawable.close, nomproc + " " + array.get(i+5),"", Integer.parseInt(array.get(i+5))));
        }

        System.out.println(datosAct.size() + " tamano " + tamano);

        lista.setAdapter(new Lista_adaptador_panel(ActualizarRegistro.context, R.layout.entrada, datosAct){
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
            public void onItemClick(final AdapterView<?> pariente, View view, final int posicion, long id) {

                final Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);

                CustomDialogClassPanel cdd=new CustomDialogClassPanel((Activity) ActualizarRegistro.context,String.valueOf(elegido.get_proceso()),
                        proceso,tamano,ip,empresa,sucursal,pass,generalAux,general,Id,placa,columnas,user,nombreProceso,esAutonext,
                        conexion,num_rombo,elegido.get_textoEncima(),atributosD1,valoresD1);

                cdd.show();


            }
        });
    }

    private class MyCustomAdapter extends ArrayAdapter<Atributos> {

        private ArrayList<Atributos> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Atributos> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Atributos>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            TextView boton;
            RelativeLayout relative;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.atributos_info, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.boton = (TextView) convertView.findViewById(R.id.boton);
                holder.relative = (RelativeLayout) convertView.findViewById(R.id.relative);

                convertView.setTag(holder);

                MyCustomAdapter.ViewHolder finalHolder = holder;

                Atributos country = countryList.get(position);
                holder.code.setText(country.getName());

                if (country.getFormula().equals("F")) {
                    holder.boton.setVisibility(View.VISIBLE);
                }else{
                    holder.boton.setVisibility(View.INVISIBLE);
                }

                final String atr = holder.code.getText().toString();

                holder.boton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                    }
                });

                /*holder.code.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Atributos country = countryList.get(position);
                        atributo = country.getName();

                        new ConsultarDetalles().execute("");
                    }
                });

                holder.relative.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Atributos country = countryList.get(position);
                        atributo = country.getName();

                        new ConsultarDetalles().execute("");
                    }
                });*/
            }
            else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }



            return convertView;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_procesos, menu);

        int positionOfMenuItem = 0; // or whatever...
        MenuItem item = menu.getItem(positionOfMenuItem);
        SpannableString s = new SpannableString("SETTINGS");
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        item.setTitle(s);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            ArrayList valores = new ArrayList();
            ArrayList atributos = new ArrayList();
            ArrayList ArrayDatos = new ArrayList();

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
                generalAux = new String[jArray.length()][21];

                int contador = 0;
                int j =0;
                longitud = jArray.length();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    generalAux[i][j] = json.getString("cod_empresa");
                    generalAux[i][j+1] = json.getString("cod_sucursal");
                    generalAux[i][j+2] = json.getString("cod_usuario");
                    generalAux[i][j+3] = json.getString("cod_proceso");
                    generalAux[i][j+4] = json.getString("cod_atributo");
                    if (proceso.trim().equals("9")){
                        generalAux[i][j+5] = Id + "-" + placa + "-" + json.getString("num_secuencia");
                    }else{
                        generalAux[i][j+5] = json.getString("cod_valor");
                    }
                    generalAux[i][j+6] = json.getString("num_secuencia");
                    generalAux[i][j+7] = json.getString("num_orden");
                    generalAux[i][j+8] = json.getString("ind_requerido");
                    generalAux[i][j+9] = json.getString("ind_tipo");
                    generalAux[i][j+10] = json.getString("val_minimo");
                    generalAux[i][j+11] = json.getString("val_maximo");
                    generalAux[i][j+12] = json.getString("num_longitud");
                    generalAux[i][j+13] = json.getString("nom_ruta");
                    generalAux[i][j+14] = json.getString("val_defecto");
                    generalAux[i][j+15] = json.getString("cod_proceso_padre");
                    generalAux[i][j+16] = json.getString("ind_autonext");
                    generalAux[i][j+17] = json.getString("ind_estado");
                    if (generalAux[i][j+17].equals("I")){
                        contador++;
                    }
                    generalAux[i][j+18] = json.getString("nom_columna");
                    generalAux[i][j+19] = json.getString("nom_tabla");
                    generalAux[i][j+20] = json.getString("idx_foto");
                    j=0;
                }

                for (int i = 0; i < generalAux.length; i++) {
                    for (j = 0; j < generalAux[i].length; j++) {
                        System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                    }
                }

                if (contador == 0 ){
                    general = new String[generalAux.length][21];

                    for (int i = 0; i < generalAux.length; i++) {
                        for (j = 0; j < generalAux[i].length; j++) {
                            System.out.println(" AUXILIAR  " + i + " " + generalAux[i][j]);
                            if (generalAux[i][j].equals("null")){
                                generalAux[i][j] = "";
                            }

                            general[i][j] = generalAux[i][j];

                        }


                        if (generalAux[i][17].equals("I") && generalAux[i][15].equals(generalAux[i][3])){
                            columnas.add(generalAux[i][18]);
                            ArrayDatos.add(generalAux[i][5]);
                        }
                    }
                }else{
                    general = new String[generalAux.length-contador][21];
                    System.out.println("LONGITUD ->" + (generalAux.length-contador));

                    int k=0;
                    for (int i = 0; i < general.length; i++) {
                        for (j = 0; j < generalAux[i].length; j++) {
                            if (generalAux[k][j].equals("null")) {
                                generalAux[k][j] = "";
                            }

                            if (!generalAux[k][17].equals("I")) {
                                general[i][j] = generalAux[k][j];
                            } else {
                                if (generalAux[k][15].equals(generalAux[k][3])) {
                                    columnas.add(generalAux[k][18].trim());
                                    ArrayDatos.add(generalAux[k][5].trim());
                                }
                                k++;
                                general[i][j] = generalAux[k][j];
                            }
                        }
                        k++;
                    }
                }
            }catch (JSONException e ){
                e.printStackTrace();
            }

            Intent intent = new Intent((Context) ActualizarRegistro.this, (Class) RecepcionPanel.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("nombreTabla", "");
            bundle2.putString("codAtributo", "");
            bundle2.putString("nuevoRegistro", "SI");
            bundle2.putString("user", user);
            bundle2.putString("nombreProceso", nombreProceso);
            bundle2.putString("esAutonext", esAutonext);
            bundle2.putString("pass", pass);
            bundle2.putString("sucursal", sucursal);
            bundle2.putString("empresa", empresa);
            bundle2.putString("conexion", conexion);
            bundle2.putString("sucursal", sucursal);
            bundle2.putString("placa", placa);
            bundle2.putSerializable("general", general);
            bundle2.putSerializable("generalAux", generalAux);
            bundle2.putStringArrayList("columnas", columnas);
            bundle2.putStringArrayList("datos", ArrayDatos);
            bundle2.putString("rombo", num_rombo);
            bundle2.putSerializable("general", general);
            bundle2.putString("ip", ip);
            bundle2.putString("titulo", titulo + "/Rec");
            bundle2.putString("proceso", proceso);
            bundle2.putStringArrayList("atributos", atributos);
            bundle2.putString("id", Id);
            bundle2.putStringArrayList("valores", valores);
            intent.putExtras(bundle2);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
