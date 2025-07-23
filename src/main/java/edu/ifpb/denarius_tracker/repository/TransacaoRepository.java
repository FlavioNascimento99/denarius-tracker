package edu.ifpb.denarius_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ifpb.denarius_tracker.entities.Conta;
import edu.ifpb.denarius_tracker.entities.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {
    Transacao findByConta(Conta conta);

    @Query("select t from Transacao t where t.id = :id")
    Transacao findTransacaoById(Integer id);

    @Query("select t from Transacao t where t.conta.numero = :nuConta")
    Transacao findTransacaoByNuConta(String nuConta);
}
