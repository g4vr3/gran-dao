
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

    // Guardar una bicicleta en el archivo XML
    public void saveBicicleta(Bicicleta bicicleta) throws Exception {
        List<Bicicleta> bicicletas = readBicicletasFromFile(); // Leer todas las bicicletas del archivo
        bicicletas.add(bicicleta); // Agregar la nueva bicicleta a la lista
        writeBicicletasToFile(bicicletas); // Escribir todas las bicicletas en el archivo
    }

    // Verificar si una bicicleta existe por su ID
    public boolean existsById(String matricula) throws Exception {
        return readBicicletaById(matricula).isPresent();
    }

    // Escribir todas las bicicletas en el archivo XML
    private void writeBicicletasToFile(List<Bicicleta> bicicletas) throws Exception {
        // Crear un nuevo documento XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        // Crear el elemento raíz <bicicletas>
        Element rootElement = doc.createElement("bicicletas");
        doc.appendChild(rootElement);

        // Iterar sobre la lista de bicicletas
        for (Bicicleta bicicleta : bicicletas) {
            // Crear el elemento <bicicleta>
            Element bicicletaElement = doc.createElement("bicicleta");

            // Crear y agregar el elemento <matrícula>
            Element matricula = doc.createElement("matricula");
            matricula.appendChild(doc.createTextNode(bicicleta.getId()));
            bicicletaElement.appendChild(matricula);

            // Crear y agregar el elemento <marca>
            Element marca = doc.createElement("marca");
            marca.appendChild(doc.createTextNode(bicicleta.getMarca()));
            bicicletaElement.appendChild(marca);

            // Crear y agregar el elemento <modelo>
            Element modelo = doc.createElement("modelo");
            modelo.appendChild(doc.createTextNode(bicicleta.getModelo()));
            bicicletaElement.appendChild(modelo);

            // Crear y agregar el elemento <color>
            Element color = doc.createElement("color");
            color.appendChild(doc.createTextNode(bicicleta.getColor()));
            bicicletaElement.appendChild(color);

            // Crear y agregar el elemento <precio>
            Element precio = doc.createElement("precio");
            precio.appendChild(doc.createTextNode(bicicleta.getPrecio().toString()));
            bicicletaElement.appendChild(precio);

            // Crear y agregar el elemento <peso>
            Element peso = doc.createElement("peso");
            peso.appendChild(doc.createTextNode(bicicleta.getPeso().toString()));
            bicicletaElement.appendChild(peso);

            // Crear y agregar el elemento <material>
            Element material = doc.createElement("material");
            material.appendChild(doc.createTextNode(bicicleta.getMaterial()));
            bicicletaElement.appendChild(material);

            // Agregar el elemento <bicicleta> al elemento raíz <bicicletas>
            rootElement.appendChild(bicicletaElement);
        }

        // Configurar el transformador para escribir el documento XML con sangría
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(FILE_PATH));
        // Transformar el documento DOM en un archivo XML
        transformer.transform(source, result);
    }
}