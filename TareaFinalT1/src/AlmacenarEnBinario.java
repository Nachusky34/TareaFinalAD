import com.thoughtworks.xstream.XStream;

import java.io.*;

public class AlmacenarEnBinario {

    public static void main(String[] args) throws IOException {
        //Metemos el archivo
        FileInputStream fil1 = new FileInputStream("paises.xml");
        //Sacamos los datos del Xstream
        ObtenerPaisesXstream xs = new ObtenerPaisesXstream();

        //Generamos los objetos de Escritura y lectura por objetos
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Paises.dat"));
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream("Paises.dat"));

        //Escribimos el fichero
        writer.writeObject(xs.getListaPaises());

        //Leemos el fichero
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
