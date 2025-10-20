package sistema.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class QsaDTO implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private String qual;
	
	private String pais_origem;
	
	private String nome_resp_legal;
	
	private String qual_rep_legal;

}
