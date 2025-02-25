package ad.grandao.service;

import ad.grandao.model.Moto;
import ad.grandao.repository.MotoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class MotoService {

    private final MotoDAO motoDAO;

    @Autowired
    public MotoService(MotoDAO motoDAO) {
        this.motoDAO = motoDAO;
    }

    // Obtener todas las motos
    public List<Moto> getAllMotos() throws IOException {
        List<Moto> motos = motoDAO.readMotosFromFile();
        if (motos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay motos que mostrar");
        }
        return motos;
    }

    // Obtener una moto por su matrícula
    public Moto getMotoById(String matricula) throws IOException {
        return motoDAO.readMotoById(matricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto no encontrada"));
    }
}