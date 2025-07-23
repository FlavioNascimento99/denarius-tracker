package edu.ifpb.denarius_tracker.service;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ifpb.denarius_tracker.entities.Categoria;
import edu.ifpb.denarius_tracker.repository.CategoriaRepository;
import edu.ifpb.denarius_tracker.enums.NaturezaCategoria;

@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> findAllAtivas() {
        return categoriaRepository.findByAtivaTrue();
    }

    public List<Categoria> findByNatureza(NaturezaCategoria natureza) {
        return categoriaRepository.findByNaturezaAndAtivaTrue(natureza);
    }

    public Optional<Categoria> findById(Integer id) {
        return categoriaRepository.findById(id);
    }

    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void delete(Integer id) {
        categoriaRepository.deleteById(id);
    }

    public void desativarCategoria(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoria.get().setAtiva(false);
            categoriaRepository.save(categoria.get());
        }
    }

    public void ativarCategoria(Integer id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoria.get().setAtiva(true);
            categoriaRepository.save(categoria.get());
        }
    }

    // Método para inicializar as categorias predefinidas
    @Transactional
    public void inicializarCategoriasPredefinidas() {
        if (categoriaRepository.count() == 0) {
            List<Categoria> categoriasPredefinidas = Arrays.asList(
                // ENTRADAS
                new Categoria("Salário", NaturezaCategoria.ENTRADA, 1, true),
                new Categoria("Cashback", NaturezaCategoria.ENTRADA, 2, true),
                new Categoria("Resgate Investimento", NaturezaCategoria.ENTRADA, 3, true),
                new Categoria("Outras Entradas", NaturezaCategoria.ENTRADA, 4, true),

                // SAÍDAS
                new Categoria("Saúde e Remédios", NaturezaCategoria.SAIDA, 1, true),
                new Categoria("Academia e Personal", NaturezaCategoria.SAIDA, 2, true),
                new Categoria("Carros e Uber", NaturezaCategoria.SAIDA, 3, true),
                new Categoria("Educação e Cursos", NaturezaCategoria.SAIDA, 4, true),
                new Categoria("Lazer e Turismo", NaturezaCategoria.SAIDA, 5, true),
                new Categoria("Condomínio", NaturezaCategoria.SAIDA, 6, true),
                new Categoria("Energia", NaturezaCategoria.SAIDA, 7, true),
                new Categoria("Celular", NaturezaCategoria.SAIDA, 8, true),
                new Categoria("Internet", NaturezaCategoria.SAIDA, 9, true),
                new Categoria("Itens Pessoais", NaturezaCategoria.SAIDA, 10, true),
                new Categoria("Feira", NaturezaCategoria.SAIDA, 11, true),
                new Categoria("Casa", NaturezaCategoria.SAIDA, 12, true),
                new Categoria("Impostos", NaturezaCategoria.SAIDA, 13, true),
                new Categoria("Outros gastos", NaturezaCategoria.SAIDA, 14, true),

                // INVESTIMENTOS
                new Categoria("Aporte Renda Fixa", NaturezaCategoria.INVESTIMENTO, 1, true),
                new Categoria("Aporte Renda Variável", NaturezaCategoria.INVESTIMENTO, 2, true),
                new Categoria("Aporte Reserva Emergencia", NaturezaCategoria.INVESTIMENTO, 3, true),
                new Categoria("Aporte Previdência", NaturezaCategoria.INVESTIMENTO, 4, true)
            );

            categoriaRepository.saveAll(categoriasPredefinidas);
        }
    }

    public List<Categoria> findByNaturezaOrderByOrdem(NaturezaCategoria natureza) {
        return categoriaRepository.findByNaturezaAndAtivaOrderByOrdem(natureza, true);
    }

    // Método para buscar a próxima ordem disponível para uma natureza
    public Integer getProximaOrdem(NaturezaCategoria natureza) {
        Integer maxOrdem = categoriaRepository.findMaxOrdemByNatureza(natureza);
        return maxOrdem != null ? maxOrdem + 1 : 1;
    }
}