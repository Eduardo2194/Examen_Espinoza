package com.upc.examen_espinoza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.upc.examen_espinoza.entidades.Empleados;
import com.upc.examen_espinoza.modelo.DAOEmpleados;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText txtDNI, txtNombres, txtApellidos, txtCorreo, txtEdad, txtFechaIngreso;
    RadioButton rbHombre, rbMujer;
    Button btnGuardar;

    String dni, codigo, nombres, apellidos, correo, fecha_ingreso, genero, full_codigo;
    int edad, id;
    boolean registrar = true;

    DAOEmpleados daoEmpleado = new DAOEmpleados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDNI = findViewById(R.id.txtDNI);
        txtNombres = findViewById(R.id.txtNombres);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtEdad = findViewById(R.id.txtEdad);
        txtFechaIngreso = findViewById(R.id.txtFechaIngreso);
        rbHombre = findViewById(R.id.radioButtonM);
        rbMujer = findViewById(R.id.radioButtonF);
        btnGuardar = findViewById(R.id.btnGuardar);

        String data = getIntent().getStringExtra("data");
        if (data.equals("registro")) {
            setTitle("Registro de Empleados");
            btnGuardar.setText("Guardar datos");
        } else {
            setTitle("Actualización de Empleados");
            btnGuardar.setText("Actualizar datos");
        }

        txtFechaIngreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPickerFecha(txtFechaIngreso);
            }
        });

        generarCodigoEmpleado();
        loadCurrentDate();
        asignarReferencias();
        recibirDatos();
    }

    private void recibirDatos() {
        if (getIntent().hasExtra("id")) {
            registrar = false;
            id = Integer.parseInt(getIntent().getStringExtra("id"));
            dni = getIntent().getStringExtra("dni");
            nombres = getIntent().getStringExtra("nombres");
            apellidos = getIntent().getStringExtra("apellidos");
            correo = getIntent().getStringExtra("correo");
            edad = Integer.parseInt(getIntent().getStringExtra("edad"));
            fecha_ingreso = getIntent().getStringExtra("fecha_ingreso");
            genero = getIntent().getStringExtra("genero");

            txtDNI.setText(dni);
            txtNombres.setText(nombres);
            txtApellidos.setText(apellidos);
            txtCorreo.setText(correo);
            txtEdad.setText(edad + "");
            txtFechaIngreso.setText(fecha_ingreso);

            if (genero.equals("M")) {
                rbHombre.setChecked(true);
            } else {
                rbMujer.setChecked(true);
            }
        }
    }

    private void asignarReferencias() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    daoEmpleado.abridDB();
                    Empleados persona;
                    String mensaje;
                    if (registrar) {
                        persona = new Empleados(dni, full_codigo.toUpperCase(), nombres, apellidos, correo, edad, fecha_ingreso, genero);
                        mensaje = daoEmpleado.registrarEmpleado(persona);
                    } else {
                        persona = new Empleados(id, dni, codigo, nombres, apellidos, correo, edad, fecha_ingreso, genero);
                        mensaje = daoEmpleado.modificarEmpleado(persona);
                    }
                    AlertDialog.Builder ventana = new AlertDialog.Builder(MainActivity.this);
                    ventana.setTitle("Mensaje Informativo");
                    ventana.setMessage(mensaje);
                    ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            onBackPressed();
                        }
                    });
                    ventana.create().show();
                }else{

                }

            }
        });

    }

    private boolean validar() {
        boolean resultado = true;
        dni = txtDNI.getText().toString();
        nombres = txtNombres.getText().toString();
        apellidos = txtApellidos.getText().toString();
        correo = txtCorreo.getText().toString();
        fecha_ingreso = txtFechaIngreso.getText().toString();

        if (rbHombre.isChecked()) {
            genero = "M";
        } else if (rbMujer.isChecked()) {
            genero = "F";
        }

        if (dni.isEmpty()) {
            txtDNI.setError("DNI Obligatorio");
            resultado = false;
        }
        if (nombres.isEmpty()) {
            txtNombres.setError("Nombres Obligatorio");
            resultado = false;
        }
        if (apellidos.isEmpty()) {
            txtApellidos.setError("Apellidos Obligatorio");
            resultado = false;
        }
        if (correo.isEmpty()) {
            txtCorreo.setError("Correo Obligatorio");
            resultado = false;
        }
        if (txtEdad.getText().toString().isEmpty()){
            txtEdad.setError("Edad Obligatorio");
            resultado = false;
        }
        if(resultado) {
            edad = Integer.parseInt(txtEdad.getText().toString());
            String inicial_nombre = String.valueOf(txtNombres.getText().toString().charAt(0));
            String inicial_apellido = String.valueOf(txtApellidos.getText().toString().charAt(0));
            full_codigo = inicial_nombre + inicial_apellido + "-" + codigo;
            return resultado;
        }else{
            Toast.makeText(this,"Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void generarCodigoEmpleado() {
        ArrayList<Integer> codigoList = new ArrayList<>();
        for (int i = 10000; i < 99999; i++) {
            codigoList.add(i);
        }

        Collections.shuffle(codigoList);
        for (int i = 0; i < 1; i++) {
            codigo = String.valueOf(codigoList.get(i));
        }
    }

    private void abrirPickerFecha(EditText txtFechaIngreso) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                txtFechaIngreso.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(MainActivity.this, dateSetListener,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR))
                .show();
    }


    private void loadCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = simpleDateFormat.format(date);
        txtFechaIngreso.setText(currentDate);
    }
}
