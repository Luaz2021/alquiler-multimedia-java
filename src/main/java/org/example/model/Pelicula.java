package org.example.model;

public class Pelicula extends AbstractArticulo {

    public Pelicula(String genero, String num_serie, String titulo) {
        super(genero, num_serie, titulo);
    }

    @Override
    public String getTipo() {
        return "PELICULA";
    }

}
