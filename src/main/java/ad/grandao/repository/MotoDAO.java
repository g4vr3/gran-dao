package ad.grandao.repository;

import ad.grandao.model.Moto;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MotoDAO {

    private static final String FILE_PATH = "resources/databases/motos.txt";

    // Leer todas las motos desde el archivo
    public List<Moto> readMotosFromFile() throws IOException {
        List<Moto> motos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Moto moto = parseMoto(line);
                motos.add(moto);
            }
        }
        return motos;
    }

    // Leer una moto por su matrícula
    public Optional<Moto> readMotoById(String matricula) throws IOException {
        return readMotosFromFile().stream()
                .filter(moto -> moto.getMatricula().equals(matricula))
                .findFirst();
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