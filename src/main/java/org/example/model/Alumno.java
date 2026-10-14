package org.example.model;

public class Alumno extends AbstractUsuario {

    public Alumno(String nombre, String dni, int edad) {
        super(nombre, dni, edad);
    }

    @Override
    public String getTipo() {
        return "ALUMNO";
    }

    // Alumno(nombre,dni,edad,tipo="ALUMO")
    // Docente(nombre,dni,edad,asignatura,tipo="DOCENTE")

}