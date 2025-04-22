package es.upm.sos.cliente.model.historico;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Historico {
    
    private int historicoId;
    private int usuarioId;
    private String tituloLibro;
    private String isbnLibro;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPrestamo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaDevolucion;

    private Boolean devuelto;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format(
                "Historico: \n" +
                        "\t id de historico: %d \n" +
                        "\t id de usuario: %d \n" +
                        "\t titulo del libro: %s \n" +
                        "\t isbn del libro: %s \n" +
                        "\t fecha prestamo: %s \n" +
                        "\t fecha devolucion: %s \n" +
                        "\t devuelto: %b \n",
                historicoId,
                usuarioId,
                tituloLibro,
                isbnLibro,
                fechaPrestamo != null ? fechaPrestamo.format(formatter) : "null",
                fechaDevolucion != null ? fechaDevolucion.format(formatter) : "",
                devuelto);
    }

}