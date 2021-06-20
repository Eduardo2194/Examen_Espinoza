package com.upc.examen_espinoza;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.upc.examen_espinoza.entidades.Empleados;
import com.upc.examen_espinoza.modelo.DAOEmpleados;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPersonalizado extends RecyclerView.Adapter<AdaptadorPersonalizado.MyViewHolder> {

    private Activity activity;
    private Context context;
    private List<Empleados> listEmpleados = new ArrayList<>();

    public AdaptadorPersonalizado(Context context, List<Empleados> listEmpleados){
        this.context = context;
        this.listEmpleados = listEmpleados;
    }

    @NonNull
    @Override
    public AdaptadorPersonalizado.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View vista = inflater.inflate(R.layout.lista, parent,false);
        return new MyViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPersonalizado.MyViewHolder holder, int position) {
        holder.fila_codigo.setText(listEmpleados.get(position).getCodigo());
        String nombre_apellidos = listEmpleados.get(position).getNombres() + " " + listEmpleados.get(position).getApellidos();
        holder.fila_nombres.setText(nombre_apellidos);
        holder.fila_correo.setText(listEmpleados.get(position).getCorreo());
        holder.fila_fecha.setText(listEmpleados.get(position).getFecha_ingreso());

        if (listEmpleados.get(position).getGenero().equals("M")) {
            holder.imgFoto.setImageResource(R.drawable.ic_hombre);
        } else if (listEmpleados.get(position).getGenero().equals("F")) {
            holder.imgFoto.setImageResource(R.drawable.ic_mujer);
        }

        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, listEmpleados.get(position).getNombres()+"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", String.valueOf(listEmpleados.get(position).getId()));
                intent.putExtra("dni", listEmpleados.get(position).getDni().toString());
                intent.putExtra("nombres", listEmpleados.get(position).getNombres().toString());
                intent.putExtra("apellidos", listEmpleados.get(position).getApellidos().toString());
                intent.putExtra("correo", listEmpleados.get(position).getCorreo().toString());
                intent.putExtra("edad", String.valueOf(listEmpleados.get(position).getEdad()));
                intent.putExtra("fecha_ingreso", listEmpleados.get(position).getFecha_ingreso().toString());
                intent.putExtra("genero", listEmpleados.get(position).getGenero().toString());
                intent.putExtra("data", "actualizar");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ventana = new AlertDialog.Builder(context);
                ventana.setTitle("Desea eliminar?");
                ventana.setMessage("Desea eliminar a la persona "+listEmpleados.get(position).getNombres());
                ventana.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DAOEmpleados daoPersona = new DAOEmpleados(context);
                        daoPersona.abridDB();
                        String mensaje = daoPersona.eliminarPersona(listEmpleados.get(position).getId());
                        AlertDialog.Builder v = new AlertDialog.Builder(context);
                        v.setTitle("Mensaje Informativo");
                        v.setMessage(mensaje);
                        v.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, InicioActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                activity = (Activity) holder.itemView.getContext();
                                activity.overridePendingTransition(0,0);

                            }
                        });
                        v.create().show();
                    }
                });
                ventana.setNegativeButton("NO",null);
                ventana.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listEmpleados.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fila_codigo, fila_nombres, fila_correo, fila_fecha;
        ImageView imgFoto, btnEditar, btnEliminar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imageView);
            fila_codigo = itemView.findViewById(R.id.fila_codigo);
            fila_nombres = itemView.findViewById(R.id.fila_nombres);
            fila_correo = itemView.findViewById(R.id.fila_correo);
            fila_fecha = itemView.findViewById(R.id.fila_fecha);
            btnEditar = itemView.findViewById(R.id.btn_editar);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar);

        }
    }
}
