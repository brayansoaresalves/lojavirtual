package sistema.lojavirtual.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "seq_avaliacao_produto", sequenceName = "seq_avaliacao_produto", allocationSize = 1, initialValue = 1)
@Getter
@Setter
public class AvaliacaoProduto implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_avaliacao_produto")
	private Long id;
	
	@NotBlank(message = "Informar a descrição")
	@Column(columnDefinition = "text", nullable = false)
	private String descricao;
	
	@Min(5)
	@Max(10)
	@NotNull(message = "Informar a nota para avaliação")
	@Column(nullable = false)
	private Integer nota = 0;
	
	@JsonIgnore
	@NotNull(message = "Necessário informar o cliente")
	@ManyToOne(targetEntity = PessoaFisica.class)
	@JoinColumn(name = "pessoa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "pessoa_fk"))
	private PessoaFisica pessoa;
	
	@JsonIgnore
	@NotNull(message = "Por favor informe a empresa")
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;
	
	@JsonIgnore
	@NotNull(message = "Por favor informe o produto para avaliação")
	@ManyToOne(targetEntity = Produto.class)
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
	private Produto produto;

}
