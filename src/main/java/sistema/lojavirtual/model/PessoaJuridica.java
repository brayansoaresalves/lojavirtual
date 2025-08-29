package sistema.lojavirtual.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity	
@Getter
@Setter
public class PessoaJuridica extends Pessoa {

	private static final long serialVersionUID = 1L;
	
	private String cnpj;
	
	private String inscricaoEstadual;
	
	private String inscricaoMunicipal;
	
	private String fantasia;
	
	private String razao;
	
	private String categoria;

}
