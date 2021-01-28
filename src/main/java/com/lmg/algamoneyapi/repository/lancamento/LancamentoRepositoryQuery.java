package com.lmg.algamoneyapi.repository.lancamento;

import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LancamentoRepositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
