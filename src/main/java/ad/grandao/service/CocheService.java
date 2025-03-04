package ad.grandao.service;

import ad.grandao.model.Coche;
import ad.grandao.repository.CocheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CocheService {

    private final CocheRepository cocheRepository;

    @Autowired
    public CocheService(CocheRepository cocheRepository) {
        this.cocheRepository = cocheRepository;
    }

    // Obtener todos los coches
    public List<Coche> findAll() {
        List<Coche> coches = cocheRepository.findAll();
        if (coches.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay coches que mostrar");
        }
        return coches;
    }

    // Obtener un coche por la matrícula
    public Coche findById(String matricula) {
        return cocheRepository.findById(matricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coche no encontrado"));
    }

    // Crear un nuevo coche
    public Coche save(Coche coche) {
        if (cocheRepository.existsById(coche.getMatricula())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este coche ya existe");
        }
        return cocheRepository.save(coche);
    }
    // Actualizar un coche
    public Coche update(String matricula, Coche cocheDetails) {
        if (!matricula.equals(cocheDetails.getMatricula())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La matrícula del coche no coincide con la de la URL");
        }

        Coche coche = cocheRepository.findById(matricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coche no encontrado"));

        coche.setMarca(cocheDetails.getMarca());
        coche.setModelo(cocheDetails.getModelo());
        coche.setColor(cocheDetails.getColor());
        coche.setPrecio(cocheDetails.getPrecio());

        return cocheRepository.save(coche);
    }

    // Eliminar un coche
    public void delete(String matricula) {
        if (!cocheRepository.existsById(matricula)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coche no encontrado");
        }
        cocheRepository.deleteById(matricula);
    }
}