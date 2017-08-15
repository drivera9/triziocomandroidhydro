package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
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
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BuscarDesc extends ActionBarActivity {


    AutoCompleteTextView txtSearch;
    AutoCompleteTextView txtSearch2;
    List<Objeto> mList;
    List<Objeto> mList2;
    PeopleAdapter adapter;
    PeopleAdapter adapter2;
    String url = "";
    String ip = "";
    String indice = "";
    ArrayList<String> conceptos;
    ArrayList<String> codigos;
    int selected;

    ProgressDialog mProgressDialog;

    TextView seleccionado ;
    TextView seleccionado2;
    String concepto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_desc);

        //String[] countries = {"ho la", "co mo", "estas ss", "hola1hola"};
        //    ArrayAdapter<String> adapter = new ArrayAdapter<String>(t his,android.R.layout.simple_list_ite m_1,countries);


        ip = "192.168.1.3:8090";
        url = "http://" + ip + "/consultarGeneralCotHydro.php";

        Bundle b = this.getIntent().getExtras();
        indice = b.getString("i");
        concepto = b.getString("concepto");
        seleccionado = (TextView) findViewById(R.id.seleccionado);
        seleccionado2 = (TextView) findViewById(R.id.seleccionado2);

        mProgressDialog = new ProgressDialog(BuscarDesc.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Consultando datos...");

        
        mList = retrievePeople();
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txt_search);
        txtSearch.setThreshold(1);
        adapter = new PeopleAdapter(this, R.layout.cotizacion_metro, R.id.lbl_name, mList);
        txtSearch.setAdapter(adapter);
        txtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                selected= position;
                seleccionado.setText(mList.get(position).getCodigo());

            }
        });


    }

    private List<Objeto> retrievePeople() {
        List<Objeto> list = new ArrayList<Objeto>();
        conceptos = new ArrayList<>();
        codigos = new ArrayList<>();

        mProgressDialog.show();
        url = "http://" + ip + "/consultarGeneralCotHydro.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("sConcepto", concepto));
        params.add(new BasicNameValuePair("sParametro", "inventario"));

        String resultServer = getHttpPost(url, params);
        System.out.println(resultServer);

        try {
            JSONArray jArray = new JSONArray(resultServer);
            ArrayList<String> array = new ArrayList<String>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);
                conceptos.add(json.getString("descripcion"));
                codigos.add(json.getString("codigo"));
                list.add(new Objeto(codigos.get(i) +  " " + conceptos.get(i) , " - " + conceptos.get(i), i+1));
            }

        } catch (Throwable e) {

            e.printStackTrace();

        }

        mProgressDialog.dismiss();

        return list;
    }

    public void guardar(View v){
        String resultado = "";
        String texto = seleccionado.getText().toString().trim();
        for (int i =0;i<texto.length();i++){
            if (!texto.substring(i,i+1).equals(" ")){
                resultado+=seleccionado.getText().subSequence(i,i+1);
            }else{
                break;
            }
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result_cot", resultado);
        returnIntent.putExtra("i", indice);
        setResult(3, returnIntent);
        finish();
    }

    public void regresar(View v){

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


}

