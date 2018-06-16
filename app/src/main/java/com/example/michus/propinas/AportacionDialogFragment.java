package com.example.michus.propinas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AportacionDialogFragment extends AppCompatDialogFragment{

    private EditText meditText;
    private NoticeDialogListener listener;

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(float numero);
        public void onDialogNegativeClick(int numero);
    }



   @Override
   //onAttach es un método que participa en el ciclo de vida del fragment
   //y sirve para comunicarse con la activity mediante el context
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            //a traves del context vamos a acceder a la interfaz implementada en la activity(main activity)
            listener = (NoticeDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó NoticeDialogListener");

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        //Usamos la clase builder que pertence a la clase alertdialog para poder setear sus métodos
        //y dar vida a alertdialog
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        //usamos la clase layoutinflater para poder inflar el layout personalizado
        LayoutInflater inflater=getActivity().getLayoutInflater();
        //ya podemos setear la vista del layout
        View view=inflater.inflate(R.layout.layoutaportacion,null);
        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       float aportacion=Float.parseFloat(meditText.getText().toString());
                       //llamamos al método ondialogpositive de la activity
                       listener.onDialogPositiveClick(aportacion);
                    }
                })
                 .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                     public void onClick(DialogInterface dialogInterface, int i) {

                    }
                 });
        meditText=view.findViewById(R.id.aportacion);
        return builder.create();
    }
}
