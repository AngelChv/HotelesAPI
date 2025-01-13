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
    private int capacidad;

    @Schema(description = "Precio por noche", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private double precioPorNoche;

    @Schema(description = "Cuenta con desayuno", defaultValue = "false")
    private boolean desayuno;

    @Schema(description = "La habitación esta ocupada", defaultValue = "false")
    private boolean ocupada;

    //todo faltan relaciones
}
