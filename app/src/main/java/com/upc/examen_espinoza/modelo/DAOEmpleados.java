package com.upc.examen_espinoza.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.upc.examen_espinoza.entidades.Empleados;
import com.upc.examen_espinoza.util.Constantes;
import com.upc.examen_espinoza.util.EmpleadosDB;

import java.util.ArrayList;
import java.util.List;

public class DAOEmpleados {
    EmpleadosDB personaDB;
    SQLiteDatabase db;
    private Context context;

    public DAOEmpleados(Context context){
        this.context = context;
        this.personaDB = new EmpleadosDB(context);
    }

    public void abridDB(){
        db = personaDB.getWritableDatabase();
    }

    public String registrarEmpleado(Empleados persona){
        String mensaje = "";
        try{
            ContentValues valores = new ContentValues();
            valores.put("dni", persona.getDni());
            valores.put("codigo", persona.getCodigo());
            valores.put("nombres", persona.getNombres());
            valores.put("apellidos", persona.getApellidos());
            valores.put("correo", persona.getCorreo());
            valores.put("edad", persona.getEdad());
            valores.put("fecha_ingreso", persona.getFecha_ingreso());
            valores.put("genero", persona.getGenero());
            long resultado =db.insert(Constantes.NOMBRE_TABLA, null, valores);
            if(resultado == -1){
                mensaje = "Error al insertar";
            }else{
                mensaje = "Se registro correctamente";
            }
        }catch (Exception e){
            Log.d("=>", e.getMessage());
        }
        return mensaje;
    }

    public List<Empleados> listarPersonas(){
        List<Empleados> personaList = new ArrayList<>();
        try {
            Cursor c = db.rawQuery("SELECT * FROM " +Constantes.NOMBRE_TABLA,null);
            while (c.moveToNext()){
                personaList.add(new Empleados(c.getInt(0), c.getString(1), c.getString(2),
                        c.getString(3), c.getString(4), c.getString(5), c.getInt(6),
                        c.getString(7), c.getString(8)));
            }
        }catch (Exception e){
            Log.d("=>", e.getMessage());
        }
        return personaList;
    }

    public String modificarEmpleado(Empleados persona){
        String mensaje = "";
        try{
            ContentValues valores = new ContentValues();
            valores.put("dni", persona.getDni());
            valores.put("nombres", persona.getNombres());
            valores.put("apellidos", persona.getApellidos());
            valores.put("correo", persona.getCorreo());
            valores.put("edad", persona.getEdad());
            valores.put("fecha_ingreso", persona.getFecha_ingreso());
            valores.put("genero", persona.getGenero());
            long resultado =db.update(Constantes.NOMBRE_TABLA, valores, "id="+persona.getId(),null);
            if(resultado == -1){
                mensaje = "Error al update";
            }else{
                mensaje = "Se actualizó correctamente";
            }
        }catch (Exception e){
            Log.d("=>", e.getMessage());
        }
        return mensaje;
    }

    public String eliminarPersona(int id){
        String mensaje = "";
        try {
            long resultado = db.delete(Constantes.NOMBRE_TABLA, "id="+id,null);
            if(resultado == -1){
                mensaje = "Error al delete";
            }else{
                mensaje = "Se elimino correctamente";
            }
        }catch (Exception e){
            Log.d("=>", e.getMessage());
        }
        return mensaje;
    }

}
