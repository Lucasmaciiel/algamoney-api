package com.lmg.algamoneyapi.service;

import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.model.Pessoa;
import com.lmg.algamoneyapi.repository.LancamentoRepository;
import com.lmg.algamoneyapi.repository.PessoaRepository;
import com.lmg.algamoneyapi.service.exceptions.PessoaInexistenteOuInativaException;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class LancamentoService {

    private final PessoaRepository pessoaRepository;
    private final LancamentoRepository lancamentoRepository;

    public Lancamento save(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.getOne(lancamento.getPessoa().getCodigo());

        if (pessoa == null || pessoa.isInativo()){
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }
}
