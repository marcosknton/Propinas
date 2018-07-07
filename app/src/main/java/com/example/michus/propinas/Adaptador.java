package com.example.michus.propinas;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class Adaptador extends RecyclerView.Adapter {

    protected Context context;
    protected Cursor cursor;
    protected LayoutInflater inflater;

    public Adaptador(Context context, Cursor cursor) {
        this.context=context;
        this.cursor=cursor;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.registro_aportacion,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
           cursor.moveToPosition(position);
           personalizarVista((ViewHolder) holder,cursor);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void personalizarVista(ViewHolder holder,Cursor cursor) {
        holder.fecha_registro.setText(cursor.getString(0));
        holder.cantidad_registro.setText(cursor.getString(1));
        holder.tipo_registro.setText(cursor.getString(2));
        holder.total_registro.setText(cursor.getString(3));

    }
    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            cursor = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return cursor;
    }

}
