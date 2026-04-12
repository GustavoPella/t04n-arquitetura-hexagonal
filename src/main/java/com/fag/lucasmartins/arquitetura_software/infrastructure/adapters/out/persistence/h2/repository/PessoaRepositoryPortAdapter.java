// pacote: com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.repository

package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.repository;

import com.fag.lucasmartins.arquitetura_software.application.ports.out.persistence.h2.PessoaRepositoryPort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.entity.PessoaEntity;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.jpa.PessoaJpaRepository;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.out.persistence.h2.mapper.PessoaMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PessoaRepositoryPortAdapter implements PessoaRepositoryPort {

    private final PessoaJpaRepository jpaRepository;
    private final PessoaMapper pessoaMapper;

    public PessoaRepositoryPortAdapter(PessoaJpaRepository jpaRepository, PessoaMapper pessoaMapper) {
        this.jpaRepository = jpaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    @Override
    public PessoaBO save(PessoaBO pessoaBO) {
        PessoaEntity entity = pessoaMapper.toEntity(pessoaBO);
        PessoaEntity saved = jpaRepository.save(entity);
        return pessoaMapper.toBO(saved);
    }

    @Override
    public Optional<PessoaBO> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(pessoaMapper::toBO);
    }

    @Override
    public List<PessoaBO> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(pessoaMapper::toBO)
                .collect(Collectors.toList());
    }
}