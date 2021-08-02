package com.ilha.controlehoras.repository;

import com.ilha.controlehoras.model.Batida;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe Repository para entidade Batida
 *
 * @author Guilherme Matos
 */
public interface BatidaRepository  extends MongoRepository<Batida, String> {

    @Query("{ 'dataBatida': ?0 }")
    public List<Batida> findByDataBatida(LocalDate dataBatida);
}
