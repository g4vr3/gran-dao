package ad.grandao.service;

import ad.grandao.model.Bicicleta;
import ad.grandao.repository.BicicletaDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BicicletaService {

    private final BicicletaDAO bicicletaDAO;

    @Autowired
    public BicicletaService(BicicletaDAO bicicletaDAO) {
        this.bicicletaDAO = bicicletaDAO;
    }

    // Obtener todas las bicicletas
    public List<Bicicleta> findAll() {
        try {
            return bicicletaDAO.readBicicletasFromFile();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer las bicicletas desde el archivo");
        }
    }

    // Obtener una bicicleta por su ID
    public Bicicleta findById(String id) {
        try {
            return bicicletaDAO.readBicicletaById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bicicleta no encontrada"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al buscar la bicicleta");
        }
    }

    // Crear una nueva bicicleta
    public void createBicicleta(@Valid Bicicleta bicicleta) throws Exception {
        if (bicicletaDAO.existsById(bicicleta.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta bicicleta ya existe");
        }
        bicicletaDAO.saveBicicleta(bicicleta);
    }
}
