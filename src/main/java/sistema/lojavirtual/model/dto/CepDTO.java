package sistema.lojavirtual.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CepDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String unidade;
	private String localidade;
	private String uf;
	private String estado;
	private String regiao;
	private String ibge;
	private String ddd;
	private String gia;
	private String siafi;

}
