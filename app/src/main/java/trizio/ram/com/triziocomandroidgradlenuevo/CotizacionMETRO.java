package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CotizacionMETRO extends Activity {

    String Codigo = "";
    TableLayout tabla;
    TableLayout cabecera;
    TableLayout tablaTotales;
    TableRow.LayoutParams layoutFila;
    TableRow.LayoutParams layoutId;
    TableRow.LayoutParams layoutTexto;
    TableRow.LayoutParams layoutApellido;

    TableRow.LayoutParams layoutInd_estado;
    TableRow.LayoutParams layoutNum_item;
    TableRow.LayoutParams layoutCod_ubicacion;
    TableRow.LayoutParams layoutCan_pedida;
    TableRow.LayoutParams layoutCan_despachada;
    TableRow.LayoutParams layoutCod_codigo;
    TableRow.LayoutParams layoutNom_codigo;
    TableRow.LayoutParams layoutCan_existencia;
    TableRow.LayoutParams layoutCuenta;

    ArrayList<String> mercado;
    ArrayList<String> detalle;
    ArrayList<String> totales;
    CheckBox check;
    int selector = 0;
    long posicion = 0;

    ArrayList<String> arrayDatos = new ArrayList<String>();

    int numero = 4;
    int cuenta = 0;

    EditText nit;
    EditText placa;
    EditText nombre;
    EditText rombo;
    EditText fecha;
    EditText ot;
    EditText edit_marca;
    EditText edit_referencia;
    EditText edit_modelo;
    String COT;
    String ip = "";
    String url = "";
    String Rombo;
    TextView marca;
    TextView modelo;
    TextView civic;
    TextView accord;
    ProgressDialog mProgressDialog;
    EditText cot;
    TextView listaT;
    String item_seleccionado = "";
    ArrayList<String> array_uni;

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cotizacion_metro);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        layout = (LinearLayout) findViewById(R.id.layoutdetalle);

        mProgressDialog = new ProgressDialog(CotizacionMETRO.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Consultando datos...");

        ip = getIntent().getExtras().getString("ip");
        url = "http://" + ip + "/consultarGeneralCotHydro.php";

        //ImageView asesor = (ImageView) findViewById(R.id.imageView_asesor);

        //asesor.setBackgroundResource(R.drawable.asesor);


        FindViewIds();

        ArrayList<Lista_entrada_mov> datos = new ArrayList<Lista_entrada_mov>();
        ListView listaMarcas = (ListView) findViewById(R.id.listaMarcas);



        COT = "";

        EditText nit = (EditText) findViewById(R.id.editText_nit);
        EditText placa = (EditText) findViewById(R.id.editText_placa);
        EditText nombre = (EditText) findViewById(R.id.editText_nombre);
        EditText numero = (EditText) findViewById(R.id.editText_numero);
        final EditText rombo = (EditText) findViewById(R.id.editText_rombo);

        array_uni = new ArrayList<>();
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        array_uni.add(String.valueOf(formatoNumero.format(Integer.parseInt("10000"))));
        array_uni.add(String.valueOf(formatoNumero.format(Integer.parseInt("20000"))));



        ArrayList<String> array_lista = new ArrayList<>();

        array_lista.add("11120");

        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sParametro", "codigos"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);
        ArrayList<String> array = new ArrayList<String>();
        try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add(json.getString("codigo"));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        String[] codigos = new String[array.size()];

        for (int i = 0;i<array.size();i++){
            codigos[i] = array.get(i).trim();
        }


        AutoCompleteTextView editCodigo = (AutoCompleteTextView) findViewById(R.id.editCodigo);
        ArrayAdapter<String> adapterCodigo = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,codigos);
        editCodigo.setAdapter(adapterCodigo);

        final Spinner añadir = (Spinner) findViewById(R.id.spinner_añadir);
        ArrayAdapter<String> adapter_añadir = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array_lista);
        adapter_añadir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        añadir.setAdapter(adapter_añadir);
        añadir.setGravity(Gravity.CENTER);

        añadir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                item_seleccionado = añadir.getSelectedItem().toString().trim();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });

        ArrayList<String> array_lista_producto = new ArrayList<>();

        array_lista_producto.add("PASTAS");

        final Spinner añadir_producto = (Spinner) findViewById(R.id.spinner_producto);
        ArrayAdapter<String> adapter_añadir_producto = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array_lista_producto);
        adapter_añadir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        añadir_producto.setAdapter(adapter_añadir_producto);
        añadir_producto.setGravity(Gravity.CENTER);

        añadir_producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                item_seleccionado = añadir_producto.getSelectedItem().toString().trim();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });

        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sParametro", "marcas"));

        resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        array = new ArrayList<String>();
        try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                datos.add(new Lista_entrada_mov(R.drawable.validacion,json.getString("marca").trim(),"",0));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }



        listaMarcas.setAdapter(new Lista_adaptador(this, R.layout.entrada_marcas, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada_mov) entrada).get_textoEncima());

                    TextView texto_debajo_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_debajo_entrada != null)
                        texto_debajo_entrada.setText(((Lista_entrada_mov) entrada).get_textoDebajo());


                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada_mov) entrada).get_idImagen());
                }
            }
        });

        listaMarcas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_mov elegido = (Lista_entrada_mov) pariente.getItemAtPosition(posicion);
                actualizarListaReferencias(elegido.get_textoEncima());

            }
        });

    }

    public void actualizarListaReferencias(final String marca){
        ArrayList<Lista_entrada_mov> datos = new ArrayList<>();
        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMarca", marca.trim()));
        params.add(new BasicNameValuePair("sParametro", "ref"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                datos.add(new Lista_entrada_mov(R.drawable.validacion,json.getString("referencia").trim(),"",0));
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        ListView listaRef = (ListView) findViewById(R.id.listaRef);

        listaRef.setAdapter(new Lista_adaptador(this, R.layout.entrada_marcas, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada_mov) entrada).get_textoEncima());

                    TextView texto_debajo_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_debajo_entrada != null)
                        texto_debajo_entrada.setText(((Lista_entrada_mov) entrada).get_textoDebajo());


                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada_mov) entrada).get_idImagen());
                }
            }
        });

        listaRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_mov elegido = (Lista_entrada_mov) pariente.getItemAtPosition(posicion);
                actualizarListaModelos(marca,elegido.get_textoEncima());

            }
        });
    }

    public void actualizarListaModelos(final String marca, final String referencia){
        ArrayList<Lista_entrada_mov> datos = new ArrayList<>();
        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sMarca", marca.trim()));
        params.add(new BasicNameValuePair("sRef", referencia.trim()));
        params.add(new BasicNameValuePair("sParametro", "modelo"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);
        final ArrayList<String> idPlantillas = new ArrayList<>();

        try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                datos.add(new Lista_entrada_mov(R.drawable.validacion,json.getString("modelo").trim(),"",0));
                idPlantillas.add(json.getString("id").trim());
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        ListView listaModelo = (ListView) findViewById(R.id.listaModelo);

        listaModelo.setAdapter(new Lista_adaptador(this, R.layout.entrada_marcas, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada_mov) entrada).get_textoEncima());

                    TextView texto_debajo_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_debajo_entrada != null)
                        texto_debajo_entrada.setText(((Lista_entrada_mov) entrada).get_textoDebajo());


                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada_mov) entrada).get_idImagen());
                }
            }
        });

        listaModelo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_mov elegido = (Lista_entrada_mov) pariente.getItemAtPosition(posicion);
                actualizarListaPlantillas(marca,referencia,elegido.get_textoEncima(), idPlantillas);

            }
        });
    }

    public void actualizarListaPlantillas(String marca, String referencia , String modelo, ArrayList<String> idPlantillas){

        EditText editMarca = (EditText) findViewById(R.id.editText_marca);
        EditText editRef = (EditText) findViewById(R.id.editText_referencia);
        EditText editModelo = (EditText) findViewById(R.id.editText_modelo);

        editMarca.setText(marca.trim());
        editRef.setText(referencia.trim());
        editModelo.setText(modelo.trim());

        String[] plantillas = new String[idPlantillas.size()];

        for(int i = 0;i<idPlantillas.size();i++){
            plantillas[i] = idPlantillas.get(i);
        }

        final Spinner lista = (Spinner) findViewById(R.id.spinner_lista);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item, plantillas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lista.setAdapter(adapter);
        lista.setGravity(Gravity.CENTER);

        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                url = "http://" + ip + "/consultarGeneralCotHydro.php";
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sId", lista.getItemAtPosition(lista.getSelectedItemPosition()).toString()));
                params.add(new BasicNameValuePair("sParametro", "plantillasItems"));

                ArrayList<String> productos = new ArrayList<>();
                ArrayList<String> precios = new ArrayList<>();
                ArrayList<String> grupos = new ArrayList<>();

                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);
                ArrayList<String> array = new ArrayList<>();
                try {
                    arrayDatos = new ArrayList<>();
                    JSONArray jArray = new JSONArray(resultServer);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        productos.add(json.getString("producto_id").trim());
                        precios.add(json.getString("precio_id").trim());
                        grupos.add(json.getString("grupo_id").trim());
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

                String sql = "";

                for(int i = 0;i<productos.size();i++){
                    sql+= "referencias.id = '" + productos.get(0) + "' OR ";
                }

                try {
                    sql = sql.substring(0, sql.length() - 3);
                }catch (Exception e){
                    e.printStackTrace();
                }

                url = "http://" + ip + "/consultarGeneralCotHydro.php";
                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sSql", sql));
                params.add(new BasicNameValuePair("sParametro", "plantillasItemsProductos"));

                resultServer = getHttpPost(url, params);
                System.out.println(resultServer);
                array = new ArrayList<>();

                detalle = new ArrayList<>();
                try {
                    arrayDatos = new ArrayList<>();
                    JSONArray jArray = new JSONArray(resultServer);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        detalle.add(json.getString("grupo"));
                        detalle.add(json.getString("codigo"));
                        try {
                            detalle.add(json.getString("descripcion").trim().substring(0,30) + " ...");
                        }catch (Exception e){
                            detalle.add(json.getString("descripcion").trim());
                            e.printStackTrace();
                        }
                        detalle.add("R");
                        detalle.add("1");
                        detalle.add("2");
                        detalle.add(json.getString("valor"));
                        detalle.add(String.valueOf(Integer.parseInt(json.getString("valor")) * Integer.parseInt("2")));
                        detalle.add("check");
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

                agregarFilasTabla(detalle);

                mProgressDialog.dismiss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //
            }
        });


    }

    public void añadir(View v) {

        mProgressDialog.show();

        AutoCompleteTextView codigo = (AutoCompleteTextView) findViewById(R.id.editCodigo);


        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sCodigo", codigo.getText().toString()));
        params.add(new BasicNameValuePair("sParametro", "añadir"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                detalle.add(json.getString("grupo"));
                detalle.add(json.getString("codigo"));
                try {
                    detalle.add(json.getString("descripcion").trim().substring(0,30) + " ...");
                }catch (Exception e){
                    detalle.add(json.getString("descripcion").trim());
                    e.printStackTrace();
                }
                detalle.add("R");
                detalle.add("1");
                detalle.add("2");
                detalle.add(json.getString("valor"));
                detalle.add(String.valueOf(Integer.parseInt(json.getString("valor")) * Integer.parseInt("2")));
                detalle.add("check");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        mProgressDialog.dismiss();


        agregarFilasTabla(detalle);
    }

    public void buscarRombo(View v){
        Intent i = new Intent(this, MostrarRombUbicMETRO.class);
        Bundle bundle = new Bundle();
        bundle.putString("ip",ip);
        bundle.putString("parametro", "rombo");
        i.putExtras(bundle);
        startActivityForResult(i, 1);
    }

    public void buscarCot(View v){
        Intent i = new Intent(this, MostrarRombUbicMETRO.class);
        Bundle bundle = new Bundle();
        bundle.putString("ip",ip);
        bundle.putString("nit",nit.getText().toString());
        bundle.putString("parametro","cot");
        i.putExtras(bundle);
        startActivityForResult(i, 1);
    }


    class ConsultarCot extends AsyncTask<String , String, String> {

        String numero = cot.getText().toString().trim();

        @Override
        protected void onPreExecute(){
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... f_url) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sNumero",numero));
            params.add(new BasicNameValuePair("sParametro", "actualizarCot"));


            String resultServer  = getHttpPost(url,params);
            System.out.println(resultServer);

            try {
                arrayDatos = new ArrayList<>();
                detalle = new ArrayList<>();
                JSONArray jArray = new JSONArray(resultServer);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    detalle.add(json.getString("grupo"));
                    detalle.add(json.getString("codigo"));
                    try {
                        detalle.add(json.getString("descripcion").trim().substring(0,30) + " ...");
                    }catch (Exception e){
                        detalle.add(json.getString("descripcion").trim());
                        e.printStackTrace();
                    }
                    detalle.add("R");
                    detalle.add("1");
                    detalle.add(json.getString("cantidad"));
                    detalle.add(String.valueOf(Double.parseDouble(json.getString("valor_unitario"))));
                    detalle.add(String.valueOf(Double.parseDouble(json.getString("valor_unitario")) *
                            Double.parseDouble(json.getString("cantidad"))));
                    detalle.add("check");
                }
            } catch (Exception e){


                e.printStackTrace();

            }


            return null;


        }



        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            mProgressDialog.dismiss();
            FindViewIds();

            agregarFilasTabla(detalle);
            /*if (arrayDatos.size()>0) {
                nit.setText(arrayDatos.get(0));
                placa.setText(arrayDatos.get(1));
                nombre.setText(arrayDatos.get(2));
                COT = arrayDatos.get(3);
            }else{
                nit.setText("");
                placa.setText("");
                nombre.setText("");
                COT = "";¨
            }*/
        }
    }

    public void pedido(View v){

        EditText ot = (EditText) findViewById(R.id.edit_OT);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sNumero",ot.getText().toString().trim()));
        params.add(new BasicNameValuePair("sParametro", "verNumeroPedido"));


        String resultServer  = getHttpPost(url,params);
        System.out.println(resultServer);

        String num = "";
        try {
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                num = (json.getString("numero"));
            }
        } catch (Exception e){

            e.printStackTrace();

        }

        if (num.equals("") ){
            new AlertDialog.Builder(CotizacionMETRO.this)
                    .setTitle("HECER PEDIDO")
                    .setMessage("Esta seguro que desea convertir la cotizacion en pedido?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            new HacerPedido().execute("");
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else{
            Toast.makeText(getApplicationContext(), "Ya hay un pedido asociado a esta cotizacion!", Toast.LENGTH_SHORT).show();
        }



    }

    class HacerPedido extends AsyncTask<String , String, String> {

        String numero = cot.getText().toString().trim();

        @Override
        protected void onPreExecute(){
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... f_url) {


        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sNumero", numero));
        params.add(new BasicNameValuePair("sParametro", "convertirPedido"));


        String resultServer = getHttpPost(url, params);

        System.out.println(resultServer);


            return null;
        }



        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            mProgressDialog.dismiss();
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(CotizacionMETRO.this);
            dialogo1.setTitle("Importante");
            dialogo1.setMessage("Pedido Exitoso!");
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo1, int id) {
                    Intent i = new Intent(CotizacionMETRO.this, CotizacionMETRO.class);
                    i.putExtra("ip",ip);
                    startActivity(i);
                    finish();
                }
            });
            dialogo1.show();

        }
    }



    class ConsultarDatos extends AsyncTask<String , String, String> {

        String resultado;

        String numeroCot = "";

        @Override
        protected void onPreExecute(){
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... f_url) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sRombo", f_url[0]));
            params.add(new BasicNameValuePair("sParametro", "rombo"));


            String resultServer  = getHttpPost(url,params);
            System.out.println(resultServer );
            arrayDatos = new ArrayList<>();
            try {

                JSONArray jArray = new JSONArray(resultServer);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    arrayDatos.add(json.getString("nit"));
                    arrayDatos.add(json.getString("serie"));
                    arrayDatos.add(json.getString("nombres"));
                    arrayDatos.add(json.getString("numero"));
                    arrayDatos.add(json.getString("numeroot"));
                    arrayDatos.add(json.getString("fecha"));
                    System.out.println("--------------------------------------------------");
                    System.out.println(arrayDatos.get(0) + " - " + arrayDatos.get(1) + " - " + arrayDatos.get(2) + " - ");

                }
            } catch (Exception e){

                e.printStackTrace();

            }

            try {

                params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sOt", arrayDatos.get(4).trim()));
                params.add(new BasicNameValuePair("sParametro", "numeroCotizacion"));


                resultServer = getHttpPost(url, params);
                System.out.println(resultServer);

                try {
                    JSONArray jArray = new JSONArray(resultServer);
                    ArrayList<String> array = new ArrayList<String>();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        numeroCot = (json.getString("numero"));

                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }

                if (!numeroCot.equals("") || !numeroCot.equals("null")) {
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sNumeroCot", numeroCot));
                    params.add(new BasicNameValuePair("sParametro", "detalleCotizacion"));


                    resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);

                    detalle = new ArrayList<>();

                    try {
                        JSONArray jArray = new JSONArray(resultServer);
                        ArrayList<String> array = new ArrayList<String>();
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            detalle.add(json.getString("grupo").trim());
                            detalle.add(json.getString("codigo").trim());
                            try {
                                detalle.add(json.getString("descripcion").trim().substring(0,30) + " ...");
                            }catch (Exception e){
                                detalle.add(json.getString("descripcion").trim());
                                e.printStackTrace();
                            }
                            detalle.add("R");
                            detalle.add("1");
                            detalle.add(json.getString("cantidad").trim());
                            detalle.add(json.getString("valor_unitario").trim());
                            detalle.add(String.valueOf(Integer.parseInt(json.getString("valor_unitario").trim()) *
                                    Integer.parseInt(json.getString("cantidad").trim())));
                            detalle.add("check");
                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }



        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {
            mProgressDialog.dismiss();
            FindViewIds();
            if (arrayDatos.size()>0) {
                nit.setText(arrayDatos.get(0));
                placa.setText(arrayDatos.get(1));
                nombre.setText(arrayDatos.get(2));
                cot.setText(numeroCot);
                ot.setText(arrayDatos.get(4));
                String fechaAux = arrayDatos.get(5);
                fecha.setText(fechaAux.substring(0,10));
            }else{
                nit.setText("");
                placa.setText("");
                nombre.setText("");
                COT = "";
            }

            agregarFilasTabla(detalle);
        }
    }

    public void FindViewIds(){
        nit = (EditText) findViewById(R.id.editText_nit);
        placa = (EditText) findViewById(R.id.editText_placa);
        nombre = (EditText) findViewById(R.id.editText_nombre);
        rombo = (EditText) findViewById(R.id.editText_rombo);
        cot  = (EditText) findViewById(R.id.editText_numero);
        ot =  (EditText) findViewById(R.id.edit_OT);
        fecha  = (EditText) findViewById(R.id.edit_Fecha);
        edit_marca = (EditText) findViewById(R.id.editText_marca);
        edit_referencia = (EditText) findViewById(R.id.editText_referencia);
        edit_modelo = (EditText) findViewById(R.id.editText_modelo);
    }


    public void quitar(int numero,int indice){

        System.out.println(indice + " ");
        for (int i =indice;i<indice+1;i++){
            detalle.set(i,"");
            detalle.set(i+1,"");
            detalle.set(i+2,"");
            detalle.set(i+3,"");
            detalle.set(i+4,"");
            detalle.set(i+5,"");
            detalle.set(i+6,"");
            detalle.set(i+7,"");
            detalle.set(i+8,"");
        }


        agregarFilasTabla(detalle);
    }

    public void update(int seleccion,long pos){
        selector = seleccion;
        posicion = pos ;
        agregarFilasTabla(detalle);

    }


    public void civic(View v){
        modelo.setVisibility(View.VISIBLE);
    }

    public void honda(View v){
        civic.setVisibility(View.VISIBLE);
    }

    public void imagen(View v){
        //ImageView imagen = (ImageView) findViewById(R.id.imageView);
        //imagen.setBackgroundResource(R.drawable.civic);

        edit_marca.setText(marca.getText().toString());
        edit_referencia.setText(civic.getText().toString());
        edit_modelo.setText(modelo.getText().toString());

        detalle = new ArrayList();

        mProgressDialog.show();

        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sLista", "1"));
        params.add(new BasicNameValuePair("sParametro", "detalle"));

        String resultServer  = getHttpPost(url,params);
        System.out.println(resultServer );

        try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                detalle.add(json.getString("grupo"));
                detalle.add(json.getString("codigo"));
                try {
                    detalle.add(json.getString("descripcion").trim().substring(0,30) + " ...");
                }catch (Exception e){
                    detalle.add(json.getString("descripcion").trim());
                    e.printStackTrace();
                }
                detalle.add("R");
                detalle.add("1");
                detalle.add("2");
                detalle.add(json.getString("valor"));
                detalle.add(String.valueOf(Integer.parseInt(json.getString("valor"))* Integer.parseInt("2")));
                detalle.add("check");
            }

        } catch (Exception e){

            e.printStackTrace();

        }
        mProgressDialog.dismiss();



        agregarFilasTabla(detalle);





    }

    public void prueba(View v){

        EditText ot = (EditText) findViewById(R.id.edit_OT);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sNumero",ot.getText().toString().trim()));
        params.add(new BasicNameValuePair("sParametro", "verNumeroPedido"));


        String resultServer  = getHttpPost(url,params);
        System.out.println(resultServer);

        String num = "";
        try {
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                num = (json.getString("numero"));
            }
        } catch (Exception e){

            e.printStackTrace();

        }

        if (num.equals("")) {
            FindViewIds();
            String NIT = nit.getText().toString().trim();

            EditText editCot = (EditText) findViewById(R.id.editText_numero);
            final String[] numeroCot = {editCot.getText().toString()};

            EditText editOT = (EditText) findViewById(R.id.edit_OT);
            final String numeroOT = editOT.getText().toString();

            if (NIT.equals("")) {
                Toast.makeText(getApplicationContext(), "Hace falta el nit!", Toast.LENGTH_LONG).show();
            } else {
                new AlertDialog.Builder(CotizacionMETRO.this)
                        .setTitle("HACER COTIZACION")
                        .setMessage("Esta seguro que desea realizar la cotizacion?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (numeroCot[0].equals("")) {
                                    try {
                                        url = "http://" + ip + "/consultarGeneralCotHydro.php";
                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("sValor", String.valueOf(cuenta)));
                                        params.add(new BasicNameValuePair("sNit", nit.getText().toString()));
                                        Calendar c = Calendar.getInstance();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String strDate = sdf.format(c.getTime());
                                        params.add(new BasicNameValuePair("sFecha", strDate));
                                        params.add(new BasicNameValuePair("sNotas", "nota"));
                                        params.add(new BasicNameValuePair("sVendedor", nit.getText().toString()));
                                        params.add(new BasicNameValuePair("sBodega", "5"));
                                        params.add(new BasicNameValuePair("sNumero", numeroOT));
                                        params.add(new BasicNameValuePair("sCondicion", "cond"));
                                        params.add(new BasicNameValuePair("sSw", "2"));
                                        params.add(new BasicNameValuePair("sDiasValidez", "1"));
                                        params.add(new BasicNameValuePair("sNitDestino", nit.getText().toString()));
                                        params.add(new BasicNameValuePair("sUsuario", "DAVID"));
                                        params.add(new BasicNameValuePair("sPlaca", placa.getText().toString()));
                                        params.add(new BasicNameValuePair("sNumRombo", rombo.getText().toString()));
                                        params.add(new BasicNameValuePair("sNumeroOt", numeroOT));
                                        params.add(new BasicNameValuePair("sParametro", "documentos_ped"));


                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);

                    /*ArrayList<String> array = new ArrayList<String>();
                    try {
                        arrayDatos = new ArrayList<>();
                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("numero_ped"));
                        }
                    } catch (Exception e){

                        e.printStackTrace();

                    }*/

                                        params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("sOt", numeroOT));
                                        params.add(new BasicNameValuePair("sParametro", "numeroCotizacion"));


                                        resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);

                                        try {
                                            JSONArray jArray = new JSONArray(resultServer);
                                            ArrayList<String> array = new ArrayList<String>();
                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json = jArray.getJSONObject(i);
                                                numeroCot[0] = (json.getString("numero"));

                                            }
                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }

                                        for (int i = 0; i < detalle.size(); i = i + 9) {
                                            url = "http://" + ip + "/consultarGeneralCotHydro.php";
                                            params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("sValor", detalle.get(i + 6)));
                                            c = Calendar.getInstance();
                                            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            strDate = sdf.format(c.getTime());
                                            params.add(new BasicNameValuePair("sFecha", strDate));
                                            params.add(new BasicNameValuePair("sNotas", "HOLA"));
                                            params.add(new BasicNameValuePair("sBodega", "5"));
                                            params.add(new BasicNameValuePair("sNumeroPedido", numeroCot[0].trim()));
                                            params.add(new BasicNameValuePair("sCodigo", detalle.get(i + 1)));
                                            params.add(new BasicNameValuePair("sSw", "2"));
                                            params.add(new BasicNameValuePair("sSeq", "50"));
                                            params.add(new BasicNameValuePair("sCantidad", detalle.get(i + 5)));
                                            params.add(new BasicNameValuePair("sDcto", "1"));
                                            params.add(new BasicNameValuePair("sParametro", "prueba"));

                                            resultServer = getHttpPost(url, params);
                                            System.out.println(resultServer);
                                        }

                                        url = "http://" + ip + "/consultarGeneralCotHydro.php";
                                        params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("sOt", numeroOT));
                                        params.add(new BasicNameValuePair("sParametro", "numeroCotizacion"));

                                        resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);

                                        ArrayList<String> array = new ArrayList<String>();

                                        try {
                                            arrayDatos = new ArrayList<>();
                                            JSONArray jArray = new JSONArray(resultServer);

                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json = jArray.getJSONObject(i);
                                                array.add(json.getString("numero"));
                                            }

                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }


                                        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(CotizacionMETRO.this);
                                        dialogo1.setTitle("Importante");
                                        dialogo1.setMessage("Numero de la cotizacion :" + array.get(0).trim());
                                        dialogo1.setCancelable(false);
                                        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogo1, int id) {
                                                Intent i = new Intent(CotizacionMETRO.this, CotizacionMETRO.class);
                                                i.putExtra("ip", ip);
                                                startActivity(i);
                                                finish();
                                            }
                                        });
                                        dialogo1.show();

                                        mProgressDialog.dismiss();

                                        Toast.makeText(getApplicationContext(), "Se guardo correctamente! ", Toast.LENGTH_LONG).show();
                                        rombo.setText("");
                                        placa.setText("");
                                        COT = "";
                                        nit.setText("");
                                        nombre.setText("");

                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Ocurrio un problema guardando el encabezado!", Toast.LENGTH_LONG).show();
                                        mProgressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                } else {
                                    try {
                                        mProgressDialog.show();
                                        String cotizacion = cot.getText().toString().trim();

                                        for (int i = 0; i < detalle.size(); i = i + 9) {
                                            url = "http://" + ip + "/consultarGeneralCotHydro.php";
                                            List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                                            params2.add(new BasicNameValuePair("sParametro", "eliminarLineas"));
                                            params2.add(new BasicNameValuePair("sNumeroCot", cot.getText().toString()));

                                            String resultServer2 = getHttpPost(url, params2);
                                            System.out.println(resultServer2);
                                        }


                                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                                        params.add(new BasicNameValuePair("sOt", numeroOT));
                                        params.add(new BasicNameValuePair("sParametro", "numeroCotizacion"));


                                        String resultServer = getHttpPost(url, params);
                                        System.out.println(resultServer);

                                        try {
                                            JSONArray jArray = new JSONArray(resultServer);
                                            ArrayList<String> array = new ArrayList<String>();
                                            for (int i = 0; i < jArray.length(); i++) {
                                                JSONObject json = jArray.getJSONObject(i);
                                                numeroCot[0] = (json.getString("numero"));

                                            }
                                        } catch (Exception e) {

                                            e.printStackTrace();

                                        }

                                        for (int i = 0; i < detalle.size(); i = i + 9) {
                                            url = "http://" + ip + "/consultarGeneralCotHydro.php";
                                            params = new ArrayList<NameValuePair>();
                                            params.add(new BasicNameValuePair("sValor", detalle.get(i + 6)));
                                            Calendar c = Calendar.getInstance();
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String strDate = sdf.format(c.getTime());
                                            params.add(new BasicNameValuePair("sFecha", strDate));
                                            params.add(new BasicNameValuePair("sNotas", "HOLA"));
                                            params.add(new BasicNameValuePair("sBodega", "5"));
                                            params.add(new BasicNameValuePair("sNumeroPedido", numeroCot[0].trim()));
                                            params.add(new BasicNameValuePair("sCodigo", detalle.get(i + 1)));
                                            params.add(new BasicNameValuePair("sSw", "2"));
                                            params.add(new BasicNameValuePair("sSeq", "50"));
                                            params.add(new BasicNameValuePair("sCantidad", detalle.get(i + 5)));
                                            params.add(new BasicNameValuePair("sDcto", "1"));
                                            params.add(new BasicNameValuePair("sParametro", "prueba"));

                                            resultServer = getHttpPost(url, params);
                                            System.out.println(resultServer);
                                        }

                                        try {


                                            mProgressDialog.dismiss();

                                            Toast.makeText(getApplicationContext(), "Se guardo correctamente!", Toast.LENGTH_LONG).show();
                                            rombo.setText("");
                                            placa.setText("");
                                            COT = "";
                                            nit.setText("");
                                            nombre.setText("");

                                            Intent i = new Intent(CotizacionMETRO.this, CotizacionMETRO.class);
                                            i.putExtra("ip", ip);
                                            startActivity(i);
                                            finish();

                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "Ocurrio un problema guardando el encabezado!", Toast.LENGTH_LONG).show();
                                            mProgressDialog.dismiss();
                                            e.printStackTrace();
                                        }



        /*try {
            arrayDatos = new ArrayList<>();
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                array.add(json.getString("cod_empresa"));

            }

            (getApplicationContext(),array.get(0),Toast.LENGTH_LONG).show();

        } catch (Exception e){

            e.printStackTrace();

        }*/
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Ocurrio un problema guardando las lineas!", Toast.LENGTH_LONG).show();
                                        mProgressDialog.dismiss();
                                        e.printStackTrace();
                                    }
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            }
        }else{
            Toast.makeText(getApplicationContext(), "Ya hay un pedido asociado a esta cotizacion!", Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarFilasTabla(final ArrayList<String> detalleAux){

        ArrayList<String> array_lista = new ArrayList<>();


        array_lista.add("PASTAS DE HONDA CIVIC");
        array_lista.add("CAMPANA DE HONDA CIVIC");
        array_lista.add("DISCOS DE HONDA CIVIC");
        NumberFormat formatoNumero = NumberFormat.getNumberInstance();
        cuenta =0;

        tabla = (TableLayout)findViewById(R.id.tabla);
        cabecera = (TableLayout)findViewById(R.id.cabecera);
        tablaTotales = (TableLayout) findViewById(R.id.tablaTotales);

        tabla.removeAllViews();
        cabecera.removeAllViews();
        tablaTotales.removeAllViews();

        layoutFila = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                100);

        layoutInd_estado = new TableRow.LayoutParams(60,
                50);
        layoutNum_item = new TableRow.LayoutParams(70,
                50);
        layoutCod_ubicacion = new TableRow.LayoutParams(280,
                50);
        layoutCan_pedida = new TableRow.LayoutParams(50,
                50);
        layoutCan_despachada = new TableRow.LayoutParams(100 ,
                50);
        layoutCod_codigo = new TableRow.LayoutParams(60,
                50);
        layoutNom_codigo = new TableRow.LayoutParams(120,
                50);
        layoutCan_existencia = new TableRow.LayoutParams(120,
                50);
        layoutCuenta = new TableRow.LayoutParams(80,
                50);

        layoutInd_estado.setMargins(3,3,3,3);
        layoutNum_item.setMargins(3,3,3,3);
        layoutCod_ubicacion.setMargins(3,3,3,3);
        layoutCan_pedida.setMargins(3,3,3,3);
        layoutCan_despachada.setMargins(3,3,3,3);
        layoutCod_codigo.setMargins(3,3,3,3);
        layoutNom_codigo.setMargins(3,3,3,3);
        layoutCan_existencia.setMargins(3,3,3,3);
        layoutCuenta.setMargins(3,3,3,3);

        TableRow fila;
        TextView txtInd_estado;
        TextView txtNum_item;
        TextView txtCod_ubicacion;
        TextView txtCan_pedida;

        TextView txtCod_codigo;
        TextView txtNom_codigo;
        TextView txtCan_existencia;
        TextView txtCheck;
        Spinner spinner;


        mercado = new ArrayList();

        mercado.add("CONC");
        mercado.add("COD");
        mercado.add("DESCRIPCION");
        mercado.add("TIPO");
        mercado.add("L");
        mercado.add("CANT");
        mercado.add("V.UNI");
        mercado.add("V.TOT");
        mercado.add("CHECK");

        for(int i = 0;i<mercado.size();i= i+9){


            fila = new TableRow(this);
            fila.setLayoutParams(layoutFila);

            txtInd_estado = new TextView(this);
            txtNum_item = new TextView(this);
            txtCod_ubicacion = new TextView(this);
            txtCan_pedida = new TextView(this);
            listaT = new TextView(this);
            txtCod_codigo = new TextView(this);
            txtNom_codigo = new TextView(this);
            txtCan_existencia = new TextView(this);
            txtCheck = new TextView(this);
            spinner = new Spinner(this);

            txtInd_estado.setText(mercado.get(i));
            txtInd_estado.setGravity(Gravity.CENTER);
            txtInd_estado.setLayoutParams(layoutInd_estado);
            txtInd_estado.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtNum_item.setText(mercado.get(i + 1));
            txtNum_item.setGravity(Gravity.CENTER);
            txtNum_item.setLayoutParams(layoutNum_item);
            txtNum_item.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCod_ubicacion.setText(mercado.get(i + 2));
            txtCod_ubicacion.setGravity(Gravity.CENTER);
            txtCod_ubicacion.setLayoutParams(layoutCod_ubicacion);
            txtCod_ubicacion.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCan_pedida.setText(mercado.get(i + 3));
            txtCan_pedida.setGravity(Gravity.CENTER);
            txtCan_pedida.setLayoutParams(layoutCan_pedida);
            txtCan_pedida.setBackgroundResource(R.drawable.cell_shape_titulo);

            listaT.setText(mercado.get(i + 4));
            listaT.setGravity(Gravity.CENTER);
            listaT.setLayoutParams(layoutCan_despachada);
            listaT.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCod_codigo.setText(mercado.get(i + 5));
            txtCod_codigo.setGravity(Gravity.CENTER);
            txtCod_codigo.setLayoutParams(layoutCod_codigo);
            txtCod_codigo.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtNom_codigo.setText(mercado.get(i + 6));
            txtNom_codigo.setGravity(Gravity.CENTER);
            txtNom_codigo.setLayoutParams(layoutNom_codigo);
            txtNom_codigo.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCan_existencia.setText(mercado.get(i + 7));
            txtCan_existencia.setGravity(Gravity.CENTER);
            txtCan_existencia.setLayoutParams(layoutCan_existencia);
            txtCan_existencia.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCheck.setText(mercado.get(i + 8));
            txtCheck.setGravity(Gravity.CENTER);
            txtCheck.setLayoutParams(layoutCuenta);
            txtCheck.setBackgroundResource(R.drawable.cell_shape_titulo);




            fila.addView(txtInd_estado);
            fila.addView(txtNum_item);
            fila.addView(txtCod_ubicacion);
            fila.addView(txtCan_pedida);
            fila.addView(listaT);
            fila.addView(txtCod_codigo);
            fila.addView(txtNom_codigo);
            fila.addView(txtCan_existencia);
            fila.addView(txtCheck);
            cabecera.addView(fila);




        }

        TableRow tablaDetalle;
        TextView concepto;
        TextView cod;
        TextView desc;
        TextView tipo;
        Spinner lista = null;
        EditText cantidad;
        TextView vUni;

        TextView vTot;


        detalle = new ArrayList<>();

        for (int i =0;i<detalleAux.size();i++){
            if (detalleAux.get(i).equals("")){

            }else{
                detalle.add(detalleAux.get(i));
            }
        }

        for (int i =0;i<detalle.size();i++){
            String str = detalle.get(i);
            String result = "";
            for (int j=0;j<str.length();j++){
                if (str.substring(j,j+1).equals(".")){
                    result = str.substring(0,j);
                    detalle.set(i,result);
                }
            }
            System.out.println(detalleAux.get(i));
        }


        //numero = detalle.size();
        for(int i = 0;i<detalle.size();i= i+9){


            tablaDetalle = new TableRow(this);
            tablaDetalle.setLayoutParams(layoutFila);

            concepto = new TextView(this);
            cod = new TextView(this);
            desc = new TextView(this);
            tipo = new TextView(this);
            lista=  new Spinner(this);
            cantidad = new EditText(this);
            vUni = new TextView(this);
            vTot = new TextView(this);
            check = new CheckBox(this);


            concepto.setText(detalle.get(i));
            concepto.setGravity(Gravity.CENTER);
            concepto.setLayoutParams(layoutInd_estado);
            concepto.setBackgroundResource(R.drawable.cell_shape);

            cod.setText(detalle.get(i + 1));
            cod.setGravity(Gravity.CENTER);
            cod.setLayoutParams(layoutNum_item);
            cod.setBackgroundResource(R.drawable.cell_shape);





            /*ArrayAdapter<String> adapter_añadir = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, array_lista);
            adapter_añadir.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            desc.setAdapter(adapter_añadir);
            desc.setGravity(Gravity.CENTER);
            desc.setLayoutParams(layoutCod_ubicacion);*/

            desc.setClickable(true);
            final int finalI = i;
            desc.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle b = new Bundle();
                    b.putString("i", String.valueOf(finalI));
                    b.putString("concepto",detalle.get(finalI));
                    Intent i = new Intent(CotizacionMETRO.this,BuscarDesc.class);
                    i.putExtras(b);
                    startActivityForResult(i, 3);
                }
            });
            desc.setText(detalle.get(i + 2));
            desc.setGravity(Gravity.CENTER);
            desc.setLayoutParams(layoutCod_ubicacion);
            desc.setBackgroundResource(R.drawable.cell_shape);

            tipo.setText(detalle.get(i + 3));
            tipo.setGravity(Gravity.CENTER);
            tipo.setLayoutParams(layoutCan_pedida);
            tipo.setBackgroundResource(R.drawable.cell_shape);




            ArrayAdapter<String> adapter_uni = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, array_uni);
            lista.setAdapter(adapter_uni);
            lista.setGravity(Gravity.CENTER);
            lista.setLayoutParams(layoutCan_despachada);
            int numeroo = i+4;




            if ( posicion == numeroo) {
                lista.setSelection(selector);
            }else {
                lista.setSelection(0);
            }
            final String seleccionado = lista.getSelectedItem().toString();

            final Spinner finalLista = lista;
            final int finalI1 = i;
            lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    if (!finalLista.getSelectedItem().toString().equals(seleccionado)) {
                        update(Integer.parseInt(String.valueOf(finalLista.getSelectedItemId())), finalI1 + 4);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                    //
                }
            });

            cantidad.setText(detalle.get(i + 5));
            cantidad.setSingleLine(true);
            cantidad.setGravity(Gravity.CENTER);
            cantidad.setLayoutParams(layoutCod_codigo);
            cantidad.setBackgroundResource(R.drawable.cell_shape);

            final int finalI2 = i;
            final EditText finalCantidad = cantidad;
            cantidad.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // If the event is a key-down event on the "enter" button
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        // Perform action on key press
                        detalle.set(finalI2 + 5, finalCantidad.getText().toString());
                        agregarFilasTabla(detalle);
                        return true;
                    }
                    return false;
                }
            });


            vUni.setText(lista.getSelectedItem().toString());
            vUni.setGravity(Gravity.CENTER);
            vUni.setLayoutParams(layoutNom_codigo);
            vUni.setBackgroundResource(R.drawable.cell_shape);
            vUni.setTextSize(20);


            String c = lista.getSelectedItem().toString();
            double a = Double.parseDouble((detalle.get(i + 5).replace(".","")).replace(",",""));
            int b = Integer.parseInt((c.replace(".","")).replace(",",""));
            String d = formatoNumero.format(a * b);
            cuenta+=(a * b);
            vTot.setText(d);
            vTot.setGravity(Gravity.CENTER);
            vTot.setLayoutParams(layoutCan_existencia);
            vTot.setBackgroundResource(R.drawable.cell_shape);
            vTot.setTextSize(20);

            //check.setText(detalle.get(i + 8));
            check.setGravity(Gravity.CENTER);
            check.setLayoutParams(layoutCuenta);
            check.setBackgroundResource(R.drawable.cell_shape);
            check.setChecked(true);



            tablaDetalle.addView(concepto);
            tablaDetalle.addView(cod);
            tablaDetalle.addView(desc);
            tablaDetalle.addView(tipo);
            tablaDetalle.addView(lista);
            tablaDetalle.addView(cantidad);
            tablaDetalle.addView(vUni);
            tablaDetalle.addView(vTot);
            tablaDetalle.addView(check);
            tabla.addView(tablaDetalle);

            check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    quitar(numero--, finalI);
                }
            });

        }







        TableRow Totales;
        TextView txtInd_estadoT;
        TextView txtNum_itemT;
        TextView txtCod_ubicacionT;
        TextView txtCan_pedidaT;
        TextView txtCan_despachadaT;
        TextView txtCod_codigoT;
        TextView txtNom_codigoT;
        TextView txtCan_existenciaT;
        TextView txtCheckT;

        totales = new ArrayList();

        totales.add("-");
        totales.add("-");
        totales.add("-");
        totales.add("-");
        totales.add("-");
        totales.add("-");
        totales.add("-");
        totales.add("-");
        totales.add("-");



        for(int i = 0;i<totales.size();i= i+9){



            Totales = new TableRow(this);
            tablaTotales.setLayoutParams(layoutFila);

            txtInd_estadoT = new TextView(this);
            txtNum_itemT = new TextView(this);
            txtCod_ubicacionT = new TextView(this);
            txtCan_pedidaT = new TextView(this);
            txtCan_despachadaT = new TextView(this);
            txtCod_codigoT = new TextView(this);
            txtNom_codigoT = new TextView(this);
            txtCan_existenciaT = new TextView(this);
            txtCheckT = new TextView(this);

            txtInd_estadoT.setText(totales.get(i));
            txtInd_estadoT.setGravity(Gravity.CENTER);
            txtInd_estadoT.setLayoutParams(layoutInd_estado);
            txtInd_estadoT.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtNum_itemT.setText(totales.get(i + 1));
            txtNum_itemT.setGravity(Gravity.CENTER);
            txtNum_itemT.setLayoutParams(layoutNum_item);
            txtNum_itemT.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCod_ubicacionT.setText(totales.get(i + 2));
            txtCod_ubicacionT.setGravity(Gravity.CENTER);
            txtCod_ubicacionT.setLayoutParams(layoutCod_ubicacion);
            txtCod_ubicacionT.setBackgroundResource(R.drawable.cell_shape_titulo);



            txtCan_pedidaT.setText(totales.get(i + 3));
            txtCan_pedidaT.setGravity(Gravity.CENTER);
            txtCan_pedidaT.setLayoutParams(layoutCan_pedida);
            txtCan_pedidaT.setBackgroundResource(R.drawable.cell_shape_titulo);


            txtCan_despachadaT.setText(totales.get(i + 4));
            txtCan_despachadaT.setGravity(Gravity.CENTER);
            txtCan_despachadaT.setLayoutParams(layoutCan_despachada);
            txtCan_despachadaT.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCod_codigoT.setText(totales.get(i + 5));
            txtCod_codigoT.setGravity(Gravity.CENTER);
            txtCod_codigoT.setLayoutParams(layoutCod_codigo);
            txtCod_codigoT.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtNom_codigoT.setText(totales.get(i + 6));
            txtNom_codigoT.setGravity(Gravity.CENTER);
            txtNom_codigoT.setLayoutParams(layoutNom_codigo);
            txtNom_codigoT.setBackgroundResource(R.drawable.cell_shape_titulo);



            txtCan_existenciaT.setText(formatoNumero.format(cuenta));
            txtCan_existenciaT.setGravity(Gravity.CENTER);
            txtCan_existenciaT.setLayoutParams(layoutCan_existencia);
            txtCan_existenciaT.setBackgroundResource(R.drawable.cell_shape_titulo);

            txtCheckT.setText(totales.get(i + 8));
            txtCheckT.setGravity(Gravity.CENTER);
            txtCheckT.setLayoutParams(layoutCuenta);
            txtCheckT.setBackgroundResource(R.drawable.cell_shape_titulo);




            Totales.addView(txtInd_estadoT);
            Totales.addView(txtNum_itemT);
            Totales.addView(txtCod_ubicacionT);
            Totales.addView(txtCan_pedidaT);
            Totales.addView(txtCan_despachadaT);
            Totales.addView(txtCod_codigoT);
            Totales.addView(txtNom_codigoT);
            Totales.addView(txtCan_existenciaT);
            Totales.addView(txtCheckT);
            tablaTotales.addView(Totales);




        }


    }



    public String getHttpPost(String url, List<NameValuePair> params) {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                EditText rombo = (EditText) findViewById(R.id.editText_rombo);
                rombo.setText(result);

                new ConsultarDatos().execute(rombo.getText().toString());
            }else{
                if(resultCode == 2){
                    String result=data.getStringExtra("result_cot");
                    cot.setText(result);


                    url = "http://" + ip + "/consultarGeneralCotHydro.php";
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sNumero", result));
                    params.add(new BasicNameValuePair("sParametro", "encabezadoActualizarCot"));

                    String resultServer  = getHttpPost(url,params);
                    System.out.println(resultServer );

                    try {
                        arrayDatos = new ArrayList<>();
                        JSONArray jArray = new JSONArray(resultServer);
                        ArrayList<String> array = new ArrayList<String>();
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            nit.setText(json.getString("nit"));
                            placa.setText(json.getString("placa"));
                            rombo.setText(json.getString("rombo"));
                        }

                    } catch (Exception e){

                        e.printStackTrace();

                    }


                    new ConsultarCot().execute("");
                }else{

                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else{
            if (resultCode == 3){

                String result=data.getStringExtra("result_cot");
                String indice=data.getStringExtra("i");
                quitar(numero--, Integer.parseInt(indice));

                Toast.makeText(CotizacionMETRO.this, result, Toast.LENGTH_SHORT).show();

                mProgressDialog.show();
                url = "http://" + ip + "/consultarGeneralCotHydro.php";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sCodigo", result.trim()));
                params.add(new BasicNameValuePair("sParametro", "añadir"));

                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);

                try {
                    arrayDatos = new ArrayList<>();
                    JSONArray jArray = new JSONArray(resultServer);
                    ArrayList<String> array = new ArrayList<String>();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        detalle.add(json.getString("grupo"));
                        detalle.add(json.getString("codigo"));
                        try {
                            detalle.add(json.getString("descripcion").trim().substring(0,30) + " ...");
                        }catch (Exception e){
                            detalle.add(json.getString("descripcion").trim());
                            e.printStackTrace();
                        }
                        detalle.add("R");
                        detalle.add("1");
                        detalle.add("2");
                        detalle.add(json.getString("valor"));
                        detalle.add(String.valueOf(Integer.parseInt(json.getString("valor")) * Integer.parseInt("2")));
                        detalle.add("check");
                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

                mProgressDialog.dismiss();




                agregarFilasTabla(detalle);
            }
        }
    }

    public void cancelar(View v){

        new AlertDialog.Builder(CotizacionMETRO.this)
                .setTitle("SALIR")
                .setMessage("Esta seguro que desea salir?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

