package es.upm.sos.cliente.view;

import java.util.NoSuchElementException;
import java.util.Scanner;

import es.upm.sos.cliente.controller.Controller;

public class View {
    private Scanner scanner;
    private MenuUsuario menuUsuario;
    private MenuLibro menuLibro;
    private MenuPrestamo menuPrestamo;

    public View() {
        this.scanner = new Scanner(System.in);
        this.menuUsuario = new MenuUsuario(scanner);
        this.menuLibro = new MenuLibro(scanner);
        this.menuPrestamo = new MenuPrestamo(scanner);
    }

    public void desplegarMenuPrincipal() {
        System.out.println("Bienvenido al Sistema de Gestión de Préstamos.");

        try {
            while (true) {
                System.out.println("¿Qué desea consultar?");
                System.out.println("a. Usuarios");
                System.out.println("b. Libros");
                System.out.println("c. Préstamos");
                System.out.println("Ctrl + C para salir");
                
                if (!scanner.hasNextLine()) {
                    throw new NoSuchElementException();
                }

                String opcion = scanner.nextLine();
                System.out.println("Seleccionaste: " + opcion);
                switch (opcion.toLowerCase()) {
                    case "a":
                        menuUsuario.desplegarMenu();
                        break;
                    case "b":
                        menuLibro.desplegarMenu();
                        break;
                    case "c":
                        menuPrestamo.desplegarMenu();
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Fin del programa. ¡Hasta luego!");
        } finally {
            scanner.close();
        }
    }

    public void setController(Controller controller) {
        this.menuUsuario.setController(controller);
        this.menuLibro.setController(controller);
        this.menuPrestamo.setController(controller);
    }
}
