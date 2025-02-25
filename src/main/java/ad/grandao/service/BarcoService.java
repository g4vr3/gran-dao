package ad.grandao.service;


import ad.grandao.model.Barco;
import ad.grandao.repository.BarcoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BarcoService {
    private final BarcoRepository barcoRepository;
    //Constructor autowired
    @Autowired
    public BarcoService(BarcoRepository barcoRepository) {
        this.barcoRepository = barcoRepository;
    }
    //Obtener todos los barcos
    public List<Barco> getAllBarcos() {
        List<Barco> barcos = barcoRepository.findAll();
        if (barcos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay barcos disponibles");
        }
        return barcos;
    }
    //Leer barco por ID
    public Barco getBarcoById(String id) {
        return barcoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barco no encontrado"));
    }
    //Crear barco
    public void createBarco(Barco barco) {
        if (barco.getId() != null && barcoRepository.existsById(barco.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un barco con el ID " + barco.getId());
        }
        barcoRepository.save(barco);
    }
}
