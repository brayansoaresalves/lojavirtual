package sistema.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AvaliacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String descricao;
	
	private Integer nota;
	
	private Long pessoa;
	

	private Long empresa;
	
	private Long produto;

}
