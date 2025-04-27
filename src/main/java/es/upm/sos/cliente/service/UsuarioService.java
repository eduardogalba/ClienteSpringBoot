package es.upm.sos.cliente.service;

import java.util.Map;

import java.sql.Date;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import es.upm.sos.cliente.model.ErrorMessage;

import es.upm.sos.cliente.model.historico.PageHistorico;
import es.upm.sos.cliente.model.usuarios.PageUsuario;
import es.upm.sos.cliente.model.usuarios.Usuario;
import reactor.core.publisher.Mono;

public class UsuarioService {

        private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080/api/v1").build();
        private static final String HTTP_ERROR = "Error %d:\n %s\n";

        public String getUsuario(int usuarioId) {
                StringBuilder sb = new StringBuilder();
                try {
                        Usuario usuario = webClient.get()
                                        .uri("/usuarios/" + usuarioId)
                                        .exchangeToMono(response -> {
                                                sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                                                sb.append("**********************************************************\n");
                                                if (response.statusCode().is2xxSuccessful()) {
                                                    return response.bodyToMono(Usuario.class);
                                                } else {
                                                    return response.bodyToMono(ErrorMessage.class)
                                                            .flatMap(body -> Mono.error(new RuntimeException(
                                                                    String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                                                }
                                            })
                                        .block(); // Usamos block() para obtener la respuesta de forma síncrona

                        if (usuario == null) {
                                sb.append("Usuario no encontrado\n");
                        } else {
                                sb.append(usuario.toString());
                        }
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String postUsuario(Map<String, String> attrs) {
                StringBuilder sb = new StringBuilder();

                try {
                        Usuario usuario = crearUsuario(attrs);
                        MediaType formato;
                        if (attrs.containsKey("formato")) {
                                if (attrs.get("formato").equals("xml")) {
                                        throw new RuntimeException(
                                                        String.format(HTTP_ERROR, 415, "Formato no soportado"));
                                } else {
                                        formato = MediaType.APPLICATION_JSON;
                                }

                        } else {
                                formato = MediaType.APPLICATION_JSON;
                        }

                        String referencia = webClient.post()
                                        .uri("/usuarios")
                                        .contentType(formato)
                                        .body(Mono.just(usuario), Usuario.class)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR,
                                                                        response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR,
                                                                        response.statusCode().value(), body)))
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

        public String putUsuario(int usuarioId, Map<String, String> attrs) {
                StringBuilder sb = new StringBuilder();
                try {
                        Usuario usuario = crearUsuario(attrs);
                        MediaType formato;
                        if (attrs.containsKey("formato")) {
                                if (attrs.get("formato").equals("xml")) {
                                        throw new RuntimeException(
                                                        String.format(HTTP_ERROR, 415, "Formato no soportado"));
                                } else {
                                        formato = MediaType.APPLICATION_JSON;
                                }

                        } else {
                                formato = MediaType.APPLICATION_JSON;
                        }
                        webClient.put()
                                        .uri("/usuarios/{id}", usuarioId)
                                        .contentType(formato)
                                        .body(Mono.just(usuario), Usuario.class)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR,
                                                                        response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR,
                                                                        response.statusCode().value(), body)))
                                                        .then(Mono.empty()))
                                        .toBodilessEntity()
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

        public String deleteUsuario(int usuarioId) {
                StringBuilder sb = new StringBuilder();
                try {
                        webClient.delete()
                                        .uri("/usuarios/{id}", usuarioId)
                                        .retrieve()
                                        .onStatus(HttpStatusCode::is4xxClientError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR,
                                                                        response.statusCode().value(), body)))
                                                        .then(Mono.empty()) // Propagate the error
                                        )
                                        .onStatus(HttpStatusCode::is5xxServerError, response -> response
                                                        .bodyToMono(String.class)
                                                        .doOnNext(body -> sb.append(String.format(HTTP_ERROR,
                                                                        response.statusCode().value(), body)))
                                                        .then(Mono.empty()) // Propagate the error
                                        )
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

        public String getUsuarios(int page, int size) {
                StringBuilder sb = new StringBuilder();
                try {
                        PageUsuario usuarios = webClient.get()
                                        .uri("/usuarios?page={page}&size={size}", page, size)
                                        .exchangeToMono(response -> {
                                                sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                                                sb.append("**********************************************************\n");
                                                if (response.statusCode().is2xxSuccessful()) {
                                                    return response.bodyToMono(PageUsuario.class);
                                                } else {
                                                    return response.bodyToMono(ErrorMessage.class)
                                                            .flatMap(body -> Mono.error(new RuntimeException(
                                                                    String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                                                }
                                            })
                                        .block();

                        sb.append(usuarios.toString());
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String getActividad(int usuarioId) {
                StringBuilder sb = new StringBuilder();
                try {
                        Usuario actividad = webClient.get()
                                        .uri("/usuarios/{usuarioId}/actividad", usuarioId)
                                        .exchangeToMono(response -> {
                                                sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                                                sb.append("**********************************************************\n");
                                                if (response.statusCode().is2xxSuccessful()) {
                                                    return response.bodyToMono(Usuario.class);
                                                } else {
                                                    return response.bodyToMono(ErrorMessage.class)
                                                            .flatMap(body -> Mono.error(new RuntimeException(
                                                                    String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                                                }
                                            })
                                        .block();

                        if (actividad == null) {
                                sb.append("Usuario no encontrado\n");
                        } else {
                                sb.append(actividad.toString());
                        }
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        public String getHistorico(int usuarioId, int page, int size) {
                StringBuilder sb = new StringBuilder();
                try {
                        PageHistorico historico = webClient.get()
                                        .uri("/usuarios/{usuarioId}/historico?page={page}&size={size}", usuarioId, page, size)
                                        .exchangeToMono(response -> {
                                                sb.append("Código HTTP: ").append(response.statusCode().value()).append("\n");
                                                sb.append("**********************************************************\n");
                                                if (response.statusCode().is2xxSuccessful()) {
                                                    return response.bodyToMono(PageHistorico.class);
                                                } else {
                                                    return response.bodyToMono(ErrorMessage.class)
                                                            .flatMap(body -> Mono.error(new RuntimeException(
                                                                    String.format(HTTP_ERROR, response.statusCode().value(), body.toString()))));
                                                }
                                            })
                                        .block();
                        sb.append(historico.toString());
                } catch (RuntimeException e) {
                        sb.append(e.getMessage());
                }
                return sb.toString();
        }

        private Usuario crearUsuario(Map<String, String> attrs) {
                Usuario usuario = new Usuario();
                if (attrs.containsKey("usuarioId")) {
                        usuario.setUsuarioId(Integer.parseInt(attrs.get("usuarioId")));
                }

                if (attrs.containsKey("nombre")) {
                        usuario.setNombre(attrs.get("nombre"));
                }

                if (attrs.containsKey("matricula")) {
                        usuario.setMatricula(attrs.get("matricula"));
                }

                if (attrs.containsKey("nacimiento")) {
                        usuario.setNacimiento(Date.valueOf(attrs.get("nacimiento")));
                }

                if (attrs.containsKey("correo")) {
                        usuario.setCorreo(attrs.get("correo"));
                }

                return usuario;
        }

}