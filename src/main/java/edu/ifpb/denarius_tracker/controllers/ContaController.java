package edu.ifpb.denarius_tracker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ifpb.denarius_tracker.entities.Conta;
import edu.ifpb.denarius_tracker.entities.Correntista;
import edu.ifpb.denarius_tracker.entities.Transacao;


@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @Autowired
    private CorrentistaService correntistaService;

    @GetMapping("/form")
    public ModelAndView getForm(ModelAndView model) {
        model.setViewName("contas/form");
        model.addObject("conta", new Conta(new Correntista()));
        return model;
    }

    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaService.findAll();
    }

    @PostMapping
    public ModelAndView save(Conta conta, ModelAndView model, RedirectAttributes attr) {
        contaService.save(conta);
        attr.addFlashAttribute("mensagem", "Conta inserida com sucesso!");
        model.setViewName("redirect:/contas");
        return model;
    }

    @GetMapping
    public ModelAndView liste(ModelAndView model) {
        model.setViewName("contas/list");
        model.addObject("contas", contaService.findAll());
        return model;
    }
    

    @GetMapping("/{id}")
    public ModelAndView getCorrentistaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.addObject("conta", contaService.findById(id));
        model.setViewName("contas/form");
        return model;
    }

    @GetMapping("/nuconta")
    public String getNuConta() {
        return "contas/operacao";
    }

    @PostMapping(value = "/operacao")
    public ModelAndView operacaoConta(String nuConta, Transacao transacao, ModelAndView mav) {
        if (nuConta != null && transacao.getValor() == null) {

            Conta conta = contaService.findByNumeroWithTransacoes(nuConta);
            if (conta != null) {
                mav.addObject("conta", conta);
                mav.addObject("transacao", transacao);
                mav.setViewName("contas/operacao");
            } else {
                mav.addObject("mensagem", "Conta inexistente!");
                mav.setViewName("contas/operacao");
            }
        } else {
            Conta conta = contaService.findByNumeroWithTransacoes(nuConta);
            conta.addTransacao(transacao);
            contaService.save(conta);
            return addTransacaoConta(conta.getId(), mav);
        }
        return mav;
    }

    @GetMapping(value = "/{id}/transacoes")
    public ModelAndView addTransacaoConta(@PathVariable("id") Integer idConta, ModelAndView mav) {
        Conta conta = contaService.findByIdWithTransacoes(idConta);
        mav.addObject("conta", conta);
        mav.setViewName("contas/list");
        return mav;
    }

    
}