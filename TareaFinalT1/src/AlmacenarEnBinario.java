import java.io.*;

public class AlmacenarEnBinario {

    public void almacenarDatos() throws IOException {
        //Metemos el archivo
        FileInputStream fil1 = new FileInputStream("paises.xml");

        //Sacamos los datos del Xstream
        ObtenerPaisesXstream xs = new ObtenerPaisesXstream();

        //Generamos los objetos de Escritura y lectura por objetos
        ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream("Paises.dat"));

        //Escribimos el fichero
        writer.writeObject(xs.getListaPaises());

        System.out.println("Datos almacenados exitosamente");
    }

    public void Imprimir() throws IOException {
        ObjectInputStream reader = new ObjectInputStream(new FileInputStream("Paises.dat"));

        try {
            ListaPaises lp = (ListaPaises) reader.readObject();
            for (Pais p : lp.getPaises()) {
                System.out.println(p.toString());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
