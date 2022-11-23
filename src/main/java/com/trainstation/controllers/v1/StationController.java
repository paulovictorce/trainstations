package com.trainstation.controllers.v1;

import com.trainstation.entities.Station;
import com.trainstation.services.StationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody @Valid Station station) {
        return stationService.save(station);
    }

    @GetMapping
    public ResponseEntity<Page<Station>> getAll(
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {

        return stationService.getAll(page, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Station>> search(
            @RequestParam(value = "textToSearch", required = false) String textToSearch,
            @RequestParam(
                    value = "page",
                    required = false,
                    defaultValue = "0") int page,
            @RequestParam(
                    value = "size",
                    required = false,
                    defaultValue = "10") int size) {

        if (StringUtils.isEmpty(textToSearch)) {

            return stationService.getAll(page, size);

        }

        return stationService.search(textToSearch, page, size);

    }


}
