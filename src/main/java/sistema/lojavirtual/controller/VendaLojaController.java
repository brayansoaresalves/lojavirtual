package sistema.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import sistema.lojavirtual.model.VendaLoja;
import sistema.lojavirtual.model.dto.CepDTO;
import sistema.lojavirtual.repository.VendaLojaRepository;
import sistema.lojavirtual.service.EmissaoVendaLojaService;
import sistema.lojavirtual.service.PessoaUserService;

@RestController
@RequestMapping("/vendas")
public class VendaLojaController {
	
	@Autowired
	private VendaLojaRepository vendaLojaRepository;
	
	@Autowired
	private PessoaUserService pessoaUserService;
	
	@Autowired
	private EmissaoVendaLojaService emissaoVendaLojaService;
	
	@GetMapping("/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable String cep){
		return ResponseEntity.ok(pessoaUserService.consultaCep(cep));
	}
	
	@PostMapping
	public ResponseEntity<VendaLoja> emitir(@RequestBody @Valid VendaLoja venda) throws ExceptionMentoria {
		CepDTO cepDTO = pessoaUserService.consultaCep(venda.getEnderecoEntrega().getCep());
		venda.setEnderecoEntrega(montarEndereco(cepDTO, venda.getEnderecoEntrega()));
		
		venda.getEnderecoEntrega().setEmpresa(venda.getEmpresa());
		venda.getEnderecoEntrega().setPessoa(venda.getPessoa());
		
		CepDTO cepDTO2 = pessoaUserService.consultaCep(venda.getEnderecoCobranca().getCep());
		venda.setEnderecoCobranca(montarEndereco(cepDTO2,venda.getEnderecoCobranca()));
		
		venda.getEnderecoCobranca().setEmpresa(venda.getEmpresa());
		venda.getEnderecoCobranca().setPessoa(venda.getPessoa());
		
		
		venda = emissaoVendaLojaService.emitir(venda);
		return ResponseEntity.ok(venda);
	}
	
	private Endereco montarEndereco(CepDTO cepDTO, Endereco endereco) {
		endereco.setBairro(cepDTO.getBairro());
		endereco.setCidade(cepDTO.getLocalidade());
		endereco.setComplemento(cepDTO.getComplemento());
		endereco.setLogradouro(cepDTO.getLogradouro());
		endereco.setUf(cepDTO.getUf());
		return endereco;
	}
	
	
}
