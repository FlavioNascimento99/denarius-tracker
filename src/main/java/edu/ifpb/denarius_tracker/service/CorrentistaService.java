package edu.ifpb.denarius_tracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ifpb.denarius_tracker.entities.Correntista;
import edu.ifpb.denarius_tracker.repository.CorrentistaRepository;
import edu.ifpb.denarius_tracker.util.PasswordUtil;

@Component
public class CorrentistaService implements ServiceGeneric<Correntista, Integer> {
    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public List<Correntista> findAll() {
        return correntistaRepository.findAll();
    }

    @Override
    public Correntista findById(Integer id) {
        return correntistaRepository.findById(id).orElse(null);
    }

    @Override
    public Correntista save(Correntista c) {
        c.setSenha(PasswordUtil.hashPassword(c.getSenha()));
       return correntistaRepository.save(c);
    }
}
