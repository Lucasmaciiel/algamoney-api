package com.lmg.algamoneyapi.controller;

import com.lmg.algamoneyapi.exceptionHandler.AlgaMoneyExceptionHandler;
import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.repository.LancamentoRepository;
import com.lmg.algamoneyapi.service.LancamentoService;
import com.lmg.algamoneyapi.service.exceptions.PessoaInexistenteOuInativaException;
import lombok.Builder;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/lancamentos")
@Builder
public class LancamentoController {

    private final LancamentoRepository repository;
    private final LancamentoService service;
    private final MessageSource messageSource;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Lancamento> save(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento obj = service.save(lancamento);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(obj.getCodigo()).toUri();
        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> findById(@PathVariable(name = "codigo") Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Lancamento> findAll() {
        return repository.findAll();
    }

    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
        String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-inativa", null, LocaleContextHolder.getLocale());
        String mensagemDesenvolvedor = ex.toString();
        List<AlgaMoneyExceptionHandler.Erro> erros = Arrays.asList(new AlgaMoneyExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return ResponseEntity.badRequest().body(erros);
    }
}
