package edu.ifpb.denarius_tracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.ifpb.denarius_tracker.entities.Correntista;
import edu.ifpb.denarius_tracker.repository.CorrentistaRepository;
import edu.ifpb.denarius_tracker.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CorrentistaRepository correntistaRepo;

    @GetMapping
    public ModelAndView getForm(ModelAndView model) {
        model.setViewName("auth/login");
        model.addObject("usuario", new Correntista());
        return model;
    }

    @PostMapping
    public ModelAndView valide(Correntista correntista, HttpSession session, ModelAndView model,
            RedirectAttributes redirectAttts) {
        if ((correntista = this.isValido(correntista)) != null) {
            session.setAttribute("usuario", correntista);
            model.setViewName("redirect:/home");
        } else {
            redirectAttts.addFlashAttribute("mensagem", "Login e/ou senha inválidos!");
            model.setViewName("redirect:/auth");
        }
        return model;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/auth");
        return mav;
    }

    private Correntista isValido(Correntista correntista) {
        Correntista correntistaBD = correntistaRepo.findByEmail(correntista.getEmail());
        System.out.println("valor de " + correntistaBD);

        boolean valido = false;
        if (correntistaBD != null) {
            if (PasswordUtil.checkPass(correntista.getSenha(), correntistaBD.getSenha())) {
                valido = true;
            }
        }
        return valido ? correntistaBD : null;
    }
}