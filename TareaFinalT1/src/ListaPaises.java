import java.io.Serializable;
import java.util.List;

public class ListaPaises implements Serializable {

    private List<Pais> Paises;

    public ListaPaises() {}

    public void add(Pais p) {
        Paises.add(p);
    }

    public List<Pais> getPaises() {
        return Paises;
    }

    public void Imprimir() {
        for (Pais p : Paises) {
            System.out.println(p);
        }
    }
}
