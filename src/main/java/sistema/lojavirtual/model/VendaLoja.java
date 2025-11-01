package sistema.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
	
	@NotNull(message = "Por favor informe um cliente")
	@ManyToOne(targetEntity = PessoaFisica.class)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaFisica pessoa;
	
	@NotNull(message = "Por favor informar uma empresa")
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "Informar um endereço de entrega")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_entrega_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_entrega_fk"))
	private Endereco enderecoEntrega;
	
	@NotNull(message = "Informar um endereço de cobrança")
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_cobranca_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "endereco_cobranca_fk"))
	private Endereco enderecoCobranca;
	
	@NotNull(message = "O valorTotal não pode ser nulo.")
	@Positive(message = "O valor deve ser positivo e não pode ser zero.")
	@Digits(integer = 10, fraction = 2, message = "O valor deve ter no máximo 10 dígitos inteiros e 2 casas decimais.")
	@Column(nullable = false)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private BigDecimal desconto = BigDecimal.ZERO;
	
	@NotNull(message = "Informar uma forma de pagamento")
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "forma_pagamento_id"))
	private FormaPagamento formaPagamento;
	
	@JsonIgnoreProperties(allowGetters = true)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nota_fiscal_venda_id", nullable = true,
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
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Informar a data da venda")
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataVenda;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "vendaLoja")
	private List<ItemVendaLoja> itens = new ArrayList<>();

}
