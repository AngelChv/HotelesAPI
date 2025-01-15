package org.example.hotelesapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hotelesapi.models.Habitacion;
import org.example.hotelesapi.service.HabitacionService;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Guardar una habitación", description = "Guarda una habitación en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Habitación guardada con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),})
    public Habitacion save(@RequestBody @Parameter(
            required = true,
            description = "Datos a guardar de la habitación",
            example = """
                    {
                    "id": 0,
                    "capacidad": 2,
                    "precioPorNoche": 50,
                    "desayuno": false,
                    "ocupada": false
                    }""") Habitacion habitacion) {
        try {
            return service.save(habitacion);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar la habitación", e);
        }
    }
}
