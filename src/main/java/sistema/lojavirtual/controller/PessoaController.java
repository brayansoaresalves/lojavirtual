package sistema.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.PessoaFisica;
import sistema.lojavirtual.model.PessoaJuridica;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.service.PessoaUserService;
import sistema.lojavirtual.util.ValidaCNPJ;
import sistema.lojavirtual.util.ValidaCPF;

@RestController
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	
	@PostMapping("/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPJ(@RequestBody PessoaJuridica pessoaJuridica) throws ExceptionMentoria{
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoria("Pessoa Juridica não pode ser nula");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()).isPresent()) {
			throw new ExceptionMentoria("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoria("CNPJ : " + pessoaJuridica.getCnpj() + " está inválido");
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJurdica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
	
	@PostMapping("/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPF(@RequestBody PessoaFisica pessoaFisica) throws ExceptionMentoria{
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoria("Pessoa Fisica não pode ser nula");
		}
		
		if (pessoaFisica.getId() == null && pessoaRepository.existeCpfCadastrado(pessoaFisica.getCpf()).isPresent()) {
			throw new ExceptionMentoria("Já existe CPF cadastrado com o número: " + pessoaFisica.getCpf());
		}
		
		if (!ValidaCPF.isCPF(pessoaFisica.getCpf())) {
			throw new ExceptionMentoria("CPF : " + pessoaFisica.getCpf() + " está inválido");
		}
		
		pessoaFisica = pessoaUserService.salvarPessoaFisica(pessoaFisica);
		
		return new ResponseEntity<PessoaFisica>(pessoaFisica, HttpStatus.OK);
	}

}
