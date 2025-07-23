package edu.ifpb.denarius_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ifpb.denarius_tracker.entities.Conta;
import edu.ifpb.denarius_tracker.entities.Correntista;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {
    Conta findByCorrentista(Correntista correntista);

    @Query("from Conta c left join fetch c.transacoes t where c.numero = :numero")
    Conta findByNumeroWithTransacoes(String numero);

    @Query("select distinct c from Conta c left join fetch c.transacoes t where c.id = :id")
    Conta findByIdWithTransacoes(Integer id);

}
