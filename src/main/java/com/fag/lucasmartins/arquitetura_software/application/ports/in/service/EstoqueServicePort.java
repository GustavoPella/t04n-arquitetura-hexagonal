package com.fag.lucasmartins.arquitetura_software.application.ports.in.service;

import com.fag.lucasmartins.arquitetura_software.core.domain.commands.AdicionarEstoqueCommand;
import com.fag.lucasmartins.arquitetura_software.core.domain.commands.DiminuirEstoqueCommand;

public interface EstoqueServicePort {

    void adicionarEstoque(AdicionarEstoqueCommand command);

    void diminuirEstoque(DiminuirEstoqueCommand command);
}
