package edu.ifpb.denarius_tracker.entities;

import java.math.BigDecimal;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import edu.ifpb.denarius_tracker.enums.TipoMovimento;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "conta", "comentario", "categoria" })
@ToString(exclude = { "conta", "comentario", "categoria" }) // CRÍTICO: Excluir relacionamentos do toString
@Entity
public class Transacao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date data;
	
	private String descricao;
	
	@NumberFormat(pattern="###,##0.00")
	private BigDecimal valor;
	
	
	@Enumerated(EnumType.STRING)
	private TipoMovimento movimento;

	@ManyToOne
	@JoinColumn(name="id_conta")
	private Conta conta;
	
	@ManyToOne
	@JoinColumn(name="id_categoria", nullable=false)
	private Categoria categoria;
	
	@OneToOne(mappedBy="transacao", cascade=CascadeType.ALL, orphanRemoval=true)
	private Comentario comentario;
	
	
}
