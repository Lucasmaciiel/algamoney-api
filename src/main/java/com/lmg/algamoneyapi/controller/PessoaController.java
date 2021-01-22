package com.lmg.algamoneyapi.controller;

import com.lmg.algamoneyapi.model.Pessoa;
import com.lmg.algamoneyapi.repository.PessoaRepository;
import com.lmg.algamoneyapi.service.PessoaService;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pessoas")
@Builder
public class PessoaController {

    private final PessoaRepository repository;
    private final PessoaService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pessoa> save(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa obj = repository.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(obj.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> findByCode(@PathVariable(name = "codigo") Long id) {
        return repository.findById(id)
                .map(obj -> ResponseEntity.ok(obj))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "codigo") Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> update(@PathVariable(name = "codigo") Long id, @RequestBody Pessoa pessoa) {
        return service.update(id, pessoa);
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarPropriedadeAtivo(@PathVariable(name = "codigo") Long id, @RequestBody Boolean ativo){
        service.atualizarPropriedadeAtivo(id, ativo);
    }

}
