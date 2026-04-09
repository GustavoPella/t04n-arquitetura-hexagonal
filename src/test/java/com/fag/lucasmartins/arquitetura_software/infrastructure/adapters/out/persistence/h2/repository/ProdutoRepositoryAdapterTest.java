package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.repository;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.ProdutoEntity;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.jpa.ProdutoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoRepositoryAdapterTest {

    @Mock
    private ProdutoJpaRepository jpaRepository;

    @InjectMocks
    private ProdutoRepositoryAdapter adapter;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado um ProdutoBO," +
            "Quando salvar no adapter," +
            "Deve mapear para Entity, chamar o JpaRepository e retornar BO mapeado")
    void dadoBO_quandoSalvar_deveChamarJpaEMapear() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Teclado");
        bo.setEstoque(11);
        bo.setPreco(199.9);
        bo.setPrecoFinal(179.9);

        // não precisamos do retorno do save, apenas garantir que foi chamado com os valores corretos
        ArgumentCaptor<ProdutoEntity> captor = ArgumentCaptor.forClass(ProdutoEntity.class);

        ProdutoBO retorno = adapter.salvar(bo);

        verify(jpaRepository, times(1)).save(captor.capture());
        ProdutoEntity entidadeEnviada = captor.getValue();
        assertEquals("Teclado", entidadeEnviada.getNome());
        assertEquals(11, entidadeEnviada.getEstoque());
        assertEquals(199.9, entidadeEnviada.getPreco(), 0.0001);
        assertEquals(179.9, entidadeEnviada.getPrecoFinal(), 0.0001);

        // retorno deve ser o BO mapeado a partir da entidade (que por sua vez foi mapeada do BO)
        assertEquals("Teclado", retorno.getNome());
        assertEquals(11, retorno.getEstoque());
        assertEquals(199.9, retorno.getPreco(), 0.0001);
        assertEquals(179.9, retorno.getPrecoFinal(), 0.0001);
    }
}
