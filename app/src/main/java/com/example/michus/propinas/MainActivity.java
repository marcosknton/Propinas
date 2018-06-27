package com.example.michus.propinas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AportacionDialogFragment.NoticeDialogListener {


    float numero;
    String cantidad;
    String tipo;
    private Button bsumar;
    private Button brestar;
    private TextView textView;
    List <Fragment> mylist= new ArrayList<>();
    SharedPreferences prefs;
    //instancia de acceso a la base de datos
    BBDD_helper helper=new BBDD_helper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);


        bsumar=(Button) findViewById(R.id.sumar);
        brestar=(Button) findViewById(R.id.restar);
        textView= (TextView) findViewById(R.id.propina);
        cantidad=recuperarAportacion();
        textView.setText(cantidad);
        numero=Float.parseFloat(cantidad);
        fechaActual();

        bsumar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialogoSumar();
            }
        });

        brestar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dialogoRestar();
            }
        });
    }

    /**
     * buscamos mediante el tag a que fragment hemos llamado(ya que un dialog hereda de fragment)
     * y guardamos el dato en las sharedpreferences
     * @param number
     */
    @Override
    public void onDialogPositiveClick(float number) {

        mylist=getSupportFragmentManager().getFragments();
        for (int i = 0; i < mylist.size(); ++i) {
           if (mylist.get(i).getTag().contentEquals("aportacion")){
               numero+=number;
               tipo="positiva";
               }
            else if (mylist.get(i).getTag().contentEquals("restaraportación")){
               numero=numero-number;
               tipo="negativa";
               }

            cantidad=Float.toString(numero);
            textView.setText(cantidad);
            guardarAportacion(cantidad);
            guardarAportacionSQL(Float.toString(number),tipo,cantidad);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.id_detalle)
        {
            Intent intent=new Intent(this,Buscador.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogNegativeClick(int number ) {

    }


     public void dialogoSumar(){
        new AportacionDialogFragment().show(getSupportFragmentManager(),"aportacion");
     }

    public void dialogoRestar(){
        new AportacionDialogFragment().show(getSupportFragmentManager(),"restaraportación");
    }

    public void guardarAportacion(String cantidad){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cantidad", cantidad);
        editor.commit();
    }

    public void guardarAportacionSQL(String aportacion,String tipo,String cantidad){
        // Gets the data repository in write mode
        SQLiteDatabase db = helper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(Estructura_BBDD.NOMBRE_COLUMNA1, 2);
        values.put(Estructura_BBDD.NOMBRE_COLUMNA2, fechaActual());
        values.put(Estructura_BBDD.NOMBRE_COLUMNA3,aportacion);
        values.put(Estructura_BBDD.NOMBRE_COLUMNA4,tipo);
        values.put(Estructura_BBDD.NOMBRE_COLUMNA5,cantidad);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(Estructura_BBDD.NOMBRE_TABLA, null, values);
    }

    public String recuperarAportacion(){
        cantidad = prefs.getString("cantidad", "0");
        return cantidad;
    }

    public String fechaActual(){

        Date hoy = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        String text = df.format(hoy);
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        return text;
    }
}
