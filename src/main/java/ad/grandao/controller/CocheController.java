package ad.grandao.controller;

import ad.grandao.model.Coche;
import ad.grandao.service.CocheService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coches")
@CacheConfig(cacheNames = "coches")
public class CocheController {
    private final CocheService cocheService;

    @Autowired
    public CocheController(CocheService cocheService) {
        this.cocheService = cocheService;
    }

    // GET ALL
    @GetMapping
    public List<Coche> getAllCoches() {
        return cocheService.findAll();
    }

    // GET
    @GetMapping("/{matricula}")
    @Cacheable
    public ResponseEntity<?> getCocheById(@PathVariable String matricula) {
        return ResponseEntity.ok(cocheService.findById(matricula));
    }

    // POST
    @PostMapping
    public ResponseEntity<?> createCoche(@Valid @RequestBody Coche coche) {
        return ResponseEntity.ok(cocheService.save(coche));
    }
}