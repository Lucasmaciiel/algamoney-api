package com.lmg.algamoneyapi.repository;

import com.lmg.algamoneyapi.model.Lancamento;
import com.lmg.algamoneyapi.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {
}
