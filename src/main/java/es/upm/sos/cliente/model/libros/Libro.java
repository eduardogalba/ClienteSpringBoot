package es.upm.sos.cliente.model.libros;
import es.upm.sos.cliente.model.pagination.ResourceLink;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
	private int libroId;
    private String titulo;
    private String autores;
    private String edicion;
    private String isbn;
	private String editorial;
    private int volumenes;
    private int prestados;
	private ResourceLink _links;

    @Override
    public String toString() {
        return String.format(
                "Libro: \n" + 
                "\t************************************************\n" +
                "\t libroId: %d \n" +
                "\t titulo: %s \n" +
                "\t autores: %s \n" +
                "\t edicion: %s \n" +
                "\t isbn: %s \n" +
                "\t editorial: %s \n" +
                "\t volumenes: %d \n" +
                "\t prestados: %d \n" +
                (_links != null ? "\t link: " + _links.getSelf().getHref() + " \n" : ""), 
                libroId, 
                titulo, 
                autores, 
                edicion, 
                isbn, 
                editorial, 
                volumenes, 
                prestados
            );
    }
}
