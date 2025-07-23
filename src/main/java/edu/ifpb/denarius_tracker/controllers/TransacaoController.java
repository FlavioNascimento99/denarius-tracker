package edu.ifpb.denarius_tracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.ifpb.denarius_tracker.entities.Categoria;
import edu.ifpb.denarius_tracker.entities.Comentario;
import edu.ifpb.denarius_tracker.entities.Conta;
import edu.ifpb.denarius_tracker.entities.Transacao;
import edu.ifpb.denarius_tracker.enums.NaturezaCategoria;

@Controller
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private ContaService contaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/form")
    public ModelAndView getForm(@RequestParam Integer contaId, ModelAndView model) {
        Transacao transacao = new Transacao();
        Comentario comentario = new Comentario();

        // Configurar o relacionamento bidirecional desde o início
        transacao.setComentario(comentario);
        comentario.setTransacao(transacao);

        Conta conta = contaService.findById(contaId);
        transacao.setConta(conta);

        model.addObject("transacao", transacao);
        model.addObject("contaId", contaId);
        adicionarCategoriasAoModel(model);
        model.setViewName("transacoes/form");
        return model;
    }

    @GetMapping("/form/{id}")
    public ModelAndView getFormEdit(@PathVariable Integer id, @RequestParam Integer contaId, ModelAndView model) {
        Transacao transacao = transacaoService.findById(id);

        // Garantir que existe um comentário para o formulário
        if (transacao.getComentario() == null) {
            transacao.setComentario(new Comentario());
            transacao.getComentario().setTransacao(transacao);
        }

        model.addObject("transacao", transacao);
        model.addObject("contaId", contaId);
        adicionarCategoriasAoModel(model);
        model.setViewName("transacoes/form");
        return model;
    }

    @PostMapping
    public ModelAndView save(@ModelAttribute Transacao transacao, ModelAndView model) {
        // Reconstruir a conta a partir do ID
        if (transacao.getConta() != null && transacao.getConta().getId() != null) {
            Conta conta = contaService.findById(transacao.getConta().getId());
            transacao.setConta(conta);
        }

        // Reconstruir a categoria a partir do ID
        if (transacao.getCategoria() != null && transacao.getCategoria().getId() != null) {
            Categoria categoria = categoriaService.findById(transacao.getCategoria().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            transacao.setCategoria(categoria);
        }

        // CRÍTICO: Configurar o relacionamento bidirecional com o comentário
        if (transacao.getComentario() != null) {
            // Define a transação no comentário (lado que possui a FK)
            transacao.getComentario().setTransacao(transacao);

            // Se o texto estiver vazio, você pode optar por não salvar o comentário
            if (transacao.getComentario().getTexto() == null ||
                    transacao.getComentario().getTexto().trim().isEmpty()) {
                // Opção 1: Definir texto vazio
                transacao.getComentario().setTexto("");

                // Opção 2: Remover o comentário completamente (descomente se preferir)
                // transacao.setComentario(null);
            }
        }

        transacaoService.save(transacao);

        model.setViewName("redirect:/contas");
        return model;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable Integer id, ModelAndView model) {
        transacaoService.deleteById(id);
        model.setViewName("redirect:/contas");
        return model;
    }

    @GetMapping("/{id}")
    public ModelAndView getTransacaoById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        Transacao transacao = transacaoService.findById(id);

        model.addObject("transacao", transacao);
        adicionarCategoriasAoModel(model);
        model.setViewName("transacoes/form");
        return model;
    }

    // Método auxiliar para evitar repetição de código
    private void adicionarCategoriasAoModel(ModelAndView model) {
        List<Categoria> categoriesEntrada = categoriaService.findByNatureza(NaturezaCategoria.ENTRADA);
        List<Categoria> categoriesSaida = categoriaService.findByNatureza(NaturezaCategoria.SAIDA);
        List<Categoria> categoriesInvestimento = categoriaService.findByNatureza(NaturezaCategoria.INVESTIMENTO);

        model.addObject("categoriesEntrada", categoriesEntrada);
        model.addObject("categoriesSaida", categoriesSaida);
        model.addObject("categoriesInvestimento", categoriesInvestimento);
    }
}

