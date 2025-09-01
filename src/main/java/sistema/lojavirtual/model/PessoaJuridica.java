package sistema.lojavirtual.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity	
@Getter
@Setter
public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	private String cnpj;
	
	@Column(nullable = false)
	private String inscricaoEstadual;
	
	private String inscricaoMunicipal;
	
	@Column(nullable = false)
	private String fantasia;
	
	@Column(nullable = false)
	private String razao;
	
	private String categoria;

}
