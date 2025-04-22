package es.upm.sos.cliente.model.libros;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libros {
    private Libro[] libroList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libros: \n");
        for (Libro libro : libroList) {
            sb.append(libro.toString());
        }
        return sb.toString();
    }
}