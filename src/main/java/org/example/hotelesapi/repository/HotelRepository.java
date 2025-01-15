package org.example.hotelesapi.repository;

import org.example.hotelesapi.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findAllByLocalidadAndAndCategoria(String localidad, String categoria);
}