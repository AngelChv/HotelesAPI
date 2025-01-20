package org.example.hotelesapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "habitaciones")
public class Habitacion {
    @Schema(description = "Identificador de la habitación", example = "1111", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Cuantas personas pueden dormir en la habitación", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Column(nullable = false)
    private int capacidad;

    @Schema(description = "Precio por noche", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    @Column(nullable = false)
    private double precioPorNoche;

    @Schema(description = "Cuenta con desayuno", defaultValue = "false")
    @NotNull
    @Column(nullable = false)
    private boolean desayuno;

    @Schema(description = "La habitación esta ocupada", defaultValue = "false")
    @NotNull
    @Column(nullable = false)
    private boolean ocupada;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Habitacion() {
        desayuno = false;
        ocupada = false;
    }

    public Habitacion(int id, int capacidad, double precioPorNoche, boolean desayuno, boolean ocupada, Hotel hotel) {
        this.id = id;
        this.capacidad = capacidad;
        this.precioPorNoche = precioPorNoche;
        this.desayuno = desayuno;
        this.ocupada = ocupada;
        this.hotel = hotel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(@NotNull int capacidad) {
        this.capacidad = capacidad;
    }

    @NotNull
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(@NotNull double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    @NotNull
    public boolean isDesayuno() {
        return desayuno;
    }

    public void setDesayuno(@NotNull boolean desayuno) {
        this.desayuno = desayuno;
    }

    @NotNull
    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(@NotNull boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "id=" + id +
                ", capacidad=" + capacidad +
                ", precioPorNoche=" + precioPorNoche +
                ", desayuno=" + desayuno +
                ", ocupada=" + ocupada +
                ", hotel=" + hotel +
                '}';
    }
}
