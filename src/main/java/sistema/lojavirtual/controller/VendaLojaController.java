package sistema.lojavirtual.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import sistema.lojavirtual.model.dto.VendaLojaDTO;
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
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<VendaLojaDTO>> buscar(){
		List<VendaLoja> vendas = vendaLojaRepository.findAll();
		return ResponseEntity.ok(vendas.stream().map(venda -> modelMapper.map(venda, VendaLojaDTO.class))
				.collect(Collectors.toList()));
		
	}
	
	@GetMapping("/produto/{produtoId}")
	public ResponseEntity<List<VendaLojaDTO>> buscarPorProduto(@PathVariable Long produtoId){
		List<VendaLoja> vendas = vendaLojaRepository.findDistinctByItensProdutoIdAndExcluidoFalse(produtoId);
		return ResponseEntity.ok(vendas.stream().map(venda -> modelMapper.map(venda, VendaLojaDTO.class))
				.collect(Collectors.toList()));
		
	}
	
	@GetMapping("/produto/porNome/{nome}")
	public ResponseEntity<List<VendaLojaDTO>> buscarPorProdutoPorNome(@PathVariable String nome){
		List<VendaLoja> vendas = vendaLojaRepository.findDistinctByItensProdutoNomeContainingIgnoreCaseAndExcluidoFalse(nome);
		return ResponseEntity.ok(vendas.stream().map(venda -> modelMapper.map(venda, VendaLojaDTO.class))
				.collect(Collectors.toList()));
		
	}
	
	@GetMapping("/cliente/{clienteId}")
	public ResponseEntity<List<VendaLojaDTO>> buscarPorCliente(@PathVariable Long clienteId){
		List<VendaLoja> vendas = vendaLojaRepository.findByPessoaIdAndExcluidoFalse(clienteId);
		return ResponseEntity.ok(vendas.stream().map(venda -> modelMapper.map(venda, VendaLojaDTO.class))
				.collect(Collectors.toList()));
		
	}
	
	@GetMapping("/{vendaId}")
	public ResponseEntity<VendaLojaDTO> consultarVendaId(@PathVariable Long vendaId) throws ExceptionMentoria {
		VendaLoja venda = vendaLojaRepository.findByIdAndExcluidoFalse(vendaId);
		
		if (venda == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(modelMapper.map(venda, VendaLojaDTO.class));
	}
	
	@GetMapping("/consultaCep/{cep}")
	public ResponseEntity<CepDTO> consultaCep(@PathVariable String cep){
		return ResponseEntity.ok(pessoaUserService.consultaCep(cep));
	}
	
	@PostMapping
	public ResponseEntity<VendaLojaDTO> emitir(@RequestBody @Valid VendaLoja venda) throws ExceptionMentoria {
		CepDTO cepDTO = pessoaUserService.consultaCep(venda.getEnderecoEntrega().getCep());
		venda.setEnderecoEntrega(montarEndereco(cepDTO, venda.getEnderecoEntrega()));
		
		venda.getEnderecoEntrega().setEmpresa(venda.getEmpresa());
		venda.getEnderecoEntrega().setPessoa(venda.getPessoa());
		
		CepDTO cepDTO2 = pessoaUserService.consultaCep(venda.getEnderecoCobranca().getCep());
		venda.setEnderecoCobranca(montarEndereco(cepDTO2,venda.getEnderecoCobranca()));
		
		venda.getEnderecoCobranca().setEmpresa(venda.getEmpresa());
		venda.getEnderecoCobranca().setPessoa(venda.getPessoa());
		
		
		venda = emissaoVendaLojaService.emitir(venda);
		VendaLojaDTO vendaLojaDTO = new VendaLojaDTO();
		vendaLojaDTO.setValorTotal(venda.getValorTotal());
		vendaLojaDTO.setPessoa(venda.getPessoa());
		vendaLojaDTO.setCobranca(venda.getEnderecoCobranca());
		vendaLojaDTO.setEntrega(venda.getEnderecoEntrega());
		vendaLojaDTO.setValorDesconto(venda.getDesconto());
		vendaLojaDTO.setValorFrete(venda.getValorFrete());
		vendaLojaDTO.setDiasEntrega(venda.getDiasEntrega());
		vendaLojaDTO.setId(venda.getId());
		return ResponseEntity.ok(vendaLojaDTO);
	}
	
	@DeleteMapping("/{vendaId}")
	public ResponseEntity<?> deletandoVenda(@PathVariable Long vendaId) throws ExceptionMentoria{
		emissaoVendaLojaService.removerVendaEAssociacao(vendaId);
		return new ResponseEntity<String>("Venda Excluida com sucesso.", HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/excluir/{vendaId}")
	public ResponseEntity<?> excluindoVendaLogicamente(@PathVariable Long vendaId) throws ExceptionMentoria{
		emissaoVendaLojaService.exclusaoTotalVenda(vendaId);
		return new ResponseEntity<String>("Venda Excluida com sucesso.", HttpStatus.NO_CONTENT);
	}
	
	@PostMapping("/ativar/{vendaId}")
	public ResponseEntity<?> voltarVenda(@PathVariable Long vendaId) throws ExceptionMentoria{
		emissaoVendaLojaService.retornarVenda(vendaId);
		return new ResponseEntity<String>("Venda Retornada com sucesso.", HttpStatus.OK);
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
