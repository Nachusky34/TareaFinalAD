import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ObtenerPaisesXstream {

    public ListaPaises getListaPaises() throws FileNotFoundException {
        //Metemos el archivo
        FileInputStream fil1 = new FileInputStream("paises.xml");

        //Iniciamos el Xstream y damos permisos
        XStream xstream = new XStream();
        Class[] clases = {ListaPaises.class, Pais.class};
        xstream.allowTypes(clases);

        //Ponemos los alias a las clases
        xstream.alias("Paises", ListaPaises.class);
        xstream.alias("Pais", Pais.class);
        xstream.addImplicitCollection(ListaPaises.class, "Paises");

        //Leemos el xml
        return (ListaPaises) xstream.fromXML(fil1);
    }

    public void Imprimir(ListaPaises lp){
        lp.Imprimir();
    }
}
