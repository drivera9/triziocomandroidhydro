package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ActualizarUsuarios extends AppCompatActivity {
    String user = "";
    String pass = "";
    String proceso = "";
    String sucursal;
    String empresa;
    String ip = "";
    String conexion = "";
    String nomProceso = "";
    ProgressDialog mProgressDialog;
    ArrayList<String> datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProgressDialog = new ProgressDialog(ActualizarUsuarios.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Espere...");


        Bundle bundle = this.getIntent().getExtras();
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        sucursal = bundle.getString("sucursal");
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");
        proceso = bundle.getString("proceso");
        nomProceso = bundle.getString("nomProceso");

        new ConsultarDatos().execute("");
    }

    class ConsultarDatos extends AsyncTask<String, String, String> {

        EditText editEmpresa;
        EditText editSucursal;
        EditText editUsuario;
        EditText editNomUsuario;
        EditText editClave;
        EditText editTipo;
        EditText editProceso;
        EditText editEstado;
        EditText editImei;
        EditText editRequerido;
        EditText editIp;
        ArrayList<String> datos;


        protected void onPreExecute(){

            editEmpresa = (EditText) findViewById(R.id.editEmpresa);
            editSucursal = (EditText) findViewById(R.id.editSucursal);
            editUsuario = (EditText) findViewById(R.id.editUsuario);
            editNomUsuario = (EditText) findViewById(R.id.editNomUsuario);
            editClave = (EditText) findViewById(R.id.editClave);
            editTipo = (EditText) findViewById(R.id.editTipo);
            editProceso = (EditText) findViewById(R.id.editProceso);
            editEstado = (EditText) findViewById(R.id.editEstado);
            editImei = (EditText) findViewById(R.id.editImei);
            editRequerido = (EditText) findViewById(R.id.editRequerido);
            editIp = (EditText) findViewById(R.id.editIp);
            datos = new ArrayList<>();

            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... f_url) {
            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sUser", user));
            params1.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params1.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params1.add(new BasicNameValuePair("sProceso", proceso.trim()));
            params1.add(new BasicNameValuePair("sParametro", "actualizarCapturaUsuarios"));

            String resultServer1 = getHttpPost(url1, params1);
            System.out.println(resultServer1 + " COD PROCESO ");

            try {
                JSONArray jArray = new JSONArray(resultServer1);
                final ArrayList<Integer> array = new ArrayList<Integer>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    datos.add(json.getString("cod_empresa").trim());
                    datos.add(json.getString("cod_sucursal").trim());
                    datos.add(json.getString("cod_usuario").trim());
                    datos.add(json.getString("nom_usuario").trim());
                    datos.add(json.getString("cod_clave").trim());
                    datos.add(json.getString("tip_usuario").trim());
                    datos.add(json.getString("cod_proceso").trim());
                    datos.add(json.getString("ind_estado").trim());
                    datos.add(json.getString("imei").trim());
                    datos.add(json.getString("ind_requerido").trim());
                    datos.add(json.getString("num_ip").trim());
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }

            for(int i = 0;i<datos.size();i++){
                if (datos.get(i).trim().equals("null")){
                    datos.set(i,"");
                }
            }

            return null;
        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            editEmpresa.setText(datos.get(0).trim());
            editSucursal.setText(datos.get(1).trim());
            editUsuario.setText(datos.get(2).trim());
            editNomUsuario.setText(datos.get(3).trim());
            editClave.setText(datos.get(4).trim());
            editTipo.setText(datos.get(5).trim());
            editProceso.setText(datos.get(6).trim());
            editEstado.setText(datos.get(7).trim());
            editImei.setText(datos.get(8).trim());
            editRequerido.setText(datos.get(9).trim());
            editIp.setText(datos.get(10).trim());

            mProgressDialog.dismiss();
        }
    }

    public void actualizar(View v){
        EditText editEmpresa;
        EditText editSucursal;
        EditText editUsuario;
        EditText editNomUsuario;
        EditText editClave;
        EditText editTipo;
        EditText editProceso;
        EditText editEstado;
        EditText editImei;
        EditText editRequerido;
        EditText editIp;

        editEmpresa = (EditText) findViewById(R.id.editEmpresa);
        editSucursal = (EditText) findViewById(R.id.editSucursal);
        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editNomUsuario = (EditText) findViewById(R.id.editNomUsuario);
        editClave = (EditText) findViewById(R.id.editClave);
        editTipo = (EditText) findViewById(R.id.editTipo);
        editProceso = (EditText) findViewById(R.id.editProceso);
        editEstado = (EditText) findViewById(R.id.editEstado);
        editImei = (EditText) findViewById(R.id.editImei);
        editRequerido = (EditText) findViewById(R.id.editRequerido);
        editIp = (EditText) findViewById(R.id.editIp);

        String url1 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sUser", user.trim()));
        params1.add(new BasicNameValuePair("sEmpresa", empresa.trim()));
        params1.add(new BasicNameValuePair("sSucursal", sucursal.trim()));
        params1.add(new BasicNameValuePair("sProceso", proceso.trim()));
        params1.add(new BasicNameValuePair("sEditEmpresa",editEmpresa.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditSucursal",editSucursal.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditUsuario",editUsuario.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditNomUsuario",editNomUsuario.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditClave",editClave.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditTipo",editTipo.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditProceso",editProceso.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditEstado",editEstado.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditImei",editImei.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditRequerido",editRequerido.getText().toString().trim()));
        params1.add(new BasicNameValuePair("sEditIp",editIp.getText().toString().trim()));

        params1.add(new BasicNameValuePair("sParametro", "actualizarCapturaUsuarios"));

        String resultServer1 = getHttpPost(url1, params1);
        System.out.println(resultServer1);

        Toast.makeText(getApplicationContext(), "Se actualizo correctamente! ", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void cancelar(View v){
        finish();
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
