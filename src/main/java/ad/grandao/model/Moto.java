package ad.grandao.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;

public class Moto {

    @Id
    @NotBlank(message = "La matrícula no puede estar vacía")
    @Size(max = 7, message = "La matrícula no puede tener más de 7 caracteres")
    @Pattern(regexp = "^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$", message = "La matrícula debe tener el formato español")
    private String matricula;

    @NotBlank(message = "La marca no puede estar vacía")
    @Size(max = 50, message = "La marca no puede tener más de 50 caracteres")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío")
    @Size(max = 50, message = "El modelo no puede tener más de 50 caracteres")
    private String modelo;

    @NotBlank(message = "El color no puede estar vacío")
    @Size(max = 30, message = "El color no puede tener más de 30 caracteres")
    private String color;

    @NotNull(message = "El precio no puede estar vacío")
    @PositiveOrZero(message = "El precio debe ser mayor o igual a 0")
    private Float precio;

    // Getters and setters
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}