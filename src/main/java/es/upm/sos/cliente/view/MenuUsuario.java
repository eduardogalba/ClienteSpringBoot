package es.upm.sos.cliente.view;

import es.upm.sos.cliente.controller.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MenuUsuario {
    private Scanner scanner;
    private Controller controller;

    public MenuUsuario(Scanner scanner) {
        this.scanner = scanner;
    }

    public void desplegarMenu() {
        System.out.println("\nSeleccione una opción:");
        System.out.println("\t1. Buscar usuario");
        System.out.println("\t2. Crear usuario");
        System.out.println("\t3. Modificar usuario");
        System.out.println("\t4. Eliminar usuario");
        System.out.println("\t5. Listar usuarios");
        System.out.println("\t6. Actividad de usuario");
        System.out.println("\t7. Historico de usuario");
        System.out.println("Ctrl + C para salir\t r Retroceder");

        if (!scanner.hasNextLine()) {
            throw new NoSuchElementException();
        }

        String opcion = scanner.nextLine();
        if (opcion.equalsIgnoreCase("r")) {
            System.out.println("Regresando al menú principal...");
            return;
        }
        System.out.println("Seleccionaste: " + opcion);
        
        switch (opcion.toLowerCase()) {
            case "1":
                buscarUsuario();
                break;
            case "2":
                crearUsuario();
                break;
            case "3":
                modificarUsuario();
                break;
            case "4":
                eliminarUsuario();
                break;
            case "5":
                listarUsuarios();
                break;
            case "6":
                obtenerActividad();
                break;
            case "7":
                obtenerHistorico();
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }

        desplegarMenu();
    }

    private Map<String, String> obtenerDatosUsuario () {
        Map<String, String> attrs = new HashMap<>();
        System.out.print("Nombre completo: ");
        attrs.put("nombre", scanner.nextLine());
        System.out.print("Matrícula: ");
        attrs.put("matricula", scanner.nextLine());
        System.out.print("Fecha Nacimiento (yyyy-MM-dd): ");
        attrs.put("nacimiento", scanner.nextLine());
        System.out.print("Correo electrónico: ");
        attrs.put("correo", scanner.nextLine());
        return attrs;
    }

    private void crearUsuario() {
        Map<String, String> attrs = obtenerDatosUsuario();
        attrs.put("formato", promptFormato());
        System.out.print(controller.crearUsuario(attrs));
    }

    private void modificarUsuario () {
        System.out.print("Introduce el ID del usuario: ");
        int id = Integer.parseInt(scanner.nextLine());
        Map<String, String> attrs = obtenerDatosUsuario();
        attrs.put("formato", promptFormato());
        System.out.print(controller.modificarUsuario(id, attrs));
    }

    private void eliminarUsuario() {
        System.out.print("Introduce el ID del usuario: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.eliminarUsuario(id));
    }

    private void listarUsuarios () {
        System.out.print("Introduce la página: ");
        int page = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el tamaño de la página: ");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.listarUsuarios(page, size));
    }

    private void buscarUsuario() {
        System.out.print("Introduce el ID del usuario: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.buscarUsuario(id));
    }

    private void obtenerActividad() {
        System.out.print("Introduce el ID del usuario: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.actividadUsuario(id));
    }

    private void obtenerHistorico() {
        System.out.print("Introduce el ID del usuario: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce la página: ");
        int page = Integer.parseInt(scanner.nextLine());
        System.out.print("Introduce el tamaño de la página: ");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.print(controller.historialPrestamos(id, page, size));
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
