package es.upm.sos.cliente.model.usuarios;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import es.upm.sos.cliente.model.historico.Historico;
import es.upm.sos.cliente.model.libros.Libro;
import es.upm.sos.cliente.model.pagination.ResourceLink;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
	private int usuarioId;
	private String nombre;
	private String matricula;
	private Date nacimiento;
	private String correo;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Libro> prestamos = null;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<Historico> devueltos = null;

	private ResourceLink _links;

	@Override
	public String toString() {
		StringBuilder prestamosStr = new StringBuilder();
		if (prestamos != null) {
			for (Libro libro : prestamos) {
				prestamosStr.append(libro.toString()).append("\n");
			}
		}

		StringBuilder devueltosStr = new StringBuilder();
		if (devueltos != null) {
			for (Historico historico : devueltos) {
				devueltosStr.append(historico.toString()).append("\n");
			}
		}
		return String.format(
				"Usuario: \n" +
						"\t usuarioId: %d \n" +
						"\t nombre: %s \n" +
						"\t matricula: %s \n" +
						"\t nacimiento: %s \n" +
						"\t correo: %s \n" +
						"\t link: %s \n" +
						"\t prestamos: [\n" +
						"\t %s \n \t ] \n" +
						"\t devueltos: [\n" +
						"\t %s \n \t ] \n",
				usuarioId,
				nombre,
				matricula,
				nacimiento,
				correo,
				_links.getSelf().getHref(),
				prestamosStr.toString(),
				devueltosStr.toString());
	}
}