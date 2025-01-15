package org.example.hotelesapi.repository;

import org.example.hotelesapi.models.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Integer> {
}
