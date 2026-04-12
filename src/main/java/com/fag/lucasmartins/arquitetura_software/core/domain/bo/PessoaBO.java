package com.fag.lucasmartins.arquitetura_software.core.domain.bo;

import com.fag.lucasmartins.arquitetura_software.core.domain.exceptions.DomainException;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class PessoaBO {

    private UUID id;
    private String nomeCompleto;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;

    public PessoaBO(String nomeCompleto, String cpf, LocalDate dataNascimento,
                    String email, String telefone) {
        validarNome(nomeCompleto);
        validarCpf(cpf);
        validarIdade(dataNascimento);
        validarEmail(email);
        validarTelefone(telefone);

        this.id = UUID.randomUUID();
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
    }

    public PessoaBO(UUID id, String nomeCompleto, String cpf, LocalDate dataNascimento,
                    String email, String telefone) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
    }

    private void validarNome(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            throw new DomainException("Nome completo é obrigatório.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new DomainException("CPF é obrigatório.");
        }
        if (cpf.length() != 11) {
            throw new DomainException("CPF deve conter exatamente 11 dígitos.");
        }
    }

    private void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) {
            throw new DomainException("Data de nascimento é obrigatória.");
        }
        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade < 18) {
            throw new DomainException("Idade mínima de 18 anos não atendida.");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new DomainException("E-mail é obrigatório.");
        }
        if (!email.contains("@")) {
            throw new DomainException("E-mail inválido. Deve conter '@'.");
        }
    }

    private void validarTelefone(String telefone) {
        if (telefone == null || telefone.isBlank()) {
            throw new DomainException("Telefone é obrigatório.");
        }
        if (telefone.length() != 11) {
            throw new DomainException("Telefone deve conter exatamente 11 dígitos (sem parênteses ou traços).");
        }
    }

    public UUID getId() { return id; }
    public String getNomeCompleto() { return nomeCompleto; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
}