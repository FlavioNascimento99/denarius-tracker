package edu.ifpb.denarius_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.ifpb.denarius_tracker.entities.Categoria;
import edu.ifpb.denarius_tracker.enums.NaturezaCategoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findByAtivaTrue();
    
    List<Categoria> findByNaturezaAndAtivaTrue(NaturezaCategoria natureza);
    
    List<Categoria> findByNaturezaAndAtivaOrderByOrdem(NaturezaCategoria natureza, Boolean ativa);
    
    List<Categoria> findByNaturezaOrderByOrdem(NaturezaCategoria natureza);
    
    List<Categoria> findByPredefinidaTrue();
    
    List<Categoria> findByPredefinidaFalse();

    @Query("SELECT MAX(c.ordem) FROM Categoria c WHERE c.natureza = :natureza")
    Integer findMaxOrdemByNatureza(@Param("natureza") NaturezaCategoria natureza);

    @Query("SELECT c FROM Categoria c WHERE c.ativa = true ORDER BY c.natureza, c.ordem")
    List<Categoria> findAllAtivasOrderByNaturezaAndOrdem();

    boolean existsByNomeAndNatureza(String nome, NaturezaCategoria natureza);
}