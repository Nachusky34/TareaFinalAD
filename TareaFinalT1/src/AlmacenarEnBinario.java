import com.thoughtworks.xstream.XStream;

import java.io.*;

public class AlmacenarEnBinario {

    public static void main(String[] args) throws IOException {
        FileInputStream fil1 = new FileInputStream("paises.xml");

        XStream xstream = new XStream();
        Class[] clases = {ListaPaises.class, Pais.class};
        xstream.allowTypes(clases);
        xstream.alias("Paises", ListaPaises.class);
        xstream.alias("Pais", Pais.class);
        xstream.addImplicitCollection(ListaPaises.class, "Paises");

        ListaPaises Paises = (ListaPaises) xstream.fromXML(fil1);


        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Paises.dat"));
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream("Paises.dat"));
        writer.writeObject(Paises);
        ListaPaises lp;
        try {
            lp = (ListaPaises) reader.readObject();
            for (int i = 0; i < lp.getPaises().size(); i++) {
                Pais p = lp.getPaises().get(i);
                System.out.println(p.toString());
            }
        } catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
