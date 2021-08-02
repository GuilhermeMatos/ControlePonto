package com.ilha.controlehoras.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Classe de teste para utilitário DataUtil
 * @see DataUtil
 *
 * @author Guilherme Matos
 */
public class DataUtilTests {

    @Test
    void isFimSemana() {
        assertFalse(DataUtil.isFimSemana(LocalDate.of(2021,7,26))); // Segunda
        assertFalse(DataUtil.isFimSemana(LocalDate.of(2021,7,27))); // Terça
        assertFalse(DataUtil.isFimSemana(LocalDate.of(2021,7,28))); // Quarta
        assertFalse(DataUtil.isFimSemana(LocalDate.of(2021,7,29))); // Quinta
        assertFalse(DataUtil.isFimSemana(LocalDate.of(2021,7,30))); // Sexta
        assertTrue(DataUtil.isFimSemana(LocalDate.of(2021,7,31))); // Sabado
        assertTrue(DataUtil.isFimSemana(LocalDate.of(2021,8,1))); // Domingo
    }
}
