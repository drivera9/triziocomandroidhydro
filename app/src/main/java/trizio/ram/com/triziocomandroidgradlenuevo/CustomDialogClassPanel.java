package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import java.util.ArrayList;
import java.util.List;

/*
 * Failed to analyse overrides
 */
public class CustomDialogClassPanel
        extends Dialog
        implements View.OnClickListener {
    public int _id;
    public Button ok;
    public int posicion;
    public String plu;

    public Activity c;
    public Dialog d;
    public Button yes, no, cancelar, ver;
    String proceso;
    String tamano;
    String ip;
    String grupo;
    String empresa;
    String sucursal;
    String pass;
    String[][] generalAux;
    String[][] general;
    String Id;
    String placa;
    ArrayList<String> columnas;
    String user;
    String nombreProceso;
    String esAutonext;
    String conexion;
    String num_rombo;
    String texto;
    ArrayList<String> valoresD1;
    ArrayList<String> atributosD1;
    public CustomDialogClassPanel(Activity a, String grupo, String proceso, String tamano, String ip, String empresa
                            , String sucursal , String pass, String[][] generalAux, String[][] general, String Id, String placa,
                                  ArrayList<String> columnas, String user, String nombreProceso, String esAutonext, String conexion,
                                  String num_rombo, String texto, ArrayList<String> atributosD1, ArrayList<String> valoresD1) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.proceso = proceso;
        this.tamano = tamano;
        this.ip = ip;
        this.grupo = grupo;
        this.empresa = empresa;
        this.sucursal = sucursal;
        this.pass = pass;
        this.generalAux = generalAux;
        this.general = general;
        this.Id = Id;
        this.placa = placa;
        this.columnas = columnas;
        this.user = user;
        this.nombreProceso = nombreProceso;
        this.esAutonext = esAutonext;
        this.conexion = conexion;
        this.num_rombo = num_rombo;
        this.texto = texto;
        this.atributosD1 = atributosD1;
        this.valoresD1 = valoresD1;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_panel);
        yes = (Button) findViewById(R.id.btn_yes);
        cancelar = (Button) findViewById(R.id.btnCancelar);
        no = (Button) findViewById(R.id.btn_no);
        ver = (Button) findViewById(R.id.btnVer);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        ver.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        TextView edit = (TextView) findViewById(R.id.editText_texto);
        edit.setText(texto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                ArrayList valores = new ArrayList();
                ArrayList atributos = new ArrayList();
                ArrayList ArrayDatos = new ArrayList();



                String url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sSecuencia", grupo));
                params1.add(new BasicNameValuePair("sTamano", tamano));
                params1.add(new BasicNameValuePair("sParametro", "valores"));

                ArrayList<String> array1 = new ArrayList<String>();
                String resultServer1 = getHttpPost(url1, params1);
                System.out.println(resultServer1);

                try {

                    JSONArray jArray = new JSONArray(resultServer1);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        atributos.add(json.getString("cod_atributo"));
                        valores.add(json.getString("cod_valor"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String url = "http://" + ip + "/consultarGeneralColibriHydro.php";

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sCodUser", pass));
                params.add(new BasicNameValuePair("sCodEmpresa", empresa));
                params.add(new BasicNameValuePair("sCodSucursal", sucursal));
                params.add(new BasicNameValuePair("sIndEstado", "A"));
                params.add(new BasicNameValuePair("sCodProceso", proceso));
                params.add(new BasicNameValuePair("sParametro", "consultarProceso"));

                String resultServer  = getHttpPost(url,params);
                System.out.println(resultServer);

                try {
                    JSONArray jArray = new JSONArray(resultServer);
                    generalAux = new String[jArray.length()][21];

                    int contador = 0;
                    int j =0;
                    int longitud = jArray.length();
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        generalAux[i][j] = json.getString("cod_empresa");
                        generalAux[i][j+1] = json.getString("cod_sucursal");
                        generalAux[i][j+2] = json.getString("cod_usuario");
                        generalAux[i][j+3] = json.getString("cod_proceso");
                        generalAux[i][j+4] = json.getString("cod_atributo");
                        if (proceso.trim().equals("9")){
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
                        generalAux[i][j+16] = json.getString("ind_autonext");
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
                }catch (JSONException e ){
                    e.printStackTrace();
                }
                Intent intent = new Intent(getContext(),RecepcionPanel.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("nombreTabla", "");
                bundle2.putString("codAtributo", "");
                bundle2.putString("nuevoRegistro", "NO");
                bundle2.putString("num_grupo", grupo);
                bundle2.putString("user", user);
                bundle2.putString("nombreProceso", nombreProceso);
                bundle2.putString("esAutonext", esAutonext);
                bundle2.putString("pass", pass);
                bundle2.putString("sucursal", sucursal);
                bundle2.putString("empresa", empresa);
                bundle2.putString("conexion", conexion);
                bundle2.putString("sucursal", sucursal);
                bundle2.putString("placa", placa);
                bundle2.putSerializable("general", general);
                bundle2.putSerializable("generalAux", generalAux);
                bundle2.putStringArrayList("columnas", columnas);
                bundle2.putStringArrayList("datos", ArrayDatos);
                bundle2.putString("rombo", num_rombo);
                bundle2.putSerializable("general", general);
                bundle2.putString("ip", ip);
                bundle2.putString("titulo", "/Rec");
                bundle2.putString("proceso", proceso);
                bundle2.putStringArrayList("atributos", atributos);
                bundle2.putString("id", Id);
                bundle2.putStringArrayList("valores", valores);
                intent.putExtras(bundle2);
                getContext().startActivity(intent);
                dismiss();
                break;
            case R.id.btn_no:
                url1 = "http://" + ip + "/guardarMovimientoColibriHydro.php";

                params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sProceso", proceso));
                params1.add(new BasicNameValuePair("sGrupo", grupo));
                params1.add(new BasicNameValuePair("sParametro", "eliminarGrupo"));

                resultServer1 = getHttpPost(url1, params1);
                System.out.println(resultServer1);
                ActualizarRegistro.actualizarLista();
                dismiss();
                break;
            case R.id.btnCancelar:
                dismiss();
                break;
            case R.id.btnVer:

                valores = new ArrayList();
                atributos = new ArrayList();
                ArrayDatos = new ArrayList();

                atributosD1 = new ArrayList<>();
                valoresD1 = new ArrayList<>();

                url1 = "http://" + ip + "/consultarGeneralColibriHydro.php";

                params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("sProceso", proceso));
                params1.add(new BasicNameValuePair("sId", Id));
                params1.add(new BasicNameValuePair("sGrupo", grupo));
                params1.add(new BasicNameValuePair("sParametro", "consultarAtributosDetalle1"));

                array1 = new ArrayList<String>();
                resultServer1 = getHttpPost(url1, params1);
                System.out.println(resultServer1);
                try {

                    JSONArray jArray = new JSONArray(resultServer1);

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json = jArray.getJSONObject(i);
                        atributosD1.add(json.getString("cod_atributo"));
                        valoresD1.add(json.getString("cod_valor"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                atributos = new ArrayList();

                for (int i = 0;i<atributosD1.size();i++) {

                    atributos.add(atributosD1.get(i).trim() + " :");
                    atributos.add(valoresD1.get(i).trim());


                }

                Bundle bundle = new Bundle();
                intent = new Intent(getContext(), (Class) GuardarPanel.class);
                bundle.putString("id", Id);
                bundle.putString("conexion", conexion);
                bundle.putString("esAutonext", esAutonext);
                bundle.putString("nuevoRegistro", "");
                bundle.putString("motivo", "");
                bundle.putString("num_grupo", grupo);
                bundle.putString("marca", "");
                bundle.putSerializable("notas", new String[]{""});
                bundle.putString("eventoRazon", "");
                bundle.putString("eventoCita", "");
                bundle.putString("descripcionModelo", "");
                bundle.putString("km", "");
                bundle.putString("encuentraNit", "");
                bundle.putString("encuentraPlaca", "");
                bundle.putString("solicitud", "");
                bundle.putString("tipoVehiculo", "");
                bundle.putString("costo", "");
                bundle.putString("fpago", "");
                bundle.putString("precio", "");
                bundle.putString("descPrecio", "");
                bundle.putString("promesa", "");
                bundle.putString("tecno", "");
                bundle.putString("email", "");
                bundle.putString("soat", "");
                bundle.putString("cod_empresa", empresa);
                bundle.putString("cod_sucursal", sucursal);
                bundle.putString("cod_proceso", proceso);
                bundle.putString("cod_placa", placa);
                bundle.putString("num_rombo", num_rombo);
                bundle.putString("cod_usuario", "");
                bundle.putString("cod_ubicacion", "");
                bundle.putString("fec_proceso", "");
                bundle.putString("num_nit", "");
                bundle.putString("ind_estado", "A");
                bundle.putStringArrayList("atributos", atributos);
                bundle.putStringArrayList("columnas", columnas);
                bundle.putStringArrayList("datos", ArrayDatos);
                bundle.putString("ip", ip);
                bundle.putString("faltanDatos", "");
                bundle.putString("nombre_tabla", "");
                bundle.putString("razon", "");
                bundle.putString("proceso", "");
                intent.putExtras(bundle);
                getContext().startActivity(intent);
                dismiss();

                break;
            default:
                break;
        }
        dismiss();
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