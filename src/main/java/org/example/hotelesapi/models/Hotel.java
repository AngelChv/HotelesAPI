package org.example.hotelesapi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

@Entity
@Table(name = "hoteles")
public class Hotel {
    //todo evitar los nulos correctamente.
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
    @NotNull // todo parece que no funciona
    private String categoria;

    @Schema(description = "Si el hotel tiene o no piscina", defaultValue = "false")
    private boolean piscina;

    @Schema(description = "Localidad a la que pertenece el hotel", example = "Valladolid")
    private String localidad;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Set<Habitacion> habitaciones;

    public Hotel() {
    }

    public Hotel(int id, String nombre, String descripcion, String categoria, boolean piscina, String localidad, Set<Habitacion> habitaciones) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.piscina = piscina;
        this.localidad = localidad;
        this.habitaciones = habitaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public @NotNull String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotNull String categoria) {
        this.categoria = categoria;
    }

    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public Set<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Set<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", categoria='" + categoria + '\'' +
                ", piscina=" + piscina +
                ", localidad='" + localidad + '\'' +
                ", habitaciones=" + habitaciones +
                '}';
    }
}