package sistema.lojavirtual.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class VendaLojaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal valorTotal;
	

}
