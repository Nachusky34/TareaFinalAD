import com.thoughtworks.xstream.XStream;

import java.io.*;

public class AlmacenarEnBinario {

    public static void main(String[] args) throws IOException {
        FileInputStream fil1 = new FileInputStream("paises.xml");
        ObtenerPaisesXstream xs = new ObtenerPaisesXstream();


        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Paises.dat"));
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream("Paises.dat"));
        writer.writeObject(xs.getListaPaises());
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
