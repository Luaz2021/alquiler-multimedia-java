package org.example.model;

public abstract class AbstractUsuario {
    protected String nombre;
    protected String dni;
    protected int edad;

    public AbstractUsuario(String nombre, String dni, int edad) {
        this.nombre = nombre;
        this.dni = dni;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public int getEdad() { return edad; }

    public void setEdad(int edad) { this.edad = edad;}

    public abstract String getTipo();

}
