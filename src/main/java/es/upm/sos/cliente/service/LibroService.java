package es.upm.sos.cliente.service;

import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;



import es.upm.sos.cliente.model.libros.PageLibro;
import es.upm.sos.cliente.model.ErrorMessage;
import es.upm.sos.cliente.model.libros.Libro;
import reactor.core.publisher.Mono;

public class LibroService {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/v1").build();
    private static final String HTTP_ERROR = "Error %d:\n %s\n";

    public String getLibro(int libroId) {
        StringBuilder sb = new StringBuilder();
        try {
            Libro libro = webClient.get()
                    .uri("/libros/" + libroId)
                    .exchangeToMono(response -> {
                        sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(Libro.class);
                        } else {
                            return response.bodyToMono(ErrorMessage.class)
                                    .flatMap(body -> Mono.error(new RuntimeException(
                                            String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                        }
                    })
                    .block(); // Usamos block() para obtener la respuesta de forma síncrona

            if (libro == null) {
                sb.append("Libro no encontrado\n");
            } else {
                sb.append(libro.toString());
            }
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String postLibro(Map<String, String> attrs) {
        StringBuilder sb = new StringBuilder();
        try {
            Libro libro = crearLibro(attrs);
            MediaType formato;
            if (attrs.containsKey("formato")) {
                if (attrs.get("formato").equals("xml")) {
                    throw new RuntimeException(String.format(HTTP_ERROR, 415, "Formato no soportado"));
                } else {
                    formato = MediaType.APPLICATION_JSON;
                }

            } else {
                formato = MediaType.APPLICATION_JSON;
            }
            String referencia = webClient.post()
                    .uri("/libros")
                    .contentType(formato)
                    .body(Mono.just(libro), Libro.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(ErrorMessage.class)
                            .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body.toString())))
                            .then(Mono.empty()))
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(ErrorMessage.class)
                            .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body.toString())))
                            .then(Mono.empty()))
                    .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                    .map(response -> {
                        sb.append("Código HTTP: ").append(response.getStatusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                        if (response.getHeaders().getLocation() != null) {
                            return response.getHeaders().getLocation().toString();
                        } else {
                            throw new RuntimeException(
                                    "No se recibió una URL en la cabecera Location");
                        }
                    })
                    .block();// Bloquea para obtener el resultado sincrónicamente
            if (referencia != null) {
                sb.append(referencia);
            }
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }

        return sb.toString();
    }

    public String putLibro(int libroId, Map<String, String> attrs) {
        StringBuilder sb = new StringBuilder();
        try {
            Libro libro = crearLibro(attrs);
            MediaType formato;
            if (attrs.containsKey("formato")) {
                if (attrs.get("formato").equals("xml")) {
                    throw new RuntimeException(String.format(HTTP_ERROR, 415, "Formato no soportado"));
                } else {
                    formato = MediaType.APPLICATION_JSON;
                }

            } else {
                formato = MediaType.APPLICATION_JSON;
            }
            webClient.put()
                    .uri("/libros/{id}", libroId)
                    .contentType(formato)
                    .body(Mono.just(libro), Libro.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(ErrorMessage.class)
                            .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body.toString())))
                            .then(Mono.empty()))
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(ErrorMessage.class)
                            .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body.toString())))
                            .then(Mono.empty()))
                    .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                    .doOnNext(response -> {
                        sb.append("Código HTTP: ").append(response.getStatusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                    })
                    .block(); // Bloquea hasta recibir la respuesta
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String deleteLibro(int libroId) {
        StringBuilder sb = new StringBuilder();
        try {
            webClient.delete()
                    .uri("/libros/{id}", libroId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> response
                            .bodyToMono(ErrorMessage.class)
                            .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body.toString())))
                            .then(Mono.empty()))
                    .onStatus(HttpStatusCode::is5xxServerError, response -> response
                            .bodyToMono(ErrorMessage.class)
                            .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body.toString())))
                            .then(Mono.empty()))
                    .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                    .doOnNext(response -> {
                        sb.append("Código HTTP: ").append(response.getStatusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                    })
                    .block();// Bloquea para obtener el resultado sincrónicamente
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String getLibros(int page, int size) {
        StringBuilder sb = new StringBuilder();
        try {
            PageLibro libros = webClient.get()
                    .uri("/libros?page={page}&size={size}", page, size)
                    .exchangeToMono(response -> {
                        sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(PageLibro.class);
                        } else {
                            return response.bodyToMono(ErrorMessage.class)
                                    .flatMap(body -> Mono.error(new RuntimeException(
                                            String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                        }
                    })
                    .block();

            if (libros == null) {
                sb.append("No se encontraron libros\n");
            } else {
                sb.append(libros.toString());
            }
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String getLibrosPorTitulo(String pattern, int page, int size) {
        StringBuilder sb = new StringBuilder();
        try {
            PageLibro libros = webClient.get()
                    .uri("/libros?pattern={pattern}&page={page}&size={size}", pattern, page, size)
                    .exchangeToMono(response -> {
                        sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(PageLibro.class);
                        } else {
                            return response.bodyToMono(ErrorMessage.class)
                                    .flatMap(body -> Mono.error(new RuntimeException(
                                            String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                        }
                    })
                    .block();

            if (libros == null) {
                sb.append("No se encontraron libros\n");
            } else {
                sb.append(libros.toString());
            }
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public String getLibrosDisponibles(int page, int size) {
        StringBuilder sb = new StringBuilder();
        try {
            PageLibro libros = webClient.get()
                    .uri("/libros?disponible=true&page={page}&size={size}", page, size)
                    .exchangeToMono(response -> {
                        sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                        sb.append("**********************************************************\n");
                        if (response.statusCode().is2xxSuccessful()) {
                            return response.bodyToMono(PageLibro.class);
                        } else {
                            return response.bodyToMono(ErrorMessage.class)
                                    .flatMap(body -> Mono.error(new RuntimeException(
                                            String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                        }
                    })
                    .block();

            if (libros == null) {
                sb.append("No se encontraron libros\n");
            } else {
                sb.append(libros.toString());
            }
        } catch (RuntimeException e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    private Libro crearLibro(Map<String, String> attrs) {
        Libro libro = new Libro();

        if (attrs.containsKey("titulo")) {
            libro.setTitulo(attrs.get("titulo"));
        }
        if (attrs.containsKey("autores")) {
            libro.setAutores(attrs.get("autores"));
        }
        if (attrs.containsKey("edicion")) {
            libro.setEdicion(attrs.get("edicion"));
        }
        if (attrs.containsKey("isbn")) {
            libro.setIsbn(attrs.get("isbn"));
        }
        if (attrs.containsKey("editorial")) {
            libro.setEditorial(attrs.get("editorial"));
        }
        if (attrs.containsKey("volumenes")) {
            libro.setVolumenes(Integer.parseInt(attrs.get("volumenes")));
        }
        if (attrs.containsKey("prestados")) {
            libro.setPrestados(Integer.parseInt(attrs.get("prestados")));
        }
        return libro;
    }

}