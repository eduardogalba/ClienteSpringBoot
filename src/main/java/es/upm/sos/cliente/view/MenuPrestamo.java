package es.upm.sos.cliente.view;

import java.util.Scanner;

import es.upm.sos.cliente.controller.Controller;

public class MenuPrestamo {
    private Scanner scanner;
    private Controller controller;

    public MenuPrestamo(Scanner scanner) {
        this.scanner = scanner;
    }

    public void desplegarMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("\t1. Buscar prestamo");
        System.out.println("\t2. Crear prestamo");
        System.out.println("\t3. Modificar prestamo");
        System.out.println("\t4. Eliminar prestamo");
        System.out.println("\t5. Listar prestamos");
        System.out.println("Ctrl + C para salir\t r Retroceder");

        String opcion = scanner.nextLine();
        if (opcion.equalsIgnoreCase("r")) {
            System.out.println("Regresando al menú principal...");
            return;
        }
        System.out.println("Seleccionaste: " + opcion);

        switch (opcion.toLowerCase()) {
            case "1":
                buscarPrestamo();
                break;
            case "2":
                crearPrestamo();
                break;
            case "3":
                modificarPrestamo();
                break;
            case "4":
                eliminarPrestamo();
                break;
            case "5":
                listarPrestamos();
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }

        desplegarMenu();
    }

    private void crearPrestamo() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el ID del libro: ");
        int libroId = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.crearPrestamo(usuarioId, libroId));
    }

    private void modificarPrestamo() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el ID del libro: ");
        int libroId = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.modificarPrestamo(usuarioId, libroId));
    }

    private void eliminarPrestamo() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el ID del libro: ");
        int libroId = Integer.parseInt(scanner.nextLine());
        controller.eliminarPrestamo(usuarioId, libroId);
    }

    private void listarPrestamos() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce la página: ");
        int page = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el tamaño de la página: ");
        int size = Integer.parseInt(scanner.nextLine());

        System.out.print("¿Desea filtrar por fecha? (s/n)");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Introduce la fecha (yyyy-MM-dd): ");
            String fecha = scanner.nextLine();
            System.out.print(controller.listarPrestamos(usuarioId, fecha, page, size));
        } else {
            System.out.print(controller.listarPrestamos(usuarioId, page, size));
        }
    }

    private void buscarPrestamo() {
        System.out.print("Introduce el ID del usuario: ");
        int usuarioId = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el ID del libro: ");
        int libroId = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.buscarPrestamo(usuarioId, libroId));
    }


    public void setController(Controller controller) {
        this.controller = controller;
    }
}
