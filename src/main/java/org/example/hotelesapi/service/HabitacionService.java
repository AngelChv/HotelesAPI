package org.example.hotelesapi.service;

import org.example.hotelesapi.models.Habitacion;
import org.example.hotelesapi.models.Hotel;
import org.example.hotelesapi.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HabitacionService {
    private final HabitacionRepository repository;

    public HabitacionService(HabitacionRepository repository) {
        this.repository = repository;
    }

    public Habitacion save(Habitacion habitacion) {
        return repository.save(habitacion);
    }

    public List<Habitacion> findAll() {
        return repository.findAll();
    }

    public List<Habitacion> findAllByHotelAndCapacidadAndRangoPrecio(Hotel hotel, int capacidad, double precioMin, double precioMax) {
        return repository.findAllByHotelAndCapacidadAndPrecioPorNocheAfterAndPrecioPorNocheBefore(hotel, capacidad, precioMin, precioMax);
    }

    public void delete(Habitacion habitacion) {
        repository.delete(habitacion);
    }
}
