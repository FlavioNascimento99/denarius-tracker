package edu.ifpb.denarius_tracker.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import edu.ifpb.denarius_tracker.enums.TipoConta;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String numero;
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private TipoConta tipo;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date data;

	@ManyToOne
	@JoinColumn(name="id_correntista")
	private Correntista correntista;
	
	@OneToMany(mappedBy="conta", cascade=CascadeType.ALL, orphanRemoval = true)
	private Set<Transacao> transacoes = new HashSet<Transacao>();
    
    public void addTransacao(Transacao transacao) {
    	this.transacoes.add(transacao);
    	transacao.setConta(this);
    }
    
    public Conta(Correntista correntista) {
    	this.correntista = correntista;
    }
	
}
