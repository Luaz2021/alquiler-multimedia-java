package org.example.model;

public abstract class AbstractArticulo {
    protected String num_serie;
    protected String titulo;
    protected String genero;

    public AbstractArticulo(String genero, String num_serie, String titulo) {
        this.genero = genero;
        this.num_serie = num_serie;
        this.titulo = titulo;
    }
    public String getNum_serie() { return num_serie; }
    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }

    public abstract String getTipo();
}
