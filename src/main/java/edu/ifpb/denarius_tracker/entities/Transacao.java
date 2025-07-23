package edu.ifpb.denarius_tracker.entities;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import edu.ifpb.denarius_tracker.enums.TipoMovimento;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "conta", "comentario", "categoria" })
@ToString(exclude = { "conta", "comentario", "categoria" }) // CRÍTICO: Excluir relacionamentos do toString
@Entity
public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;

    private String descricao;

    @NumberFormat(pattern = "###,##0.00")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoMovimento movimento;

    @ManyToOne
    @JoinColumn(name = "id_conta")
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    // RELACIONAMENTO 1:0..1
    @OneToOne(mappedBy = "transacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private Comentario comentario;
}