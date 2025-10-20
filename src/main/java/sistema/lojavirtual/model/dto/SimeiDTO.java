package sistema.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class SimeiDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean optante;
	
	private String data_opcap;
	
	private String data_exclusao;
	
	private String ultima_atualizacao;

}
