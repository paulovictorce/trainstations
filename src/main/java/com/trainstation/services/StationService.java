package com.trainstation.services;

import com.trainstation.entities.Station;
import com.trainstation.exceptions.UniqueException;
import com.trainstation.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public ResponseEntity<List<Station>> getAll() {
        List<Station> stationList = stationRepository.findAll();
        return new ResponseEntity<>(stationList, HttpStatus.OK);
    }

    public ResponseEntity<List<Station>> search(String textToSearch) {
        List<Station> stationList = stationRepository.searchByName(textToSearch);
        return new ResponseEntity<>(stationList, HttpStatus.OK);
    }

    public ResponseEntity save(Station station) {

        Optional<Station> stationOptional = stationRepository.findByNameIgnoreCase(station.getName());

        if (stationOptional.isPresent()) {
            throw new UniqueException("Station name already exists!", "name");
        }

        return new ResponseEntity<>(stationRepository.save(station), HttpStatus.CREATED);
    }




}
