package ad.grandao.repository;

import ad.grandao.model.Moto;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MotoDAO {

    private static final String FILE_PATH = "databases/motos.txt"; // Ruta dentro de resources

    // Leer todas las motos desde el archivo
    public List<Moto> readMotosFromFile() throws IOException {
        List<Moto> motos = new ArrayList<>();
        // Acceder al archivo dentro del classpath
        Resource resource = new ClassPathResource(FILE_PATH);

        // Asegurarnos de que el archivo existe y está disponible en el classpath
        if (!resource.exists()) {
            throw new FileNotFoundException("No se pudo encontrar el archivo: " + FILE_PATH);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Moto moto = parseMoto(line);
                motos.add(moto);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            throw e; // Lanza el error para que sea manejado adecuadamente
        }
        return motos;
    }

    // Leer una moto por su matrícula
    public Optional<Moto> readMotoById(String matricula) throws IOException {
        return readMotosFromFile().stream()
                .filter(moto -> moto.getMatricula().equals(matricula))
                .findFirst();
    }

    // Verificar si una moto existe por su matrícula
    public boolean existsById(String matricula) throws IOException {
        return readMotoById(matricula).isPresent();
    }

    // Guardar una moto en el archivo
    public void saveMoto(Moto moto) throws IOException {
        List<Moto> motos = readMotosFromFile();
        motos.removeIf(existingMoto -> existingMoto.getMatricula().equals(moto.getMatricula()));
        motos.add(moto);
        writeMotosToFile(motos);
    }

    // Escribir todas las motos en el archivo
    private void writeMotosToFile(List<Moto> motos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/databases/motos.txt"))) {
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
