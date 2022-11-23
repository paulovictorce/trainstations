package com.trainstation.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500, unique = true)
    @ApiModelProperty(notes = "Station name")
    private String name;

    @Column(nullable = false, length = 300)
    @ApiModelProperty(notes = "Station services")
    private String service;


}
