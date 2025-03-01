
package ad.grandao.repository;

import ad.grandao.model.Bicicleta;
import org.springframework.stereotype.Repository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BicicletaDAO {

    private static final String FILE_PATH = "resources/databases/bicicletas.xml";

    // Leer todas las bicicletas desde el archivo XML
    public List<Bicicleta> readBicicletasFromFile() throws Exception {
        List<Bicicleta> bicicletas = new ArrayList<>();
        File xmlFile = new File(FILE_PATH);
        if (!xmlFile.exists()) {
            return bicicletas;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("bicicleta");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Bicicleta bicicleta = new Bicicleta();
                bicicleta.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
                bicicleta.setMarca(eElement.getElementsByTagName("marca").item(0).getTextContent());
                bicicleta.setModelo(eElement.getElementsByTagName("modelo").item(0).getTextContent());
                bicicleta.setColor(eElement.getElementsByTagName("color").item(0).getTextContent());
                bicicleta.setPrecio(Float.parseFloat(eElement.getElementsByTagName("precio").item(0).getTextContent()));
                bicicleta.setPeso(Float.parseFloat(eElement.getElementsByTagName("peso").item(0).getTextContent()));
                bicicleta.setMaterial(eElement.getElementsByTagName("material").item(0).getTextContent());
                bicicletas.add(bicicleta);
            }
        }
        return bicicletas;
    }

    // Leer una bicicleta por su ID
    public Optional<Bicicleta> readBicicletaById(String id) throws Exception {
        return readBicicletasFromFile().stream()
                .filter(bicicleta -> bicicleta.getId().equals(id))
                .findFirst();
    }
}