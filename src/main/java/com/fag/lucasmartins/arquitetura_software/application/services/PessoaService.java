
package com.fag.lucasmartins.arquitetura_software.application.services;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PessoaServicePort;
import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.h2.PessoaRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PessoaService implements PessoaServicePort {

    private final PessoaRepositoryPort pessoaRepositoryPort;

    public PessoaService(PessoaRepositoryPort pessoaRepositoryPort) {
        this.pessoaRepositoryPort = pessoaRepositoryPort;
    }

    @Override
    public PessoaBO cadastrar(PessoaBO pessoaBO) {
         return pessoaRepositoryPort.save(pessoaBO);
    }

    @Override
    public PessoaBO buscarPorId(UUID id) {
        return pessoaRepositoryPort.findById(id)
                .orElseThrow(() -> new DomainException("Pessoa não encontrada com o id: " + id));
    }

    @Override
    public List<PessoaBO> listarTodos() {
        return pessoaRepositoryPort.findAll();
    }
}