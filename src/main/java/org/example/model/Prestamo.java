package org.example.model;

import java.time.LocalDate;

public class Prestamo {
    protected int id_prestamo;
    protected String usuario_dni;
    protected String articulo_num_serie;
    protected LocalDate fecha_prestamo;
    protected LocalDate fecha_devolucion_esperada;
    protected LocalDate fecha_devolucion_real;

    public Prestamo(String articulo_num_serie, LocalDate fecha_devolucion_esperada, LocalDate fecha_devolucion_real,
                    LocalDate fecha_prestamo, int id_prestamo, String usuario_dni) {
        this.articulo_num_serie = articulo_num_serie;
        this.fecha_devolucion_esperada = fecha_devolucion_esperada;
        this.fecha_devolucion_real = fecha_devolucion_real;
        this.fecha_prestamo = fecha_prestamo;
        this.id_prestamo = id_prestamo;
        this.usuario_dni = usuario_dni;
    }

    public int getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(int id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public String getUsuario_dni() {
        return usuario_dni;
    }

    public void setUsuario_dni(String usuario_dni) {
        this.usuario_dni = usuario_dni;
    }

    public String getArticulo_num_serie() {
        return articulo_num_serie;
    }

    public void setArticulo_num_serie(String articulo_num_serie) {
        this.articulo_num_serie = articulo_num_serie;
    }

    public LocalDate getFecha_prestamo() {
        return fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDate getFecha_devolucion_esperada() {
        return fecha_devolucion_esperada;
    }

    public void setFecha_devolucion_esperada(LocalDate fecha_devolucion_esperada) {
        this.fecha_devolucion_esperada = fecha_devolucion_esperada;
    }

    public LocalDate getFecha_devolucion_real() {
        return fecha_devolucion_real;
    }

    public void setFecha_devolucion_real(LocalDate fecha_devolucion_real) {
        this.fecha_devolucion_real = fecha_devolucion_real;
    }
}