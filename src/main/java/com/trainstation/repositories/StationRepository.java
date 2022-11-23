package com.trainstation.repositories;

import com.trainstation.entities.Station;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StationRepository extends JpaRepository<Station, Long> {

    Optional<Station> findByNameIgnoreCase(String name);

    @Query("select s from Station s where lower(s.name) like lower(concat('%', :textToSearch,'%'))")
    public Page<Station> searchByName(String textToSearch, Pageable pageable);

    public Page<Station> findAll(Pageable pageable);
}
