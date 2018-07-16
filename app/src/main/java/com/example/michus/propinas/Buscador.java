package com.example.michus.propinas;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Buscador extends AppCompatActivity implements View.OnClickListener {

    public EditText etfechainicio;
    public EditText etfechafinal;
    public Button boton_buscar;
    public TextView cantidad_total;
    BBDD_helper helper = new BBDD_helper(this);
    String selectfechainicio = "";
    String selectfechafinal = "";

    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    public Adaptador adaptador;
    Cursor cursito;
    Cursor totalcursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        etfechainicio = findViewById(R.id.fecha_inicio);
        etfechainicio.setOnClickListener(this);
        etfechafinal = findViewById(R.id.fecha_final);
        etfechafinal.setOnClickListener(this);
        boton_buscar=findViewById(R.id.button_buscar);
        cantidad_total=findViewById(R.id.textView_cantidad_total);

        boton_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BuscarAportaionSQL();
                BuscarSumaAportacioSQL();
            }
        });



        //recyclerView=findViewById(R.id.lista_detalle);
        //adaptador=new Adaptador(this,cursito);
        //recyclerView.setAdapter(adaptador);
        //layoutManager=new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

       // getSupportLoaderManager().restartLoader(1,null, (LoaderManager.LoaderCallbacks<Object>) this);

        }

    public void BuscarSumaAportacioSQL(){
        try {
            SQLiteDatabase database = helper.getReadableDatabase();
            totalcursor=database.rawQuery(String.format("select sum(aportacion) from (select * from detalle where fecha between '%s' and '%s') where tipo='positiva'", selectfechainicio, selectfechafinal),null);
            totalcursor.moveToFirst();
            float total = totalcursor.getFloat(0);
            cantidad_total.setText(Float.toString(total));

        }catch (Exception e){
        Toast toast = Toast.makeText(this, "no se encontraron totales", Toast.LENGTH_SHORT);
        toast.show();

    }
    }

        public void BuscarAportaionSQL() {
        SQLiteDatabase db = helper.getReadableDatabase();

        // Define un array de string llamado projection que especifica que columnas de la base de datos queremos visualizar
        // para nuestra consulta
        String[] projection = {
                Estructura_BBDD.NOMBRE_COLUMNA2,
                Estructura_BBDD.NOMBRE_COLUMNA3,
                Estructura_BBDD.NOMBRE_COLUMNA4,
                Estructura_BBDD.NOMBRE_COLUMNA5

        };

        // Filter results WHERE "title" = 'My Title'
            // select * from detalle where fecha between  '2018-28-06' and '2018-29-06'
            //select sum (aportacion) from detalle where fecha between  '2018-07-14' and '2018-07-14' and tipo='positiva'
        String selection = Estructura_BBDD.NOMBRE_COLUMNA2 + " between '"+selectfechainicio+ "' and '"+ selectfechafinal+"'";


        // con que criterio vamos a ordenar el resultado de la busqueda
        //String sortOrder =
        // FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
    try {
        cursito = db.query(
            Estructura_BBDD.NOMBRE_TABLA,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null              // The sort order
        );

        cargarAdapter();


        }catch (Exception e){
            Toast toast = Toast.makeText(this, "no se encontraon registros", Toast.LENGTH_SHORT);
            toast.show();

        }
    }


    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = year + "-"+ twoDigits(month + 1)+"-"+twoDigits(day) ;
                editText.setText(selectedDate);
                if (editText.equals(etfechainicio)){
                    selectfechainicio=selectedDate;
                    }
                else {
                    selectfechafinal=selectedDate;
                    //BuscarAportaionSQL();

                }
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
        }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }


    public void cargarAdapter(){
        recyclerView=findViewById(R.id.lista_detalle);
        adaptador=new Adaptador(this, cursito);
        recyclerView.setAdapter(adaptador);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fecha_inicio:
                showDatePickerDialog(etfechainicio);
                break;

            case R.id.fecha_final:
                showDatePickerDialog(etfechafinal);
                break;
        }
    }
}
