package es.upm.sos.cliente.model.prestamos;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoId {
    private int usuarioId;
    private int libroId;

    @Override
    public String toString() {
        return String.format(
                "PrestamoId: \n" +
                "\t usuarioId: %d \n" +
                "\t libroId: %d \n",
                usuarioId, libroId);
    }
}
