package sistema.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "seq_venda_loja", sequenceName = "seq_venda_loja", allocationSize = 1, initialValue = 1)
@Getter
@Setter
public class VendaLoja  implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_venda_loja")
	private Long id;
	
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "endereco_entrega_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_entrega_fk"))
	private Endereco enderecoEntrega;
	
	@ManyToOne
	@JoinColumn(name = "endereco_cobranca_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_cobranca_fk"))
	private Endereco enderecoCobranca;
	
	@Column(nullable = false)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private BigDecimal desconto = BigDecimal.ZERO;
	
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "forma_pagamento_id"))
	private FormaPagamento formaPagamento;
	
	@OneToOne
	@JoinColumn(name = "nota_fiscal_venda_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "nota_fiscal_venda_fk"))
	private NotaFiscalVenda notaFiscalVenda;
	
	@ManyToOne
	@JoinColumn(name = "cupon_id", 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "cupom_fk"))
	private Cupom cupom;
	
	@Column(nullable = false)
	private BigDecimal valorFrete = BigDecimal.ZERO;
	

	@Column(nullable = false)
	private Integer diasEntrega;
	

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataVenda;
	
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

}
