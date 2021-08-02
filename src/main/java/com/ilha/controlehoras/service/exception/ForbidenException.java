package com.ilha.controlehoras.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excessão quando ponto está sendo marcado no fim de semana
 *
 * @author Guilherme Matos
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbidenException extends RuntimeException {
    public ForbidenException(String message) {
        super(message);
    }
}
