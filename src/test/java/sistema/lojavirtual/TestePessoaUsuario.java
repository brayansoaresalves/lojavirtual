package sistema.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

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
		pessoaJuridica.setRazao("Administrador");
		pessoaJuridica.setEmail("brayan.iub10@gmail.com");
		pessoaJuridica.setFantasia("Administrador");
		pessoaJuridica.setInscricaoEstadual("12345435");
		pessoaJuridica.setInscricaoMunicipal("1231545");
		pessoaJuridica.setNome("Administrador");
		pessoaJuridica.setCategoria("Admin");
		pessoaJuridica.setTelefone("64992794687");
		pessoaJuridica.setTipoPessoa("Fisica");

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
