package trizio.ram.com.triziocomandroidgradlenuevo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;

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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GeneradorQuery extends AppCompatActivity {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN,12,
            Font.BOLD);

    ArrayList<String> columnasWhere;
    int tamano = 0;
    ArrayList<String> columnas;
    String ip = "";
    String tabla = "";
    Spinner spinnerColumnas;
    Spinner spinnerOperador;
    Spinner spinnerLogico;
    String columna = "";
    String operador = "";
    String logico = "";
    String[][] array = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generador_query);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        columnas = getIntent().getExtras().getStringArrayList("columnas");
        columnasWhere = getIntent().getExtras().getStringArrayList("columnasWhere");
        ip = getIntent().getExtras().getString("ip");
        tabla = getIntent().getExtras().getString("tabla");

        spinnerColumnas = (Spinner) findViewById(R.id.spinnerColumnas);

        final String[] datos =
                new String[columnas.size()];

        for (int i =0;i<columnas.size();i++){
            datos[i] = columnas.get(i).trim();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerColumnas.setOnItemSelectedListener(new ColumnaSelectedListener());

        spinnerColumnas.setAdapter(adapter);

        spinnerOperador = (Spinner) findViewById(R.id.spinnerOperador);

        final String[] datosOperador =
                new String[]{"<",">","=",">=","<=","LIKE"};


        ArrayAdapter<String> adapterOperador =
                new ArrayAdapter<String>(this,
                        R.layout.simple_spinner_item, datosOperador);
        adapterOperador.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerOperador.setAdapter(adapterOperador);

        spinnerOperador.setOnItemSelectedListener(new OperadorSelectedListener());

        spinnerLogico = (Spinner) findViewById(R.id.spinnerLogico);

        final String[] datosLogico =
                new String[]{" ","AND","OR"};


        ArrayAdapter<String> adapterLogico =
                new ArrayAdapter<String>(this,
                        R.layout.simple_spinner_item, datosLogico);
        adapterLogico.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        spinnerLogico.setAdapter(adapterLogico);

        spinnerLogico.setOnItemSelectedListener(new LogicoSelectedListener());

        TextView select = (TextView) findViewById(R.id.select);

        String colsel = "";
        for (int i = 0;i<columnasWhere.size();i++){

            colsel += columnasWhere.get(i).trim() + " , ";

        }
        select.setText("SELECT " + colsel.substring(0,colsel.length()-2) + " FROM " + tabla + " WHERE ");

    }

    public void borrar(){
        EditText editQuery = (EditText) findViewById(R.id.editWhere);
        editQuery.setText(editQuery.getText().toString().substring(0,editQuery.getText().length()-1));
    }

    public void borrarTodo(){
        EditText editQuery = (EditText) findViewById(R.id.editWhere);
        editQuery.setText("");
    }

    public class ColumnaSelectedListener  implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            columna = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            columna = parent.getSelectedItem().toString();
        }
    }

    public class OperadorSelectedListener  implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            operador = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            operador = parent.getSelectedItem().toString();
        }
    }

    public class LogicoSelectedListener  implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            logico = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView parent) {
            logico = parent.getSelectedItem().toString();
        }
    }

    public void añadir(){

        EditText editQuery = (EditText) findViewById(R.id.editWhere);
        EditText editValor = (EditText) findViewById(R.id.editValor);

        editQuery.setText(editQuery.getText().toString().trim() + " " + columna + " " + operador + " " +
                editValor.getText().toString() + " " + logico );

        editValor.setText("");

    }

    public void generar(){

        String sql = "";

        EditText editQuery = (EditText) findViewById(R.id.editWhere);
        TextView select = (TextView) findViewById(R.id.select);

        sql = select.getText() + " " + editQuery.getText().toString();


        String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("sSql", sql));
        params1.add(new BasicNameValuePair("sParametro", "generarQuery"));

        String resultServer1 = getHttpPost(url1, params1);
        System.out.println(resultServer1);


        array = new String[][]{} ;
        try {
            JSONArray jArray = new JSONArray(resultServer1);
            array = new String[jArray.length()][columnasWhere.size()];

            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json = jArray.getJSONObject(i);

                for(int j = 0;j<columnasWhere.size();j++){
                    array[i][j] = json.getString(columnasWhere.get(j).trim());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();

        }

        for (int i = 0; i < array.length; i++) {

            for(int j = 0;j<array[i].length;j++){
                tamano++;
            }
        }

        TextView resultado = (TextView) findViewById(R.id.resultado);

        resultado.setText("");

        Toast.makeText(getApplicationContext(), "Haz ejecutado el SQL correctamente!", Toast.LENGTH_SHORT).show();

        final String[] ar = new String[tamano];

        int indice = 0;

        for (int i = 0; i < array.length; i++) {
            for(int j = 0;j<array[i].length;j++){
                ar[indice] = array[i][j].trim();
                indice++;
            }
        }

        GridView grid;
        grid=(GridView)findViewById(R.id.gridView);
        grid.setNumColumns(columnasWhere.size());
        // gridview a partir del elemento del xml gridview

        grid.setAdapter(new CustomGridViewAdapter(getApplicationContext(), ar, "grd"));// con setAdapter se llena


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Toast para mostrar un mensaje. Escribe el nombre de tu clase
                // si no la llamaste MainActivity.


            }
        });



    }

    private void exportDB(String[][] array) {

        CSVWriter writer = null;
        try {
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            writer = new CSVWriter(new FileWriter(path.getPath() + "/" + tabla + ".csv"), '\t');

            // feed in your array (or convert your data to an array)
            String[] entries = new String[array.length] ;
            String s = "";
            for (int i = 0;i<array.length;i++){
                s = "";
                for (int j = 0;j<array[i].length;j++) {
                    s += array[i][j].trim() + "#";
                }
                entries = s.split("#");
                writer.writeNext(entries);
            }


            writer.close();

            Toast.makeText(getApplicationContext(), "Haz generado el CSV correctamente en la carpeta Descargas!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportPDF(String[][] array){
        try {
            String FILE = (Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + tabla + ".pdf");
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();



            createTable(document);
            document.close();

            Toast.makeText(getApplicationContext(), "Haz generado el PDF correctamente en la carpeta Descargas!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable(Document document) throws DocumentException, FileNotFoundException {

        String FILE = (Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + tabla + ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(FILE));
        // step 3
        document.open();
        // step 4

        PdfPTable table = new PdfPTable(array[0].length);

        ArrayList<String> ar = new ArrayList<>();


        for (int i = 0; i < array.length; i++) {
            for(int j = 0;j<array[i].length;j++){
                ar.add(array[i][j]);
            }
        }

        for (int i = 0;i<ar.size();i++){
            table.addCell(ar.get(i));
        }

        // Step 5
        document.add(table);
        // step 6
        document.close();

    }

    private static void createList(Section subCatPart) {
        com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void correo (){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GeneradorQuery.this);
        alertDialog.setTitle("CORREO");
        alertDialog.setMessage("Entre la direccion de correo");

        final EditText input = new EditText(GeneradorQuery.this);
        input.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*Mail m = new Mail("davidrivera0218@gmail.com", "mamaydios11");

                        String[] toArr = {input.getText().toString(), input.getText().toString()};
                        m.setTo(toArr);
                        m.setFrom("davidrivera0218@gmail.com");
                        m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                        m.setBody("Email body.");

                        try {
                            m.addAttachment(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + tabla + ".csv");

                            if(m.send()) {
                                Toast.makeText(GeneradorQuery.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(GeneradorQuery.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                            }
                        } catch(Exception e) {
                            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                            Log.e("MailApp", "Could not send email", e);
                        }*/
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();





    }

    public void correoPDF (){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(GeneradorQuery.this);
        alertDialog.setTitle("CORREO");
        alertDialog.setMessage("Entre la direccion de correo");

        final EditText input = new EditText(GeneradorQuery.this);
        input.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        /*Mail m = new Mail("davidrivera0218@gmail.com", "mamaydios11");

                        String[] toArr = {input.getText().toString(), input.getText().toString()};
                        m.setTo(toArr);
                        m.setFrom("davidrivera0218@gmail.com");
                        m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                        m.setBody("Email body.");

                        try {
                            m.addAttachment(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + tabla + ".pdf");

                            if(m.send()) {
                                Toast.makeText(GeneradorQuery.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(GeneradorQuery.this, "Email was not sent.", Toast.LENGTH_LONG).show();
                            }
                        } catch(Exception e) {
                            //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                            Log.e("MailApp", "Could not send email", e);
                        }*/
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();





    }


    public static String getHttpPost(String url, List<NameValuePair> params) {
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement

        if (id == R.id.action_csv) {
            if (array == null){
                Toast.makeText(getApplicationContext(), "Por favor genere el SQL !", Toast.LENGTH_SHORT).show();
            }else{
                exportDB(array);
            }
        }

        if (id == R.id.action_pdf) {
            if (array == null){
                Toast.makeText(getApplicationContext(), "Por favor genere el SQL !", Toast.LENGTH_SHORT).show();
            }else{
                exportPDF(array);
            }
        }

        if (id == R.id.action_mail) {
            correo();
        }

        if (id == R.id.action_mailpdf) {
            correoPDF();
        }

        if (id == R.id.action_delete) {
            borrar();
        }

        if (id == R.id.action_deleteall) {
            borrarTodo();
        }

        if (id == R.id.action_send) {
            generar();
        }

        if (id == R.id.action_add) {
            añadir();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
