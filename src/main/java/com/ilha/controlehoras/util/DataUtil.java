package com.ilha.controlehoras.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Classe utilitária para operações com data
 *
 * @author Guilherme Matos
 */
public final class DataUtil {

    /**
     * Verifica se data informada é fim de semana
     *
     * @param data
     * @return
     */
    public static Boolean isFimSemana(LocalDate data) {
        return data.getDayOfWeek() == DayOfWeek.SATURDAY || data.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
