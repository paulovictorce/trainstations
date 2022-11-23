package com.trainstation.repositories;

import com.trainstation.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByNameIgnoreCase(String name);

    @Query("select s from Station s where lower(s.name) like lower(concat('%', :textToSearch,'%'))")
    public List<Station> searchByName(String textToSearch);
}
