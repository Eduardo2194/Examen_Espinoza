package com.upc.examen_espinoza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.upc.examen_espinoza.entidades.Empleados;
import com.upc.examen_espinoza.modelo.DAOEmpleados;

import java.util.ArrayList;
import java.util.List;

public class InicioActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton btnNuevo;

    DAOEmpleados daoPersona = new DAOEmpleados(this);
    List<Empleados> personaList = new ArrayList<>();
    AdaptadorPersonalizado adaptadorPersonalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        asignarReferencias();
        daoPersona.abridDB();
        mostrarEmpleado();
    }

    private void mostrarEmpleado(){
        personaList = daoPersona.listarPersonas();
        adaptadorPersonalizado = new AdaptadorPersonalizado(InicioActivity.this,personaList);
        recyclerView.setAdapter(adaptadorPersonalizado);
        recyclerView.setLayoutManager(new LinearLayoutManager(InicioActivity.this));
    }

    private void asignarReferencias() {
        recyclerView = findViewById(R.id.recyclerView);
        btnNuevo = findViewById(R.id.btnNuevo);
        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioActivity.this,MainActivity.class);
                intent.putExtra("data","registro");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mostrarEmpleado();
    }

}