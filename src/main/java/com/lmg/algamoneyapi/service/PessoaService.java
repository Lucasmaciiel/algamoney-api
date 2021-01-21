package com.lmg.algamoneyapi.service;

import com.lmg.algamoneyapi.model.Pessoa;
import com.lmg.algamoneyapi.repository.PessoaRepository;
import lombok.Builder;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Builder
public class PessoaService {

    private final PessoaRepository repository;

    public ResponseEntity<Pessoa> update(Long id,Pessoa pessoa){
        Pessoa pessoaSalva = repository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
        return ResponseEntity.ok(repository.save(pessoaSalva));
    }
}
