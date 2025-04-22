package es.upm.sos.cliente.model.prestamos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import es.upm.sos.cliente.model.libros.Libro;
import es.upm.sos.cliente.model.usuarios.Usuario;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    private PrestamoId id;
    private Usuario usuario;
    private Libro libro;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaPrestamo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
    private LocalDateTime fechaDevolucion;


    @Override
	public String toString() {
		return String.format(
				"Prestamo: \n" + 
				"\t %s \n" +
				"\t %s \n" +
				"\t %s \n" +
				"\t fecha prestamo: %s \n" +
				"\t fecha devolucion: %s \n", 
                id.toString(), 
                usuario.toString(), 
                libro.toString(), 
                fechaPrestamo, 
                fechaDevolucion 
            );
	}
}
