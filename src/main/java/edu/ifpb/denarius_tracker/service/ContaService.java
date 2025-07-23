package edu.ifpb.denarius_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ifpb.denarius_tracker.entities.Conta;
import edu.ifpb.denarius_tracker.entities.Correntista;
import edu.ifpb.denarius_tracker.repository.ContaRepository;

@Component
public class ContaService implements Service<Conta, Integer> {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CorrentistaService correntistaService;

    @Override
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    public Conta findById(Integer id) {
        return contaRepository.findById(id).orElse(null);
    }

    @Override
    public Conta save(Conta conta) {
        Correntista correntista = correntistaService.findById(conta.getCorrentista().getId());
        conta.setCorrentista(correntista);
        return contaRepository.save(conta);
    }

    public Conta findByNumeroWithTransacoes(String nuConta) {
        return contaRepository.findByNumeroWithTransacoes(nuConta);

    }

    public Conta findByIdWithTransacoes(Integer idConta) {
        return contaRepository.findByIdWithTransacoes(idConta);
    }

}