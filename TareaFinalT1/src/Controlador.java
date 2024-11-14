import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Controlador {

    public void ejercicio1() throws ParserConfigurationException, TransformerException {
        XML xml = new XML();

        xml.agregarDatosXml();
    }

    public void ejercicio3(){
        ObtenerPaisesXstream xs = new ObtenerPaisesXstream();
        try {
            xs.imprimir(xs.getListaPaises());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ejercicio4() throws IOException {
        AlmacenarEnBinario bi = new AlmacenarEnBinario();

        bi.almacenarDatos();
        bi.imprimir();
    }

    public void ejercicio5() throws SQLException, FileNotFoundException {
        PaisesBBDD bd = new PaisesBBDD();

        bd.generarBBDD();
        bd.getPaises();
    }

    public void ejercicio6() throws SQLException, FileNotFoundException {
        PaisesBBDD bd = new PaisesBBDD();

        System.out.println("Tabla sin modificaciones:");
        bd.getPaises();
        bd.modificarPib();
        bd.getPaises();
        bd.modificarGini();
        bd.getPaises();
    }
}