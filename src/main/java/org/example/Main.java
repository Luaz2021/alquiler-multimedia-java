package org.example;

import org.example.service.ServidorWeb;
import org.example.service.MenuConsola;

public class Main {
    public static void main(String[] args) {
        // 1. Levanta el servidor HTTP nativo en segundo plano
        ServidorWeb servidor = new ServidorWeb();
        servidor.iniciarServidor(8080);

        // 2. Continúa con tu menú normal de consola
        MenuConsola app = new MenuConsola();
        app.mostrarMenuPrincipal();
    }
}