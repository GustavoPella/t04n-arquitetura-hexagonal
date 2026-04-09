package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoBOTest {

    @Test
    @DisplayName("Dado um produto Premium com preço abaixo de R$ 100," +
            "Quando validar o preço," +
            "Deve lançar DomainException")
    void dadoProdutoPremiumComPrecoAbaixoDe100_quandoValidarPreco_deveLancarDomainException() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Produto Premium");
        bo.setPreco(99.99);

        DomainException ex = assertThrows(DomainException.class, bo::validarPrecoProdutoPremium);
        assertEquals("Erro: Produtos Premium não podem custar menos de R$ 100,00.", ex.getMessage());
    }

    @Test
    @DisplayName("Dado um produto Premium com preço igual ou acima de R$ 100," +
            "Quando validar o preço," +
            "Deve não lançar exceção")
    void dadoProdutoPremiumComPrecoAcimaOuIgualA100_quandoValidarPreco_deveNaoLancarExcecao() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Premium X");
        bo.setPreco(150.00);

        assertDoesNotThrow(bo::validarPrecoProdutoPremium);
    }

    @Test
    @DisplayName("Dado um produto comum," +
            "Quando validar o preço," +
            "Deve não lançar exceção")
    void dadoProdutoComum_quandoValidarPreco_deveNaoLancarExcecao() {
        ProdutoBO bo = new ProdutoBO();
        bo.setNome("Básico");
        bo.setPreco(10.00);

        assertDoesNotThrow(bo::validarPrecoProdutoPremium);
    }

    @Test
    @DisplayName("Dado um produto com estoque >= 50," +
            "Quando calcular o preço final," +
            "Deve aplicar 10% de desconto")
    void dadoEstoqueMaiorOuIgual50_quandoCalcularPrecoFinal_deveAplicarDesconto() {
        ProdutoBO bo = new ProdutoBO();
        bo.setPreco(200.0);
        bo.setEstoque(50);

        bo.calcularPrecoFinalPorEstoqueBaixo();

        assertEquals(180.0, bo.getPrecoFinal(), 0.0001);
    }

    @Test
    @DisplayName("Dado um produto com estoque < 50," +
            "Quando calcular o preço final," +
            "Deve manter o preço final igual a 0 (sem desconto aplicado)")
    void dadoEstoqueMenorQue50_quandoCalcularPrecoFinal_deveNaoAplicarDesconto() {
        ProdutoBO bo = new ProdutoBO();
        bo.setPreco(200.0);
        bo.setEstoque(10);

        bo.calcularPrecoFinalPorEstoqueBaixo();

        assertEquals(0.0, bo.getPrecoFinal(), 0.0001);
    }

    @Test
    @DisplayName("Dado um produto com estoque nulo," +
            "Quando calcular o preço final," +
            "Deve não alterar o preço final")
    void dadoEstoqueNulo_quandoCalcularPrecoFinal_deveNaoAlterarPrecoFinal() {
        ProdutoBO bo = new ProdutoBO();
        bo.setPreco(120.0);
        bo.setEstoque(null);

        bo.calcularPrecoFinalPorEstoqueBaixo();

        assertEquals(0.0, bo.getPrecoFinal(), 0.0001);
    }
}
