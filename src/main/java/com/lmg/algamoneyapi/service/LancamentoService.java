package com.lmg.algamoneyapi.service;

import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.model.Pessoa;
import com.lmg.algamoneyapi.repository.LancamentoRepository;
import com.lmg.algamoneyapi.repository.PessoaRepository;
import com.lmg.algamoneyapi.service.exceptions.PessoaInexistenteOuInativaException;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Lancamento update(Long codigo, Lancamento lancamento){
        Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
        if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())){
            validarPessoa(lancamento);
        }
        BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");
        return lancamentoRepository.save(lancamentoSalvo);
    }

    private void validarPessoa(Lancamento lancamento) {
        Optional<Pessoa> pessoaOpt = null;
        if (lancamento.getPessoa().getCodigo() != null){
            pessoaOpt = pessoaRepository.findById(lancamento.getPessoa().getCodigo()); //TODO: trocar pra findOne ou findById
        }

        if (!pessoaOpt.isPresent() || pessoaOpt.get().isInativo()){
            throw new PessoaInexistenteOuInativaException();
        }
    }

    private Lancamento buscarLancamentoExistente(Long codigo) {
       Optional<Lancamento> lancamentoSalvoOpt = lancamentoRepository.findById(codigo); //TODO: trocar pra findOne ou findById
       if (!lancamentoSalvoOpt.isPresent()) {
           throw new IllegalArgumentException();
       }
       return lancamentoSalvoOpt.get();
    }
}
