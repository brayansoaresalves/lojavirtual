package sistema.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.controller.PessoaController;
import sistema.lojavirtual.model.PessoaJuridica;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.service.PessoaUserService;

@SpringBootTest
@ActiveProfiles("test")
public class TestePessoaUsuario {
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaController pessoaController;
	
	@Test
	public void testCadPessoaFisica() throws ExceptionMentoria {
		
		PessoaJuridica pessoaJuridica = new PessoaJuridica();
		pessoaJuridica.setCnpj("" + Calendar.getInstance().getTimeInMillis());
		pessoaJuridica.setRazao("Empresa teste");
		pessoaJuridica.setEmail("empresa@gmail.com");
		pessoaJuridica.setFantasia("Fantasia empresa");
		pessoaJuridica.setInscricaoEstadual("12345435");
		pessoaJuridica.setInscricaoMunicipal("1231545");
		pessoaJuridica.setNome("Nome empresa");
		pessoaJuridica.setCategoria("Empresa");
		pessoaJuridica.setTelefone("2121312313");
		pessoaJuridica.setTipoPessoa("Juridica");

		pessoaController.salvarPJ(pessoaJuridica);
	
		/*
		PessoaFisica pessoaFisica = new PessoaFisica();
		pessoaFisica.setCpf("70314383140");
		pessoaFisica.setNome("Brayan Alves Soares");
		pessoaFisica.setEmail("brayan.iub10@gmail.com");
		pessoaFisica.setTelefone("64992794687");
		pessoaFisica.setDataNascimento(new Date(1996, 8, 18));
		pessoaFisica.setEmpresa(pessoaJuridica);*/
		
		
	}

}
