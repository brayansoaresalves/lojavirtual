package sistema.lojavirtual.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ConsultaCnpjDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<AtividadeDTO> atividade_principal = new ArrayList<>();
	
	private String data_situacao;
	
	private String tipo;
	
	private String nome;
	
	private String uf;
	
	private String telefone; 
	
	private String email;
	
	private List<AtividadeDTO> atividades_secundarias = new ArrayList<>();
	
	private List<QsaDTO> qsa = new ArrayList<>();
	
	private String status;
	
	private String ultima_atualizacao;
	
	private String cnpj;
	
	private String porte;
	
	private String fantasia;
	
	private String abertura;
	
	private String natureza_juridica;
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String cep;
	
	private String bairro;
	
	private String municipio;
	
	private String situacao;
	
	private String efr;
	
	private String motivo_situacao;
	
	private String situacao_especial;
	
	private String data_situacao_especial;
	
	private String capital_social;
	
	@JsonIgnore
	private List<SimplesDTO> simples = new ArrayList<>();
	
	@JsonIgnore
	private List<SimeiDTO> simei = new ArrayList<>();
	
	private BillingDTO billing;
	

}
