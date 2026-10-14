package org.example.model;

public class VideoJuego extends AbstractArticulo {

    protected String plataforma;

    public VideoJuego(String genero, String num_serie, String titulo, String plataforma) {
        super(genero, num_serie, titulo);
        this.plataforma = plataforma;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    @Override
    public String getTipo() {
        return "VIDEOJUEGO";
    }

}