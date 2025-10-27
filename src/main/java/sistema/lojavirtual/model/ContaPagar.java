package sistema.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sistema.lojavirtual.model.enums.StatusConta;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "seq_conta_pagar", sequenceName = "seq_conta_pagar", allocationSize = 1, initialValue = 1)
@Getter
@Setter
public class ContaPagar implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_conta_pagar")
	private Long id;
	
	@NotBlank(message = "Informar uma descrição")
	@Column(nullable = false)
	private String descricao;
	
	@NotNull(message = "Informe o status")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatusConta status;
	
	@JsonFormat(pattern =  "dd/MM/yyyy")
	@NotNull(message = "Informe a data de vencimento da contas a pagar")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date dataVencimento;
	
	@JsonFormat(pattern =  "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;
	
	@NotNull(message = "Informe o valor total do contas a pagar")
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	private BigDecimal desconto;
	
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaJuridica pessoa;
	
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;
	
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "pessoa_fornecedor_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fornecedora_fk"))
	private PessoaJuridica pessoaFornecedora;

}
