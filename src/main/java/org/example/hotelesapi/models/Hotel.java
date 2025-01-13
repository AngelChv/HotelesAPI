package org.example.hotelesapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "hoteles")
public class Hotel {
    @Schema(description = "Identificador del hotel", example = "1111", requiredMode = Schema.RequiredMode.REQUIRED)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "Nombre del hotel", example = "Hotel las claras", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String nombre;

    @Schema(description = "Descripción")
    private String descripcion;

    @Schema(description = "Categoría del hotel", example = "Apartamento", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private String categoria;

    @Schema(description = "Si el hotel tiene o no piscina", defaultValue = "false")
    private boolean piscina;

    @Schema(description = "Localidad a la que pertenece el hotel", example = "Valladolid")
    private String localidad;

    public Hotel() {
    }

    public Hotel(int id, String nombre, String descripcion, String categoria, boolean piscina, String localidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.piscina = piscina;
        this.localidad = localidad;
    }
}