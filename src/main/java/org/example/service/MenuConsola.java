package org.example.service;

import java.util.Scanner;
import org.example.Utils.ValidacionesUtil;

public class MenuConsola {

    private final Scanner sc = new Scanner(System.in);
    private final ValidacionesUtil val = new ValidacionesUtil();
    private final GestionAlquiler serv = new GestionAlquiler();

    public void mostrarMenuPrincipal() {
        int opcionPrincipal;
        do {
            System.out.println("\n***********************************");
            System.out.println("Bienvenido al sistema de préstamos");
            System.out.println("***********************************");
            System.out.println("¿Qué desea realizar el día de hoy?");
            System.out.println("1. Gestión de registro");
            System.out.println("2. Gestión de préstamos y devoluciones");
            System.out.println("3. Revisar datos");
            System.out.println("0. Salir");

            opcionPrincipal = val.leerEnteroValidado(sc, "Ingrese opción: ");

            switch (opcionPrincipal) {
                case 1:
                    mostrarMenuGestionRegistro();
                    break;
                case 2:
                    mostrarMenuGestionDevolucion();
                    break;
                case 3:
                    serv.revisarDatos();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }

        } while (opcionPrincipal != 0);
    }

    public void mostrarMenuGestionRegistro() {
        int opcionRegistro;
        do {
            System.out.println("\n--- GESTIÓN DE REGISTRO ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Modificar Usuario");
            System.out.println("3. Eliminar Usuario");
            System.out.println("4. Registrar Artículo");
            System.out.println("5. Modificar Artículo");
            System.out.println("6. Eliminar Artículo");
            System.out.println("0. Volver al menú principal");

            opcionRegistro = val.leerEnteroValidado(sc, "Ingrese opción: ");

            switch (opcionRegistro) {
                case 1:
                    serv.registrarUsuario();
                    break;
                case 2:
                    serv.modificarUsuario();
                    break;
                case 3:
                    serv.eliminarUsuario();
                    break;
                case 4:
                    serv.registrarArticulo();
                    break;
                case 5:
                    serv.modificarArticulo();
                    break;
                case 6:
                    serv.eliminarArticulo();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        } while (opcionRegistro != 0);
    }

    public void mostrarMenuGestionDevolucion() {
        int opcionDevolucion;
        do {
            System.out.println("\n--- GESTIÓN DE PRÉSTAMOS / DEVOLUCIONES ---");
            System.out.println("1. Prestar un Artículo");
            System.out.println("2. Devolver un Artículo");
            System.out.println("0. Volver al menú principal");

            opcionDevolucion = val.leerEnteroValidado(sc, "Ingrese opción: ");

            switch (opcionDevolucion) {
                case 1:
                    serv.prestarArticulo();
                    break;
                case 2:
                    serv.devolverArticulo();
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción incorrecta.");
                    break;
            }
        } while (opcionDevolucion != 0);
    }
}