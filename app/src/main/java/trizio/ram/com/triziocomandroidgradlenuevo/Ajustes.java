package trizio.ram.com.triziocomandroidgradlenuevo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Ajustes extends AppCompatActivity {
    String dirIP = "";
    String nombreTabla = "";
    String procesoFinal = "";
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
    ArrayList<String> arrayPlacas = new ArrayList<>();
    ProgressDialog mProgressDialog;
    String ip = "";
    String conexion = "";
    String cod_placa = "";
    String Id = "";
    ArrayList<Lista_entrada_panel> placas = new ArrayList();
    ArrayList ids = new ArrayList();
    ArrayList<String> atributos = new ArrayList();
    ArrayList<String> autoNextProcesos = new ArrayList<>();
    ArrayList<String> valores = new ArrayList();    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);
        Bundle bundle = this.getIntent().getExtras();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("AJUSTES");

        Progress = findViewById(R.id.progressbar);
        empresa = bundle.getString("empresa");
        user = bundle.getString("user");
        pass = bundle.getString("pass");
        sucursal = bundle.getString("sucursal");
        procesos = bundle.getStringArrayList("procesos");
        nomProceso = bundle.getStringArrayList("nomProcesos");
        autoNextProcesos = bundle.getStringArrayList("autoNextProcesos");
        System.out.println("sucursal ------------>" + sucursal);
        conexion = bundle.getString("conexion");
        ip = bundle.getString("ip");

        ListView ajustesProcesos = (ListView) findViewById(R.id.listaAjustes);

        ArrayList<Lista_entrada_panel> arrayAjustesProcesos = new ArrayList<>();
        ArrayList<Lista_entrada_panel> arrayAjustesAtributos = new ArrayList<>();


        for (int i = 0;i<procesos.size();i++){


            try {

                if (procesos.get(i).trim().equals("200")) {
                    proceso = "200";
                    arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }

            try {

                if (procesos.get(i).trim().equals("201")) {
                    proceso = "201";
                    arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }

            try {

                if (procesos.get(i).trim().equals("144")) {
                    proceso = "144";
                    arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }

            try {

                if (procesos.get(i).trim().equals("199")) {
                    proceso = "199";
                    arrayAjustesProcesos.add(new Lista_entrada_panel(R.drawable.recepcion,  nomProceso.get(i).trim() , "" ,Integer.parseInt(procesos.get(i))));
                    continue;
                }
            }catch(Exception e){
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(),"Los procesos estan mal configurados!",Toast.LENGTH_LONG).show();
            }


        }

        ajustesProcesos.setAdapter(new Lista_adaptador_panel(this, R.layout.entrada, arrayAjustesProcesos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                    TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                    if (texto_superior_entrada != null)
                        texto_superior_entrada.setText(((Lista_entrada_panel) entrada).get_textoEncima());

                    if (texto_inferior_entrada != null)
                        texto_inferior_entrada.setText(((Lista_entrada_panel) entrada).get_textoDebajo());


                    ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                    if (imagen_entrada != null)
                        imagen_entrada.setImageResource(((Lista_entrada_panel) entrada).get_idImagen());
                }
            }
        });

        ajustesProcesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                Lista_entrada_panel elegido = (Lista_entrada_panel) pariente.getItemAtPosition(posicion);

                if (String.valueOf(elegido.get_proceso()).equals("200")){
                    mProgressDialog= new ProgressDialog(Ajustes.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    Intent i = new Intent(Ajustes.this,CrearProceso.class);
                    i.putExtra("empresa" , empresa);
                    i.putExtra("sucursal" , sucursal);
                    i.putExtra("ip" , ip);
                    i.putExtra("user" , user);
                    i.putExtra("pass" , pass);

                    startActivity(i);

                    mProgressDialog.dismiss();
                }

                if (String.valueOf(elegido.get_proceso()).equals("201")){
                    mProgressDialog= new ProgressDialog(Ajustes.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ListaProcesos.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("cod_proceso", String.valueOf(elegido.get_proceso()));
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putStringArrayList("procesos", procesos);
                    bundle.putString("placa",  "");
                    bundle.putString("id", Id);
                    bundle.putStringArrayList("nomProcesos", nomProceso);
                    bundle.putStringArrayList("autoNextProcesos", autoNextProcesos);
                    bundle.putString("ip", ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    mProgressDialog.dismiss();
                }

                if (String.valueOf(elegido.get_proceso()).equals("144")){
                    mProgressDialog= new ProgressDialog(Ajustes.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ListaProcesos.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putStringArrayList("procesos", procesos);
                    bundle.putString("cod_proceso", String.valueOf(elegido.get_proceso()));
                    bundle.putString("placa",  "");
                    bundle.putString("id", Id);
                    bundle.putStringArrayList("nomProcesos", nomProceso);
                    bundle.putStringArrayList("autoNextProcesos", autoNextProcesos);
                    bundle.putString("ip", ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    mProgressDialog.dismiss();
                }

                if (String.valueOf(elegido.get_proceso()).equals("199")){
                    mProgressDialog= new ProgressDialog(Ajustes.this);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setMessage("Espere...");

                    mProgressDialog.show();

                    Bundle bundle = new Bundle();
                    Intent intent = new Intent((Context) getApplicationContext(), (Class) ControlUsuarios.class);
                    bundle.putString("sucursal", sucursal);
                    bundle.putString("empresa", empresa);
                    bundle.putString("user", user);
                    bundle.putString("pass", pass);
                    bundle.putString("conexion", conexion);
                    bundle.putString("ip", ip);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    mProgressDialog.dismiss();
                }



            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}
