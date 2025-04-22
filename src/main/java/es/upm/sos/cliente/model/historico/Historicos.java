package es.upm.sos.cliente.model.historico;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Historicos {
    private Historico[] historicoList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Historicos: \n");
        sb.append("**********************************************************\n");
        for (Historico historico : historicoList) {
            sb.append(historico.toString());
        }
        return sb.toString();
    }
}
