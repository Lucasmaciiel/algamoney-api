package com.lmg.algamoneyapi.controller;

import com.lmg.algamoneyapi.model.Categoria;
import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.repository.LancamentoRepository;
import lombok.Builder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lancamentos")
@Builder
public class LancamentoController {

    private final LancamentoRepository repository;

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> findById(@PathVariable(name = "codigo") Long id) {
        return repository.findById(id)
                .map(obj -> ResponseEntity.ok(obj))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Lancamento> findAll() {
        return repository.findAll();
    }
}
