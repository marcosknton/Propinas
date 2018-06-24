package com.example.michus.propinas;

import android.provider.BaseColumns;

/**
 * clase con métodos de creación de las tablas de nuestra base de datos
 */
public class Estructura_BBDD {

    // Sentencia de creación de la tabla
    // make the constructor private.
    private Estructura_BBDD() {}


    public static final String NOMBRE_TABLA = "Detalle";
    public static final String NOMBRE_COLUMNA1 = "Id";
    public static final String NOMBRE_COLUMNA2 = "Fecha";
    public static final String NOMBRE_COLUMNA3 = "Aportacion";


    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.NOMBRE_TABLA + " (" +
                    Estructura_BBDD.NOMBRE_COLUMNA1 + " INTEGER PRIMARY KEY," +
                    Estructura_BBDD.NOMBRE_COLUMNA2 + " TEXT," +
                    Estructura_BBDD.NOMBRE_COLUMNA3 + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.NOMBRE_TABLA;
}
