package org.example.hotelesapi.repository;

import org.example.hotelesapi.models.Habitacion;
import org.example.hotelesapi.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
    List<Habitacion> findAllByHotelAndCapacidadAndPrecioPorNocheAfterAndPrecioPorNocheBefore(Hotel hotel, int capacidad, double precioMin, double precioMax);
}
