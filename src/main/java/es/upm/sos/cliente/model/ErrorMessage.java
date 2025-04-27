package es.upm.sos.cliente.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {
    private String message;

    @Override
    public String toString() {
        return message;
    }
}


