package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.ProdutoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ProdutoDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProdutoControllerAdapterTest {

    @Mock
    private ProdutoServicePort servicePort;

    @InjectMocks
    private ProdutoControllerAdapter controller;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Dado um payload válido," +
            "Quando chamar POST /produtos," +
            "Deve retornar 201 com o corpo do produto criado")
    void dadoPayloadValido_quandoPostProdutos_deveRetornar201() throws Exception {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Caderno");
        dto.setEstoque(5);
        dto.setPreco(15.0);

        ProdutoBO boRetorno = new ProdutoBO();
        boRetorno.setNome("Caderno");
        boRetorno.setEstoque(5);
        boRetorno.setPreco(15.0);

        when(servicePort.salvar(any(ProdutoBO.class))).thenReturn(boRetorno);

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Caderno"))
                .andExpect(jsonPath("$.estoque").value(5))
                .andExpect(jsonPath("$.preco").value(15.0));
    }

    @Test
    @DisplayName("Dado que o serviço lança DomainException," +
            "Quando chamar POST /produtos," +
            "Deve retornar 400 com a mensagem de erro no corpo")
    void dadoServicoLancaDomainException_quandoPostProdutos_deveRetornar400() throws Exception {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Premium");
        dto.setEstoque(1);
        dto.setPreco(50.0);

        when(servicePort.salvar(any(ProdutoBO.class)))
                .thenThrow(new DomainException("Erro: Produtos Premium não podem custar menos de R$ 100,00."));

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Erro: Produtos Premium não podem custar menos de R$ 100,00."))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @DisplayName("Dado que o serviço lança uma exceção genérica," +
            "Quando chamar POST /produtos," +
            "Deve retornar 500 com mensagem padrão")
    void dadoServicoLancaExcecaoGenerica_quandoPostProdutos_deveRetornar500() throws Exception {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Item");
        dto.setEstoque(1);
        dto.setPreco(10.0);

        when(servicePort.salvar(any(ProdutoBO.class))).thenThrow(new RuntimeException("boom"));

        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Erro interno no servidor"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
