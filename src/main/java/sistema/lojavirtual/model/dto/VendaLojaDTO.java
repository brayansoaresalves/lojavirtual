package sistema.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import sistema.lojavirtual.model.Endereco;
import sistema.lojavirtual.model.Pessoa;

@Data
public class VendaLojaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal valorTotal;
	private Pessoa pessoa;
	private Endereco cobranca;
	private Endereco entrega;
	private BigDecimal valorDesconto;
	private BigDecimal valorFrete;
	private Integer diasEntrega;
	

}
