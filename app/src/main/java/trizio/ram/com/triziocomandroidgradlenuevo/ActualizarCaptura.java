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

public class ActualizarCaptura extends AppCompatActivity {

    ArrayList<String> atributos = new ArrayList<>();
    String atributo = "";
    String ip = "";
    String empresa = "";
    String sucursal = "";
    String proceso = "";
    String atributoInicial = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_captura);
        atributos = getIntent().getStringArrayListExtra("atributosArray");
        proceso = getIntent().getExtras().getString("proceso");
        empresa = getIntent().getExtras().getString("empresa");
        sucursal = getIntent().getExtras().getString("sucursal");
        ip = getIntent().getExtras().getString("ip");
        atributo = getIntent().getExtras().getString("atributo");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for(int i = 0;i<atributos.size();i++){
            if (atributos.get(i).trim().equals("null")) {

                atributos.set(i,"");
            }

        }


        EditText codAtributo = (EditText) findViewById(R.id.editCodAtributo);
        EditText codValor = (EditText) findViewById(R.id.editCodValor);
        EditText numSecuencia = (EditText) findViewById(R.id.editNumSecuencia);
        EditText indAutoNext = (EditText) findViewById(R.id.editIndAutoNext);
        EditText indEstado = (EditText) findViewById(R.id.editIndEstado);
        EditText nomTabla = (EditText) findViewById(R.id.editNomTabla);
        EditText nomColumna = (EditText) findViewById(R.id.editNomColumna);
        EditText nomAgregado = (EditText) findViewById(R.id.editNomAgregado);
        EditText indFormula = (EditText) findViewById(R.id.editIndFormula);

        codAtributo.setText(atributos.get(0).trim());
        codValor.setText(atributos.get(1).trim());
        numSecuencia.setText(atributos.get(2).trim());
        indAutoNext.setText(atributos.get(3).trim());
        indEstado.setText(atributos.get(4).trim());
        nomTabla.setText(atributos.get(5).trim());
        nomColumna.setText(atributos.get(6).trim());
        nomAgregado.setText(atributos.get(7).trim());
        indFormula.setText(atributos.get(8).trim());

        atributoInicial = codAtributo.getText().toString();
    }

    public void actualizar(View v){

        EditText codAtributo = (EditText) findViewById(R.id.editCodAtributo);
        EditText codValor = (EditText) findViewById(R.id.editCodValor);
        EditText numSecuencia = (EditText) findViewById(R.id.editNumSecuencia);
        EditText indAutoNext = (EditText) findViewById(R.id.editIndAutoNext);
        EditText indEstado = (EditText) findViewById(R.id.editIndEstado);
        EditText nomTabla = (EditText) findViewById(R.id.editNomTabla);
        EditText nomColumna = (EditText) findViewById(R.id.editNomColumna);
        EditText nomAgregado = (EditText) findViewById(R.id.editNomAgregado);
        EditText indFormula = (EditText) findViewById(R.id.editIndFormula);

        if (numSecuencia.getText().toString().trim().equals("")){
            numSecuencia.setText("0");
        }


        String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sCodAtributo", codAtributo.getText().toString().trim()));
        params.add(new BasicNameValuePair("sCodAtributoInicial", atributoInicial));
        params.add(new BasicNameValuePair("sCodValor", codValor.getText().toString().trim()));
        params.add(new BasicNameValuePair("sNumSecuencia", numSecuencia.getText().toString().trim()));
        params.add(new BasicNameValuePair("sIndAutonext", indAutoNext.getText().toString().trim()));
        params.add(new BasicNameValuePair("sIndEstado", indEstado.getText().toString().trim()));
        params.add(new BasicNameValuePair("sNomTabla", nomTabla.getText().toString().trim()));
        params.add(new BasicNameValuePair("sNomColumna", nomColumna.getText().toString().trim()));
        params.add(new BasicNameValuePair("sNomAgregado", nomAgregado.getText().toString().trim()));
        params.add(new BasicNameValuePair("sIndFormula", indFormula.getText().toString().trim()));
        params.add(new BasicNameValuePair("sProceso", proceso));
        params.add(new BasicNameValuePair("sParametro", "actualizarCaptura"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        Toast.makeText(getApplicationContext(), "Se actualizo correctamente! ", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void eliminar(View v){
        EditText codAtributo = (EditText) findViewById(R.id.editCodAtributo);

        String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sCodAtributo", codAtributo.getText().toString().trim()));
        params.add(new BasicNameValuePair("sProceso", proceso));
        params.add(new BasicNameValuePair("sParametro", "eliminarCaptura"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        Toast.makeText(getApplicationContext(), "Se elimino correctamente! ", Toast.LENGTH_SHORT).show();

        finish();
    }

    public void cancelar (View v){
        finish();
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
