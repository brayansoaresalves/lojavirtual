package sistema.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AtividadeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String text;
	private String code;
	
	

}
