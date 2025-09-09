package sistema.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ObjetoErroDTO implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	private String error;
	private String code;

}
