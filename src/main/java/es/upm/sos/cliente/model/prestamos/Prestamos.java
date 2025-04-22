package es.upm.sos.cliente.model.prestamos;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamos {
    private Prestamo[] prestamoList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Prestamos: \n");
        for (Prestamo prestamo : prestamoList) {
            sb.append(prestamo.toString());
        }
        return sb.toString();
    }
}
