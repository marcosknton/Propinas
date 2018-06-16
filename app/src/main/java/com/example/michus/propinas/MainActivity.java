package com.example.michus.propinas;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AportacionDialogFragment.NoticeDialogListener {


    float numero;
    String cantidad;
    private Button bsumar;
    private Button brestar;
    private TextView textView;
    List <Fragment> mylist= new ArrayList<>();
    SharedPreferences prefs;



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
               }
            else if (mylist.get(i).getTag().contentEquals("restaraportación")){
               numero=numero-number;
               }

            cantidad=Float.toString(numero);
            textView.setText(cantidad);
            guardarAportacion(cantidad);
        }



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

    public String recuperarAportacion(){
        cantidad = prefs.getString("cantidad", "0");
        return cantidad;
    }
}
