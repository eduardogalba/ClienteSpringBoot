package es.upm.sos.cliente.model.pagination;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageLinks {
    private Href first;
    private Href self;
    private Href next;
    private Href last;

    @Override
    public String toString() {
        return String.format("Links \n" +
                "\t First: %s \n" +
                "\t Self: %s \n" +
                "\t Next: %s \n" +
                "\t Last: %s \n", 
                (first == null) ? "" : first.getHref(), 
                (self == null) ? "" : self.getHref(), 
                (next == null) ? "" : next.getHref(), 
                (last == null) ? "" : last.getHref());
    }
}
