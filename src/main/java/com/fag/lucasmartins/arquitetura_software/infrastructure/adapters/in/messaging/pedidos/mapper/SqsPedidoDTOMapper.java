package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoProdutoBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.ProdutoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.dto.PedidoDTO;

public class SqsPedidoDTOMapper {

    public static PedidoBO toBO(PedidoDTO dto) {

        PessoaBO pessoa = new PessoaBO();
        pessoa.setId(dto.getCustomerId());

        List<PedidoProdutoBO> itens = dto.getOrderItems()
                .stream()
                .map(item -> {
                    ProdutoBO produto = new ProdutoBO();
                    produto.setId(item.getSku());

                    PedidoProdutoBO pedidoProduto = new PedidoProdutoBO();
                    pedidoProduto.setProduto(produto);
                    pedidoProduto.setQuantidade(item.getAmount());
                    return pedidoProduto;
                })
                .collect(Collectors.toList());

        PedidoBO pedido = new PedidoBO();
        pedido.setCep(dto.getZipCode());
        pedido.setPessoa(pessoa);
        pedido.setItens(itens);

        return pedido;
    }
}