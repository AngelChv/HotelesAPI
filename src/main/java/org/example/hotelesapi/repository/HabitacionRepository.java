package org.example.hotelesapi.repository;

import org.example.hotelesapi.models.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
    List<Habitacion> findAllByHotelIdAndOcupadaIsFalseAndCapacidadAndPrecioPorNocheBetween(int idHotel, int capacidad, double precioMin, double precioMax);
}
