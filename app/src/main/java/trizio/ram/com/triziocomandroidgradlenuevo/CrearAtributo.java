    package trizio.ram.com.triziocomandroidgradlenuevo;

    import android.animation.Animator;
    import android.animation.AnimatorListenerAdapter;
    import android.annotation.TargetApi;
    import android.app.AlertDialog;
    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Build;
    import android.os.Bundle;
    import android.os.StrictMode;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.LinearLayout;
    import android.widget.ListAdapter;
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
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

    public class CrearAtributo extends AppCompatActivity {
        boolean esta = false;
        String dirIP = "";
        String procesoFinal = "";
        String atributo = "";
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
        ProgressDialog mProgressDialog;
        String ip = "";
        String conexion = "";
        String placa = "";
        String Id = "";
        ArrayList<String> atributos = new ArrayList();
        ArrayList<String> valores = new ArrayList();
        MyCustomAdapter.ViewHolder holder = null;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_atributo);
            Bundle bundle = this.getIntent().getExtras();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            procesoFinal = getIntent().getExtras().getString("proceso");
            empresa = bundle.getString("empresa");
            user = bundle.getString("user");
            pass = bundle.getString("pass");
            sucursal = bundle.getString("sucursal");
            Id = getIntent().getExtras().getString("id");
            conexion = bundle.getString("conexion");
            ip = bundle.getString("ip");

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        generalAux[i][j+16] = json.getString("ind_confirmacion");
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

                    esta = true;

                }catch (JSONException e ){

                    esta = false;
                    e.printStackTrace();
                    Intent i = new Intent(CrearAtributo.this,CrearAtributo.class);
                    startActivity(i);
                }

                String url12 = "http://" + ip + "/consultarGeneralColibriHydro.php";
                List<NameValuePair> params12 = new ArrayList<NameValuePair>();


                params12.add(new BasicNameValuePair("sId",Id));
                params12.add(new BasicNameValuePair("sParametro", "atributos"));


                String resultServer12 = getHttpPost(url12, params12);
                System.out.println("---------------------------------");
                System.out.println(resultServer11);
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

                return null;

            }

            // Once Music File is downloaded
            @Override
            protected void onPostExecute(String file_url) {

                if (esta) {
                    Intent intent = new Intent((Context) CrearAtributo.this, (Class) Recepcion.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("nombreTabla", "");
                    bundle2.putString("codAtributo", "");
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
                    bundle2.putString("id", Id);
                    bundle2.putStringArrayList("valores", valores);
                    intent.putExtras(bundle2);
                    startActivity(intent);
                    mProgressDialog.dismiss();
                }else{
                    mProgressDialog.dismiss();
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
                Intent i = new Intent(CrearAtributo.this,CrearAtributoNombre.class);
                i.putExtra("proceso" , procesoFinal);
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

                            DataBaseHelper dataBaseHelper = new DataBaseHelper(CrearAtributo.this);
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

            procesos = new ArrayList<>();
            nomProceso = new ArrayList<>();
            ArrayList<String> formulas = new ArrayList<>();
            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sUser", user));
            params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params1.add(new BasicNameValuePair("sProceso", procesoFinal));
            params1.add(new BasicNameValuePair("sParametro", "procesosCaptura"));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1 + " COD PROCESO ");

            try {
                JSONArray jArray = new JSONArray(resultServer1);
                final ArrayList<Integer> array = new ArrayList<Integer>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    procesos.add((json.getString("cod_proceso")));
                    nomProceso.add((json.getString("cod_atributo")));
                    formulas.add(json.getString("ind_formula"));
                    System.out.println("ESTOS SON LOS PROCESOS ->" + procesos.get(i));
                    System.out.println("ESTOS SON LOS NOMBRES PROCESOS ->" + nomProceso.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }



            titulo = "Sucursal " + sucursal;
            setTitle(titulo);

            final ArrayList<Atributos> countryList = new ArrayList<>();
            for (int i = 0;i<procesos.size();i++){
                Atributos atr = new Atributos(procesos.get(i),nomProceso.get(i).trim(),formulas.get(i).trim());
                countryList.add(atr);
            }


            //create an ArrayAdaptar from the String Array
            MyCustomAdapter dataAdapter = new MyCustomAdapter(CrearAtributo.this,
                    R.layout.atributos_info, countryList);
            ListView listView = (ListView) findViewById(R.id.listView2);
            // Assign adapter to ListView
            listView.setAdapter((ListAdapter) dataAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Atributos country = countryList.get(position);
                    atributo = country.getName();

                    new ConsultarDetalles().execute("");
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


                }
                else {
                    holder = (MyCustomAdapter.ViewHolder) convertView.getTag();
                }

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

                        Intent i = new Intent(CrearAtributo.this,CrearFormula.class);
                        i.putStringArrayListExtra("atributos",nomProceso);
                        i.putExtra("ip",ip);
                        i.putExtra("empresa",empresa);
                        i.putExtra("sucursal",sucursal);
                        i.putExtra("proceso",procesoFinal);
                        i.putExtra("atributo",atr);
                        startActivity(i);
                    }
                });

                return convertView;

            }

        }

        public void añadir(View v){
            Intent i = new Intent(CrearAtributo.this,CrearAtributoNombre.class);
            i.putExtra("proceso" , procesoFinal);
            i.putExtra("empresa" , empresa);
            i.putExtra("sucursal" , sucursal);
            i.putExtra("ip" , ip);
            i.putExtra("user" , user);
            i.putExtra("pass" , pass);

            startActivity(i);
        }

        public void firmar(View v){
            Intent intent = new Intent(CrearAtributo.this, Firma2.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("ip", ip);
            bundle2.putString("placa", placa);
            bundle2.putString("id", Id);
            intent.putExtras(bundle2);
            startActivity(intent);
        }

        @Override
        public boolean onSupportNavigateUp() {
            onBackPressed();
            return false;
        }

        class ConsultarDetalles extends AsyncTask<String, String, String> {

            ArrayList<String> atributos = new ArrayList<>();
            @Override
            protected void onPreExecute() {


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
                params.add(new BasicNameValuePair("sAtributo", atributo));
                params.add(new BasicNameValuePair("sParametro", "consultarProcesoUnico"));

                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);
                try {
                    JSONArray jArray = new JSONArray(resultServer);
                    generalAux = new String[jArray.length()][21];

                    int contador = 0;
                    int j = 0;
                    longitud = jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        atributos.add(json.getString("cod_atributo"));
                        atributos.add(json.getString("cod_valor"));
                        atributos.add(json.getString("num_secuencia"));
                        atributos.add(json.getString("ind_autonext"));
                        atributos.add(json.getString("ind_estado"));
                        atributos.add(json.getString("nom_tabla"));
                        atributos.add(json.getString("nom_columna"));
                        atributos.add(json.getString("nom_agregado"));
                        atributos.add(json.getString("ind_formula"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return null;

            }

            // Once Music File is downloaded
            @Override
            protected void onPostExecute(String file_url) {

                Intent intent = new Intent((Context) CrearAtributo.this, (Class) ActualizarCaptura.class);
                intent.putExtra("proceso",procesoFinal);
                intent.putExtra("atributo",atributo);
                intent.putExtra("ip",ip);
                intent.putExtra("empresa",empresa);
                intent.putExtra("sucursal",sucursal);
                intent.putStringArrayListExtra("atributosArray",atributos);
                startActivity(intent);

            }
        }


    }
