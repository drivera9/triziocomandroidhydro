package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PanelLateral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String nombreTabla = "";
    String procesoFinal = "";
    ArrayList<String> columnas;
    ArrayList<String> ArrayDatos;
    ArrayList<String> colores;
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

    String logo = "";
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
    String ipconsulta = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_lateral);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Bundle bundle = this.getIntent().getExtras();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        layout = (LinearLayout) findViewById(R.id.LayoutRelativeProcesos);
        Progress = findViewById(R.id.progressbar);
        empresa = bundle.getString("empresa");
        ipconsulta = bundle.getString("ipconsulta");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        sucursal = bundle.getString("sucursal");
        procesos = bundle.getStringArrayList("procesos");
        nomProceso = bundle.getStringArrayList("nomProcesos");
        autoNextProcesos = bundle.getStringArrayList("autoNextProcesos");
        logo = bundle.getString("logo");
        System.out.println("sucursal ------------>" + sucursal);
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");

        titulo = "Sucursal " + sucursal;
        setTitle(titulo);

        for(int i = 0;i<procesos.size();i++){
            System.out.println("NOMBRE " + nomProceso.get(i).trim() + " PROCESO " + procesos.get(i).trim());
        }

        ArrayList<Lista_entrada_panel> datos = new ArrayList<Lista_entrada_panel>();
        ArrayList<Lista_entrada_panel> datosProcesoCero = new ArrayList<Lista_entrada_panel>();
        ListView lista = (ListView) findViewById(R.id.listView2);

        if (conexion.equals("local")) {

            for (int i = 0;i<procesos.size();i++) {
                try {

                    if (procesos.get(i).trim().equals("1")) {
                        datos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i) , "", Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
            }
            lista.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, datos){
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

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);


                    if (elegido.get_textoEncima().trim().equals("ELIMINAR")) {

                        Intent intent = new Intent((Context) PanelLateral.this, (Class) MostrarPlaca.class);
                        Bundle bundle2 = new Bundle();

                        bundle2.putString("user", user);
                        bundle2.putString("pass", pass);
                        bundle2.putString("procesOriginal", "4");
                        bundle2.putString("conexion", conexion);
                        bundle2.putString("empresa", empresa);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("ip", ip);
                        bundle2.putString("titulo", titulo + "/Eliminar");
                        bundle2.putString("proceso", "4");
                        intent.putExtras(bundle2);
                        startActivity(intent);
                    }

                    if (elegido.get_proceso() == 1) {
                        Intent intent = new Intent((Context) PanelLateral.this, (Class) RecepcionPanel.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("nuevoRegistro", "NO");
                        bundle2.putString("esAutonext", "false");
                        bundle2.putString("nombreTabla", nombreTabla);
                        bundle2.putString("codAtributo", "");
                        bundle2.putString("user", user);
                        bundle2.putString("pass", pass);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("empresa", empresa);
                        bundle2.putString("conexion", conexion);
                        bundle2.putString("sucursal", sucursal);
                        bundle2.putString("ip", ip);
                        bundle2.putString("titulo", titulo + "/Rec");
                        bundle2.putString("proceso", "1");
                        intent.putExtras(bundle2);
                        startActivity(intent);

                    }
                }
            });
        }else{

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            final Menu menu = navigationView.getMenu();

            for (int i = 0;i<procesos.size();i++){
                if (procesos.get(i).trim().equals("0")) {
                    menu.add(nomProceso.get(i).trim());
                }
                if (procesos.get(i).trim().equals("1")) {
                    menu.add(nomProceso.get(i).trim());
                }
                if (procesos.get(i).trim().equals("54")) {
                    menu.add(nomProceso.get(i).trim());
                }
                if (procesos.get(i).trim().equals("100")) {
                    menu.add(nomProceso.get(i).trim());
                }
            }

            menu.add("AJUSTES");
            menu.add("MOVIMIENTOS");
            menu.add("CONSULTAS");

            navigationView.setNavigationItemSelectedListener(this);

            /*ImageView img = (ImageView) navigationView.findViewById(R.id.imageView);

            Context context = img.getContext();
            int id = context.getResources().getIdentifier(logo.trim(), "drawable", context.getPackageName());
            img.setImageResource(id);*/

            /*View headerView = navigationView.inflateHeaderView(R.layout.nav_header_panel_lateral);

            ImageView imLogo = (ImageView)  headerView.findViewById(R.id.imageView);

            imLogo.setBackgroundResource(R.drawable.mercallantas);

            Context context = imLogo.getContext();
            int id = context.getResources().getIdentifier(logo.trim(), "drawable", context.getPackageName());
            imLogo.setImageResource(id);*/




            ListView ajustesProcesos = (ListView) findViewById(R.id.listViewAjustesProcesos);
            ListView procesoCero = (ListView) findViewById(R.id.listViewProcesoCero);
            ListView asignarCitas = (ListView) findViewById(R.id.listViewAsignarCitas);
            ListView reportes = (ListView) findViewById(R.id.listViewReportes);
            ListView cotizacion = (ListView) findViewById(R.id.listViewCotizacion);
            ListView consultas = (ListView) findViewById(R.id.listViewConsultas);
            ListView scanner = (ListView) findViewById(R.id.listViewScanner);
            ListView scannerPlaca = (ListView) findViewById(R.id.listViewScannerPlaca);
            ListView cambiarTecnico = (ListView) findViewById(R.id.listViewCambiarTecnico);

            ArrayList<Lista_entrada_panel> arrayAjustesProcesos = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayAjustesAtributos = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayLlamada = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayCitas = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayAsignarCitas = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayReportes = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayCot = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayConsultas = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayScanner = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayScannerPlaca = new ArrayList<>();
            ArrayList<Lista_entrada_panel> arrayCambiarTecnico = new ArrayList<>();

            for (int i = 0;i<procesos.size();i++){
                try {

                    if (procesos.get(i).trim().equals("0")) {
                        proceso = "0";
                        datosProcesoCero.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                /*try {

                    if (procesos.get(i).trim().equals("1")) {
                        proceso = "1";
                        datos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }*/

                try {

                    if (procesos.get(i).trim().equals("200")) {
                        proceso = "200";
                        arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("201")) {
                        proceso = "201";
                        arrayAjustesAtributos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }


                try {

                    if (procesos.get(i).trim().equals("54")) {
                        proceso = "54";
                        arrayAsignarCitas.add(new Lista_entrada_panel(R.drawable.cita,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("100")) {
                        proceso = "100";
                        arrayReportes.add(new Lista_entrada_panel(R.drawable.report,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }

                try {

                    if (procesos.get(i).trim().equals("101")) {
                        proceso = "101";
                        arrayCot.add(new Lista_entrada_panel(R.drawable.money,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try {

                    if (procesos.get(i).trim().equals("102")) {
                        proceso = "102";
                        arrayConsultas.add(new Lista_entrada_panel(R.drawable.report,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
                try {

                    if (procesos.get(i).trim().equals("103")) {
                        proceso = "103";
                        arrayCambiarTecnico.add(new Lista_entrada_panel(R.drawable.report,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                        continue;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
                }
            }

            //arrayScanner.add(new Lista_entrada_panel(R.drawable.report,"SCANNER CODIGO BARRAS","",0));
            //arrayScannerPlaca.add(new Lista_entrada_panel(R.drawable.report,"SCANNER PLACA","",0));

            arrayAjustesProcesos = new ArrayList<>();
            arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.ajustes,  "AJUSTES" , "" ,0));

            consultas.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayConsultas){
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

            cambiarTecnico.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayCambiarTecnico){
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

            scanner.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayScanner){
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

            scannerPlaca.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayScannerPlaca){
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

            reportes.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayReportes){
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

            cotizacion.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayCot){
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

            asignarCitas.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayAsignarCitas){
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



            procesoCero.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, datosProcesoCero){
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

            ajustesProcesos.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, arrayAjustesProcesos){
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

            lista.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, datos){
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

            scanner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    /*Intent i = new Intent(PanelLateral.this,ScanBarcodeActivity.class);
                    i.putExtra("ip",ip);
                    startActivity(i);*/
                }
            });

            scannerPlaca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    /*Intent i = new Intent(PanelLateral.this,ScanLicensePlateActivity.class);
                    i.putExtra("ip",ip);
                    startActivity(i);*/
                }
            });

            cambiarTecnico.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Intent intent = new Intent((Context) PanelLateral.this, (Class) Cambiar.class);
                    Bundle bundle2 = new Bundle();

                    bundle2.putString("parametro", "TECNICO");
                    bundle2.putString("user", user);
                    bundle2.putString("pass", pass);
                    bundle2.putString("procesOriginal", "15");
                    bundle2.putString("conexion", conexion);
                    bundle2.putString("empresa", empresa);
                    bundle2.putString("sucursal", sucursal);
                    bundle2.putString("ip", ip);
                    bundle2.putString("titulo", titulo + "/Cambiar Promesa");
                    bundle2.putString("proceso","21" );
                    intent.putExtras(bundle2);
                    startActivity(intent);
                }
            });

            cotizacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) CotizacionMETRO.class);
                    bundle.putString("ip",ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            consultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ListaVistasConsultas.class);

                    bundle.putString("ip",ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            reportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ListaVistas.class);
                    bundle.putString("ip",ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            procesoCero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    procesoFinal = "0";
                    new ConsultarGeneral().execute("");
                }
            });

            asignarCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) Citas.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa",empresa );
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putStringArrayList("procesos", procesos);
                    bundle.putStringArrayList("nomProcesos",nomProceso);
                    bundle.putStringArrayList("autoNextProcesos",autoNextProcesos);
                    bundle.putString("ip",ip);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });

            ajustesProcesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);


                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) Ajustes.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa",empresa );
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putStringArrayList("procesos", procesos);
                    bundle.putStringArrayList("nomProcesos",nomProceso);
                    bundle.putStringArrayList("autoNextProcesos",autoNextProcesos);
                    bundle.putString("ip",ip);
                    intent.putExtras(bundle);
                    startActivity(intent);


                }
            });

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);


                    switch (elegido.get_textoEncima().trim()){
                        case "LLANTAS":
                            procesoFinal = "2";
                            break;

                        case "FRENOS":
                            procesoFinal = "3";
                            break;

                        case "RECEPCION":
                            procesoFinal = String.valueOf(elegido.get_proceso());
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

                        case "REV SEG":
                            procesoFinal = "99";
                            break;

                        default: procesoFinal = String.valueOf(elegido.get_proceso());

                    }

                    System.out.println("NUMERO DE PROCESO !!! - " + procesoFinal);



                    if (!cod_placa.trim().equals("ELIJA UNA PLACA")) {

                        if (elegido.get_proceso() == 1){
                            procesoFinal = String.valueOf(elegido.get_proceso());
                            new ConsultarGeneral().execute("");
                        }else {
                            Bundle bundle = new Bundle();
                            Intent intent = new Intent((Context) getApplicationContext(), (Class) Procesos.class);
                            bundle.putString("sucursal", sucursal);
                            bundle.putString("empresa", empresa);
                            bundle.putString("user", user);
                            bundle.putString("pass", pass);
                            bundle.putString("conexion", conexion);
                            bundle.putStringArrayList("procesos", procesos);
                            bundle.putString("placa",  arrayPlacas.get(posicion));
                            bundle.putString("id", Id);
                            bundle.putStringArrayList("nomProcesos", nomProceso);
                            bundle.putStringArrayList("autoNextProcesos", autoNextProcesos);
                            bundle.putString("ip", ip);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }else{
                        if (!(ids.size()>1)){
                            if (elegido.get_proceso() == 1){
                                new ConsultarGeneral().execute("");

                            }else {
                                Bundle bundle = new Bundle();
                                Intent intent = new Intent((Context) getApplicationContext(), (Class) Procesos.class);
                                bundle.putString("sucursal", sucursal);
                                bundle.putString("empresa", empresa);
                                bundle.putString("user", user);
                                bundle.putString("pass", pass);
                                bundle.putString("conexion", conexion);
                                bundle.putStringArrayList("procesos", procesos);
                                bundle.putString("placa",  arrayPlacas.get(posicion));
                                bundle.putString("id", Id);
                                bundle.putStringArrayList("nomProcesos", nomProceso);
                                bundle.putStringArrayList("autoNextProcesos", autoNextProcesos);
                                bundle.putString("ip", ip);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }else {
                            Toast.makeText(PanelLateral.this, "Por favor, elija una placa!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        }

        placas.add(new Lista_entrada_panel(R.drawable.bike, "MOVIMIENTOS" ,"", 0));

        ListView listaPlacas = (ListView) findViewById(R.id.listViewPlacas);
        listaPlacas.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada_panel, placas){
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

        listaPlacas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent((Context) getApplicationContext(), (Class) OT.class);
                bundle.putString("sucursal", sucursal);
                bundle.putString("empresa",empresa );
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                bundle.putString("conexion", conexion);
                bundle.putStringArrayList("procesos", procesos);
                bundle.putStringArrayList("nomProcesos",nomProceso);
                bundle.putStringArrayList("autoNextProcesos",autoNextProcesos);
                bundle.putString("ip",ip);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        /*
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinnerPlaca);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, placas);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        */
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


            Intent intent = new Intent((Context) PanelLateral.this, (Class) RecepcionPanel.class);
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String p = "";

        for (int i = 0;i<procesos.size();i++){
            if (item.getTitle().toString().equals(nomProceso.get(i).trim())){
                p = procesos.get(i).trim();
            }
        }

        switch (p){
            case "0" :
                procesoFinal = "0";
                new ConsultarGeneral().execute("");
                break;

            case "1" :
                procesoFinal = "1";
                new ConsultarGeneral().execute("");
                break;

            case "54" :
                Bundle bundle = new Bundle();
                Intent intent = new Intent((Context) getApplicationContext(), (Class) Citas.class);
                bundle.putString("sucursal", sucursal);
                bundle.putString("empresa",empresa );
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                bundle.putString("conexion", conexion);
                bundle.putStringArrayList("procesos", procesos);
                bundle.putStringArrayList("nomProcesos",nomProceso);
                bundle.putStringArrayList("autoNextProcesos",autoNextProcesos);
                bundle.putString("ip",ip);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case "100" :
                bundle = new Bundle();
                intent = new Intent((Context) getApplicationContext(), (Class) ListaVistas.class);
                bundle.putString("ip",ip);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }

        switch (item.getTitle().toString()){
            case "AJUSTES" :
                Bundle bundle = new Bundle();
                Intent intent = new Intent((Context) getApplicationContext(), (Class) Ajustes.class);
                bundle.putString("sucursal", sucursal);
                bundle.putString("empresa",empresa );
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                bundle.putString("conexion", conexion);
                bundle.putStringArrayList("procesos", procesos);
                bundle.putStringArrayList("nomProcesos",nomProceso);
                bundle.putStringArrayList("autoNextProcesos",autoNextProcesos);
                bundle.putString("ip",ip);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case "MOVIMIENTOS" :
                bundle = new Bundle();
                intent = new Intent((Context) getApplicationContext(), (Class) OT.class);
                bundle.putString("sucursal", sucursal);
                bundle.putString("empresa",empresa );
                bundle.putString("user", user);
                bundle.putString("pass", pass);
                bundle.putString("conexion", conexion);
                bundle.putStringArrayList("procesos", procesos);
                bundle.putStringArrayList("nomProcesos",nomProceso);
                bundle.putStringArrayList("autoNextProcesos",autoNextProcesos);
                bundle.putString("ip",ip);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case "CONSULTAS" :
                bundle = new Bundle();
                intent = new Intent((Context) getApplicationContext(), (Class) ListaVistasConsultas.class);

                bundle.putString("ip",ipconsulta);
                intent.putExtras(bundle);
                startActivity(intent);
                break;




        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

