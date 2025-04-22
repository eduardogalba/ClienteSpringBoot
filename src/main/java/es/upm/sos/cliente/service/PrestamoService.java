package es.upm.sos.cliente.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import es.upm.sos.cliente.model.prestamos.PagePrestamo;
import es.upm.sos.cliente.model.prestamos.Prestamo;
import es.upm.sos.cliente.model.prestamos.PrestamoId;
import reactor.core.publisher.Mono;

public class PrestamoService {
        private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/v1").build();
        private static final String HTTP_ERROR = "Error %d:\n %s\n";

        public String getPrestamo(int usuarioId, int libroId) {
                StringBuilder sb = new StringBuilder();
                try {
                        Prestamo libro = webClient.get()
                                        .uri("/usuarios/{usuarioId}/libros/{libroId}", usuarioId, libroId)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .bodyToMono(Prestamo.class)
                                        .block(); // Usamos block() para obtener la respuesta de forma síncrona

                        if (libro == null) {
                                sb.append("Prestamo no encontrado\n");
                        } else {
                                sb.append(libro.toString());
                        }
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String postPrestamo(int usuarioId, int libroid) {
                StringBuilder sb = new StringBuilder();
                try {
                        PrestamoId prestamo = new PrestamoId();
                        prestamo.setUsuarioId(usuarioId);
                        prestamo.setLibroId(libroid);
                        String referencia = webClient.post()
                                        .uri("/usuarios/{usuarioId}/libros", usuarioId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .body(Mono.just(prestamo), PrestamoId.class)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                                        .map(response -> {
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

        public String putPrestamo(int usuarioId, int libroId) {
                StringBuilder sb = new StringBuilder();
                try {
                        webClient.put()
                                        .uri("/usuarios/{usuarioId}/libros/{id}", usuarioId, libroId)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                                        .block(); // Bloquea hasta recibir la respuesta
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String deletePrestamo(int usuarioId, int libroId) {
                StringBuilder sb = new StringBuilder();
                try {
                        webClient.delete()
                                        .uri("/usuarios/{usuarioId}/libros/{id}", usuarioId, libroId)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .toBodilessEntity() // Obtiene solo la respuesta HTTP sin cuerpo
                                        .block();// Bloquea para obtener el resultado sincrónicamente
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String getPrestamos(int usuarioId, int page, int size) {
                StringBuilder sb = new StringBuilder();
                try {
                        PagePrestamo libros = webClient.get()
                                        .uri("/usuarios/{usuarioId}/prestamos?page={page}&size={size}", usuarioId, page,
                                                        size)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .bodyToMono(PagePrestamo.class)
                                        .block();

                        sb.append(libros.toString());
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String getPrestamos(int usuarioId, String fechaPrestamo, int page, int size) {
                StringBuilder sb = new StringBuilder();
                try {
                        PagePrestamo libros = webClient.get()
                                        .uri("/usuarios/{usuarioId}/prestamos?fechaPrestamo={fecha}&page={page}&size={size}", fechaPrestamo, usuarioId, page,
                                                        size)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR, response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .bodyToMono(PagePrestamo.class)
                                        .block();

                        sb.append(libros.toString());
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }
}