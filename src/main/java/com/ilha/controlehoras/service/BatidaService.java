package com.ilha.controlehoras.service;

import com.ilha.controlehoras.model.Batida;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface service para Batida
 *
 * @author Guilherme Matos
 */
public interface BatidaService {

    /**
     * Método responsável por incluir novas batidas no ponto.
     *
     * @param batida
     * @return
     */
    Batida baterPonto(Batida batida);

    /**
     * Método responsável por listar todas as batidas do ponto.
     *
     * @return
     */
    List<Batida> obterBatidas();

    /**
     * Método responsável por pesquisar se existe batida para aquele dia.
     *
     * @param dataBatida
     * @return
     */
    Boolean isExisteBatida(LocalDate dataBatida);

    /**
     * Método responsável por alterar novas batidas no ponto.
     *
     * @param batida
     */
    void alterarPonto(Batida batida);

    /**
     * Método responsável por deletar batida de ponto.
     *
     * @param id
     */
    void deletarPonto(String id);

    /**
     * Método responsável por deletar batida de ponto.
     *
     * @param dataBatida
     */
    void deletarPonto(LocalDate dataBatida);
}
