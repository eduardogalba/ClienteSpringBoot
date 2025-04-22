package es.upm.sos.cliente.model.usuarios;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuarios {
    private Usuario[] usuarioList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuarios: \n");
        for (Usuario usuario : usuarioList) {
            sb.append(usuario.toString());
        }
        return sb.toString();
    }
}
