package sistema.lojavirtual;

import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sistema.lojavirtual.controller.PessoaController;
import sistema.lojavirtual.model.Endereco;
import sistema.lojavirtual.model.PessoaJuridica;
import sistema.lojavirtual.model.enums.TipoEndereco;
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
		
		Endereco endereco1 = new Endereco();
		endereco1.setLogradouro("Rua teste");
		endereco1.setBairro("Teste Bairro");
		endereco1.setComplemento("");
		endereco1.setCep("41545874");
		endereco1.setNumero("100");
		endereco1.setEmpresa(pessoaJuridica);
		endereco1.setPessoa(pessoaJuridica);
		endereco1.setCidade("Goias");
		endereco1.setTipo(TipoEndereco.COBRANCA);
		endereco1.setUf("GO");
		

		Endereco endereco2 = new Endereco();
		endereco2.setLogradouro("Rua teste2");
		endereco2.setBairro("Teste Bairro2");
		endereco2.setComplemento("CASA");
		endereco2.setCep("545754");
		endereco2.setNumero("1001");
		endereco2.setEmpresa(pessoaJuridica);
		endereco2.setPessoa(pessoaJuridica);
		endereco2.setCidade("Itumbiara");
		endereco2.setTipo(TipoEndereco.ENTREGA);
		endereco2.setUf("GO");
		
		pessoaJuridica.getEnderecos().add(endereco1);
		pessoaJuridica.getEnderecos().add(endereco2);

		pessoaController.salvarPJ(pessoaJuridica).getBody();
		
		Assertions.assertEquals(true, pessoaJuridica.getId() > 0);
		
		for (Endereco endereco : pessoaJuridica.getEnderecos()) {
			Assertions.assertEquals(true, endereco.getId() > 0);
		}
		
		Assertions.assertEquals(2, pessoaJuridica.getEnderecos().size());
	
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
