package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.ProdutoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoDTOMapperTest {

    @Test
    @DisplayName("Dado um ProdutoDTO preenchido," +
            "Quando converter para BO," +
            "Deve mapear todos os campos corretamente")
    void dadoDTO_quandoToBo_deveMapearCampos() {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setNome("Caneta");
        dto.setEstoque(10);
        dto.setPreco(5.5);

        ProdutoBO bo = ProdutoDTOMapper.toBo(dto);

        assertEquals("Caneta", bo.getNome());
        assertEquals(10, bo.getEstoque());
        assertEquals(5.5, bo.getPreco(), 0.0001);
    }

    @Test
    @DisplayName("Dado um ProdutoBO preenchido," +
            "Quando converter para DTO," +
            "Deve mapear todos os campos corretamente")
    void dadoBO_quandoToDto_deveMapearCampos() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Lápis");
        bo.setEstoque(20);
        bo.setPreco(2.3);

        ProdutoDTO dto = ProdutoDTOMapper.toDto(bo);

        assertEquals("Lápis", dto.getNome());
        assertEquals(20, dto.getEstoque());
        assertEquals(2.3, dto.getPreco(), 0.0001);
    }
}
