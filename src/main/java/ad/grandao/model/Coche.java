package ad.grandao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "coche")
public class Coche {
    @Id
    @Column(name = "matricula", nullable = false, length = 7)
    @NotBlank(message = "La matrícula no puede estar vacía")
    @Size(max = 7, message = "La matrícula no puede tener más de 7 caracteres")
    @Pattern(regexp = "^[0-9]{4}[BCDFGHJKLMNPRSTVWXYZ]{3}$", message = "La matrícula debe tener el formato español")
    private String matricula;

    @Column(name = "marca", nullable = false, length = 50)
    @NotBlank(message = "La marca no puede estar vacía")
    @Size(max = 50, message = "La marca no puede tener más de 50 caracteres")
    private String marca;

    @Column(name = "modelo", nullable = false, length = 50)
    @NotBlank(message = "El modelo no puede estar vacío")
    @Size(max = 50, message = "El modelo no puede tener más de 50 caracteres")
    private String modelo;

    @Column(name = "color", nullable = false, length = 30)
    @NotBlank(message = "El color no puede estar vacío")
    @Size(max = 30, message = "El color no puede tener más de 30 caracteres")
    private String color;

    @Column(name = "precio", nullable = false)
    @NotNull(message = "El precio no puede estar vacío")
    @PositiveOrZero(message = "El precio debe ser mayor o igual a 0")
    private Float precio;

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