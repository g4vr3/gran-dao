package ad.grandao.controller;

import ad.grandao.model.*;
import ad.grandao.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vehiculos")
@CacheConfig(cacheNames = {"coches", "motos", "bicicletas", "barcos"})
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @Autowired
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // Métodos para coches
    @GetMapping("/coches")
    public List<Coche> getAllCoches() {
        return vehiculoService.findAllCoches();
    }

    @GetMapping("/coches/{matricula}")
    @Cacheable
    public ResponseEntity<?> getCocheById(@PathVariable String matricula) {
        return ResponseEntity.ok(vehiculoService.findCocheById(matricula));
    }

    @PostMapping("/coches")
    public ResponseEntity<?> createCoche(@Valid @RequestBody Coche coche) {
        return ResponseEntity.ok(vehiculoService.saveCoche(coche));
    }

    @PutMapping("/coches/{matricula}")
    public ResponseEntity<?> updateCoche(@PathVariable String matricula, @Valid @RequestBody Coche cocheDetails) {
        return ResponseEntity.ok(vehiculoService.updateCoche(matricula, cocheDetails));
    }

    @DeleteMapping("/coches/{matricula}")
    public ResponseEntity<?> deleteCoche(@PathVariable String matricula) {
        vehiculoService.deleteCoche(matricula);
        return ResponseEntity.noContent().build();
    }

    // Métodos para motos
    @GetMapping("/motos")
    public ResponseEntity<List<Moto>> getAllMotos() throws IOException {
        return ResponseEntity.ok(vehiculoService.getAllMotos());
    }

    @GetMapping("/motos/{matricula}")
    @Cacheable
    public ResponseEntity<Moto> getMotoById(@PathVariable String matricula) throws IOException {
        return ResponseEntity.ok(vehiculoService.getMotoById(matricula));
    }

    @PostMapping("/motos")
    public ResponseEntity<Moto> createMoto(@Valid @RequestBody Moto moto) throws IOException {
        vehiculoService.createMoto(moto);
        return ResponseEntity.status(201).body(moto);
    }

    // Métodos para bicicletas
    @GetMapping("/bicicletas")
    public ResponseEntity<?> getAllBicicletas() {
        return ResponseEntity.ok(vehiculoService.findAllBicicletas());
    }

    @GetMapping("/bicicletas/{id}")
    @Cacheable
    public ResponseEntity<?> getBicicleta(@PathVariable String id) {
        return ResponseEntity.ok(vehiculoService.findBicicletaById(id));
    }

    @PostMapping("/bicicletas")
    public ResponseEntity<Bicicleta> createBicicleta(@Valid @RequestBody Bicicleta bicicleta) throws Exception {
        vehiculoService.createBicicleta(bicicleta);
        return ResponseEntity.status(201).body(bicicleta);
    }

    // Métodos para barcos
    @GetMapping("/barcos")
    public ResponseEntity<?> getAllBarcos() {
        return ResponseEntity.ok(vehiculoService.getAllBarcos());
    }

    @GetMapping("/barcos/{id}")
    public ResponseEntity<?> getBarco(@PathVariable String id) {
        return ResponseEntity.ok(vehiculoService.getBarcoById(id));
    }

    @PostMapping("/barcos")
    public ResponseEntity<?> postBarco(@Valid @RequestBody Barco barco) {
        vehiculoService.createBarco(barco);
        return ResponseEntity.status(201).body(barco);
    }

    @PutMapping("/barcos/{id}")
    public ResponseEntity<?> updateBarco(@PathVariable String id, @Valid @RequestBody Barco barcoDetails) {
        return ResponseEntity.ok(vehiculoService.updateBarco(id, barcoDetails));
    }

    @DeleteMapping("/barcos/{id}")
    public ResponseEntity<?> deleteBarco(@PathVariable String id) {
        vehiculoService.deleteBarco(id);
        return ResponseEntity.noContent().build();
    }
}