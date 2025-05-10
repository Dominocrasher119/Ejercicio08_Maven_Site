package campus.grupo02;

import java.util.Scanner;

public class Utils {

    // Reutilizar un único Scanner para evitar cerrar System.in
    private static final Scanner scanner = new Scanner(System.in, "UTF-8");

    /**
     * Muestra un menú con título y opciones y retorna la opción seleccionada.
     * Valida que la opción esté dentro del rango válido.
     */
    public static int menu(String titulo, String[] opciones) {
        int opcion;
        do {
            System.out.println("\n" + "=".repeat(50));
            System.out.println(titulo);
            System.out.println("=".repeat(50));
            
            for (int i = 0; i < opciones.length; i++) {
                System.out.println((i + 1) + ". " + opciones[i]);
            }
            System.out.println("0. Salir");
            System.out.println("-".repeat(50));
            System.out.print("Seleccione una opción: ");
            
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número válido.");
                scanner.next(); // Limpiar entrada no válida
            }
            
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            
            if (opcion < 0 || opcion > opciones.length) {
                System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
            
        } while (opcion < 0 || opcion > opciones.length);
        
        return opcion;
    }

    /**
     * Muestra un submenú con título y opciones y retorna la opción seleccionada.
     */
    public static int submenu(String titulo, String[] opciones) {
        return menu("  ► " + titulo, opciones);
    }

    /**
     * Solicita un entero con límites mínimo y máximo
     */
    public static int askInteger(String label, int min, int max) {
        int result;
        do {
            System.out.print(label + " (" + min + "-" + max + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Por favor, introduce un número entero válido.");
                scanner.next(); // Limpiar entrada no válida
            }
            result = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            
            if (result < min || result > max) {
                System.out.println("El valor debe estar entre " + min + " y " + max);
            }
        } while (result < min || result > max);
        
        return result;
    }

    public static int askInteger(String label) {
        System.out.print(label + ": ");
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, introduce un número entero válido.");
            scanner.next(); // Limpiar entrada no válida
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer
        return result;
    }

    public static String askText(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine();
    }

    /**
     * Solicita confirmación (S/N)
     */
    public static boolean confirm(String message) {
        System.out.print(message + " (S/N): ");
        String response = scanner.nextLine().trim().toUpperCase();
        while (!response.equals("S") && !response.equals("N")) {
            System.out.print("Por favor, introduce S o N: ");
            response = scanner.nextLine().trim().toUpperCase();
        }
        return response.equals("S");
    }

    /**
     * Pausa la ejecución hasta que el usuario pulse Enter
     */
    public static void pause() {
        System.out.print("\nPulse Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Limpia la pantalla (conceptual, no funciona en todas las terminales)
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
