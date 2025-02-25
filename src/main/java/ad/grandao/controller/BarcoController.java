package ad.grandao.controller;

import ad.grandao.model.Barco;
import ad.grandao.service.BarcoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/barcos")
@CacheConfig(cacheNames = {"barcos"})
public class BarcoController {
    private final BarcoService barcoService;

    @Autowired
    public BarcoController(BarcoService barcoService) {
        this.barcoService = barcoService;
    }

    // Obtener todos los barcos
    @GetMapping
    public ResponseEntity<?> getAllBarcos() {
        return ResponseEntity.ok(barcoService.getAllBarcos());
    }

    // Obtener un barco por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBarco(@PathVariable String id) {
        return ResponseEntity.ok(barcoService.getBarcoById(id));
    }

    // Crear un nuevo barco
    @PostMapping
    public ResponseEntity<?> postBarco(@Valid @RequestBody Barco barco) {
        barcoService.createBarco(barco);
        return ResponseEntity.status(201).body(barco);
    }
}