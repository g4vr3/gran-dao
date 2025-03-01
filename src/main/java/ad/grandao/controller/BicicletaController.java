package ad.grandao.controller;

import ad.grandao.model.Bicicleta;
import ad.grandao.service.BicicletaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bicicletas")
public class BicicletaController {

    private final BicicletaService bicicletaService;

    @Autowired
    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    // Obtener todas las bicicletas
    @GetMapping
    public ResponseEntity<?> getAllBicicletas() {
        return ResponseEntity.ok(bicicletaService.findAll());
    }

    // Obtener una bicicleta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBicicleta(@PathVariable String id) {
        return ResponseEntity.ok(bicicletaService.findById(id));
    }
}
