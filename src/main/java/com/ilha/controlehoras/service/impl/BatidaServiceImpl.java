package com.ilha.controlehoras.service.impl;

import com.ilha.controlehoras.model.Batida;
import com.ilha.controlehoras.repository.BatidaRepository;
import com.ilha.controlehoras.service.BatidaService;
import com.ilha.controlehoras.service.exception.BadRequestException;
import com.ilha.controlehoras.service.exception.ConflictException;
import com.ilha.controlehoras.service.exception.ForbidenException;
import com.ilha.controlehoras.util.DataUtil;
import com.ilha.controlehoras.util.MensagemError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Service para Batida
 *
 * @author Guilherme Matos
 */
@Service
public class BatidaServiceImpl  implements BatidaService {

    @Autowired
    private BatidaRepository batidaRepository;

    /**
     * (non-Javadoc)
     * @see BatidaService#baterPonto(Batida)
     */
    @Override
    public Batida baterPonto(Batida batida) {
        this.validarBatida(batida);

        if(this.isExisteBatida(batida.getDataBatida())) {
            throw new ConflictException(MensagemError.ERROR_HORARIO_CADASTRADO);
        }

        return this.batidaRepository.save(batida);
    }

    /**
     * (non-Javadoc)
     * @see BatidaService#alterarPonto(Batida)
     */
    @Override
    public void alterarPonto(Batida batida) {

        this.validarBatida(batida);

        if(batida.getId() == null) {
            throw new BadRequestException(MensagemError.ERROR_ID_CAMPO_OBRIGATORIO);
        }

        Batida bat = this.batidaRepository
                .findById(batida.getId())
                .orElseThrow(() -> new BadRequestException(MensagemError.ERROR_BATIDA_NAO_ENCONTRADA));

        bat.setPrimeiraBatida(batida.getPrimeiraBatida());
        bat.setSegundaBatida(batida.getSegundaBatida());
        bat.setTerceiraBatida(batida.getTerceiraBatida());
        bat.setTerceiraBatida(batida.getQuartaBatida());

        this.batidaRepository.save(bat);
    }

    /**
     * (non-Javadoc)
     * @see BatidaService#deletarPonto(String)
     */
    @Override
    public void deletarPonto(String id) {
        this.batidaRepository.deleteById(id);
    }

    /**
     * (non-Javadoc)
     * @see BatidaService#deletarPonto(LocalDate)
     */
    @Override
    public void deletarPonto(LocalDate dataBatida) {
        List<Batida> byDataBatida = batidaRepository.findByDataBatida(dataBatida);
        for (Batida batida : byDataBatida) {
            batidaRepository.deleteById(batida.getId());
        }
    }

    /**
     * (non-Javadoc)
     * @see BatidaService#obterBatidas()
     */
    @Override
    public List<Batida> obterBatidas() {
        return this.batidaRepository.findAll();
    }

    /**
     * (non-Javadoc)
     * @see BatidaService#isExisteBatida(LocalDate)
     */
    @Override
    public Boolean isExisteBatida(LocalDate dataBatida) {
        return !batidaRepository.findByDataBatida(dataBatida).isEmpty();
    }

    /**
     * Validações para saber se a batida de ponto está correta.
     *
     * @param batida
     */
    private void validarBatida(Batida batida) {

        if(batida.getDataBatida().isAfter(LocalDate.now())) {
            throw new ForbidenException(MensagemError.ERROR_BATIDA_FUTURA);
        }

        if(DataUtil.isFimSemana(batida.getDataBatida())) {
            throw new ForbidenException(MensagemError.ERROR_FIM_SEMANA);
        }

        if(this.isUmaHoraAlmoco(batida.getSegundaBatida(), batida.getTerceiraBatida())) {
            throw new ForbidenException(MensagemError.ERROR_UMA_HORA_ALMOCO);
        }
    }

    /**
     * Método que verifica se o colaborador tirou uma hora de almoço.
     *
     * @param saida
     * @param volta
     * @return
     */
    private Boolean isUmaHoraAlmoco(LocalTime saida, LocalTime volta) {
        return volta.minusHours(1).isBefore(saida);
    }
}
