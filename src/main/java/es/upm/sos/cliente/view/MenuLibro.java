package es.upm.sos.cliente.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import es.upm.sos.cliente.controller.Controller;

public class MenuLibro {
    private Scanner scanner;
    private Controller controller;

    public MenuLibro(Scanner scanner) {
        this.scanner = scanner;
    }

    public void desplegarMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("\t1. Buscar libro");
        System.out.println("\t2. Crear libro");
        System.out.println("\t3. Modificar libro");
        System.out.println("\t4. Eliminar libro");
        System.out.println("\t5. Listar libros");
        System.out.println("Ctrl + C para salir\t r Retroceder");

        String opcion = scanner.nextLine();
        if (opcion.equalsIgnoreCase("r")) {
            System.out.println("Regresando al menú principal...");
            return;
        }
        System.out.println("Seleccionaste: " + opcion);

        switch (opcion.toLowerCase()) {
            case "1":
                buscarLibro();
                break;
            case "2":
                crearLibro();
                break;
            case "3":
                modificarLibro();
                break;
            case "4":
                eliminarLibro();
                break;
            case "5":
                listarLibros();
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }

        desplegarMenu();
    }

    private Map<String, String> obtenerDatosLibro() {
        Map<String, String> attrs = new HashMap<>();
        System.out.print("Título: ");
        attrs.put("titulo", scanner.nextLine());
        System.out.print("Autores: ");
        attrs.put("autores", scanner.nextLine());
        System.out.print("Edición: ");
        attrs.put("edicion", scanner.nextLine());
        System.out.print("ISBN: ");
        attrs.put("isbn", scanner.nextLine());
        System.out.print("Editorial: ");
        attrs.put("editorial", scanner.nextLine());
        System.out.print("Volumenes: ");
        attrs.put("volumenes", scanner.nextLine());
        System.out.print("Prestados: ");
        attrs.put("prestados", scanner.nextLine());

        return attrs;
    }

    private void crearLibro() {
        Map<String, String> attrs = obtenerDatosLibro();
        attrs.put("formato", promptFormato());
        System.out.print(controller.crearLibro(attrs));
    }

    private void modificarLibro () {
        System.out.print("Introduce el ID del libro: ");
        int id = Integer.parseInt(scanner.nextLine());
        Map<String, String> attrs = obtenerDatosLibro();
        attrs.put("formato", promptFormato());
        System.out.print(controller.modificarLibro(id, attrs));
    }

    private void eliminarLibro() {
        System.out.print("Introduce el ID del libro: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.eliminarLibro(id));
    }

    private void listarLibros () {
        System.out.print("Introduce la página: ");
        int page = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el tamaño de la página: ");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.print("¿Desea buscar algún título concreto? (s/n): ");
        String respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.print("Introduce alguna frase o palabra contenida en el título: ");
            String pattern = scanner.nextLine();
            System.out.print(controller.listarLibrosPorTitulo(pattern, page, size));
        } else {
            System.out.print("¿Desea buscar aquellos libros disponibles? (s/n): ");
            respuesta = scanner.nextLine();
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print(controller.listarLibrosDisponibles(page, size));
            } else {
                System.out.print(controller.listarLibros(page, size));
            }
        }    
    }

    private void buscarLibro() {
        System.out.print("Introduce el ID del libro: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.buscarLibro(id));
    }

    private String promptFormato() {
        System.out.println("Formato del cuerpo de la petición: ");
        System.out.println("1. JSON");
        System.out.println("2. XML");
        String formato = scanner.nextLine();
        System.out.println("Seleccionaste: " + formato);
        return formato.equals("2") ? "xml" : "json";
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
