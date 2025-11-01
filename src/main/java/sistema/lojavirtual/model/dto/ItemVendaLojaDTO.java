package sistema.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import sistema.lojavirtual.model.Produto;

@Data
public class ItemVendaLojaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private BigDecimal quantidade;
	
}
