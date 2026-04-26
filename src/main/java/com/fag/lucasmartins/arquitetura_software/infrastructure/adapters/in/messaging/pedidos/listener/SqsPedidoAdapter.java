package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PedidoServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PedidoBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.dto.PedidoDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.messaging.pedidos.mapper.SqsPedidoDTOMapper;

import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class SqsPedidoAdapter {

    private static final Logger log = LoggerFactory.getLogger(SqsPedidoAdapter.class);

    private final PedidoServicePort pedidoServicePort;

    public SqsPedidoAdapter(PedidoServicePort pedidoServicePort) { 
        this.pedidoServicePort = pedidoServicePort;
    }

    @SqsListener(value = "${aws.sqs.queue.order.events}")
    public void receberMensagem(PedidoDTO dto) {
    try {
        log.info("Evento de pedido recebido para o cliente {}", dto.getCustomerId());

        final PedidoBO bo = SqsPedidoDTOMapper.toBO(dto);
        pedidoServicePort.criarPedido(bo);

        log.info("Pedido processado com sucesso para o cliente {}", dto.getCustomerId());
    } catch (Exception e) {
        log.error("Erro ao processar pedido para o cliente {}", dto.getCustomerId(), e);
        throw e;
    }
}
}