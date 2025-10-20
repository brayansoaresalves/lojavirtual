package sistema.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ServiceContagemAcessoAPI {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void atualizaAcessoEndPointPessoaFisica() {
		jdbcTemplate.execute("begin; update tabela_acesso_end_point set qtd_acesso_end_point = qtd_acesso_end_point + 1"
				+ "where nome = 'END-POINT-NOME-PESSOA-FISICA'; commit;");
	}
	
	public void atualizaAcessoEndPointPessoaJuridica() {
		jdbcTemplate.execute("begin; update tabela_acesso_end_point set qtd_acesso_end_point = qtd_acesso_end_point + 1"
				+ "where nome = 'END-POINT-RAZAP-PESSOA-JURIDICA'; commit;");
	}

}
