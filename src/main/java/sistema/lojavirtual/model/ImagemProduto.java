package sistema.lojavirtual.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "seq_imagem_produto", sequenceName = "seq_imagem_produto", allocationSize = 1, initialValue = 1)
@Getter
@Setter
public class ImagemProduto implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_imagem_produto")
	private Long id;
	
	@Column(nullable = false, columnDefinition = "text")
	private String imagemOriginal;
	
	@Column(nullable = false, columnDefinition = "text")
	private String imagemMiniatura;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Produto.class)
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "produto_fk"))
	private Produto produto;
	
	@JsonIgnore
	@ManyToOne(targetEntity = PessoaJuridica.class)
	@JoinColumn(name = "empresa_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "empresa_fk"))
	private PessoaJuridica empresa;

}
