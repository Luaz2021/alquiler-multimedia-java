package org.example.model;

public class Docente extends AbstractUsuario {

    protected String asignatura;

    public Docente(String nombre, String dni, int edad, String asignatura) {
        super(nombre, dni, edad);
        this.asignatura = asignatura;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    @Override
    public String getTipo() {
        return "DOCENTE";
    }

}
