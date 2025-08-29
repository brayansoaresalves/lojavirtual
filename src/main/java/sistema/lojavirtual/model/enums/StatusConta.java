package sistema.lojavirtual.model.enums;

public enum StatusConta {
	
	COBRANCA("Cobrança"), 
	VENCIDA("Vencida"),
	ABERTA("Aberta"),
	QUITADA("Quitada"),
	ALUGUEL("Aluguel"),
	FUNCIONARIO("Funcionario"),
	NEGOCIADA("Negociada");
	
	private String descricao;
	
	private StatusConta (String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	
}
