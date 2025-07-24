package edu.ifpb.denarius_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ifpb.denarius_tracker.entities.Conta;
import edu.ifpb.denarius_tracker.entities.Transacao;
import edu.ifpb.denarius_tracker.repository.TransacaoRepository;

@Component
public class TransacaoService implements ServiceGeneric<Transacao, Integer> {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaService contaService;

    @Override
    public List<Transacao> findAll() {
        return transacaoRepository.findAll();
    }

    @Override
    public Transacao findById(Integer id) {
        return transacaoRepository.findById(id).orElse(null);
    }

    @Override
    public Transacao save(Transacao transacao) {
        Conta conta = contaService.findById(transacao.getConta().getId());
        transacao.setConta(conta);
        return transacaoRepository.save(transacao);
    }

    public Transacao findTransacaoById(Integer id) {
        return transacaoRepository.findTransacaoById(id);
    }

    public Transacao findTransacaoByNuConta(String nuConta) {
        return transacaoRepository.findTransacaoByNuConta(nuConta);
    }

    public Transacao update(Transacao transacao) {
        Conta conta = contaService.findById(transacao.getConta().getId());
        transacao.setConta(conta);
        return transacaoRepository.save(transacao);
    }

    public void deleteById(Integer id) {
        transacaoRepository.deleteById(id);
    }
}
