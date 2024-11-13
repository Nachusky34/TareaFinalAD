import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XML {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();
        Document doc = impl.createDocument(null, "Paises", null);
        doc.setXmlVersion("1.0");

        String[] paises = { "Belice", "El Salvador", "Guatemala", "Honduras", "Nicaragua", "México", "Panamá", "Costa Rica" };
        String[] presidentes = { "Froyla Tzalam", "Nayib Bukele", "Alejandro Giammattei", "Xiomara Castro",
                "Daniel Ortega", "Andrés Manuel López Obrador", "Laurentino Cortizo", "Rodrigo Chaves" };
        Long[] pib = { 1987000000L, 74818000000L, 185474000000L, 85625000000L, 47770000000L, 2890685000000L, 128500000000L, 129950000000L };
        Double[] coGini = { 53.3, 38.8, 48.3, 48.2, 46.2, 45.4, 50.9, 47.2 };

        Element elemento, elemPais, elemPresidente, elemPib, elemCoGini;
        Text textPais, textPresidente, textPib, textCoGini;

        for (int i = 0; i < paises.length; i++) {
            elemento = doc.createElement("Pais");
            doc.getDocumentElement().appendChild(elemento);

            elemPais = doc.createElement("nombre");
            elemento.appendChild(elemPais);
            textPais = doc.createTextNode(paises[i]);
            elemPais.appendChild(textPais);

            elemPresidente = doc.createElement("presidente");
            elemento.appendChild(elemPresidente);
            textPresidente = doc.createTextNode(presidentes[i]);
            elemPresidente.appendChild(textPresidente);

            elemPib = doc.createElement("pib");
            elemento.appendChild(elemPib);
            textPib = doc.createTextNode(pib[i].toString());
            elemPib.appendChild(textPib);

            elemCoGini = doc.createElement("coGini");
            elemento.appendChild(elemCoGini);
            textCoGini = doc.createTextNode(coGini[i].toString());
            elemCoGini.appendChild(textCoGini);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("Paises.xml"));
        transformer.transform(source, result);
    }
}
