package com.lmg.algamoneyapi.controller;

import com.lmg.algamoneyapi.model.Categoria;
import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.model.Pessoa;
import com.lmg.algamoneyapi.repository.LancamentoRepository;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
@Builder
public class LancamentoController {

    private final LancamentoRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Lancamento> save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento obj = repository.save(lancamento);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(obj.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(obj);
    }

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
