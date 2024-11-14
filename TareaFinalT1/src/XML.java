import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XML {

    //Metemos los datos a guardar
    String[] paises = {"Belice", "El Salvador", "Guatemala", "Honduras", "Nicaragua", "México", "Panamá", "Costa Rica"};
    String[] presidentes = {"Froyla Tzalam", "Nayib Bukele", "Alejandro Giammattei", "Xiomara Castro",
            "Daniel Ortega", "Andrés Manuel López Obrador", "Laurentino Cortizo", "Rodrigo Chaves"};
    Long[] pib = {1987000000L, 74818000000L, 185474000000L, 85625000000L, 47770000000L, 2890685000000L, 128500000000L, 129950000000L};
    Double[] coGini = {53.3, 38.8, 48.3, 48.2, 46.2, 45.4, 50.9, 47.2};

    //Creamos variables necesarias para guardar el xml
    Element elemento, elemPais, elemPresidente, elemPib, elemCoGini;
    Text textPais, textPresidente, textPib, textCoGini;

    public void agregarDatosXml() throws ParserConfigurationException, TransformerException {

        // Hacemos los objetos necesarios
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();
        Document doc = impl.createDocument(null, "Paises", null);
        doc.setXmlVersion("1.0");

        for (int i = 0; i < paises.length; i++) {
            //Creamos el nodo pais
            elemento = doc.createElement("Pais");
            doc.getDocumentElement().appendChild(elemento);

            //Creamos el nodo nombre dentro de pais
            elemPais = doc.createElement("nombre");
            elemento.appendChild(elemPais);
            textPais = doc.createTextNode(paises[i]);
            elemPais.appendChild(textPais);

            //Creamos el nodo presidente     dentro de pais
            elemPresidente = doc.createElement("presidente");
            elemento.appendChild(elemPresidente);
            textPresidente = doc.createTextNode(presidentes[i]);
            elemPresidente.appendChild(textPresidente);

            //Creamos el nodo pib dentro de pais
            elemPib = doc.createElement("pib");
            elemento.appendChild(elemPib);
            textPib = doc.createTextNode(pib[i].toString());
            elemPib.appendChild(textPib);

            //Creamos el nodo coGini dentro de pais
            elemCoGini = doc.createElement("coGini");
            elemento.appendChild(elemCoGini);
            textCoGini = doc.createTextNode(coGini[i].toString());
            elemCoGini.appendChild(textCoGini);

            //Se crea el archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("Paises.xml"));
            transformer.transform(source, result);
        }

        System.out.println("Xml generado");
    }
}
