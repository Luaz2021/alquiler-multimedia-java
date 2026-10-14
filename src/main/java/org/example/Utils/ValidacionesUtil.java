package org.example.Utils;

import java.util.Scanner;

public class ValidacionesUtil {

    // Método que valida números enteros del 0 al 10 (específico para menús)
    public int leerEnteroValidado(Scanner sc, String mensaje) {
        String regexRango0Al10 = "^([0-9]|10)$";

        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();

            if (entrada.matches(regexRango0Al10)) {
                return Integer.parseInt(entrada);
            } else {
                System.out.println("Error: Debes ingresar un número válido del 0 al 10.");
            }
        }
    }

    // Método que valida cualquier número entero positivo (edad, días de préstamo, IDs numéricos)
    public int leerEnteroPositivo(Scanner sc, String mensaje) {
        String regexNumero = "^[0-9]+$";

        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine().trim();

            if (entrada.matches(regexNumero) && !entrada.isEmpty()) {
                try {
                    return Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.println("Error: El número ingresado es demasiado grande.");
                }
            } else {
                System.out.println("Error: Debes ingresar solo números enteros positivos.");
            }
        }
    }

    // Validador de documento: admite DNI tradicional español y NIE (extranjeros)
    public String leerDniValidado(Scanner sc, String mensaje) {
        String documento;
        // DNI: 8 números + 1 letra (ej: 12345678A)
        // NIE: X, Y o Z + 7 números + 1 letra (ej: Z2131362A)
        String regexDniNie = "^([0-9]{8}[A-Za-z]|[XYZxyz][0-9]{7}[A-Za-z])$";

        while (true) {
            System.out.print(mensaje);
            documento = sc.nextLine().trim();

            if (documento.matches(regexDniNie)) {
                return documento.toUpperCase(); // Normaliza automáticamente a mayúscula
            } else {
                System.out.println("Formato inválido. Debe ser un DNI (ej: 12345678A) o un NIE (ej: Z1234567A).");
            }
        }
    }

    // Validador de texto para evitar campos vacíos o con solo espacios
    public String leerTextoNoVacio(Scanner sc, String mensaje) {
        String texto;

        while (true) {
            System.out.print(mensaje);
            texto = sc.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            } else {
                System.out.println("Error: Este campo no puede dejarse vacío ni contener solo espacios.");
            }
        }
    }
}