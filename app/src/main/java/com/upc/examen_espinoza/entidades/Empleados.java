package com.upc.examen_espinoza.entidades;

public class Empleados {
    private int id;
    private String dni;
    private String codigo;
    private String nombres;
    private String apellidos;
    private String correo;
    private int edad;
    private String fecha_ingreso;
    private String genero;

    public Empleados(String dni, String codigo, String nombres, String apellidos, String correo, int edad, String fecha_ingreso, String genero) {
        this.dni = dni;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.edad = edad;
        this.fecha_ingreso = fecha_ingreso;
        this.genero = genero;
    }

    public Empleados(int id, String dni, String codigo, String nombres, String apellidos, String correo, int edad, String fecha_ingreso, String genero) {
        this.id = id;
        this.dni = dni;
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.edad = edad;
        this.fecha_ingreso = fecha_ingreso;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
