package edu.ifpb.denarius_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ifpb.denarius_tracker.entities.Correntista;

@Repository
public interface CorrentistaRepository extends JpaRepository<Correntista, Integer> {
    
    Correntista findByEmail(String email);
    
}