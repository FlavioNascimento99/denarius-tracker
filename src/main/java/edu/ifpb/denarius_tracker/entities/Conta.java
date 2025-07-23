package edu.ifpb.denarius_tracker.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import org.springframework.format.annotation.DateTimeFormat;

import edu.ifpb.denarius_tracker.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String numero;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoConta tipo;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;

    @OneToMany(mappedBy="conta", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Transacao> transacoes = new HashSet<Transacao>();

    @ManyToOne
    @JoinColumn(name = "id_correntista")
    private Correntista correntista;

    public Conta(Correntista correntista) {
        this.correntista = correntista;
    }

    public void addTransacao(Transacao transacao) {
        this.transacoes.add(transacao);
        transacao.setConta(this);
    }
}
