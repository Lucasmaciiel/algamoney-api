package com.lmg.algamoneyapi.controller;

import com.lmg.algamoneyapi.model.Categoria;
import com.lmg.algamoneyapi.repository.CategoriaRepository;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
@Builder
public class CategoriaController {

    private final CategoriaRepository repository;

    @GetMapping
    public List<Categoria> findAll() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria obj = repository.save(categoria);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{codigo}")
                .buildAndExpand(obj.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(obj);
    }

    //TODO: Corrigir
    @GetMapping("{codigo}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(obj -> ResponseEntity.ok(obj))
                .orElse(ResponseEntity.notFound().build());
    }

}
