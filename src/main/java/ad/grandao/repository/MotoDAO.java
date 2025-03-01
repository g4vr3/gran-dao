package ad.grandao.repository;

import ad.grandao.model.Moto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MotoDAO {

    private static final String FILE_PATH = "databases/motos.txt"; // Ruta dentro de resources

    // Leer todas las motos desde el archivo
    public List<Moto> readMotosFromFile() throws IOException {
        List<Moto> motos = new ArrayList<>();
        File file = new File("src/main/resources/databases/motos.txt");  // Ruta directa en el sistema de archivos

        if (!file.exists()) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + file.getPath());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Moto moto = parseMoto(line);
                motos.add(moto);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            throw e;
        }
        return motos;
    }


    // Guardar una moto en el archivo
    public void saveMoto(Moto moto) throws IOException {
        List<Moto> motos = readMotosFromFile();  // Leer motos actuales del archivo
        motos.add(moto);  // Agregar la nueva moto a la lista
        writeMotosToFile(motos);  // Escribir todas las motos en el archivo de nuevo
    }

    // Escribir todas las motos en el archivo
    private void writeMotosToFile(List<Moto> motos) throws IOException {
        File file = new File("src/main/resources/databases/motos.txt");  // Ruta directa en el sistema de archivos
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Moto moto : motos) {
                writer.write(formatMoto(moto));
                writer.newLine();
            }
        }
    }

    // Formatear un objeto Moto a una línea de texto
    private String formatMoto(Moto moto) {
        return String.join(",",
                moto.getMatricula(),
                moto.getMarca(),
                moto.getModelo(),
                moto.getColor(),
                moto.getPrecio().toString());
    }

    // Parsear una línea de texto a un objeto Moto
    private Moto parseMoto(String line) {
        String[] fields = line.split(",");
        Moto moto = new Moto();
        moto.setMatricula(fields[0]);
        moto.setMarca(fields[1]);
        moto.setModelo(fields[2]);
        moto.setColor(fields[3]);
        moto.setPrecio(Float.parseFloat(fields[4]));
        return moto;
    }
}
