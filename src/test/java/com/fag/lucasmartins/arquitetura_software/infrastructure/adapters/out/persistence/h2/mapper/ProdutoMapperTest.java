package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoMapperTest {

    @Test
    @DisplayName("Dado um ProdutoBO preenchido," +
            "Quando converter para Entity," +
            "Deve mapear todos os campos corretamente")
    void dadoBO_quandoToEntity_deveMapearCampos() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Notebook");
        bo.setEstoque(7);
        bo.setPreco(3500.0);
        bo.setPrecoFinal(3200.0);

        ProdutoEntity entity = ProdutoMapper.toEntity(bo);

        assertEquals("Notebook", entity.getNome());
        assertEquals(7, entity.getEstoque());
        assertEquals(3500.0, entity.getPreco(), 0.0001);
        assertEquals(3200.0, entity.getPrecoFinal(), 0.0001);
    }

    @Test
    @DisplayName("Dado um ProdutoEntity preenchido," +
            "Quando converter para BO," +
            "Deve mapear todos os campos corretamente")
    void dadoEntity_quandoToBO_deveMapearCampos() {
        ProdutoEntity entity = new ProdutoEntity();
        entity.setNome("Monitor");
        entity.setEstoque(3);
        entity.setPreco(899.99);
        entity.setPrecoFinal(799.99);

        ProdutoBO bo = ProdutoMapper.toBO(entity);

        assertEquals("Monitor", bo.getNome());
        assertEquals(3, bo.getEstoque());
        assertEquals(899.99, bo.getPreco(), 0.0001);
        assertEquals(799.99, bo.getPrecoFinal(), 0.0001);
    }
}
