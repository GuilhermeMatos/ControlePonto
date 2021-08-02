package com.ilha.controlehoras.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excessão quando já encontra um registro de batidades de ponto naquele dia.
 *
 * @author Guilherme Matos
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}

