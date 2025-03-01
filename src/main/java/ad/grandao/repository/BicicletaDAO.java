package ad.grandao.repository;

import ad.grandao.model.Bicicleta;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BicicletaDAO {

    private static final String FILE_PATH = "src/main/resources/databases/bicicletas.xml"; // Ruta dentro de resources

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
    public Optional<Bicicleta> readBicicletaById(String id) {
        try {
            return readBicicletasFromFile().stream()
                    .filter(bicicleta -> bicicleta.getId().equals(id))
                    .findFirst();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer las bicicletas desde el archivo");
        }
    }

    // Guardar una bicicleta en el archivo XML
    public void saveBicicleta(Bicicleta bicicleta) throws Exception {
        List<Bicicleta> bicicletas = readBicicletasFromFile(); // Leer todas las bicicletas del archivo
        bicicletas.add(bicicleta); // Agregar la nueva bicicleta a la lista
        writeBicicletasToFile(bicicletas); // Escribir todas las bicicletas en el archivo
    }

    // Verificar si una bicicleta existe por su ID
    public boolean existsById(String id) throws Exception {
        return readBicicletaById(id).isPresent();
    }

    // Escribir todas las bicicletas en el archivo XML utilizando BufferedWriter
    private void writeBicicletasToFile(List<Bicicleta> bicicletas) throws Exception {
        File xmlFile = new File(FILE_PATH);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("bicicletas");
        doc.appendChild(rootElement);

        for (Bicicleta bicicleta : bicicletas) {
            Element bicicletaElement = doc.createElement("bicicleta");

            Element id = doc.createElement("id");
            id.appendChild(doc.createTextNode(bicicleta.getId()));
            bicicletaElement.appendChild(id);

            Element marca = doc.createElement("marca");
            marca.appendChild(doc.createTextNode(bicicleta.getMarca()));
            bicicletaElement.appendChild(marca);

            Element modelo = doc.createElement("modelo");
            modelo.appendChild(doc.createTextNode(bicicleta.getModelo()));
            bicicletaElement.appendChild(modelo);

            Element color = doc.createElement("color");
            color.appendChild(doc.createTextNode(bicicleta.getColor()));
            bicicletaElement.appendChild(color);

            Element precio = doc.createElement("precio");
            precio.appendChild(doc.createTextNode(bicicleta.getPrecio().toString()));
            bicicletaElement.appendChild(precio);

            Element peso = doc.createElement("peso");
            peso.appendChild(doc.createTextNode(bicicleta.getPeso().toString()));
            bicicletaElement.appendChild(peso);

            Element material = doc.createElement("material");
            material.appendChild(doc.createTextNode(bicicleta.getMaterial()));
            bicicletaElement.appendChild(material);

            rootElement.appendChild(bicicletaElement);
        }

        // Utilizamos BufferedWriter para escritura
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile))) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Usar un StreamResult con el BufferedWriter
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);  // Escribir el documento en el archivo usando BufferedWriter
        }
    }
}