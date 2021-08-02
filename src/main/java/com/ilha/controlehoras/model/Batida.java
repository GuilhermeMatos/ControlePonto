package com.ilha.controlehoras.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidade Batida
 *
 * @author Guilherme Matos
 */
@Data
@Document
public class Batida implements Serializable {

    @Id
    private String id;

    @NonNull
    private LocalDate dataBatida;

    @NonNull
    private LocalTime primeiraBatida;

    @NonNull
    private LocalTime segundaBatida;

    @NonNull
    private LocalTime terceiraBatida;

    @NonNull
    private LocalTime quartaBatida;
}

