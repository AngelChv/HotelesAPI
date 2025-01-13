package org.example.hotelesapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hotelesapi.models.Hotel;
import org.example.hotelesapi.service.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("hotel")
@Tag(name = "Hoteles", description = "Catálogo de Hoteles")
public class HotelController {
    private final HotelService service;

    public HotelController(HotelService service) {
        this.service = service;
    }

    @GetMapping("/")
    @Operation(summary = "Obtener todos los hoteles", description = "Obtiene una lista de todos los hoteles")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Lista de hoteles obtenida exitosamente"), @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"), @ApiResponse(responseCode = "404", description = "No se encontraron hoteles")})
    public List<Hotel> getAll() {
        try {
            return service.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al obtener todos los hoteles", e);
        }
    }

    @PostMapping("save")
    @Operation(summary = "Guardar un hotel", description = "Guarda un hotel en la base de datos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Hotel guardado con éxito"), @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),})
    public Hotel save(@RequestBody @Parameter(description = "Datos a guardar del hotel", example = "{\n" + "\"id\": 0,\n" + "\"nombre\": \"prueba1\",\n" + "\"descripcion\": \"asdfasf\",\n" + "\"categoria\": \"1\",\n" + "\"piscina\": true,\n" + "\"localidad\": \"valladolid\"\n" + "}") Hotel hotel) {
        try {
            return service.save(hotel);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al guardar el hotel", e);
        }
    }
}
