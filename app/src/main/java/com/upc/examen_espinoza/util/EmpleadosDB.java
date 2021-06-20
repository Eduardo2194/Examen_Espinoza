package com.upc.examen_espinoza.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmpleadosDB extends SQLiteOpenHelper {

    public EmpleadosDB(Context context){
        super(context, Constantes.NOMBRE_BD,null,Constantes.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " +Constantes.NOMBRE_TABLA+
                        "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " dni TEXT NOT NULL, " +
                        " codigo TEXT NOT NULL, " +
                        " nombres TEXT NOT NULL, " +
                        " apellidos TEXT NOT NULL, " +
                        " correo TEXT NOT NULL, " +
                        " edad INTEGER NOT NULL, " +
                        " fecha_ingreso TEXT NOT NULL, " +
                        " genero TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
