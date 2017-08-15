package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CrearFormula extends Activity {

    ArrayList<String> atributos ;
    String ip = "";
    String empresa = "";
    String sucursal = "";
    String proceso = "";
    String atr = "";
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_formula);

        atributos = getIntent().getExtras().getStringArrayList("atributos");
        ip = getIntent().getExtras().getString("ip");
        empresa = getIntent().getExtras().getString("empresa");
        sucursal = getIntent().getExtras().getString("sucursal");
        proceso = getIntent().getExtras().getString("proceso");
        atr = getIntent().getExtras().getString("atributo");

        mProgressDialog= new ProgressDialog(CrearFormula.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Guardando datos...");

        ArrayList<Lista_entrada_mov> datos = new ArrayList<>();

        for (int i = 0;i<atributos.size();i++){
            datos.add(new Lista_entrada_mov(R.drawable.close, atributos.get(i).trim() , "",0));
        }

        ListView lista = (ListView) findViewById(R.id.lista);

        lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada_mov) entrada).get_textoEncima());

                    TextView texto_superior_debajo = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_superior_debajo != null)
                        texto_superior_debajo.setText(((Lista_entrada_mov) entrada).get_textoDebajo());

                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada_mov) entrada).get_idImagen());
                }
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {

                Lista_entrada_mov elegido = (Lista_entrada_mov) pariente.getItemAtPosition(posicion);
                EditText formula = (EditText) findViewById(R.id.editFormula);
                formula.setText(formula.getText() + elegido.get_textoEncima());

            }
        });

    }

    public void parentesisizq(View v){
        EditText formula = (EditText) findViewById(R.id.editFormula);
        formula.setText(formula.getText() + "(");
    }
    public void parentesisder(View v){
        EditText formula = (EditText) findViewById(R.id.editFormula);
        formula.setText(formula.getText() + ")");
    }
    public void suma(View v){
        EditText formula = (EditText) findViewById(R.id.editFormula);
        formula.setText(formula.getText() + "+");
    }
    public void resta(View v){
        EditText formula = (EditText) findViewById(R.id.editFormula);
        formula.setText(formula.getText() + "-");
    }
    public void multiplicacion(View v){
        EditText formula = (EditText) findViewById(R.id.editFormula);
        formula.setText(formula.getText() + "*");
    }
    public void division(View v){
        EditText formula = (EditText) findViewById(R.id.editFormula);
        formula.setText(formula.getText() + "/");
    }
    public void borrar(View v){
        try {
            EditText formula = (EditText) findViewById(R.id.editFormula);
            String str = formula.getText().toString();
            formula.setText(str.substring(0, str.length() - 1));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void borrartodo(View v){
        try {
            EditText formula = (EditText) findViewById(R.id.editFormula);
            formula.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public boolean isWhite(String name) {
        boolean esta = false;
        for (char c : name.toCharArray()) {
            if (Character.isWhitespace(c)) {
                esta = true;
                break;
            }
        }
        return esta;
    }

    public void guardar(View v){

        new guardarDatos().execute("");

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

    class guardarDatos extends AsyncTask<String, String, String> {

        ArrayList<String> atrs;
        EditText formula;
        String f;

        @Override
        protected void onPreExecute(){
            formula = (EditText) findViewById(R.id.editFormula);
            mProgressDialog.show();
            f = formula.getText().toString().trim();
            atrs = new ArrayList<>();

        }
        @Override
        protected String doInBackground(String... f_url) {
            String s = "";
            for(int i = 0;i<f.length();i++){
                if (isAlpha(String.valueOf(f.charAt(i))) || isWhite(String.valueOf(f.charAt(i)))){
                    s += String.valueOf(f.charAt(i));
                }else{
                    atrs.add(s);
                    atrs.add(String.valueOf(f.charAt(i)));
                    if (i<f.length()) {
                        s = "";
                    }
                }
            }

            atrs.add(s);

            for (int j = 0;j<atrs.size();j++){
                if (atrs.get(j).trim().equals("")){
                    atrs.remove(j);
                }
            }

            String url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sCodAtributoPadre", atr));
            params.add(new BasicNameValuePair("sCodEmpresa", empresa));
            params.add(new BasicNameValuePair("sCodSucursal", sucursal));
            params.add(new BasicNameValuePair("sCodProceso", proceso));
            params.add(new BasicNameValuePair("sParametro", "borrarFormula"));

            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);
            for (int j = 0;j<atrs.size();j++){
                url = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                if (isAlpha(atrs.get(j)) || isWhite(atrs.get(j))) {

                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sCodAtributoPadre", atr));
                    params.add(new BasicNameValuePair("sCodAtributo", atrs.get(j)));
                    params.add(new BasicNameValuePair("sCodEmpresa", empresa));
                    params.add(new BasicNameValuePair("sCodSucursal", sucursal));
                    params.add(new BasicNameValuePair("sCodProceso", proceso));
                    params.add(new BasicNameValuePair("sSec", String.valueOf(j+1)));
                    params.add(new BasicNameValuePair("sTipoFormula", "A"));
                    params.add(new BasicNameValuePair("sParametro", "formula"));

                    resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);
                }else{
                    params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sCodAtributoPadre", atr));
                    params.add(new BasicNameValuePair("sCodAtributo", atrs.get(j)));
                    params.add(new BasicNameValuePair("sCodEmpresa", empresa));
                    params.add(new BasicNameValuePair("sCodSucursal", sucursal));
                    params.add(new BasicNameValuePair("sCodProceso", proceso));
                    params.add(new BasicNameValuePair("sSec", String.valueOf(j+1)));
                    params.add(new BasicNameValuePair("sTipoFormula", "O"));
                    params.add(new BasicNameValuePair("sParametro", "formula"));

                    resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);
                }


            }



            return null;

        }

        // Once Music File is downloaded
        @Override
        protected void onPostExecute(String file_url) {

            Toast.makeText(getApplicationContext(), "Se actualizo correctamente! ", Toast.LENGTH_SHORT).show();

            finish();

            mProgressDialog.dismiss();
        }
    }
}
