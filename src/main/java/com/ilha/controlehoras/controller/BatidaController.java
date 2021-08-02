package com.ilha.controlehoras.controller;

import com.ilha.controlehoras.model.Batida;
import com.ilha.controlehoras.service.BatidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Controller para Batidas do ponto.
 *
 * @author Guilherme Matos
 */
@RestController
@RequestMapping("/batidas")
public class BatidaController {

    @Autowired
    private BatidaService batidaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void baterPonto(@RequestBody Batida batida) {
        this.batidaService.baterPonto(batida);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void alterarPonto(@RequestBody Batida batida) {
        this.batidaService.alterarPonto(batida);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deletarPonto(@RequestParam("id") String id) {
        this.batidaService.deletarPonto(id);
    }

    @GetMapping("/obter")
    public List<Batida> obterBatidas() {
        return this.batidaService.obterBatidas();
    }
}
