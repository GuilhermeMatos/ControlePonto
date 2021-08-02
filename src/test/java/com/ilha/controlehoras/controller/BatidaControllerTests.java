package com.ilha.controlehoras.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.ilha.controlehoras.model.Batida;
import com.ilha.controlehoras.service.BatidaService;
import com.ilha.controlehoras.service.exception.BadRequestException;
import com.ilha.controlehoras.service.exception.ConflictException;
import com.ilha.controlehoras.service.exception.ForbidenException;
import com.ilha.controlehoras.util.MensagemError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe teste para controller das Batidas
 *
 * @author Guilherme Matos
 */
@SpringBootTest
@AutoConfigureMockMvc
public class BatidaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BatidaService batidaService;

    /**
     * Teste quando há uma tentativa de bater o ponto com sucesso.
     *
     * @throws Exception
     */
    @Test
    void baterPontoSucesso() throws Exception {

        LocalDate dataBatida = LocalDate.of(2021,7, 30);

        this.batidaService.deletarPonto(dataBatida);

        Batida batida = new Batida(
                dataBatida,
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        this.mockMvc.perform(post("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isCreated());
    }

    /**
     * Teste quando há uma tentativa de alterar bater o ponto com sucesso.
     *
     * @throws Exception
     */
    @Test
    void alterarPontoSucesso() throws Exception {

        LocalDate dataBatida = LocalDate.of(2021,7, 30);

        this.batidaService.deletarPonto(dataBatida);

        Batida batida = new Batida(
                dataBatida,
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        Batida bat = this.batidaService.baterPonto(batida);

        bat.setPrimeiraBatida(LocalTime.of(9,0));

        this.mockMvc.perform(put("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isOk());
    }

    /**
     * Teste quando há uma tentativa de alterar bater o ponto com id inexistente.
     *
     * @throws Exception
     */
    @Test
    void obterPonto() throws Exception {

        LocalDate dataBatida = LocalDate.of(2021,7, 30);

        this.batidaService.deletarPonto(dataBatida);

        Batida batida = new Batida(
                dataBatida,
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        Batida bat = this.batidaService.baterPonto(batida);

        MvcResult result = this.mockMvc.perform(get("/batidas/obter")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isOk())
                .andReturn();

        Type listType = new TypeToken<ArrayList<LinkedTreeMap>>(){}.getType();
        List<LinkedTreeMap> listBatida = new Gson().fromJson(result.getResponse().getContentAsString(), listType);
        LinkedTreeMap batidaRetornada = listBatida.get(0);

        //TODO Descobrir por que não está sendo possivel serializar o objeto Batida que está vinda da resposta do endpoint
        assertEquals(bat.getId(), batidaRetornada.get("id"));
        assertEquals(bat.getDataBatida(), LocalDate.parse(batidaRetornada.get("dataBatida").toString()));
        assertEquals(bat.getPrimeiraBatida(), LocalTime.parse(batidaRetornada.get("primeiraBatida").toString()));
        assertEquals(bat.getSegundaBatida(), LocalTime.parse(batidaRetornada.get("segundaBatida").toString()));
        assertEquals(bat.getTerceiraBatida(), LocalTime.parse(batidaRetornada.get("terceiraBatida").toString()));
        assertEquals(bat.getQuartaBatida(), LocalTime.parse(batidaRetornada.get("quartaBatida").toString()));
    }

    /**
     * Teste quando há uma tentativa de deletar bater o ponto.
     *
     * @throws Exception
     */
    @Test
    void deletarPonto() throws Exception {

        LocalDate dataBatida = LocalDate.of(2021,7, 30);

        this.batidaService.deletarPonto(dataBatida);

        Batida batida = new Batida(
                dataBatida,
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        Batida bat = this.batidaService.baterPonto(batida);

        this.mockMvc.perform(delete("/batidas?id=" + bat.getId())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isOk());

        assertTrue(this.batidaService.obterBatidas().isEmpty());
    }

    /**
     * Teste quando há uma tentativa de alterar bater o ponto com id não informado.
     *
     * @throws Exception
     */
    @Test
    void alterarPontoIdNaoInformado() throws Exception {

        Batida batida = new Batida(
                LocalDate.of(2021,7, 30),
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        this.mockMvc.perform(put("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isBadRequest());

        try {
            this.batidaService.alterarPonto(batida);
            fail();
        }catch (Exception e) {
            assertTrue(e instanceof BadRequestException);
            assertEquals(MensagemError.ERROR_ID_CAMPO_OBRIGATORIO, e.getMessage());
        }
    }

    /**
     * Teste quando há uma tentativa de alterar bater o ponto com id inexistente.
     *
     * @throws Exception
     */
    @Test
    void alterarPontoIdinexistente() throws Exception {

        Batida batida = new Batida(
                LocalDate.of(2021,7, 30),
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        batida.setId("idInexistente");

        this.mockMvc.perform(put("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isBadRequest());

        try {
            this.batidaService.alterarPonto(batida);
            fail();
        }catch (Exception e) {
            assertTrue(e instanceof BadRequestException);
            assertEquals(MensagemError.ERROR_BATIDA_NAO_ENCONTRADA, e.getMessage());
        }
    }

    /**
     * Teste quando há uma tentativa de bater o ponto já batido.
     *
     * @throws Exception
     */
    @Test
    void baterPontoJaCastrado() throws Exception {

        LocalDate dataBatida = LocalDate.of(2021,7, 30);

        this.batidaService.deletarPonto(dataBatida);

        Batida batida = new Batida(
                LocalDate.of(2021,7, 30),
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        this.mockMvc.perform(post("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isCreated());

        this.mockMvc.perform(post("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isConflict());

        try {
            this.batidaService.baterPonto(batida);
            fail();
        }catch (Exception e) {
            assertTrue(e instanceof ConflictException);
            assertEquals(MensagemError.ERROR_HORARIO_CADASTRADO, e.getMessage());
        }
    }

    /**
     * Teste quando há uma tentativa de bater o ponto no fim de semana
     *
     * @throws Exception
     */
    @Test
    void baterPontoErroFimSemana() throws Exception {

        Batida batida = new Batida(
                LocalDate.of(2021,8, 1),
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        this.mockMvc.perform(post("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isForbidden());

        try {
            this.batidaService.baterPonto(batida);
            fail();
        }catch (Exception e) {
            assertTrue(e instanceof ForbidenException);
            assertEquals(MensagemError.ERROR_FIM_SEMANA, e.getMessage());
        }
    }

    /**
     * Teste quando há uma tentativa de bater o ponto dia futuro.
     *
     * @throws Exception
     */
    @Test
    void baterPontoViajanteDoTempo() throws Exception {

        Batida batida = new Batida(
                LocalDate.now().plusDays(1),
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(13,0),
                LocalTime.of(17,0)
        );

        this.mockMvc.perform(post("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isForbidden());

        try {
            this.batidaService.baterPonto(batida);
            fail();
        }catch (Exception e) {
            assertTrue(e instanceof ForbidenException);
            assertEquals(MensagemError.ERROR_BATIDA_FUTURA, e.getMessage());
        }
    }

    /**
     * Teste quando há uma tentativa de bater o ponto com menos de uma hora de almoço.
     *
     * @throws Exception
     */
    @Test
    void baterPontoUmaHoraAlmoco() throws Exception {

        Batida batida = new Batida(
                LocalDate.of(2021,7, 30),
                LocalTime.of(8,0),
                LocalTime.of(12,0),
                LocalTime.of(12,30),
                LocalTime.of(17,0)
        );

        this.mockMvc.perform(post("/batidas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(batida)))
                .andExpect(status().isForbidden());

        try {
            this.batidaService.baterPonto(batida);
            fail();
        }catch (Exception e) {
            assertTrue(e instanceof ForbidenException);
            assertEquals(MensagemError.ERROR_UMA_HORA_ALMOCO, e.getMessage());
        }
    }
}
