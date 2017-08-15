package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Informes extends Activity {

    ProgressDialog mProgressDialog;
    private int pageHeight;
    private int pageWidth;
    public PdfDocument myPdfDocument;
    String Id = "";
    String ip = "";
    String placa = "";
    int totalpages = 0;
    ArrayList<String> procesos;
    ArrayList<String> nomProcesos;
    String empresa = "";
    String sucursal = "";
    String user = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        mProgressDialog = new ProgressDialog(Informes.this);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Enviando Correo...");

        Id = getIntent().getExtras().getString("id");
        ip = getIntent().getExtras().getString("ip");
        placa = getIntent().getExtras().getString("placa");
        user = getIntent().getExtras().getString("user");
        empresa = getIntent().getExtras().getString("empresa");
        sucursal = getIntent().getExtras().getString("sucursal");
        procesos = getIntent().getExtras().getStringArrayList("procesos");
        nomProcesos = getIntent().getExtras().getStringArrayList("nomProcesos");

        ArrayList<Lista_entrada_mov> informes = new ArrayList<Lista_entrada_mov>();

        informes.add(new Lista_entrada_mov(R.drawable.informe, "INFORME PDF FOTOS", "", 0));
        informes.add(new Lista_entrada_mov(R.drawable.informe,"INFORME PDF TEXTO" ,"",  0));
        informes.add(new Lista_entrada_mov(R.drawable.informe, "ENVIAR CORREO PDF FOTOS" ,"",  0));
        informes.add(new Lista_entrada_mov(R.drawable.informe, "ENVIAR CORREO PDF TEXTO" ,"",  0));

        ListView listInforme = (ListView) findViewById(R.id.listaInforme);
        listInforme.setAdapter(new Lista_adaptador(this, R.layout.entrada, informes){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada_mov) entrada).get_textoEncima());


                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada_mov) entrada).get_idImagen());
                }
            }
        });

        listInforme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_mov elegido = (Lista_entrada_mov) pariente.getItemAtPosition(posicion);

                if (elegido.get_textoEncima().equals("INFORME PDF TEXTO")){
                    Intent i = new Intent(Informes.this,SeleccionarInformeProcesos.class);
                    i.putExtra("procesos",procesos);
                    i.putExtra("nomProcesos",nomProcesos);
                    i.putExtra("empresa",empresa);
                    i.putExtra("sucursal",sucursal);
                    i.putExtra("user",user);
                    i.putExtra("ip",ip);
                    i.putExtra("id",Id);
                    i.putExtra("placa",placa);
                    i.putExtra("atributo","TEXTO");
                    startActivity(i);
                }

                if (elegido.get_textoEncima().equals("INFORME PDF FOTOS")){
                    Intent i = new Intent(Informes.this,SeleccionarInformeProcesos.class);
                    i.putExtra("procesos",procesos);
                    i.putExtra("nomProcesos",nomProcesos);
                    i.putExtra("empresa",empresa);
                    i.putExtra("sucursal",sucursal);
                    i.putExtra("user",user);
                    i.putExtra("ip",ip);
                    i.putExtra("id",Id);
                    i.putExtra("placa",placa);
                    i.putExtra("atributo","FOTOS");
                    startActivity(i);

                }

                if (elegido.get_textoEncima().equals("ENVIAR CORREO PDF TEXTO")){
                    new EnviarCorreoInforme().execute("");
                }

                if (elegido.get_textoEncima().equals("ENVIAR CORREO PDF FOTOS")){
                    new EnviarCorreoFotos().execute("");
                }
            }

        });
    }

    public class MyPrintDocumentAdapterFotos extends PrintDocumentAdapter
    {
        Context context;

        public MyPrintDocumentAdapterFotos(Context context)
        {
            this.context = context;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback,
                             Bundle metadata) {

            myPdfDocument = new PrintedPdfDocument(context, newAttributes);

            pageHeight =
                    newAttributes.getMediaSize().getHeightMils()/1000 * 72;
            pageWidth =
                    newAttributes.getMediaSize().getWidthMils()/1000 * 72;

            if (cancellationSignal.isCanceled() ) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder(placa + "FOTOS.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }


        @Override
        public void onWrite(final PageRange[] pageRanges,
                            final ParcelFileDescriptor destination,
                            final CancellationSignal cancellationSignal,
                            final WriteResultCallback callback) {

            /*String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
            params.add(new BasicNameValuePair("sId", Id));


            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);

            ArrayList<String> array = new ArrayList<String>();
            ArrayList<String> arrayTxt = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo").trim());
                    arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                    arrayTxt.add("");
                    arrayTxt.add("");
                    arrayTxt.add("");
                    array.add(json.getString("cod_valor").trim());
                    array.add(json.getString("nom_proceso").trim());
                    array.add(json.getString("foto").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            int indice = 0;
            for (int i = 0; i < totalpages; i++) {
                if (pageInRange(pageRanges, i))
                {
                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                            pageHeight, i).create();

                    PdfDocument.Page page =
                            myPdfDocument.startPage(newPage);

                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        myPdfDocument.close();
                        myPdfDocument = null;
                        return;
                    }
                    try {
                        drawPageFotos(page, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.finishPage(page);
                }

                indice += 4;
            }

            try {
                myPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }

            callback.onWriteFinished(pageRanges);


        }

    }

    public class MyPrintDocumentAdapter extends PrintDocumentAdapter
    {
        Context context;

        public MyPrintDocumentAdapter(Context context)
        {
            this.context = context;
        }

        @Override
        public void onLayout(PrintAttributes oldAttributes,
                             PrintAttributes newAttributes,
                             CancellationSignal cancellationSignal,
                             LayoutResultCallback callback,
                             Bundle metadata) {

            myPdfDocument = new PrintedPdfDocument(context, newAttributes);

            pageHeight =
                    newAttributes.getMediaSize().getHeightMils()/1000 * 72;
            pageWidth =
                    newAttributes.getMediaSize().getWidthMils()/1000 * 72;

            if (cancellationSignal.isCanceled() ) {
                callback.onLayoutCancelled();
                return;
            }

            if (totalpages > 0) {
                PrintDocumentInfo.Builder builder = new PrintDocumentInfo
                        .Builder(placa + ".pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(totalpages);

                PrintDocumentInfo info = builder.build();
                callback.onLayoutFinished(info, true);
            } else {
                callback.onLayoutFailed("Page count is zero.");
            }
        }


        @Override
        public void onWrite(final PageRange[] pageRanges,
                            final ParcelFileDescriptor destination,
                            final CancellationSignal cancellationSignal,
                            final WriteResultCallback callback) {

            for (int i = 0; i < totalpages; i++) {
                if (pageInRange(pageRanges, i))
                {
                    PdfDocument.PageInfo newPage = new PdfDocument.PageInfo.Builder(pageWidth,
                            pageHeight, i).create();

                    PdfDocument.Page page =
                            myPdfDocument.startPage(newPage);

                    if (cancellationSignal.isCanceled()) {
                        callback.onWriteCancelled();
                        myPdfDocument.close();
                        myPdfDocument = null;
                        return;
                    }
                    try {
                        drawPage(page, i);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    myPdfDocument.finishPage(page);
                }
            }

            try {
                myPdfDocument.writeTo(new FileOutputStream(
                        destination.getFileDescriptor()));
            } catch (IOException e) {
                callback.onWriteFailed(e.toString());
                return;
            } finally {
                myPdfDocument.close();
                myPdfDocument = null;
            }

            callback.onWriteFinished(pageRanges);


        }

    }

    private boolean pageInRange(PageRange[] pageRanges, int page)
    {
        for (int i = 0; i<pageRanges.length; i++)
        {
            if ((page >= pageRanges[i].getStart()) &&
                    (page <= pageRanges[i].getEnd()))
                return true;
        }
        return false;
    }

    private void drawPageFotos(PdfDocument.Page page,
                               int pagenumber) throws IOException {

        pagenumber++; // Make sure page numbers start at 1

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        if (pagenumber == 1) {

            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
            params.add(new BasicNameValuePair("sId", Id));


            String resultServer = getHttpPost(url, params);
            System.out.println(resultServer);

            ArrayList<String> array = new ArrayList<String>();
            ArrayList<String> arrayTxt = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo").trim());
                    arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                    arrayTxt.add("");
                    arrayTxt.add("");
                    arrayTxt.add("");
                    array.add(json.getString("cod_valor").trim());
                    array.add(json.getString("nom_proceso").trim());
                    array.add(json.getString("foto").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
            params1.add(new BasicNameValuePair("sId", Id));


            String resultServer1 = getHttpPost(url1, params1);
            System.out.println("---------------------------------resultserver----------------");
            ArrayList<String> array1 = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer1);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array1.add(json.getString("nit").trim());
                    array1.add(json.getString("nombres").trim());
                    array1.add(json.getString("direccion").trim());
                    array1.add(json.getString("telefono_1").trim());
                    array1.add(json.getString("celular").trim());
                    array1.add(json.getString("mail").trim());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            int titleBaseLine = 120;
            int leftMargin = 100;


            paint.setColor(Color.rgb(228, 228, 228));
            canvas.drawRect(0, 0, 700, 330, paint);

            paint.setColor(Color.rgb(255, 255, 255));
            canvas.drawRect(0, 90, 700, 92, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);

            canvas.drawText(
                    "NOMBRES  : " + array1.get(1) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "NIT  : " + array1.get(0),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;


            canvas.drawText(
                    "DIRECCION  : " + array1.get(2) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "TELEFONO  : " + array1.get(3) ,
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;



            canvas.drawText(
                    "CELULAR  : " + array1.get(4),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "MAIL  : " + array1.get(5),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 85;

            try {

                String nombre = Id + "-" + placa + "-" + "1";
                String fotoplaca = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COLIBRI/" + nombre + ".jpg";
                ;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fotoplaca, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 180, 180, false), 370, 120, paint);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Por favor tome la foto de la placa !", Toast.LENGTH_SHORT).show();
            }

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logogeneral);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bm, 80, 80, false),0, 0, paint);



            paint.setColor(Color.BLACK);

            canvas.drawText(
                    "TRIZIO",
                    90,
                    20,
                    paint);

            canvas.drawText(
                    "Cll 29 # 41-105 Edificio SOHO",
                    90,
                    50,
                    paint);

            canvas.drawText(
                    "310 3703366",
                    90,
                    80,
                    paint);





            for (int i = 0; i < array.size(); i = i + 4) {


                if (i!=0) {

                    if (i <= 3) {
                        if (array.get(i - 1).equals(array.get(i + 2))) {

                            paint.setColor(Color.BLACK);


                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;

                        } else {

                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);


                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine, paint);

                            titleBaseLine += 350;
                        }
                    }
                }else{
                    paint.setColor(Color.rgb(255, 0, 0));

                    canvas.drawText(
                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                            20,
                            titleBaseLine,
                            paint);
                    titleBaseLine += 35;

                    paint.setColor(Color.BLACK);

                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine, paint);

                    titleBaseLine += 350;
                }

            }


        }else {
            if (pagenumber ==2) {
                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
                params.add(new BasicNameValuePair("sId", Id));


                String resultServer = getHttpPost(url, params);
                System.out.println(resultServer);

                ArrayList<String> array = new ArrayList<String>();
                ArrayList<String> arrayTxt = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array.add(json.getString("cod_atributo").trim());
                        arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                        arrayTxt.add("");
                        arrayTxt.add("");
                        arrayTxt.add("");
                        array.add(json.getString("cod_valor").trim());
                        array.add(json.getString("nom_proceso").trim());
                        array.add(json.getString("foto").trim());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                params1.add(new BasicNameValuePair("sId", Id));


                String resultServer1 = getHttpPost(url1, params1);
                System.out.println("---------------------------------resultserver----------------");
                ArrayList<String> array1 = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer1);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array1.add(json.getString("nit").trim());
                        array1.add(json.getString("nombres").trim());
                        array1.add(json.getString("direccion").trim());
                        array1.add(json.getString("telefono_1").trim());
                        array1.add(json.getString("celular").trim());
                        array1.add(json.getString("mail").trim());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int titleBaseLine = 60;
                int leftMargin = 100;

                paint.setTextSize(20);


                for (int i = 4; i < array.size(); i = i + 4) {

                    if (i!=0) {

                        if (i <= 8) {
                            if (array.get(i - 1).equals(array.get(i + 2))) {

                                paint.setColor(Color.BLACK);

                                byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                titleBaseLine += 150;

                            } else {

                                paint.setColor(Color.rgb(255, 0, 0));

                                canvas.drawText(
                                        array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                        20,
                                        titleBaseLine,
                                        paint);
                                titleBaseLine += 35;

                                paint.setColor(Color.BLACK);

                                byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                titleBaseLine += 350;
                            }
                        }
                    }else{
                        paint.setColor(Color.rgb(255, 0, 0));

                        canvas.drawText(
                                array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                20,
                                titleBaseLine,
                                paint);
                        titleBaseLine += 35;

                        paint.setColor(Color.BLACK);

                        byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                        canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                        titleBaseLine += 350;
                    }

                }
            }else{
                if (pagenumber ==3) {
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);

                    ArrayList<String> array = new ArrayList<String>();
                    ArrayList<String> arrayTxt = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                            arrayTxt.add("");
                            arrayTxt.add("");
                            arrayTxt.add("");
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                            array.add(json.getString("foto").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 12; i < array.size(); i = i + 4) {

                        if (i != 0) {

                            if (i <= 16) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;
                        }

                    }
                }else{
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdfFotos"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println(resultServer);

                    ArrayList<String> array = new ArrayList<String>();
                    ArrayList<String> arrayTxt = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            arrayTxt.add(getStringFromFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/Notes/"+ Id  + json.getString("cod_atributo").trim() + ".txt"));
                            arrayTxt.add("");
                            arrayTxt.add("");
                            arrayTxt.add("");
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                            array.add(json.getString("foto").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 20; i < array.size(); i = i + 4) {

                        if (i != 0) {

                            if (i <= 24 ) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                                    canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                                    titleBaseLine += 350;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2) + " " +  array.get(i) + " " +  array.get(i + 1),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            byte[] decodedString = Base64.decode(arrayTxt.get(i).trim(), Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);


                            canvas.drawBitmap(Bitmap.createScaledBitmap(decodedByte, 300, 300, false),leftMargin, titleBaseLine , paint);

                            titleBaseLine += 350;
                        }

                    }
                }
            }

        }



    }

    private void drawPage(PdfDocument.Page page,
                          int pagenumber) throws IOException {

        pagenumber++; // Make sure page numbers start at 1

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        if (pagenumber == 1) {



            String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("sParametro", "pdf"));
            params.add(new BasicNameValuePair("sId", Id));


            String resultServer = getHttpPost(url, params);
            System.out.println("---------------------------------resultserver----------------");

            ArrayList<String> array = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array.add(json.getString("cod_atributo").trim());
                    array.add(json.getString("cod_valor").trim());
                    array.add(json.getString("nom_proceso").trim());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params1 = new ArrayList<NameValuePair>();
            params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
            params1.add(new BasicNameValuePair("sId", Id));


            String resultServer1 = getHttpPost(url1, params1);
            System.out.println("---------------------------------resultserver----------------");
            ArrayList<String> array1 = new ArrayList<String>();
            try {

                JSONArray jArray = new JSONArray(resultServer1);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    array1.add(json.getString("nit").trim());
                    array1.add(json.getString("nombres").trim());
                    array1.add(json.getString("direccion").trim());
                    array1.add(json.getString("telefono_1").trim());
                    array1.add(json.getString("celular").trim());
                    array1.add(json.getString("mail").trim());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


            int titleBaseLine = 150;
            int leftMargin = 100;


            paint.setColor(Color.rgb(228, 228, 228));
            canvas.drawRect(0, 0, 700, 300, paint);

            paint.setColor(Color.BLACK);
            paint.setTextSize(20);

            canvas.drawText(
                    "NOMBRES  : " + array1.get(1) + "   " + "NIT  : " + array1.get(0),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;


            canvas.drawText(
                    "DIRECCION  : " + array1.get(2) + "   " + "TELEFONO  : " + array1.get(3),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "CELULAR  : " + array1.get(4),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 35;

            canvas.drawText(
                    "MAIL  : " + array1.get(5),
                    leftMargin-50,
                    titleBaseLine,
                    paint);
            titleBaseLine += 85;

            try {

                String nombre = Id + "-" + placa + "-" + "1";
                String fotoplaca = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/COLIBRI/" + nombre + ".jpg";
                ;

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fotoplaca, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false), 440, 140, paint);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Por favor tome la foto de la placa !", Toast.LENGTH_SHORT).show();
            }

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.logogeneral);
            canvas.drawBitmap(Bitmap.createScaledBitmap(bm, 80, 80, false),0, 0, paint);

            paint.setColor(Color.BLACK);

            canvas.drawText(
                    "TRIZIO",
                    90,
                    20,
                    paint);

            canvas.drawText(
                    "Cll 29 # 41-105 Edificio SOHO",
                    90,
                    50,
                    paint);

            canvas.drawText(
                    "310 3703366",
                    90,
                    80,
                    paint);



            for (int i = 0; i < array.size(); i = i + 3) {

                if (i!=0) {

                    if (i < 30) {
                        if (array.get(i - 1).equals(array.get(i + 2))) {

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;

                        } else {

                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;
                        }
                    }
                }else{
                    paint.setColor(Color.rgb(255, 0, 0));

                    canvas.drawText(
                            array.get(i + 2),
                            20,
                            titleBaseLine,
                            paint);
                    titleBaseLine += 35;

                    paint.setColor(Color.BLACK);

                    canvas.drawText(
                            array.get(i) + " : " + array.get(i + 1),
                            leftMargin,
                            titleBaseLine,
                            paint);

                    titleBaseLine += 35;
                }

            }


        }else {
            if (pagenumber ==2) {
                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sParametro", "pdf"));
                params.add(new BasicNameValuePair("sId", Id));


                String resultServer = getHttpPost(url, params);
                System.out.println("---------------------------------resultserver----------------");

                ArrayList<String> array = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array.add(json.getString("cod_atributo").trim());
                        array.add(json.getString("cod_valor").trim());
                        array.add(json.getString("nom_proceso").trim());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                params1.add(new BasicNameValuePair("sId", Id));


                String resultServer1 = getHttpPost(url1, params1);
                System.out.println("---------------------------------resultserver----------------");
                ArrayList<String> array1 = new ArrayList<String>();
                try {

                    JSONArray jArray = new JSONArray(resultServer1);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        array1.add(json.getString("nit").trim());
                        array1.add(json.getString("nombres").trim());
                        array1.add(json.getString("direccion").trim());
                        array1.add(json.getString("telefono_1").trim());
                        array1.add(json.getString("celular").trim());
                        array1.add(json.getString("mail").trim());
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                int titleBaseLine = 60;
                int leftMargin = 100;

                paint.setTextSize(20);


                for (int i = 30; i < array.size(); i = i + 3) {

                    if (i!=0) {

                        if (i < 81) {
                            if (array.get(i - 1).equals(array.get(i + 2))) {

                                paint.setColor(Color.BLACK);

                                canvas.drawText(
                                        array.get(i) + " : " + array.get(i + 1),
                                        leftMargin,
                                        titleBaseLine,
                                        paint);

                                titleBaseLine += 35;

                            } else {

                                paint.setColor(Color.rgb(255, 0, 0));

                                canvas.drawText(
                                        array.get(i + 2),
                                        20,
                                        titleBaseLine,
                                        paint);
                                titleBaseLine += 35;

                                paint.setColor(Color.BLACK);

                                canvas.drawText(
                                        array.get(i) + " : " + array.get(i + 1),
                                        leftMargin,
                                        titleBaseLine,
                                        paint);

                                titleBaseLine += 35;
                            }
                        }
                    }else{
                        paint.setColor(Color.rgb(255, 0, 0));

                        canvas.drawText(
                                array.get(i + 2),
                                20,
                                titleBaseLine,
                                paint);
                        titleBaseLine += 35;

                        paint.setColor(Color.BLACK);

                        canvas.drawText(
                                array.get(i) + " : " + array.get(i + 1),
                                leftMargin,
                                titleBaseLine,
                                paint);

                        titleBaseLine += 35;
                    }

                }
            }else{
                if (pagenumber ==3) {
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdf"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println("---------------------------------resultserver----------------");

                    ArrayList<String> array = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 81; i < array.size(); i = i + 3) {

                        if (i != 0) {

                            if (i < 129) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;
                        }

                    }
                }else{
                    String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sParametro", "pdf"));
                    params.add(new BasicNameValuePair("sId", Id));


                    String resultServer = getHttpPost(url, params);
                    System.out.println("---------------------------------resultserver----------------");

                    ArrayList<String> array = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array.add(json.getString("cod_atributo").trim());
                            array.add(json.getString("cod_valor").trim());
                            array.add(json.getString("nom_proceso").trim());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                    List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                    params1.add(new BasicNameValuePair("sParametro", "pdfUser"));
                    params1.add(new BasicNameValuePair("sId", Id));


                    String resultServer1 = getHttpPost(url1, params1);
                    System.out.println("---------------------------------resultserver----------------");
                    ArrayList<String> array1 = new ArrayList<String>();
                    try {

                        JSONArray jArray = new JSONArray(resultServer1);

                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject json = jArray.getJSONObject(i);
                            array1.add(json.getString("nit").trim());
                            array1.add(json.getString("nombres").trim());
                            array1.add(json.getString("direccion").trim());
                            array1.add(json.getString("telefono_1").trim());
                            array1.add(json.getString("celular").trim());
                            array1.add(json.getString("mail").trim());
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    int titleBaseLine = 60;
                    int leftMargin = 100;

                    paint.setTextSize(20);


                    for (int i = 129; i < array.size(); i = i + 3) {

                        if (i != 0) {

                            if (i < 160) {
                                if (array.get(i - 1).equals(array.get(i + 2))) {

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;

                                } else {

                                    paint.setColor(Color.rgb(255, 0, 0));

                                    canvas.drawText(
                                            array.get(i + 2),
                                            20,
                                            titleBaseLine,
                                            paint);
                                    titleBaseLine += 35;

                                    paint.setColor(Color.BLACK);

                                    canvas.drawText(
                                            array.get(i) + " : " + array.get(i + 1),
                                            leftMargin,
                                            titleBaseLine,
                                            paint);

                                    titleBaseLine += 35;
                                }
                            }
                        } else {
                            paint.setColor(Color.rgb(255, 0, 0));

                            canvas.drawText(
                                    array.get(i + 2),
                                    20,
                                    titleBaseLine,
                                    paint);
                            titleBaseLine += 35;

                            paint.setColor(Color.BLACK);

                            canvas.drawText(
                                    array.get(i) + " : " + array.get(i + 1),
                                    leftMargin,
                                    titleBaseLine,
                                    paint);

                            titleBaseLine += 35;
                        }

                    }
                }
            }

        }

        if (totalpages-pagenumber == 0) {

            canvas.drawText(
                    "FIRMA : ",
                    200,
                    720,
                    paint);

            try {
                String fileName = Environment.getExternalStorageDirectory() + "/FIRMA.JPG";


                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
                canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 120, 120, false), 300, 650, paint);

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Por favor dibuje la firma digital !", Toast.LENGTH_SHORT).show();
            }


        }

    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
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

    class EnviarCorreoInforme extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){

            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... f_url) {
            String correo = "";
            String url9 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params9 = new ArrayList<NameValuePair>();
            params9.add(new BasicNameValuePair("sPlaca", placa.trim()));
            params9.add(new BasicNameValuePair("sParametro", "correo"));


            String resultServer9 = getHttpPost(url9, params9);
            System.out.println(resultServer9);
            try {

                JSONArray jArray = new JSONArray(resultServer9);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    correo = json.getString("mail").trim();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*Mail m = new Mail("triziotool@gmail.com", "1Triziotool2");

            String[] toArr = {correo, correo};
            m.setTo(toArr);
            m.setFrom("triziotool@gmail.com");
            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
            m.setBody("Email body.");

            try {
                m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + ".pdf");

                m.send();
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }*/

            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {

            mProgressDialog.dismiss();

            Toast.makeText(Informes.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();

            File fichero = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + ".pdf");

            fichero.delete();

        }
    }

    class EnviarCorreoFotos extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute(){

            mProgressDialog.show();

        }
        @Override
        protected String doInBackground(String... f_url) {

            String correo = "";
            String url9 = "http://" + ip + "/consultarGeneralColibriHydro.php";

            List<NameValuePair> params9 = new ArrayList<NameValuePair>();
            params9.add(new BasicNameValuePair("sPlaca", placa.trim()));
            params9.add(new BasicNameValuePair("sParametro", "correo"));


            String resultServer9 = getHttpPost(url9, params9);
            System.out.println(resultServer9);
            try {

                JSONArray jArray = new JSONArray(resultServer9);
                ArrayList<String> array = new ArrayList<String>();
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    correo = json.getString("mail").trim();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            /*Mail m = new Mail("triziotool@gmail.com", "1Triziotool2");

            String[] toArr = {correo, correo};
            m.setTo(toArr);
            m.setFrom("triziotool@gmail.com");
            m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
            m.setBody("Email body.");

            try {
                m.addAttachment(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + "FOTOS.pdf");

                m.send();
            } catch(Exception e) {
                //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                Log.e("MailApp", "Could not send email", e);
            }*/



            return null;
        }
        @Override
        protected void onPostExecute(String file_url) {

            mProgressDialog.dismiss();

            File fichero = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/" +  placa + "FOTOS.pdf");

            fichero.delete();

            Toast.makeText(Informes.this, "Email was sent successfully.", Toast.LENGTH_LONG).show();

        }
    }

}
