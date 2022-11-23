package com.trainstation.services;

import com.trainstation.entities.Station;
import com.trainstation.repositories.StationRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StationServiceTest {

    @InjectMocks
    private StationService stationService;

    @Mock
    private StationRepository stationRepository;

    private static final Station STATION = new Station();

    private static final Station STATION_WITH_SAME_NAME = new Station();
    private static final Station INVALID_STATION = new Station();

    private static final List<Station> STATION_LIST = new ArrayList<>();
    private static  Page<Station> STATION_PAGE = null;
    private static final Page<Station> EMPTY_STATION_PAGE = Page.empty();

    private static final PageRequest pageRequest = PageRequest.of(
            0,
            10,
            Sort.Direction.ASC,
            "name");

    @BeforeAll
    static void setupMocks() {

        STATION.setId(1L);
        STATION.setName("Station");
        STATION.setService("Service");

        STATION_WITH_SAME_NAME.setId(2L);
        STATION_WITH_SAME_NAME.setName("Station");
        STATION_WITH_SAME_NAME.setService("Service");

        STATION_LIST.add(STATION);

        STATION_PAGE = new PageImpl<>(STATION_LIST);


    }

    @Test
    void shouldNotCreateStationWithoutMandatoryInformation() {
        Mockito.when(stationRepository.save(INVALID_STATION))
                .thenReturn(INVALID_STATION);
        ResponseEntity response = stationService.save(INVALID_STATION);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldNotCreateStationIfStationNameAlreadyExists() {

        try {
            Mockito.when(stationRepository.findByNameIgnoreCase(STATION.getName()))
                    .thenReturn(Optional.of(STATION_WITH_SAME_NAME));
            Mockito.when(stationRepository.save(STATION))
                    .thenReturn(STATION);
            ResponseEntity response = stationService.save(STATION);
        } catch (Exception e) {
            assertEquals("Station name already exists!", e.getMessage());
        }
    }

    @Test
    void shouldCreateStationWithMandatoryInformation() {
        Mockito.when(stationRepository.save(STATION))
                .thenReturn(STATION);
        ResponseEntity response = stationService.save(STATION);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldNotUpdateStationWhenStationIsNotFounded() {
        Mockito.when(stationRepository.findById(STATION.getId()))
                .thenReturn(Optional.empty());

        ResponseEntity response = stationService.update(STATION.getId(), STATION);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldNotUpdateStationWhenStationNameAlreadyExistsInAnotherRegistry() {

        try {
            Mockito.when(stationRepository.findById(STATION.getId()))
                    .thenReturn(Optional.of(STATION));

            Mockito.when(stationRepository.findByNameIgnoreCase(STATION.getName()))
                    .thenReturn(Optional.of(STATION_WITH_SAME_NAME));

            ResponseEntity response = stationService.update(STATION.getId(), STATION);
        } catch (Exception e) {
            assertEquals("Station name already exists!", e.getMessage());
        }


    }

    @Test
    void shouldUpdateStationWhenStationIsFounded() {
        Mockito.when(stationRepository.findById(STATION.getId()))
                .thenReturn(Optional.of(STATION));

        ResponseEntity response = stationService.update(STATION.getId(), STATION);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnStationListSearchByNameWithoutErrorsWhenStationListIsEmpty() {
        Mockito.when(stationRepository.searchByName("aaaaa", pageRequest))
                .thenReturn(EMPTY_STATION_PAGE);
        ResponseEntity response = stationService.search("aaaaa", 0, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void shouldReturnStationListSearchByNameWhenRecordsAreFound() {
        Mockito.when(stationRepository.searchByName("st", pageRequest))
                .thenReturn(STATION_PAGE);
        ResponseEntity response = stationService.search("st", 0, 10);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
