package ad.grandao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de ResponseStatusException (errores 4xx y 5xx)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getReason());  // Razón de la excepción
        errorResponse.put("status", String.valueOf(ex.getStatusCode().value()));  // Código de estado

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    // Manejo de excepciones de validación (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Iterar sobre los errores de validación
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();  // Nombre del campo con error
            String errorMessage = error.getDefaultMessage();  // Mensaje de error
            errors.put(fieldName, errorMessage);  // Añadir al mapa
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);  // Devolver el error con código 400
    }

    // Manejo de excepciones generales (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGlobalException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error interno del servidor");
        errorResponse.put("details", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);  // Código 500
    }
}