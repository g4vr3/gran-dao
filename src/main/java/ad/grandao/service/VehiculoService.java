package ad.grandao.service;

import ad.grandao.model.*;
import ad.grandao.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class VehiculoService {

    private final CocheRepository cocheRepository;
    private final MotoDAO motoDAO;
    private final BicicletaDAO bicicletaDAO;
    private final BarcoRepository barcoRepository;

    @Autowired
    public VehiculoService(CocheRepository cocheRepository, MotoDAO motoDAO, BicicletaDAO bicicletaDAO, BarcoRepository barcoRepository) {
        this.cocheRepository = cocheRepository;
        this.motoDAO = motoDAO;
        this.bicicletaDAO = bicicletaDAO;
        this.barcoRepository = barcoRepository;
    }

    // Métodos para coches

    // Obtener todos los coches
    public List<Coche> findAllCoches() {
        List<Coche> coches = cocheRepository.findAll();
        if (coches.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay coches que mostrar");
        }
        return coches;
    }

    // Obtener un coche por la matrícula
    public Coche findCocheById(String matricula) {
        return cocheRepository.findById(matricula)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coche no encontrado"));
    }

    // Crear un nuevo coche
    public Coche saveCoche(Coche coche) {
        if (cocheRepository.existsById(coche.getMatricula())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este coche ya existe");
        }
        return cocheRepository.save(coche);
    }

    // Actualizar un coche
    public Coche updateCoche(String matricula, Coche cocheDetails) {
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
    public void deleteCoche(String matricula) {
        if (!cocheRepository.existsById(matricula)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coche no encontrado");
        }
        cocheRepository.deleteById(matricula);
    }

    // Métodos para motos
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
        List<Moto> motos = motoDAO.readMotosFromFile();
        return motos.stream()
                .filter(moto -> moto.getMatricula().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moto no encontrada"));
    }

    // Crear una nueva moto
    public void createMoto(@Valid Moto moto) throws IOException {
        List<Moto> motos = motoDAO.readMotosFromFile();
        boolean motoExistente = motos.stream()
                .anyMatch(existingMoto -> existingMoto.getMatricula().equals(moto.getMatricula()));

        if (motoExistente) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta moto ya existe");
        }

        motoDAO.saveMoto(moto);
    }

    // Métodos para bicicletas
    // Obtener todas las bicicletas
    public List<Bicicleta> findAllBicicletas() {
        try {
            return bicicletaDAO.readBicicletasFromFile();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer las bicicletas desde el archivo");
        }
    }
    // Obtener una bicicleta por su ID
    public Bicicleta findBicicletaById(String id) {
        return bicicletaDAO.readBicicletaById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bicicleta no encontrada"));
    }
    // Crear una nueva bicicleta
    public void createBicicleta(@Valid Bicicleta bicicleta) throws Exception {
        if (bicicletaDAO.existsById(bicicleta.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esta bicicleta ya existe");
        }
        bicicletaDAO.saveBicicleta(bicicleta);
    }

    // Métodos para barcos
    // Obtener todos los barcos
    public List<Barco> getAllBarcos() {
        List<Barco> barcos = barcoRepository.findAll();
        if (barcos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No hay barcos disponibles");
        }
        return barcos;
    }
    // Leer barco por ID
    public Barco getBarcoById(String id) {
        return barcoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barco no encontrado"));
    }
    // Crear barco
    public void createBarco(Barco barco) {
        if (barco.getId() != null && barcoRepository.existsById(barco.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un barco con el ID " + barco.getId());
        }
        barcoRepository.save(barco);
    }
    // Actualizar barco
    public Barco updateBarco(String id, Barco barcoDetails) {
        if (!id.equals(barcoDetails.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El ID del barco no coincide con el ID de la URL");
        }

        Barco barco = barcoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barco no encontrado"));

        barco.setNombre(barcoDetails.getNombre());
        barco.setTipo(barcoDetails.getTipo());
        barco.setCapacidad(barcoDetails.getCapacidad());
        barco.setAnoConstruccion(barcoDetails.getAnoConstruccion());

        return barcoRepository.save(barco);
    }
    // Eliminar barco
    public void deleteBarco(String id) {
        if (!barcoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barco no encontrado");
        }
        barcoRepository.deleteById(id);
    }
}