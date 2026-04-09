package com.fag.lucasmartins.arquitetura_software.application.services;

import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.h2.ProdutoRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @Mock
    private ProdutoRepositoryPort repositoryPort;

    @InjectMocks
    private ProdutoService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado um Produto com estoque >= 50," +
            "Quando salvar," +
            "Deve aplicar desconto e chamar o repositório uma vez")
    void dadoEstoqueMaiorIgual50_quandoSalvar_deveAplicarDescontoEChamarRepositorio() {
        ProdutoBO entrada = new ProdutoBO();
        entrada.setNome("Produto");
        entrada.setPreco(200.0);
        entrada.setEstoque(50);

        when(repositoryPort.salvar(any(ProdutoBO.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProdutoBO saida = service.salvar(entrada);

        assertNotNull(saida);
        assertEquals(180.0, saida.getPrecoFinal(), 0.0001);
        verify(repositoryPort, times(1)).salvar(any(ProdutoBO.class));
    }

    @Test
    @DisplayName("Dado um Produto Premium com preço abaixo de R$ 100," +
            "Quando salvar," +
            "Deve lançar DomainException e não chamar o repositório")
    void dadoProdutoPremiumBarato_quandoSalvar_deveLancarExcecaoENaoChamarRepositorio() {
        ProdutoBO entrada = new ProdutoBO();
        entrada.setNome("Super Premium");
        entrada.setPreco(50.0);
        entrada.setEstoque(100);

        assertThrows(DomainException.class, () -> service.salvar(entrada));
        verifyNoInteractions(repositoryPort);
    }
}
