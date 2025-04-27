package es.upm.sos.cliente.controller;

import es.upm.sos.cliente.service.UsuarioService;

import java.util.Map;

import es.upm.sos.cliente.service.LibroService;
import es.upm.sos.cliente.service.PrestamoService;

public class Controller {
    private UsuarioService usuarioService;
    private LibroService libroService;
    private PrestamoService prestamoService;

    public Controller() {
        usuarioService = new UsuarioService();
        libroService = new LibroService();
        prestamoService = new PrestamoService();
    }

    public String buscarUsuario(int usuarioId) {
        return usuarioService.getUsuario(usuarioId);
    }

    public String crearUsuario(Map<String, String> attrs) {
        return usuarioService.postUsuario(attrs);
    }

    public String modificarUsuario(int usuarioId, Map<String, String> attrs) {
        return usuarioService.putUsuario(usuarioId, attrs);
    }

    public String eliminarUsuario(int usuarioId) {
        return usuarioService.deleteUsuario(usuarioId);
    }

    public String listarUsuarios(int page, int size) {
        return usuarioService.getUsuarios(page, size);
    }

    public String buscarLibro(int libroId) {
        return libroService.getLibro(libroId);
    }

    public String crearLibro(Map<String, String> attrs) {
        return libroService.postLibro(attrs);
    }

    public String modificarLibro(int libroId, Map<String, String> attrs) {
        return libroService.putLibro(libroId, attrs);
    }

    public String eliminarLibro(int libroId) {
        return libroService.deleteLibro(libroId);
    }

    public String listarLibros(int page, int size) {
        return libroService.getLibros(page, size);
    }

    public String listarLibrosDisponibles(int page, int size) {
        return libroService.getLibrosDisponibles(page, size);
    }

    public String listarLibrosPorTitulo(String pattern, int page, int size) {
        return libroService.getLibrosPorTitulo(pattern, page, size);
    }

    public String buscarPrestamo(int usuarioId, int libroId) {
        return prestamoService.getPrestamo(usuarioId, libroId);
    }

    public String crearPrestamo(int usuarioId, int libroId) {
        return prestamoService.postPrestamo(usuarioId, libroId);
    }

    public String modificarPrestamo(int usuarioId, int libroId) {
        return prestamoService.putPrestamo(usuarioId, libroId);
    }

    public String eliminarPrestamo(int usuarioId, int libroId) {
        return prestamoService.deletePrestamo(usuarioId, libroId);
    }

    public String listarPrestamos(int usuarioId, int page, int size) {
        return prestamoService.getPrestamos(usuarioId, page, size);
    }

    public String listarPrestamos(int usuarioId, String start, String end, int page, int size) {
        return prestamoService.getPrestamos(usuarioId, start, end, page, size);
    }

    public String actividadUsuario(int usuarioId) {
        return usuarioService.getActividad(usuarioId);
    }

    public String historialPrestamos(int usuarioId, int page, int size) {
        return usuarioService.getHistorico(usuarioId, page, size);
    }

}
