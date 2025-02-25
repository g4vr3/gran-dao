package ad.grandao.controller;

import ad.grandao.model.Moto;
import ad.grandao.service.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/motos")
@CacheConfig(cacheNames = "motos")
public class MotoController {
    private final MotoService motoService;

    @Autowired
    public MotoController(MotoService motoService) {
        this.motoService = motoService;
    }

    // Obtener todas las motos
    @GetMapping
    public ResponseEntity<List<Moto>> getAllMotos() throws IOException {
        return ResponseEntity.ok(motoService.getAllMotos());
    }

    // Obtener una moto por su matrícula
    @GetMapping("/{matricula}")
    @Cacheable
    public ResponseEntity<Moto> getMotoById(@PathVariable String matricula) throws IOException {
        return ResponseEntity.ok(motoService.getMotoById(matricula));
    }
}