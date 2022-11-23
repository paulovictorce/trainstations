package com.trainstation.services;

import com.trainstation.entities.Station;
import com.trainstation.exceptions.UniqueException;
import com.trainstation.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public ResponseEntity<Page<Station>> getAll(int page, int size) {

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");

        Page<Station> stationList = stationRepository.findAll(pageRequest);
        return new ResponseEntity<>(stationList, HttpStatus.OK);
    }

    public ResponseEntity<Page<Station>> search(String textToSearch, int page,
                                      int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");

        Page<Station> stationList = stationRepository.searchByName(textToSearch, pageRequest);
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
