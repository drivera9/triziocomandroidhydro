package trizio.ram.com.triziocomandroidgradlenuevo;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CrearProceso extends AppCompatActivity {
    String empresa = "";
    String ip = "";
    String user = "";
    String pass = "";
    String sucursal = "";
    String proceso = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_proceso);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Crear Proceso");

        empresa = getIntent().getExtras().getString("empresa");
        ip = getIntent().getExtras().getString("ip");
        sucursal = getIntent().getExtras().getString("sucursal");
        user = getIntent().getExtras().getString("user");
        pass = getIntent().getExtras().getString("pass");
        proceso = getIntent().getExtras().getString("proceso");
    }

    public void guardar(View v){
        EditText nombre = (EditText) findViewById(R.id.nombre);

        if (nombre.getText().toString().isEmpty()){
            Toast.makeText(this, "Necesita digitar un nombre del proceso", Toast.LENGTH_SHORT).show();
        }else{
            String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sNombre", nombre.getText().toString().trim()));
            params.add(new BasicNameValuePair("sEmpresa", empresa));
            params.add(new BasicNameValuePair("sParametro", "nuevoProceso"));

            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer );

            Toast.makeText(this, "Haz creado el proceso!", Toast.LENGTH_SHORT).show();

            String url3 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

            List<NameValuePair> params3 = new ArrayList<NameValuePair>();
            params3.add(new BasicNameValuePair("sNombre", nombre.getText().toString().trim()));
            params3.add(new BasicNameValuePair("sEmpresa", empresa));
            params3.add(new BasicNameValuePair("sSucursal", sucursal));
            params3.add(new BasicNameValuePair("sUser", user));
            params3.add(new BasicNameValuePair("sPass", pass));
            params3.add(new BasicNameValuePair("sParametro", "nuevoProcesoCaptura"));
            String resultServer3 = getHttpPost(url3, params3);
            System.out.println("---------------------------------resultserver----------------");
            System.out.println(resultServer3);



            finish();
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
