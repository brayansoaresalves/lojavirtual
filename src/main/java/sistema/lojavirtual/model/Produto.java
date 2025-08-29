package sistema.lojavirtual.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private Boolean ativo;

	@Column(nullable = false, columnDefinition = "text")
	private String descricao;
	
	@Column(nullable = false)
	private String unidade;

	@Column(nullable = false)
	private BigDecimal peso = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal largura = BigDecimal.ZERO;
	
	@Column(nullable = false)
	private BigDecimal altura = BigDecimal.ZERO;
	
	private BigDecimal profundidade = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal valorVenda = BigDecimal.ZERO;
	
	@Column(nullable = false)
	private Integer quantidadeEstoque = 0;

	@Column(nullable = false)
	private Integer quantidadeAlertaEstoque = 0;
	
	@Column(nullable = false)
	private String linkYoutube;

	@Column(nullable = false)
	private Boolean alertaQuantidadeEstoque = Boolean.FALSE;

	private Integer quantidadeCliques = 0;

}
