package sistema.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.ContaPagar;
import sistema.lojavirtual.repository.ContaPagarRepository;
import sistema.lojavirtual.service.EmissaoContaPagarService;

@RestController
@RequestMapping("contas/pagar")
public class ContaPagarController {
	
	@Autowired
	private ContaPagarRepository contaPagarRepository;
	
	@Autowired
	private EmissaoContaPagarService emissaoContaPagarService;
	
	@GetMapping("/{descricao}")
	public ResponseEntity<List<ContaPagar>> buscarContasPorDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(emissaoContaPagarService.buscarPorDescricao(descricao));
	}
	
	@GetMapping("/pessoa/{pessoaId}")
	public ResponseEntity<List<ContaPagar>> buscarContasPagarPorPessoa(@PathVariable Long codigo) throws ExceptionMentoria{
		return ResponseEntity.ok(emissaoContaPagarService.buscarContaPagarPorPessoa(codigo));
	}
	
	@GetMapping("/fornecedor/{pessoaId}")
	public ResponseEntity<List<ContaPagar>> buscarContasPagarPorFornecedor(@PathVariable Long codigo) throws ExceptionMentoria{
		return ResponseEntity.ok(emissaoContaPagarService.buscarContaPagarPorFornecedor(codigo));
	}
	
	@GetMapping("/empresa/{pessoaId}")
	public ResponseEntity<List<ContaPagar>> buscarContasPagarPorEmpresa(@PathVariable Long codigo) throws ExceptionMentoria{
		return ResponseEntity.ok(emissaoContaPagarService.buscarContaPagarPorEmpresa(codigo));
	}
	
	@PostMapping
	public ResponseEntity<ContaPagar> registrar(@Valid @RequestBody ContaPagar contaPagar) throws ExceptionMentoria{
		ContaPagar contaRecebida = emissaoContaPagarService.registrarConta(contaPagar);
		return ResponseEntity.ok(contaRecebida);
		
	}
	
	@PutMapping("/{contaPagarId}")
	public ResponseEntity<?> atualizarConta(@Valid @RequestBody ContaPagar contaPagar, @PathVariable Long contaPagarId) throws ExceptionMentoria{
		ContaPagar contaFiltrada = contaPagarRepository.findById(contaPagarId).orElseThrow(() -> new ExceptionMentoria("Conta Pagar não encontrada."));
		
		BeanUtils.copyProperties(contaPagar, contaFiltrada, "id");
		contaFiltrada = emissaoContaPagarService.registrarConta(contaFiltrada);
		return ResponseEntity.ok(contaFiltrada);
	}
	
	@DeleteMapping("/{contasPagarId}")
	public ResponseEntity<?> excluirConta(@PathVariable Long contasPagarId) throws ExceptionMentoria{
		emissaoContaPagarService.excluirContaPagar(contasPagarId);
		return ResponseEntity.noContent().build();
	}
	
}
