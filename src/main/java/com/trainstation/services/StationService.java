package com.trainstation.services;

import com.trainstation.entities.Station;
import com.trainstation.exceptions.UniqueException;
import com.trainstation.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
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

    private ResponseEntity stationNotFoundedResponse = new ResponseEntity<>("Station not founded!",
            HttpStatus.NOT_FOUND);

    private ResponseEntity validationStationFieldsFailResponse = new ResponseEntity<>(
            "The name and service fields must be informed", HttpStatus.BAD_REQUEST);

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

        if (validateStation(station)) {
            return validationStationFieldsFailResponse;
        }

        return new ResponseEntity<>(stationRepository.save(station), HttpStatus.CREATED);
    }

    public ResponseEntity update(Long stationId, Station station) {

        Optional<Station> stationOptional = stationRepository.findById(stationId);

        if (!stationOptional.isPresent()) {
            return stationNotFoundedResponse;
        }

        if (validateStation(station)) {
            return validationStationFieldsFailResponse;
        }


        Optional<Station> stationOptionalToValidateName = stationRepository.findByNameIgnoreCase(station.getName());

        if (stationOptionalToValidateName.isPresent() &&
                stationOptionalToValidateName.get().getId() != stationOptional.get().getId()) {
            throw new UniqueException("Station name already exists!", "name");
        }

        station.setId(stationOptional.get().getId());

        return new ResponseEntity<>(stationRepository.save(station), HttpStatus.OK);
    }

    private boolean validateStation(Station station) {

        if (StringUtils.isEmpty(station.getName()) || StringUtils.isEmpty(station.getService())) {
            return true;
        }

        return false;

    }


}
