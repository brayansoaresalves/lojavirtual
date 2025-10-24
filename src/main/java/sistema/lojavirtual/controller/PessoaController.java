package sistema.lojavirtual.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.Endereco;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.PessoaFisica;
import sistema.lojavirtual.model.PessoaJuridica;
import sistema.lojavirtual.model.dto.CepDTO;
import sistema.lojavirtual.model.dto.ConsultaCnpjDTO;
import sistema.lojavirtual.model.enums.TipoPessoa;
import sistema.lojavirtual.repository.EnderecoRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.service.PessoaUserService;
import sistema.lojavirtual.service.ServiceContagemAcessoAPI;
import sistema.lojavirtual.util.ValidaCNPJ;
import sistema.lojavirtual.util.ValidaCPF;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ServiceContagemAcessoAPI serviceContagemAcessoAPI;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> buscarPessoasJuridicas() {
		return ResponseEntity.ok(pessoaRepository.findAll());
	}
	
	
	@GetMapping("/consultaPFNome/{nome}")
	public ResponseEntity<List<PessoaFisica>> consultaPFNome(@PathVariable String nome){
		List<PessoaFisica> fisicas = pessoaRepository.findByNomeContainingIgnoreCase(nome.toUpperCase());
		serviceContagemAcessoAPI.atualizaAcessoEndPointPessoaFisica();
		return ResponseEntity.ok(fisicas);
	}
	
	@GetMapping("/consultarRazao/{razao}")
	public ResponseEntity<List<PessoaJuridica>> consultarRazao(@PathVariable String razao){
		List<PessoaJuridica> juridicas = pessoaRepository.findByRazaoContainingIgnoreCase(razao.toUpperCase());		
		serviceContagemAcessoAPI.atualizaAcessoEndPointPessoaJuridica();
		return ResponseEntity.ok(juridicas);
		
	}

	
	@GetMapping("/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable String cep){
		return ResponseEntity.ok(pessoaUserService.consultaCep(cep));
	}
	
	@GetMapping("/consultaCnpjReceitaWs/{cnpj}")
	public ResponseEntity<ConsultaCnpjDTO> consultaCnpjReceitaWs(@PathVariable String cnpj){
		return ResponseEntity.ok(pessoaUserService.consultaCnpjReceitaWS(cnpj));
	}
	
	@PostMapping("/salvarPj")
	public ResponseEntity<PessoaJuridica> salvarPJ(@RequestBody @Valid PessoaJuridica pessoaJuridica) throws ExceptionMentoria{
		
		if (pessoaJuridica == null) {
			throw new ExceptionMentoria("Pessoa Juridica não pode ser nula");
		}
		
		if (pessoaJuridica.getTipoPessoa() == null) {
			throw new ExceptionMentoria("Informe o tipo Jurídico ou Fornecedor da Loja");
		}
		
		if (pessoaJuridica.getId() == null && pessoaRepository.existeCnpjCadastrado(pessoaJuridica.getCnpj()).isPresent()) {
			throw new ExceptionMentoria("Já existe CNPJ cadastrado com o número: " + pessoaJuridica.getCnpj());
		}
		
		if (!ValidaCNPJ.isCNPJ(pessoaJuridica.getCnpj())) {
			throw new ExceptionMentoria("CNPJ : " + pessoaJuridica.getCnpj() + " está inválido");
		}
		
		if (pessoaJuridica.getId() == null || pessoaJuridica.getId() <= 0) {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
				pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
				pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
				pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
				pessoaJuridica.getEnderecos().get(p).setLogradouro(cepDTO.getLogradouro());
				pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
			}
		} else {
			for (int p = 0; p < pessoaJuridica.getEnderecos().size(); p++) {
				Optional<Endereco> enderecoFiltrado = enderecoRepository.findById(pessoaJuridica.getEnderecos().get(p).getId());
				if (!enderecoFiltrado.isEmpty()) {
					if (!enderecoFiltrado.get().getCep().equals(pessoaJuridica.getEnderecos().get(p).getCep())) {
						CepDTO cepDTO = pessoaUserService.consultaCep(pessoaJuridica.getEnderecos().get(p).getCep());
						pessoaJuridica.getEnderecos().get(p).setBairro(cepDTO.getBairro());
						pessoaJuridica.getEnderecos().get(p).setCidade(cepDTO.getLocalidade());
						pessoaJuridica.getEnderecos().get(p).setComplemento(cepDTO.getComplemento());
						pessoaJuridica.getEnderecos().get(p).setLogradouro(cepDTO.getLogradouro());
						pessoaJuridica.getEnderecos().get(p).setUf(cepDTO.getUf());
					}
				}
			}
		}
		
		pessoaJuridica = pessoaUserService.salvarPessoaJurdica(pessoaJuridica);
		
		return new ResponseEntity<PessoaJuridica>(pessoaJuridica, HttpStatus.OK);
	}
	
	@PostMapping("/salvarPf")
	public ResponseEntity<PessoaFisica> salvarPF(@RequestBody @Valid PessoaFisica pessoaFisica) throws ExceptionMentoria{
		
		if (pessoaFisica == null) {
			throw new ExceptionMentoria("Pessoa Fisica não pode ser nula");
		}
		
		if (pessoaFisica.getTipoPessoa() == null) {
			pessoaFisica.setTipoPessoa(TipoPessoa.FISICA.getDescricao());
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
