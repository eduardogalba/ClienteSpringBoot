package es.upm.sos.cliente.model.pagination;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageMetadata {
    private int size;
    private int totalElements;
    private int totalPages;
    private int number;

    @Override
    public String toString() {
        return String.format(
                "\t Total de elementos: %d \n" +
                "\t Página actual: %d \n" +
                "\t Tamaño de página: %d \n" +
                "\t Número de páginas: %d \n", totalElements, number, size, totalPages);
    }
}
