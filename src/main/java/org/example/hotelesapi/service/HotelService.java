package org.example.hotelesapi.service;

import org.example.hotelesapi.models.Hotel;
import org.example.hotelesapi.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private final HotelRepository repository;

    public HotelService(HotelRepository repository) {
        this.repository = repository;
    }

    public Hotel save(Hotel hotel) {
        return repository.save(hotel);
    }

    public List<Hotel> findAll() {
        return repository.findAll();
    }
}
