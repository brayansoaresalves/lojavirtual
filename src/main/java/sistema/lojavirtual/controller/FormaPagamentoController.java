package sistema.lojavirtual.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.FormaPagamento;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.repository.FormaPagamentoRepository;
import sistema.lojavirtual.repository.PessoaRepository;

@RestController
@RequestMapping("/formas")
@RequiredArgsConstructor
public class FormaPagamentoController {
	
	private final FormaPagamentoRepository formaPagamentoRepository;
	private final PessoaRepository pessoaRepository;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamento>> carregarTodos() {
		return ResponseEntity.ok(formaPagamentoRepository.findAll());
	}
	
	@GetMapping("empresa/{empresaId}")
	public ResponseEntity<List<FormaPagamento>> carregarPorEmpresa(@PathVariable Long empresaId){
		return ResponseEntity.ok(formaPagamentoRepository.findByEmpresaId(empresaId));
	}
	
	@PostMapping
	public ResponseEntity<FormaPagamento> registrarForma(@RequestBody FormaPagamento forma) throws ExceptionMentoria {
		Optional<Pessoa> empresaFiltrada = pessoaRepository.findById(forma.getEmpresa().getId());
		
		if (empresaFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Empresa não cadastrada");
		}
		
		forma = formaPagamentoRepository.saveAndFlush(forma);
		return ResponseEntity.status(HttpStatus.CREATED).body(forma);
	}
	

}
