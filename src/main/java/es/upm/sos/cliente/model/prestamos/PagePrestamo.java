package es.upm.sos.cliente.model.prestamos;
import es.upm.sos.cliente.model.pagination.PageLinks;
import es.upm.sos.cliente.model.pagination.PageMetadata;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagePrestamo {
    private Prestamos _embedded;
    private PageLinks _links;
    private PageMetadata page;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append((_embedded == null) ? "" : _embedded.toString());
        sb.append("\n\t************************************************\n");
        sb.append((_links == null) ? "" : _links.toString());
        sb.append("\n\t************************************************\n");
        sb.append(page.toString());
        return sb.toString();
    }
}
