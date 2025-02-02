package org.example.hotelesapi.service;

import org.example.hotelesapi.models.Habitacion;
import org.example.hotelesapi.repository.HabitacionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public List<Habitacion> findAllByHotelIdAndCapacidadAndRangoPrecio(int idHotel, int capacidad, double precioMin, double precioMax) {
        return repository.findAllByHotelIdAndOcupadaIsFalseAndCapacidadAndPrecioPorNocheBetween(idHotel, capacidad, precioMin, precioMax);
    }

    public void delete(Habitacion habitacion) {
        repository.delete(habitacion);
    }

    public Habitacion ocupar(int idHabitacion) {
        Optional<Habitacion> habitacion = repository.findById(idHabitacion);
        if (habitacion.isPresent()) {
            Habitacion habitacionActual = habitacion.get();
            habitacionActual.setOcupada(true);
            return repository.save(habitacionActual);
        }
        return null;
    }

    public List<Habitacion> getHabitacionesWithJPQL() {
        return repository.getHabitacionesWithJPQL();
    }
}
