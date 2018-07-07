package com.example.michus.propinas;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class Buscador extends AppCompatActivity implements View.OnClickListener {

    public EditText etfechainicio;
    public EditText etfechafinal;
    BBDD_helper helper = new BBDD_helper(this);
    String selectfechainicio = "";
    String selectfechafinal = "";

    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    public Adaptador adaptador;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscador);

        etfechainicio = findViewById(R.id.fecha_inicio);
        etfechainicio.setOnClickListener(this);
        etfechafinal = findViewById(R.id.fecha_final);
        etfechafinal.setOnClickListener(this);

        //recyclerView=findViewById(R.id.lista_detalle);
        //adaptador=new Adaptador(this,cursor);
        //recyclerView.setAdapter(adaptador);
        //layoutManager=new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

       // getSupportLoaderManager().restartLoader(1,null, (LoaderManager.LoaderCallbacks<Object>) this);

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
        String selection = Estructura_BBDD.NOMBRE_COLUMNA2 + " between '"+selectfechainicio+ "' and '"+ selectfechafinal+"'";


        // con que criterio vamos a ordenar el resultado de la busqueda
        //String sortOrder =
        // FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";
    try {
        cursor = db.query(
            Estructura_BBDD.NOMBRE_TABLA,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null              // The sort order
        );
       //reccorrer(cursor);
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
                final String selectedDate = year + "-" + twoDigits(day) + "-" + twoDigits(month + 1);
                editText.setText(selectedDate);
                if (editText.equals(etfechainicio)){
                    selectfechainicio=selectedDate;
                    }
                else {
                    selectfechafinal=selectedDate;
                    BuscarAportaionSQL();

                }
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
        }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }
    public void reccorrer(Cursor cursor){
        //Nos aseguramos de que existe al menos un registro
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String codigo= cursor.getString(0);
                String nombre = cursor.getString(1);
                Toast toast = Toast.makeText(this, codigo+" "+nombre, Toast.LENGTH_SHORT);
                toast.show();
            } while(cursor.moveToNext());
        }

    }

    public void cargarAdapter(){
        recyclerView=findViewById(R.id.lista_detalle);
        adaptador=new Adaptador(this,cursor);
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
