package sistema.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "seq_produto", sequenceName = "seq_produto", allocationSize = 1, initialValue = 1)
@Getter
@Setter
public class Produto implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_produto")
	private Long id;
	
	@NotBlank(message = "Nome do produto deve ser informado")
	@Size(min = 10, message = "Nome do produto deve ter mais de 10 letras.")
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Boolean ativo = Boolean.TRUE;

	@Size(max = 2000, message = "Descrição não pode ser maior que 2000 caracteres")
	@NotBlank(message = "Descrição do produto deve ser informado")
	@Column(nullable = false, columnDefinition = "text")
	private String descricao;
	
	@NotBlank(message = "O tipo de unidade deve ser informado")
	@Column(nullable = false)
	private String unidade;

	@NotNull(message = "O peso deve ser informado")
	@Column(nullable = false)
	private BigDecimal peso = BigDecimal.ZERO;

	@NotNull(message = "A largura deve ser informada")
	@Column(nullable = false)
	private BigDecimal largura = BigDecimal.ZERO;
	
	@NotNull(message = "A altura deve ser informada")
	@Column(nullable = false)
	private BigDecimal altura = BigDecimal.ZERO;
	
	private BigDecimal profundidade = BigDecimal.ZERO;

	@NotNull(message = "O valor de venda deve ser informada")
	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;
	
	@Min(value = 1, message = "A quantidade em estoque deve ser maior que 0")
	@NotNull(message = "A quantidade deve ser informada")
	@Column(nullable = false)
	private Integer quantidadeEstoque = 0;

	private Integer quantidadeAlertaEstoque = 0;
	
	private String linkYoutube;

	private Boolean alertaQuantidadeEstoque = Boolean.FALSE;

	private Integer quantidadeCliques = 0;
	
	@NotNull(message = "A empresa responsável deve ser informada")
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;
	
	@NotNull(message = "A categoria deve ser informada")
	@ManyToOne(targetEntity = CategoriaProduto.class)
	@JoinColumn(name = "categoria_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, 
	name = "categoria_produto_fk"))
	private CategoriaProduto categoriaProduto;
	

	@NotNull(message = "A marca deve ser informada")
	@ManyToOne(targetEntity = MarcaProduto.class)
	@JoinColumn(name = "marca_produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, 
	name = "marca_produto_fk"))
	private MarcaProduto marcaProduto;

}
