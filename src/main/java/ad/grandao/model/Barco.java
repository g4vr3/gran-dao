package ad.grandao.model;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "barco")
public class Barco {

    @Id
    private String id;

    @Field(value = "nombre")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "El nombre solo puede contener letras, números y espacios")
    private String nombre;

    @Field(value = "tipo")
    @NotBlank(message = "El tipo no puede estar vacío")
    @Size(max = 50, message = "El tipo no puede tener más de 50 caracteres")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "El tipo solo puede contener letras y espacios")
    private String tipo;

    @Field(value = "capacidad")
    @NotNull(message = "La capacidad no puede estar vacía")
    @Positive(message = "La capacidad debe ser un número positivo")
    private Integer capacidad;

    @Field(value = "ano_construccion")
    @NotNull(message = "El año de construcción no puede estar vacío")
    @Min(value = 1700, message = "El año de construcción debe ser posterior a 1800")
    @Max(value = 2040, message = "El año de construcción debe ser anterior a 2040")
    private Integer anoConstruccion;

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Integer getAnoConstruccion() {
        return anoConstruccion;
    }

    public void setAnoConstruccion(Integer anoConstruccion) {
        this.anoConstruccion = anoConstruccion;
    }
}
