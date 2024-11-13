import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ObtenerPaisesXstream {

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fil1 = new FileInputStream("paises.xml");

        XStream xstream = new XStream();
        Class[] clases = {ListaPaises.class, Pais.class};
        xstream.allowTypes(clases);
        xstream.alias("Paises", ListaPaises.class);
        xstream.alias("Pais", Pais.class);
        xstream.addImplicitCollection(ListaPaises.class, "Paises");

        ListaPaises Paises = (ListaPaises) xstream.fromXML(fil1);

        Paises.Imprimir();
    }
}
