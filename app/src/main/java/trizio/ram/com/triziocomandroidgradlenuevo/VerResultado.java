package trizio.ram.com.triziocomandroidgradlenuevo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class VerResultado extends AppCompatActivity {
    ArrayList<String> datos;
    ArrayList<String> datosTotales;
    String tamano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resultado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datos = getIntent().getExtras().getStringArrayList("datos");
        datosTotales = getIntent().getExtras().getStringArrayList("datosTotales");
        tamano = getIntent().getExtras().getString("size");

        String[] ar = new String[datos.size()];


        for (int i = 0;i<datos.size();i++){
            ar[i] = datos.get(i);
        }

        GridView grid;
        grid=(GridView)findViewById(R.id.gridView);
        grid.setNumColumns(Integer.parseInt(tamano));
        // gridview a partir del elemento del xml gridview

        grid.setAdapter(new CustomGridViewAdapter(getApplicationContext(), ar, "grd"));// con setAdapter se llena


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Toast para mostrar un mensaje. Escribe el nombre de tu clase
                // si no la llamaste MainActivity.


            }
        });

        String[] ar2 = new String[datosTotales.size()];


        for (int i = 0;i<datosTotales.size();i++){
            ar2[i] = datosTotales.get(i);
        }

        grid=(GridView)findViewById(R.id.gridViewTotales);
        grid.setNumColumns(2);
        // gridview a partir del elemento del xml gridview


        grid.setAdapter(new CustomGridViewAdapter(getApplicationContext(), ar2, "grd"));// con setAdapter se llena


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Toast para mostrar un mensaje. Escribe el nombre de tu clase
                // si no la llamaste MainActivity.


            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return false;
    }
}
