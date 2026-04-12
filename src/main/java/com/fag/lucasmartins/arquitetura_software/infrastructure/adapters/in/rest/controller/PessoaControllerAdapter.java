package com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.controller;

import com.fag.lucasmartins.arquitetura_software.application.ports.in.service.PessoaServicePort;
import com.fag.lucasmartins.arquitetura_software.core.domain.bo.PessoaBO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PessoaRequestDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.dto.PessoaResponseDTO;
import com.fag.lucasmartins.arquitetura_software.infrastructure.adapters.in.rest.mapper.PessoaDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaControllerAdapter {

    private final PessoaServicePort pessoaServicePort;
    private final PessoaDTOMapper mapper;

    public PessoaControllerAdapter(PessoaServicePort pessoaServicePort, PessoaDTOMapper mapper) {
        this.pessoaServicePort = pessoaServicePort;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<PessoaResponseDTO> cadastrar(@RequestBody PessoaRequestDTO requestDTO) {
        PessoaBO bo = mapper.toBO(requestDTO);
        PessoaBO salvo = pessoaServicePort.cadastrar(bo);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDTO(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaResponseDTO> buscarPorId(@PathVariable UUID id) {
        PessoaBO bo = pessoaServicePort.buscarPorId(id);
        return ResponseEntity.ok(mapper.toResponseDTO(bo));
    }

    @GetMapping
    public ResponseEntity<List<PessoaResponseDTO>> listarTodos() {
        List<PessoaResponseDTO> lista = pessoaServicePort.listarTodos()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }
}