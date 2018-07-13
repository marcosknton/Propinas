package com.example.michus.propinas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import junit.framework.TestCase;

public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView fecha_registro;
    public TextView cantidad_registro;
    public TextView tipo_registro;
    public TextView total_registro;


    public ViewHolder(View itemView) {
        super(itemView);
        fecha_registro=(TextView)itemView.findViewById(R.id.fecha_regitro_relative);
        cantidad_registro=(TextView)itemView.findViewById(R.id.cantidad_registro_relative);
        tipo_registro=(TextView)itemView.findViewById(R.id.tipo_registro_relative);
        total_registro=(TextView)itemView.findViewById(R.id.total_registro_relative);

    }
}
