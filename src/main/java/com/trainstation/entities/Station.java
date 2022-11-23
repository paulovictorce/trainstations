package com.trainstation.entities;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull(message = "Station name is mandatory!")
    @ApiModelProperty(notes = "Station name")
    private String name;

    @NotBlank(message = "Station service is mandatory!")
    @ApiModelProperty(notes = "Station services")
    private String service;


}
