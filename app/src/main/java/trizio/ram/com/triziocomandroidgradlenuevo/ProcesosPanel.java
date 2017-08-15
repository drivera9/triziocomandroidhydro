package trizio.ram.com.triziocomandroidgradlenuevo;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProcesosPanel extends AppCompatActivity {
    Context context;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    public int totalpages = 2;
    String nombreProceso = "";
    String hayAtributos = "";
    boolean hayDetalle = false;
    String esAutonext = "";
    String nomproc = "";
    String nombreTercero = "";
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
        setContentView(R.layout.activity_procesos_panel);
        Bundle bundle = this.getIntent().getExtras();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        layout = (LinearLayout) findViewById(R.id.LayoutRelativeProcesos);
        Progress = findViewById(R.id.progressbar);
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        Id = bundle.getString("id");
        nombreTercero = bundle.getString("nombreTercero");
        sucursal = bundle.getString("sucursal");
        placa = bundle.getString("placa");
        procesos = bundle.getStringArrayList("procesos");
        nomProceso = bundle.getStringArrayList("nomProcesos");
        autoNextProcesos = bundle.getStringArrayList("autoNextProcesos");


        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");


        titulo = "Sucursal " + sucursal;
        setTitle(titulo);

        TextView textPlaca = (TextView) findViewById(R.id.textPlaca);
        setTitle(nombreTercero);




        ArrayList<Lista_entrada_panel> datos = new ArrayList<Lista_entrada_panel>();
        ListView lista = (ListView) findViewById(R.id.listView2);

        if (conexion.equals("local")) {

            /*DataBaseHelper dataBaseHelper = new DataBaseHelper(ProcesosPanel.this);


            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            procesos = new ArrayList<String>();
            nomProceso = new ArrayList<String>();

            procesos = dataBaseHelper.getProcesosJoin(user,sucursal,empresa);


            for (int i = 0;i<procesos.size();i++) {

                String estaId = dataBaseHelper.getId(Id,procesos.get(i));
                String nom_proceso = dataBaseHelper.getNomProcesos(procesos.get(i));

                //Toast.makeText(this, estaId, Toast.LENGTH_SHORT).show();

                try {

                    if (procesos.get(i).trim().equals("2")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close,  nom_proceso,"", Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nom_proceso ,"", Integer.parseInt(procesos.get(i))));
                            continue;

                        }

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try {

                    if (procesos.get(i).trim().equals("3")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("4")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("5")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("6")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"",  Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try{
                    if (procesos.get(i).trim().equals("7")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("8")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("9")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try {

                    if (procesos.get(i).trim().equals("10")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("11")) {

                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"", Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("12")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("13")) {

                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("15")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("16")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("17")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("18")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("19")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("20")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
            }
            lista.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada, datos){
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

                    if (elegido.get_textoDebajo().trim().equals("detalle")){
                        hayDetalle = true;
                    }else{
                        hayDetalle = false;
                    }

                    nombreProceso = elegido.get_textoEncima().trim();

                    mProgressDialog = new ProgressDialog(ProcesosPanel.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();



                    DataBaseHelper dataBaseHelper = new DataBaseHelper(ProcesosPanel.this);


                    try {
                        dataBaseHelper.createDataBase();
                        dataBaseHelper.openDataBase();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    String num_rombo = dataBaseHelper.getRombo(empresa,sucursal);

                    ArrayList g = dataBaseHelper.getGeneral(pass,empresa,sucursal,procesoFinal);

                    try{

                        generalAux = new String[g.size()/21][21];

                        int contador = 0;
                        int j =0;
                        longitud = g.size()/21;
                        for (int i = 0; i < g.size(); i=i+21) {
                            generalAux[i][j] = g.get(0).toString();
                            generalAux[i][j+1] =  g.get(1).toString();
                            generalAux[i][j+2] =  g.get(2).toString();
                            generalAux[i][j+3] =  g.get(3).toString();
                            generalAux[i][j+4] =  g.get(4).toString();
                            if (procesoFinal.trim().equals("9")){
                                generalAux[i][j+5] = Id + "-" + placa + "-" +  g.get(6).toString();;
                            }else{
                                generalAux[i][j+5] =  g.get(5).toString();;
                            }
                            generalAux[i][j+6] =  g.get(6).toString();
                            generalAux[i][j+7] =  g.get(7).toString();
                            generalAux[i][j+8] =  g.get(8).toString();
                            generalAux[i][j+9] =  g.get(9).toString();
                            generalAux[i][j+10] =  g.get(10).toString();
                            generalAux[i][j+11] =  g.get(11).toString();
                            generalAux[i][j+12] =  g.get(12).toString();
                            generalAux[i][j+13] =  g.get(13).toString();
                            generalAux[i][j+14] =  g.get(14).toString();
                            generalAux[i][j+15] =  g.get(18).toString();
                            generalAux[i][j+16] =  g.get(16).toString();
                            generalAux[i][j+17] =  g.get(17).toString();
                            if (generalAux[i][j+17].equals("I")){
                                contador++;
                            }
                            generalAux[i][j+18] =  g.get(20).toString();
                            generalAux[i][j+19] =  g.get(19).toString();
                            generalAux[i][j+20] =  g.get(21).toString();
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

                        esta = true;




                    }catch (Exception e ){

                        esta = false;
                        e.printStackTrace();
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent((Context) getApplicationContext(), (Class) CrearAtributo.class);
                        bundle.putString("sucursal", sucursal);
                        bundle.putString("empresa", empresa);
                        bundle.putString("user", user);
                        bundle.putString("pass", pass);
                        bundle.putString("conexion", conexion);
                        bundle.putString("ip", ip);
                        bundle.putString("id", Id);
                        bundle.putString("proceso" ,procesoFinal);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }



                    mProgressDialog.dismiss();
                }
            });*/
        }else{
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

            for (int i = 0;i<estaId.size();i++){

                try {

                    if (!estaId.get(i).trim().equals("2")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"", Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }

                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try {

                    if (!estaId.get(i).trim().equals("3")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (!estaId.get(i).trim().equals("4")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (!estaId.get(i).trim().equals("5")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (!estaId.get(i).trim().equals("6")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try{
                    if (!estaId.get(i).trim().equals("7")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("8")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("9")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try {

                    if (estaId.get(i).trim().equals("10")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (!estaId.get(i).trim().equals("11")) {

                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"", Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("12")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (!estaId.get(i).trim().equals("13")) {

                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (!estaId.get(i).trim().equals("15")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("16")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("17")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("18")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("19")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (!estaId.get(i).trim().equals("20")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (!estaId.get(i).trim().equals("88")) {
                        //if (estaId.trim().equals("") || estaId.trim().equals("null")){
                        datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                        continue;

                    }else{
                        datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                        continue;

                    }
                    //}
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }



            }





            lista.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada, datos){
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

                    if (elegido.get_textoDebajo().trim().equals("detalle")){
                        hayDetalle = true;
                    }else{
                        hayDetalle = false;
                    }

                    nombreProceso = elegido.get_textoEncima().trim();

                    mProgressDialog= new ProgressDialog(ProcesosPanel.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    switch (elegido.get_textoEncima().trim()){
                        case "LLANTAS":
                            procesoFinal = "2";
                            break;

                        case "FRENOS":
                            procesoFinal = "3";
                            break;

                        case "RECEPCION":
                            procesoFinal = "1";
                            break;

                        case "LUCES":
                            procesoFinal = "4";
                            break;

                        case "PLUMILLAS":
                            procesoFinal = "5";
                            break;

                        case "BATERIA":
                            procesoFinal = "6";
                            break;

                        case "SUSPENSION":
                            procesoFinal = "7";
                            break;

                        case "LATAS":
                            procesoFinal = "8";
                            break;

                        case "FOTOS":
                            procesoFinal = "9";
                            break;

                        case "INFORME":
                            procesoFinal = "10";
                            break;

                    }

                    System.out.println("NUMERO DE PROCESO !!! - " + procesoFinal);

                    new ConsultarGeneral().execute("");

                }
            });
        }

    }

    class ConsultarGeneral extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            mProgressDialog.show();

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

            String resultServer  = getHttpPost(url,params);
            System.out.println(resultServer);
            ArrayList<String> datos = new ArrayList<>();
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
                    if (procesoFinal.trim().equals("9")){
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
                        columnas.add(json.getString("nom_columna"));
                        datos.add(json.getString("cod_valor"));
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

                esta = true;

            }catch (JSONException e ){

                esta = false;
                e.printStackTrace();
                Bundle bundle = new Bundle();
                Intent intent = new Intent((Context) getApplicationContext(), (Class) CrearAtributo.class);
                bundle.putString("sucursal", sucursal);
                bundle.putString("empresa", empresa);
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                bundle.putString("conexion", conexion);
                bundle.putString("ip", ip);
                bundle.putString("id", Id);
                bundle.putString("proceso" ,procesoFinal);
                intent.putExtras(bundle);
                startActivity(intent);
            }



            String url12 = "http://" + ip + "/consultarGeneralColibriHydro.php";
            List<NameValuePair> params12 = new ArrayList<NameValuePair>();


            params12.add(new BasicNameValuePair("sId",Id));
            params12.add(new BasicNameValuePair("sProceso",procesoFinal));
            params12.add(new BasicNameValuePair("sParametro", "atributos"));


            String resultServer12 = getHttpPost(url12, params12);
            System.out.println(" ATRIBUTOSSSSSS ---------------------------------");
            System.out.println(resultServer12);
            try {
                JSONArray jArray = new JSONArray(resultServer12);
                ArrayList<String> array = new ArrayList<String>();
                //Toast.makeText(getApplicationContext()," proceso " + array.get(0),Toast.LENGTH_LONG).show();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    atributos.add((json.getString("cod_atributo")));
                    valores.add((json.getString("cod_valor")));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            if (atributos.size()>0){
                hayAtributos = "true";
            }

            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {


            if (procesoFinal.equals("101")){
                Intent i = new Intent(ProcesosPanel.this,CotizacionMETRO.class);
                i.putExtra("ip",ip);
                startActivity(i);
                mProgressDialog.dismiss();
            }else {
                if (esta) {
                    if (hayAtributos.equals("true") && hayDetalle) {
                        Intent intent = new Intent((Context) ProcesosPanel.this, (Class) ActualizarRegistro.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("tamano", String.valueOf(atributos.size()));
                        bundle2.putString("nombreTabla", "");
                        bundle2.putString("codAtributo", "");
                        bundle2.putString("nuevoRegistro", "SI");
                        bundle2.putString("nomproc", nomproc);
                        bundle2.putString("esAutonext", esAutonext);
                        bundle2.putString("user", user);
                        bundle2.putString("nombreProceso", nombreProceso);
                        bundle2.putString("nombreTercero", nombreTercero);
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
                        bundle2.putString("proceso", procesoFinal);
                        bundle2.putStringArrayList("atributos", atributos);
                        bundle2.putString("id", Id);
                        bundle2.putStringArrayList("valores", valores);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                        mProgressDialog.dismiss();
                    } else {

                        Intent intent = new Intent((Context) ProcesosPanel.this, (Class) RecepcionPanel.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("nombreTabla", "");
                        bundle2.putString("codAtributo", "");
                        bundle2.putString("nuevoRegistro", "NO");
                        bundle2.putString("num_grupo", "");
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
                        bundle2.putString("proceso", procesoFinal);
                        bundle2.putStringArrayList("atributos", atributos);
                        bundle2.putString("id", Id);
                        bundle2.putStringArrayList("valores", valores);
                        intent.putExtras(bundle2);
                        startActivity(intent);
                        mProgressDialog.dismiss();
                    }

                } else {
                    mProgressDialog.dismiss();
                }
            }
        }
    }




    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        showProgress(show, this,Progress ,layout);
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
            Intent i = new Intent(ProcesosPanel.this,CrearProceso.class);
            i.putExtra("empresa" , empresa);
            i.putExtra("sucursal" , sucursal);
            i.putExtra("ip" , ip);
            i.putExtra("user" , user);
            i.putExtra("pass" , pass);

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Saliendo...")
                .setMessage("Esta Seguro que desea salir?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DataBaseHelper dataBaseHelper = new DataBaseHelper(ProcesosPanel.this);
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
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void onResume(){
        super.onResume();


        ArrayList<Lista_entrada_panel> datos = new ArrayList<Lista_entrada_panel>();
        ListView lista = (ListView) findViewById(R.id.listView2);


        if (conexion.equals("local")) {
            /*

            DataBaseHelper dataBaseHelper = new DataBaseHelper(ProcesosPanel.this);


            try {
                dataBaseHelper.createDataBase();
                dataBaseHelper.openDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            for (int i = 0;i<procesos.size();i++) {

                String estaId = dataBaseHelper.getId(Id,procesos.get(i));

                //Toast.makeText(this, estaId, Toast.LENGTH_SHORT).show();

                try {

                    if (procesos.get(i).trim().equals("2")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"".trim(), Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                            continue;

                        }

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try {

                    if (procesos.get(i).trim().equals("3")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("4")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"".trim() , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("5")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("6")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try{
                    if (procesos.get(i).trim().equals("7")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("8")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("9")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try {

                    if (procesos.get(i).trim().equals("10")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("11")) {

                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"", Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("12")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("13")) {

                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("15")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("16")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("17")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("18")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("19")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try{
                    if (procesos.get(i).trim().equals("20")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try{
                    if (procesos.get(i).trim().equals("88")) {
                        if (estaId.trim().equals("") || estaId.trim().equals("null")){
                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }else{
                            datos.add(new Lista_entrada_panel(R.drawable.validacion, nomProceso.get(i).trim() ,"" , Integer.parseInt(procesos.get(i))));
                            continue;

                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
            }
            lista.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada, datos){
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

                    if (elegido.get_textoDebajo().trim().equals("detalle")){
                        hayDetalle = true;
                    }else{
                        hayDetalle = false;
                    }

                    nombreProceso = elegido.get_textoEncima().trim();

                    mProgressDialog = new ProgressDialog(ProcesosPanel.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(ProcesosPanel.this);

                    try {
                        dataBaseHelper.createDataBase();
                        dataBaseHelper.openDataBase();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                    String num_rombo = dataBaseHelper.getRombo(empresa,sucursal);

                    ArrayList g = dataBaseHelper.getGeneral(pass,empresa,sucursal,procesoFinal);

                    if (g.size()>0){

                        generalAux = new String[g.size()/21][21];

                        int contador = 0;
                        int j =0;
                        longitud = g.size()/21;
                        for (int i = 0; i < g.size(); i=i+21) {
                            generalAux[i][j] = g.get(0).toString();
                            generalAux[i][j+1] =  g.get(1).toString();
                            generalAux[i][j+2] =  g.get(2).toString();
                            generalAux[i][j+3] =  g.get(3).toString();
                            generalAux[i][j+4] =  g.get(4).toString();
                            if (procesoFinal.trim().equals("9")){
                                generalAux[i][j+5] = Id + "-" + placa + "-" +  g.get(6).toString();;
                            }else{
                                generalAux[i][j+5] =  g.get(5).toString();;
                            }
                            generalAux[i][j+6] =  g.get(6).toString();
                            generalAux[i][j+7] =  g.get(7).toString();
                            generalAux[i][j+8] =  g.get(8).toString();
                            generalAux[i][j+9] =  g.get(9).toString();
                            generalAux[i][j+10] =  g.get(10).toString();
                            generalAux[i][j+11] =  g.get(11).toString();
                            generalAux[i][j+12] =  g.get(12).toString();
                            generalAux[i][j+13] =  g.get(13).toString();
                            generalAux[i][j+14] =  g.get(14).toString();
                            generalAux[i][j+15] =  g.get(18).toString();
                            generalAux[i][j+16] =  g.get(16).toString();
                            generalAux[i][j+17] =  g.get(17).toString();
                            if (generalAux[i][j+17].equals("I")){
                                contador++;
                            }
                            generalAux[i][j+18] =  g.get(20).toString();
                            generalAux[i][j+19] =  g.get(19).toString();
                            generalAux[i][j+20] =  g.get(21).toString();
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

                        esta = true;




                    }else{

                        esta = false;
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent((Context) getApplicationContext(), (Class) CrearAtributo.class);
                        bundle.putString("sucursal", sucursal);
                        bundle.putString("empresa", empresa);
                        bundle.putString("user", user);
                        bundle.putString("pass", pass);
                        bundle.putString("conexion", conexion);
                        bundle.putString("ip", ip);
                        bundle.putString("id", Id);
                        bundle.putString("proceso" ,procesoFinal);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }



                    mProgressDialog.dismiss();
                }
            });*/
        } else {
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
                    if (!(json.getString("cod_proceso").equals("200"))) {
                        if (!(json.getString("cod_proceso").equals("201"))) {
                            if (!(json.getString("cod_proceso").equals("0"))) {
                                if (!(json.getString("cod_proceso").equals("30"))) {
                                    if (!(json.getString("cod_proceso").equals("20"))) {
                                        if (!(json.getString("cod_proceso").equals("21"))) {
                                            if (!(json.getString("cod_proceso").equals("22"))) {
                                                if (!(json.getString("cod_proceso").equals("16"))) {
                                                    if (!(json.getString("cod_proceso").equals("54"))) {
                                                        if (!(json.getString("cod_proceso").trim().equals("4"))) {
                                                            if (!(json.getString("cod_proceso").trim().equals("6"))) {
                                                                if (!(json.getString("cod_proceso").trim().equals("8"))) {
                                                                    if (!(json.getString("cod_proceso").trim().equals("100"))) {
                                                                        if (!(json.getString("cod_proceso").trim().equals("102"))) {
                                                                            if (!(json.getString("cod_proceso").trim().equals("144"))) {
                                                                                if (!(json.getString("cod_proceso").trim().equals("199"))) {
                                                                                    if (!(json.getString("cod_proceso").trim().equals("23"))) {
                                                                                        procesos.add(json.getString("cod_proceso").trim());
                                                                                        nomProceso.add(json.getString("nom_proceso").trim());
                                                                                        if (json.getString("cod_proceso").trim().equals(json.getString("cod_proceso_cola").trim()) &&
                                                                                                json.getString("cod_proceso_cabeza").trim().equals(json.getString("cod_proceso").trim())) {
                                                                                            autoNextProcesos.add("true");
                                                                                        } else {
                                                                                            autoNextProcesos.add("false");
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
                        }
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
                                            if (!procesos.get(i).trim().equals("101")) {
                                                if (estaId.size() > 0) {

                                                    try {
                                                        if (autoNextProcesos.get(i).trim().equals("true")) {
                                                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim(), "detalle", Integer.parseInt(procesos.get(i))));
                                                            continue;
                                                        } else {
                                                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim(), "", Integer.parseInt(procesos.get(i))));
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
                                                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim(), "detalle", Integer.parseInt(procesos.get(i))));
                                                            continue;
                                                        } else {
                                                            datos.add(new Lista_entrada_panel(R.drawable.close, nomProceso.get(i).trim(), "", Integer.parseInt(procesos.get(i))));
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

            }

            for (int i = 0;i< estaId.size();i++){
                for (int j = 0;j<datos.size();j++){
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

                    if (elegido.get_textoDebajo().trim().equals("detalle")){
                        hayDetalle = true;
                    }else{
                        hayDetalle = false;
                    }

                    nombreProceso = elegido.get_textoEncima().trim();

                    mProgressDialog = new ProgressDialog(ProcesosPanel.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    procesoFinal = String.valueOf(elegido.get_proceso());

                    esAutonext = autoNextProcesos.get(posicion);


                    System.out.println("NUMERO DE PROCESO !!! - " + procesoFinal);

                    nomproc = elegido.get_textoEncima();

                    new ConsultarGeneral().execute("");

                }
            });
        }

    }

    public void añadir(View v){
        Intent i = new Intent(ProcesosPanel.this,CrearProceso.class);
        i.putExtra("empresa" , empresa);
        i.putExtra("sucursal" , sucursal);
        i.putExtra("ip" , ip);
        i.putExtra("user" , user);
        i.putExtra("pass" , pass);

        startActivity(i);
    }

    public void firmar(View v){
        Intent intent = new Intent(ProcesosPanel.this, Firma2.class);
        Bundle bundle2 = new Bundle();
        bundle2.putString("ip", ip);
        bundle2.putString("placa", placa);
        bundle2.putString("id", Id);
        intent.putExtras(bundle2);
        startActivity(intent);
    }

    public void emailFotos(View v){

        new EnviarCorreoFotos().execute("");
    }

    public void email(View v){

        new EnviarCorreoInforme().execute("");
    }

    public void pdfFotos(View view) {

        String url26 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params26 = new ArrayList<NameValuePair>();
        params26.add(new BasicNameValuePair("sParametro", "pdfFotos"));
        params26.add(new BasicNameValuePair("sId", Id));


        String resultServer26 = getHttpPost(url26, params26);
        System.out.println("---------------------------------resultserver----------------");

        int indice = 0;
        ArrayList<String> array26 = new ArrayList<String>();
        try {

            JSONArray jArray = new JSONArray(resultServer26);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array26.add(json.getString("nom_proceso").trim());
                array26.add(json.getString("cod_atributo").trim());
                array26.add(json.getString("cod_valor").trim());
                array26.add(json.getString("foto").trim());

                indice++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (array26.size() <=3){
            totalpages = 1;
        }else{
            if (array26.size() >=4 && array26.size() <= 12){
                totalpages = 2;
            }else{
                if (array26.size() > 12 && array26.size() <= 20) {
                    totalpages = 3;
                }else{
                    totalpages = 4;
                }
            }
        }


        /*switch (indice) {
            case 1 :  totalpages = 1;

                break;

            case 2 | 3 :  totalpages = 2;
                Toast.makeText(getApplicationContext(), "si", Toast.LENGTH_SHORT).show();
                break;

            case 4 | 5 :  totalpages = 3;
                break;

            case 6 | 7 :  totalpages = 4;
                break;

            case 8 | 9 :  totalpages = 5;
                break;

            case 10 | 11 :  totalpages = 6;
                break;

            case 12 | 13 :  totalpages = 7;
                break;

            case 14 | 15 :  totalpages = 8;
                break;
        }*/


        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) +
                " Document";

        printManager.print(jobName, new MyPrintDocumentAdapterFotos(this),
                null);


    }

    public void pdf(View view)
    {

        String url26 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params26 = new ArrayList<NameValuePair>();
        params26.add(new BasicNameValuePair("sParametro", "pdf"));
        params26.add(new BasicNameValuePair("sId", Id));


        String resultServer26 = getHttpPost(url26, params26);
        System.out.println("---------------------------------resultserver----------------");

        ArrayList<String> array26 = new ArrayList<String>();
        try {

            JSONArray jArray = new JSONArray(resultServer26);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array26.add(json.getString("cod_atributo").trim());
                array26.add(json.getString("cod_valor").trim());
                array26.add(json.getString("nom_proceso").trim());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (array26.size() < 30){
            totalpages = 1;
        }else{
            if (array26.size() > 30 && array26.size() < 81){
                totalpages = 2;
            }else{
                if (array26.size() > 81 && array26.size() < 129) {
                    totalpages = 3;
                }else{
                    totalpages = 4;
                }
            }
        }


        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = this.getString(R.string.app_name) +
                " Document";

        printManager.print(jobName, new MyPrintDocumentAdapter(this),
                null);


    }

    public class MyPrintDocumentAdapterFotos extends PrintDocumentAdapter
    {
        Context context;

        public MyPrintDocumentAdapterFotos(Context context)
        {
            this.context = context;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback,
                             Bundle metadata) {

            myPdfDocument = new PrintedPdfDocument(context, newAttributes);

            pageHeight =
                    newAttributes.getMediaSize().getHeightMils()/1000 * 72;
            pageWidth =
                    newAttributes.getMediaSize().getWidthMils()/1000 * 72;

            if (cancellationSignal.isCanceled() ) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder(placa + "FOTOS.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }


        @Override
        public void onWrite(final PageRange[] pageRanges,
                            final ParcelFileDescriptor destination,
                            final CancellationSignal cancellationSignal,
                            final WriteResultCallback callback) {

            /*String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
            params.add(new BasicNameValuePair("sId", Id));


            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);

            ArrayList<String> array = new ArrayList<String>();
            ArrayList<String> arrayTxt = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo").trim());
                    arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                    arrayTxt.add("");
                    arrayTxt.add("");
                    arrayTxt.add("");
                    array.add(json.getString("cod_valor").trim());
                    array.add(json.getString("nom_proceso").trim());
                    array.add(json.getString("foto").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            int indice = 0;
            for (int i = 0; i < totalpages; i++) {
                if (pageInRange(pageRanges, i))
                {
                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                            pageHeight, i).create();

                    PdfDocument.Page page =
                            myPdfDocument.startPage(newPage);

                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        myPdfDocument.close();
                        myPdfDocument = null;
                        return;
                    }
                    try {
                        drawPageFotos(page, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.finishPage(page);
                }

                indice += 4;
            }

            try {
                myPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }

            callback.onWriteFinished(pageRanges);


        }

    }

    public class MyPrintDocumentAdapter extends PrintDocumentAdapter
    {
        Context context;

        public MyPrintDocumentAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback,
                             Bundle metadata) {

            myPdfDocument = new PrintedPdfDocument(context, newAttributes);

            pageHeight =
                    newAttributes.getMediaSize().getHeightMils()/1000 * 72;
            pageWidth =
                    newAttributes.getMediaSize().getWidthMils()/1000 * 72;

            if (cancellationSignal.isCanceled() ) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder(placa + ".pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }


        @Override
        public void onWrite(final PageRange[] pageRanges,
                            final ParcelFileDescriptor destination,
                            final CancellationSignal cancellationSignal,
                            final WriteResultCallback callback) {

            for (int i = 0; i < totalpages; i++) {
                if (pageInRange(pageRanges, i))
                {
                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                            pageHeight, i).create();

                    PdfDocument.Page page =
                            myPdfDocument.startPage(newPage);

                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        myPdfDocument.close();
                        myPdfDocument = null;
                        return;
                    }
                    try {
                        drawPage(page, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.finishPage(page);
                }
            }

            try {
                myPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }

            callback.onWriteFinished(pageRanges);


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



    private boolean pageInRange(PageRange[] pageRanges, int page)
    {
        for (int i = 0; i<pageRanges.length; i++)
        {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }



    private void drawPageFotosPrueba(PdfDocument.Page page,
                                     int pagenumber , ArrayList<String> array , ArrayList<String> arrayTxt , int indice) throws IOException {

        pagenumber++; // Make sure page numbers start at 1

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        if (pagenumber == 1) {

            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
            params1.add(new BasicNameValuePair("sId", Id));


            String resultServer1 = getHttpPost(url1, params1);
            System.out.println("---------------------------------resultserver----------------");
            ArrayList<String> array1 = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer1);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array1.add(json.getString("nit").trim());
                    array1.add(json.getString("nombres").trim());
                    array1.add(json.getString("direccion").trim());
                    array1.add(json.getString("telefono_1").trim());
                    array1.add(json.getString("celular").trim());
                    array1.add(json.getString("mail").trim());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            int titleBaseLine = 120;
            int leftMargin = 100;


            paint.setColor(Color.rgb(228, 228, 228));
            canvas.drawRect(0, 0, 700, 330, paint);

            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawRect(0, 90, 700, 92, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);

            canvas.drawText(
                    "NOMBRES  : " + array1.get(1) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "NIT  : " + array1.get(0),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;


            canvas.drawText(
                    "DIRECCION  : " + array1.get(2) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "TELEFONO  : " + array1.get(3) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;



            canvas.drawText(
                    "CELULAR  : " + array1.get(4),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "MAIL  : " + array1.get(5),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 85;

            try {

                String nombre = Id + "-" + placa + "-" + "1";
                String fotoplaca = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COLIBRI/" + nombre + ".jpg";
                ;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fotoplaca, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 180, 180, false), 370, 120, paint);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Por favor tome la foto de la placa !", Toast.LENGTH_SHORT).show();
            }

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logogeneral);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bm, 80, 80, false),0, 0, paint);



            paint.setColor(Color.BLACK);

            canvas.drawText(
                    "TRIZIO",
                    90,
                    20,
                    paint);

            canvas.drawText(
                    "Cll 29 # 41-105 Edificio SOHO",
                    90,
                    50,
                    paint);

            canvas.drawText(
                    "310 3703366",
                    90,
                    80,
                    paint);


            for (int i = 0; i < array.size(); i = i + 4) {


                if (i!=0) {

                    if (i <= 3) {
                        if (array.get(i - 1).equals(array.get(i + 2))) {

                            paint.setColor(Color.BLACK);


                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;

                        } else {

                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);


                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine, paint);

                            titleBaseLine += 350;
                        }
                    }
                }else{
                    paint.setColor(Color.rgb(255, 0, 0));

                    canvas.drawText(
                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                            20,
                            titleBaseLine,
                            paint);
                    titleBaseLine += 35;

                    paint.setColor(Color.BLACK);

                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine, paint);

                    titleBaseLine += 350;
                }

            }


        }else {

            int titleBaseLine = 60;
            int leftMargin = 100;

            paint.setTextSize(20);


            if (array.get(indice - 1).equals(array.get(indice + 2))) {

                paint.setColor(Color.BLACK);

                byte[] decodedString = Base64.decode(arrayTxt.get(indice).trim(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                titleBaseLine += 150;

            } else {

                paint.setColor(Color.rgb(255, 0, 0));

                canvas.drawText(
                        array.get(indice + 2) + " " +  array.get(indice) + " " +  array.get(indice + 1),
                        20,
                        titleBaseLine,
                        paint);
                titleBaseLine += 35;

                paint.setColor(Color.BLACK);

                byte[] decodedString = Base64.decode(arrayTxt.get(indice).trim(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                titleBaseLine += 350;
            }


            paint.setTextSize(20);


            if (array.get(indice + 4 - 1).equals(array.get(indice + 4 + 2))) {

                paint.setColor(Color.BLACK);

                byte[] decodedString = Base64.decode(arrayTxt.get(indice + 4).trim(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                titleBaseLine += 150;

            } else {

                paint.setColor(Color.rgb(255, 0, 0));

                canvas.drawText(
                        array.get(indice + 4 + 2) + " " +  array.get(indice + 4) + " " +  array.get(indice + 4 + 1),
                        20,
                        titleBaseLine,
                        paint);
                titleBaseLine += 35;

                paint.setColor(Color.BLACK);

                byte[] decodedString = Base64.decode(arrayTxt.get(indice + 4).trim(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                titleBaseLine += 350;
            }



        }



    }

    private void drawPageFotos(PdfDocument.Page page,
                               int pagenumber) throws IOException {

        pagenumber++; // Make sure page numbers start at 1

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        if (pagenumber == 1) {

            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
            params.add(new BasicNameValuePair("sId", Id));


            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);

            ArrayList<String> array = new ArrayList<String>();
            ArrayList<String> arrayTxt = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo").trim());
                    arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                    arrayTxt.add("");
                    arrayTxt.add("");
                    arrayTxt.add("");
                    array.add(json.getString("cod_valor").trim());
                    array.add(json.getString("nom_proceso").trim());
                    array.add(json.getString("foto").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
            params1.add(new BasicNameValuePair("sId", Id));


            String resultServer1 = getHttpPost(url1, params1);
            System.out.println("---------------------------------resultserver----------------");
            ArrayList<String> array1 = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer1);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array1.add(json.getString("nit").trim());
                    array1.add(json.getString("nombres").trim());
                    array1.add(json.getString("direccion").trim());
                    array1.add(json.getString("telefono_1").trim());
                    array1.add(json.getString("celular").trim());
                    array1.add(json.getString("mail").trim());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            int titleBaseLine = 120;
            int leftMargin = 100;


            paint.setColor(Color.rgb(228, 228, 228));
            canvas.drawRect(0, 0, 700, 330, paint);

            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawRect(0, 90, 700, 92, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);

            canvas.drawText(
                    "NOMBRES  : " + array1.get(1) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "NIT  : " + array1.get(0),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;


            canvas.drawText(
                    "DIRECCION  : " + array1.get(2) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "TELEFONO  : " + array1.get(3) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;



            canvas.drawText(
                    "CELULAR  : " + array1.get(4),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "MAIL  : " + array1.get(5),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 85;

            try {

                String nombre = Id + "-" + placa + "-" + "1";
                String fotoplaca = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COLIBRI/" + nombre + ".jpg";
                ;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fotoplaca, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 180, 180, false), 370, 120, paint);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Por favor tome la foto de la placa !", Toast.LENGTH_SHORT).show();
            }

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logogeneral);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bm, 80, 80, false),0, 0, paint);



            paint.setColor(Color.BLACK);

            canvas.drawText(
                    "TRIZIO",
                    90,
                    20,
                    paint);

            canvas.drawText(
                    "Cll 29 # 41-105 Edificio SOHO",
                    90,
                    50,
                    paint);

            canvas.drawText(
                    "310 3703366",
                    90,
                    80,
                    paint);





            for (int i = 0; i < array.size(); i = i + 4) {


                if (i!=0) {

                    if (i <= 3) {
                        if (array.get(i - 1).equals(array.get(i + 2))) {

                            paint.setColor(Color.BLACK);


                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;

                        } else {

                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);


                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine, paint);

                            titleBaseLine += 350;
                        }
                    }
                }else{
                    paint.setColor(Color.rgb(255, 0, 0));

                    canvas.drawText(
                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                            20,
                            titleBaseLine,
                            paint);
                    titleBaseLine += 35;

                    paint.setColor(Color.BLACK);

                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine, paint);

                    titleBaseLine += 350;
                }

            }


        }else {
            if (pagenumber ==2) {
                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
                params.add(new BasicNameValuePair("sId", Id));


                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);

                ArrayList<String> array = new ArrayList<String>();
                ArrayList<String> arrayTxt = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array.add(json.getString("cod_atributo").trim());
                        arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                        arrayTxt.add("");
                        arrayTxt.add("");
                        arrayTxt.add("");
                        array.add(json.getString("cod_valor").trim());
                        array.add(json.getString("nom_proceso").trim());
                        array.add(json.getString("foto").trim());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                params1.add(new BasicNameValuePair("sId", Id));


                String resultServer1 = getHttpPost(url1, params1);
                System.out.println("---------------------------------resultserver----------------");
                ArrayList<String> array1 = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer1);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array1.add(json.getString("nit").trim());
                        array1.add(json.getString("nombres").trim());
                        array1.add(json.getString("direccion").trim());
                        array1.add(json.getString("telefono_1").trim());
                        array1.add(json.getString("celular").trim());
                        array1.add(json.getString("mail").trim());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int titleBaseLine = 60;
                int leftMargin = 100;

                paint.setTextSize(20);


                for (int i = 4; i < array.size(); i = i + 4) {

                    if (i!=0) {

                        if (i <= 8) {
                            if (array.get(i - 1).equals(array.get(i + 2))) {

                                paint.setColor(Color.BLACK);

                                byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                titleBaseLine += 150;

                            } else {

                                paint.setColor(Color.rgb(255, 0, 0));

                                canvas.drawText(
                                        array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                        20,
                                        titleBaseLine,
                                        paint);
                                titleBaseLine += 35;

                                paint.setColor(Color.BLACK);

                                byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                titleBaseLine += 350;
                            }
                        }
                    }else{
                        paint.setColor(Color.rgb(255, 0, 0));

                        canvas.drawText(
                                array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                20,
                                titleBaseLine,
                                paint);
                        titleBaseLine += 35;

                        paint.setColor(Color.BLACK);

                        byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                        canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                        titleBaseLine += 350;
                    }

                }
            }else{
                if (pagenumber ==3) {
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);

                    ArrayList<String> array = new ArrayList<String>();
                    ArrayList<String> arrayTxt = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                            arrayTxt.add("");
                            arrayTxt.add("");
                            arrayTxt.add("");
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                            array.add(json.getString("foto").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 12; i < array.size(); i = i + 4) {

                        if (i != 0) {

                            if (i <= 16) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;
                        }

                    }
                }else{
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);

                    ArrayList<String> array = new ArrayList<String>();
                    ArrayList<String> arrayTxt = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                            arrayTxt.add("");
                            arrayTxt.add("");
                            arrayTxt.add("");
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                            array.add(json.getString("foto").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 20; i < array.size(); i = i + 4) {

                        if (i != 0) {

                            if (i <= 24 ) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;
                        }

                    }
                }
            }

        }



    }

    private void drawPage(PdfDocument.Page page,
                          int pagenumber) throws IOException {

        pagenumber++; // Make sure page numbers start at 1

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        if (pagenumber == 1) {



            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sParametro", "pdf"));
            params.add(new BasicNameValuePair("sId", Id));


            String resultServer = getHttpPost(url, params);
            System.out.println("---------------------------------resultserver----------------");

            ArrayList<String> array = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo").trim());
                    array.add(json.getString("cod_valor").trim());
                    array.add(json.getString("nom_proceso").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
            params1.add(new BasicNameValuePair("sId", Id));


            String resultServer1 = getHttpPost(url1, params1);
            System.out.println("---------------------------------resultserver----------------");
            ArrayList<String> array1 = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer1);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array1.add(json.getString("nit").trim());
                    array1.add(json.getString("nombres").trim());
                    array1.add(json.getString("direccion").trim());
                    array1.add(json.getString("telefono_1").trim());
                    array1.add(json.getString("celular").trim());
                    array1.add(json.getString("mail").trim());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            int titleBaseLine = 150;
            int leftMargin = 100;


            paint.setColor(Color.rgb(228, 228, 228));
            canvas.drawRect(0, 0, 700, 300, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);

            canvas.drawText(
                    "NOMBRES  : " + array1.get(1) + "   " + "NIT  : " + array1.get(0),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;


            canvas.drawText(
                    "DIRECCION  : " + array1.get(2) + "   " + "TELEFONO  : " + array1.get(3),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "CELULAR  : " + array1.get(4),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "MAIL  : " + array1.get(5),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 85;

            try {

                String nombre = Id + "-" + placa + "-" + "1";
                String fotoplaca = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COLIBRI/" + nombre + ".jpg";
                ;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fotoplaca, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false), 440, 140, paint);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Por favor tome la foto de la placa !", Toast.LENGTH_SHORT).show();
            }

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logogeneral);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bm, 80, 80, false),0, 0, paint);

            paint.setColor(Color.BLACK);

            canvas.drawText(
                    "TRIZIO",
                    90,
                    20,
                    paint);

            canvas.drawText(
                    "Cll 29 # 41-105 Edificio SOHO",
                    90,
                    50,
                    paint);

            canvas.drawText(
                    "310 3703366",
                    90,
                    80,
                    paint);



            for (int i = 0; i < array.size(); i = i + 3) {

                if (i!=0) {

                    if (i < 30) {
                        if (array.get(i - 1).equals(array.get(i + 2))) {

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;

                        } else {

                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;
                        }
                    }
                }else{
                    paint.setColor(Color.rgb(255, 0, 0));

                    canvas.drawText(
                            array.get(i + 2),
                            20,
                            titleBaseLine,
                            paint);
                    titleBaseLine += 35;

                    paint.setColor(Color.BLACK);

                    canvas.drawText(
                            array.get(i) + " : " + array.get(i + 1),
                            leftMargin,
                            titleBaseLine,
                            paint);

                    titleBaseLine += 35;
                }

            }


        }else {
            if (pagenumber ==2) {
                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sParametro", "pdf"));
                params.add(new BasicNameValuePair("sId", Id));


                String resultServer = getHttpPost(url, params);
                System.out.println("---------------------------------resultserver----------------");

                ArrayList<String> array = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array.add(json.getString("cod_atributo").trim());
                        array.add(json.getString("cod_valor").trim());
                        array.add(json.getString("nom_proceso").trim());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                params1.add(new BasicNameValuePair("sId", Id));


                String resultServer1 = getHttpPost(url1, params1);
                System.out.println("---------------------------------resultserver----------------");
                ArrayList<String> array1 = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer1);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array1.add(json.getString("nit").trim());
                        array1.add(json.getString("nombres").trim());
                        array1.add(json.getString("direccion").trim());
                        array1.add(json.getString("telefono_1").trim());
                        array1.add(json.getString("celular").trim());
                        array1.add(json.getString("mail").trim());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int titleBaseLine = 60;
                int leftMargin = 100;

                paint.setTextSize(20);


                for (int i = 30; i < array.size(); i = i + 3) {

                    if (i!=0) {

                        if (i < 81) {
                            if (array.get(i - 1).equals(array.get(i + 2))) {

                                paint.setColor(Color.BLACK);

                                canvas.drawText(
                                        array.get(i) + " : " + array.get(i + 1),
                                        leftMargin,
                                        titleBaseLine,
                                        paint);

                                titleBaseLine += 35;

                            } else {

                                paint.setColor(Color.rgb(255, 0, 0));

                                canvas.drawText(
                                        array.get(i + 2),
                                        20,
                                        titleBaseLine,
                                        paint);
                                titleBaseLine += 35;

                                paint.setColor(Color.BLACK);

                                canvas.drawText(
                                        array.get(i) + " : " + array.get(i + 1),
                                        leftMargin,
                                        titleBaseLine,
                                        paint);

                                titleBaseLine += 35;
                            }
                        }
                    }else{
                        paint.setColor(Color.rgb(255, 0, 0));

                        canvas.drawText(
                                array.get(i + 2),
                                20,
                                titleBaseLine,
                                paint);
                        titleBaseLine += 35;

                        paint.setColor(Color.BLACK);

                        canvas.drawText(
                                array.get(i) + " : " + array.get(i + 1),
                                leftMargin,
                                titleBaseLine,
                                paint);

                        titleBaseLine += 35;
                    }

                }
            }else{
                if (pagenumber ==3) {
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdf"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println("---------------------------------resultserver----------------");

                    ArrayList<String> array = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 81; i < array.size(); i = i + 3) {

                        if (i != 0) {

                            if (i < 129) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;
                        }

                    }
                }else{
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdf"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println("---------------------------------resultserver----------------");

                    ArrayList<String> array = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 129; i < array.size(); i = i + 3) {

                        if (i != 0) {

                            if (i < 160) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;
                        }

                    }
                }
            }

        }

        if (totalpages-pagenumber == 0) {

            canvas.drawText(
                    "FIRMA : ",
                    200,
                    720,
                    paint);

            try {
                String fileName = Environment.getExternalStorageDirectory() + "/FIRMA.JPG";


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false), 300, 650, paint);

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Por favor dibuje la firma digital !", Toast.LENGTH_SHORT).show();
            }


        }

    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    class EnviarCorreoFotos extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            mProgressDialog = new ProgressDialog(ProcesosPanel.this);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Enviando Correo...");

            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... f_url) {

            String correo = "";
            String url9 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params9 = new ArrayList<NameValuePair>();
            params9.add(new BasicNameValuePair("sPlaca", placa.trim()));
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



            /*Mail m = new Mail("triziotool@gmail.com", "1Triziotool2");

            String[] toArr = {correo, correo};
            m.setTo(toArr);
            m.setFrom("triziotool@gmail.com");
            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
            m.setBody("Email body.");

            try {
                m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + "FOTOS.pdf");

                m.send();
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }*/



            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {

            mProgressDialog.dismiss();

            File fichero = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + "FOTOS.pdf");

            fichero.delete();

            Toast.makeText(ProcesosPanel.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();

        }
    }

    class EnviarCorreoInforme extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){
            mProgressDialog = new ProgressDialog(ProcesosPanel.this);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMessage("Enviando Correo...");

            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... f_url) {
            String correo = "";
            String url9 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params9 = new ArrayList<NameValuePair>();
            params9.add(new BasicNameValuePair("sPlaca", placa.trim()));
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



            /*Mail m = new Mail("triziotool@gmail.com", "1Triziotool2");

            String[] toArr = {correo, correo};
            m.setTo(toArr);
            m.setFrom("triziotool@gmail.com");
            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
            m.setBody("Email body.");

            try {
                m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + ".pdf");

                m.send();
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }*/

            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {

            mProgressDialog.dismiss();

            Toast.makeText(ProcesosPanel.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();

            File fichero = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + ".pdf");

            fichero.delete();

        }
    }

    public void informes(View v){

        //showDialog(ProcesosPanel.this);

        Intent i = new Intent(ProcesosPanel.this,Informes.class);

        i.putExtra("procesos",procesos);
        i.putExtra("nomProcesos",nomProceso);
        i.putExtra("empresa",empresa);
        i.putExtra("sucursal",sucursal);
        i.putExtra("user",user);
        i.putExtra("ip",ip);
        i.putExtra("id",Id);
        i.putExtra("placa",placa);
        startActivity(i);


    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
