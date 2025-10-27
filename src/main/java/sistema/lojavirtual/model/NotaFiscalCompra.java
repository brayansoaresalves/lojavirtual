package sistema.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
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
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "seq_nota_fiscal_compra", sequenceName = "seq_nota_fiscal_compra", allocationSize = 1, initialValue = 1)
@Getter
@Setter
public class NotaFiscalCompra implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_nota_fiscal_compra")
	private Long id;
	
	@NotBlank(message = "Numero da nota é obrigatório")
	@Column(nullable = false)
	private String numeroNota;
	
	@NotBlank(message = "Seríe da nota é obrigatória")
	@Column(nullable = false)
	private String serie;
	
	private String observacao;
	
	@Size(min = 1, message = "Valor Total deve ser superior a 0")
	@NotNull(message = "Necessario informar o valor total")
	@Column(nullable = false)
	private BigDecimal valorTotal = BigDecimal.ZERO;
	
	private BigDecimal desconto = BigDecimal.ZERO;

	@Size(min = 1, message = "Valor Total deve ser superior a 0")
	@NotNull(message = "Informar o valor de ICMS")
	@Column(nullable = false)
	private BigDecimal valorIcms = BigDecimal.ZERO;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	@NotNull(message = "Necessário informar a data da compra")
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataCompra;
	
	@NotNull(message = "A pessoa é obrigatória")
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaJuridica pessoa;
	
	@NotNull(message = "A emresa é obrigatória")
	@ManyToOne(targetEntity = Pessoa.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "Por favor informar a conta a pagar vinculada a nota")
	@ManyToOne
	@JoinColumn(name = "conta_pagar_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "conta_pagar_fk"))
	private ContaPagar contaPagar;


}
