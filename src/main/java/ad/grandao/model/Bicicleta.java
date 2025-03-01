package ad.grandao.model;

import jakarta.validation.constraints.*;

public class Bicicleta {

    @NotBlank(message = "El ID no puede estar vacío")
    private String id;

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

    @NotNull(message = "El peso no puede estar vacío")
    @Positive(message = "El peso debe ser un número positivo")
    private Float peso;

    @NotBlank(message = "El material no puede estar vacío")
    @Pattern(regexp = "^(fibra de carbono|aluminio)$", message = "El material debe ser 'fibra de carbono' o 'aluminio'")
    private String material;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}