package com.fag.lucasmartins.arquitetura_software.application.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.ProdutoRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.commands.AdicionarEstoqueCommand;
import com.fag.lucasmartins.arquitetura_software.core.domain.commands.DiminuirEstoqueCommand;

class EstoqueServiceTest {

    private ProdutoRepositoryPort produtoRepositoryPort;
    private EstoqueService service;

    @BeforeEach
    void setup() {
        produtoRepositoryPort = mock(ProdutoRepositoryPort.class);
        service = new EstoqueService(produtoRepositoryPort);
    }

    @Test
    void adicionarEstoque_deveSomarESalvar() {
        ProdutoBO produto = new ProdutoBO();
        produto.setId(10);
        produto.setEstoque(5);
        when(produtoRepositoryPort.encontrarPorId(10)).thenReturn(produto);

        service.adicionarEstoque(new AdicionarEstoqueCommand(10, 7));

        verify(produtoRepositoryPort).salvar(argThat(p -> p.getEstoque() == 12));
    }

    @Test
    void diminuirEstoque_deveSubtrairEPorSalvar() {
        ProdutoBO produto = new ProdutoBO();
        produto.setId(10);
        produto.setEstoque(10);
        when(produtoRepositoryPort.encontrarPorId(10)).thenReturn(produto);

        service.diminuirEstoque(new DiminuirEstoqueCommand(10, 3));

        verify(produtoRepositoryPort).salvar(argThat(p -> p.getEstoque() == 7));
    }
}
