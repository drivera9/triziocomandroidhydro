package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class SeleccionarCitas extends AppCompatActivity {
    MyCustomAdapter dataAdapter = null;
    ArrayList<String> procesos;
    ArrayList<String> nomProcesos;
    ArrayList<Country> countryList = new ArrayList<Country>();
    int totalpages = 0;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    String where = "";
    String atributo = "";
    String procesoFinal = "";

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
    String fechaI = "";
    String fechaF = "";
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
    String Id = "";
    private static final int CAMERA_REQUEST = 1888;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ListView listView;

    ArrayList<String> columnas = new ArrayList<>();
    ArrayList<String> datos = new ArrayList<>();
    ArrayList<String> ArrayDatos = new ArrayList<>();
    ArrayList<String> colores = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_citas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getIntent().getExtras().getString("user");
        empresa = getIntent().getExtras().getString("empresa");
        sucursal = getIntent().getExtras().getString("sucursal");
        procesos = getIntent().getExtras().getStringArrayList("procesos");
        nomProcesos = getIntent().getExtras().getStringArrayList("nomProcesos");
        colores = getIntent().getExtras().getStringArrayList("colores");
        ip = getIntent().getExtras().getString("ip");
        fechaI = getIntent().getExtras().getString("fechaInicial");
        fechaF = getIntent().getExtras().getString("fechaFinal");
        Id = getIntent().getExtras().getString("id");
        placa = getIntent().getExtras().getString("placa");
        atributo = getIntent().getExtras().getString("atributo");

        mProgressDialog = new ProgressDialog(SeleccionarCitas.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Espere...");

        ArrayList<String> atributosD1 = new ArrayList<>();
        ArrayList<String> valoresD1 = new ArrayList<>();

        Bundle bundle = this.getIntent().getExtras();

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
        cod_placa = bundle.getString("placa");
        pass = bundle.getString("pass");
        conexion = bundle.getString("conexion");
        titulo = bundle.getString("titulo");
        proceso = bundle.getString("proceso");
        procesoOriginal = proceso ;
        if (proceso.equals("11")){
            num_rombo = bundle.getString("num_rombo");
        }
        nombreTabla = bundle.getString("nombreTabla");
        nuevoRegistro = bundle.getString("nuevoRegistro");
    }

    public void continuar(View v){


    }

    public void fechaInicial(View v){

        final TextView finicial = (TextView) findViewById(R.id.textInicial);

        final View dialogView = View.inflate(SeleccionarCitas.this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(SeleccionarCitas.this).create();

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

                finicial.setText(String.valueOf(datePicker.getYear()) + "-" + String.valueOf(datePicker.getMonth())
                        + "-" + String.valueOf(datePicker.getDayOfMonth()) + " " + String.valueOf(timePicker.getCurrentHour()) +
                        ":" + String.valueOf(timePicker.getCurrentMinute()));
                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void fechaFinal(View v){

        final TextView ffinal = (TextView) findViewById(R.id.textFinal);

        final View dialogView = View.inflate(SeleccionarCitas.this, R.layout.date_time_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(SeleccionarCitas.this).create();

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

                ffinal.setText(String.valueOf(datePicker.getYear()) + "-" + String.valueOf(datePicker.getMonth())
                        + "-" + String.valueOf(datePicker.getDayOfMonth()) + " " + String.valueOf(timePicker.getCurrentHour()) +
                        ":" + String.valueOf(timePicker.getCurrentMinute()));
                alertDialog.dismiss();
            }});
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    public void filtrar() throws ParseException {

        final TextView finicial = (TextView) findViewById(R.id.textInicial);
        final TextView ffinal = (TextView) findViewById(R.id.textFinal);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        Date fechaInicial = dateFormat.parse(fechaI);

        String day = (String) DateFormat.format("dd", fechaInicial);
        String month = (String) DateFormat.format("MM", fechaInicial);
        String year = (String) DateFormat.format("yyy", fechaInicial);

        Date fechaFinal = dateFormat.parse(fechaF);

        String dayF = (String) DateFormat.format("dd", fechaFinal);
        String monthF = (String) DateFormat.format("MM", fechaFinal);
        String yearF = (String) DateFormat.format("yyy", fechaFinal);

        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sUser", user));
        params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
        params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
        params1.add(new BasicNameValuePair("sDia", day));
        params1.add(new BasicNameValuePair("sMes",month));
        params1.add(new BasicNameValuePair("sAno",year));
        params1.add(new BasicNameValuePair("sDiaF", dayF));
        params1.add(new BasicNameValuePair("sMesF",monthF));
        params1.add(new BasicNameValuePair("sAnoF",yearF));
        params1.add(new BasicNameValuePair("sCodProceso", "2"));
        params1.add(new BasicNameValuePair("sParametro", "citasFiltrar"));

        String resultServer1 = getHttpPost(url1, params1);
        System.out.println(resultServer1 + " COD PROCESO ");

        procesos = new ArrayList<>();
        nomProcesos = new ArrayList<>();
        colores = new ArrayList<>();

        final ArrayList<String> array = new ArrayList<String>();
        try {
            JSONArray jArray = new JSONArray(resultServer1);

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                procesos.add(json.getString("id"));
                array.add(json.getString("ano"));
                array.add(json.getString("mes"));
                array.add(json.getString("dia"));
                array.add(json.getString("fec_proceso"));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        for (int i =0;i<array.size();i=i+4){
            nomProcesos.add(array.get(i) + "-" + array.get(i+1) + "-" + array.get(i+2) + " " + array.get(i+3).trim());
        }

        String url2 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params2 = new ArrayList<NameValuePair>();
        params2.add(new BasicNameValuePair("sUser", user));
        params2.add(new BasicNameValuePair("sCodEmpresa", empresa));
        params2.add(new BasicNameValuePair("sCodSucursal", sucursal));
        params2.add(new BasicNameValuePair("sCodProceso", "2"));
        params2.add(new BasicNameValuePair("sParametro", "citas"));

        String resultServer2 = getHttpPost(url2, params2);
        System.out.println(resultServer2 + " COD PROCESO ");

        colores = new ArrayList<>();
        ArrayList<String> col = new ArrayList<>();

        try {
            JSONArray jArray = new JSONArray(resultServer2);
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                col.add(json.getString("ano"));
                col.add(json.getString("mes"));
                col.add(json.getString("dia"));
                col.add(json.getString("fec_proceso"));
                col.add(json.getString("nombres"));
                col.add(json.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        for (int i =0;i<col.size();i=i+6){
            colores.add(col.get(i) + "-" + col.get(i+1) + "-" + col.get(i+2) + " " + col.get(i+3).trim());
            colores.add(col.get(i+4));
            colores.add(col.get(i+5));
        }

        displayListView();
    }

    private void displayListView() {

        Country country;
        boolean color = false;
        countryList = new ArrayList<>();

        for (int i = 0;i<procesos.size();i++){
            country = new Country(procesos.get(i),nomProcesos.get(i).trim(),false);
            countryList.add(country);
        }

        for(int i = 0;i<countryList.size();i++){
            for(int j = 0;j<colores.size();j=j+3){
                if (colores.get(j).trim().equals(countryList.get(i).getName()) && countryList.get(i).getCode().equals(colores.get(j+2))){
                    countryList.get(i).setSelected(true);
                    countryList.get(i).actName(colores.get(j+1));
                }
            }
        }

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(SeleccionarCitas.this,
                R.layout.country_info, countryList);
        listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter((ListAdapter) dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(),
                //  "Clicked on Row: " + country.getName(),
                //Toast.LENGTH_LONG).show();
                Id = country.getCode();

                procesoFinal = "57";

                columnas = new ArrayList<>();
                ArrayDatos = new ArrayList<>();

                columnas.add("FEC_INICIAL");
                ArrayDatos.add(country.getName());

                new ConsultarGeneral().execute("");

            }
        });

    }

    class ConsultarGeneral extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

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
            params.add(new BasicNameValuePair("sCodUser", user));
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


            Intent intent = new Intent((Context) SeleccionarCitas.this, (Class) Recepcion.class);
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

    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new MyCustomAdapter.ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);

                convertView.setTag(holder);

                MyCustomAdapter.ViewHolder finalHolder = holder;

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Country country = (Country) cb.getTag();

                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        country.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
            }



            Country country = countryList.get(position);
            holder.code.setText(country.getName());
            holder.name.setText("");
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            if (holder.name.isChecked()) {
                holder.code.setTextColor(Color.RED);
            }else{
                holder.code.setTextColor(Color.BLACK);
            }
            return convertView;

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
    public void onResume() {
        super.onResume();
        countryList = new ArrayList<>();

        try {
            filtrar();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

}
