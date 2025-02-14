package org.example.hotelesapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hotelesapi.models.Habitacion;
import org.example.hotelesapi.service.HabitacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("habitacion")
@Tag(name = "Habitaciones", description = "Catálogo de Hoteles")
public class HabitacionController {
    private final HabitacionService service;

    public HabitacionController(HabitacionService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Obtener todas las habitaciones", description = "Obtiene una lista de todas las habitaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "404", description = "No se encontraron hoteles")})
    public List<Habitacion> getAll() {
        try {
            return service.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al obtener todos los hoteles", e);
        }
    }

    @PostMapping("save")
    @Operation(summary = "Guardar una habitación y la asocia a un hotel", description = "Guarda una habitación en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación guardada con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),})
    public Habitacion save(@RequestBody Habitacion habitacion) {
        // Por algún motivo si el id del hotel al que pertenece la habitación no es válido (no existe)
        // devuelve un código 203 como si no tuviese permisos.
        try {
            return service.save(habitacion);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar la habitación", e);
        }
    }

    @GetMapping("/{idHotel}/{capacidad}/{precioMin}/{precioMax}")
    @Operation(summary = "Obtener todas las habitaciones filtradas por capacidad y el rango de precio",
            description = "Obtiene una lista de todas las habitaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "404", description = "No se encontraron hoteles")})
    public List<Habitacion> getAllByHotelAndCantidadAndRangoPrecio(@PathVariable int capacidad, @PathVariable double precioMax, @PathVariable double precioMin, @PathVariable int idHotel) {
        try {
            return service.findAllByHotelIdAndCapacidadAndRangoPrecio(idHotel, capacidad, precioMin, precioMax);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al obtener los hoteles", e);
        }
    }

    @DeleteMapping("delete")
    @Operation(summary = "Elimina una habitación de un hotel", description = "Elimina una habitación de un hotel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación eliminada con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),})
    public void delete(@RequestBody Habitacion habitacion) {
        try {
            service.delete(habitacion);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al borrar la habitación", e);
        }
    }

    @PostMapping("ocupar/{idHabitacion}")
    @Operation(summary = "Ocupar una habitación", description = "Ocupar una habitación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación ocupada con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),})
    public ResponseEntity<?> ocupar(@PathVariable int idHabitacion) {
        try {
            Habitacion habitacion = service.ocupar(idHabitacion);
            if (habitacion != null) {
                return new ResponseEntity<>(habitacion, HttpStatus.OK);
            }
            return new ResponseEntity<>("No se encuentra la habitación", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al ocupar la habitación", e);
        }
    }

    @GetMapping("/jpql")
    @Operation(summary = "Obtener habitaciones mediante consulta JPQL", description = "Obtener habitaciones mediante consulta JPQL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de habitaciones obtenida exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "404", description = "No se encontraron hoteles")})
    public List<Habitacion> getHabitacionesWithJPQL() {
        try {
            return service.getHabitacionesWithJPQL();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al obtener los hoteles", e);
        }
    }
}
