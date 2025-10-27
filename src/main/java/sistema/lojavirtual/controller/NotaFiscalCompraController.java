package sistema.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.NotaFiscalCompra;
import sistema.lojavirtual.repository.NotaFiscalCompraRepository;
import sistema.lojavirtual.service.EmissaoNotaFiscalCompra;

@RestController
@RequestMapping("notas/compras")
public class NotaFiscalCompraController {
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@Autowired
	private EmissaoNotaFiscalCompra emissaoNotaFiscalService;
	
	@GetMapping("/{nota}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarPorNumeroDeNota(@PathVariable String nota){
		return ResponseEntity.ok(emissaoNotaFiscalService.buscarPorNumeroNota(nota));
	}
	
	@GetMapping("/pessoa/{pessoaId}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarPorPessoa(@PathVariable Long codigo) throws ExceptionMentoria{
		return ResponseEntity.ok(emissaoNotaFiscalService.buscarNotaPorPessoa(codigo));
	}
	
	@GetMapping("/contaPagar/{conta}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarPorContaPagar(@PathVariable Long conta) throws ExceptionMentoria{
		return ResponseEntity.ok(emissaoNotaFiscalService.buscarPorContaPagar(conta));
	}
	
	@GetMapping("/empresa/{pessoaId}")
	public ResponseEntity<List<NotaFiscalCompra>> buscarPorEmpresa(@PathVariable Long codigo) throws ExceptionMentoria{
		return ResponseEntity.ok(emissaoNotaFiscalService.buscarNotarPorEmpresa(codigo));
	}
	
	@PostMapping
	public ResponseEntity<NotaFiscalCompra> registrar(@Valid @RequestBody NotaFiscalCompra nota) throws ExceptionMentoria{
		NotaFiscalCompra notaRecebida = emissaoNotaFiscalService.registrarNota(nota);
		return ResponseEntity.ok(notaRecebida);
		
	}
	
	@PutMapping("/{notaId}")
	public ResponseEntity<?> atualizarConta(@Valid @RequestBody NotaFiscalCompra nota, @PathVariable Long notaId) throws ExceptionMentoria{
		NotaFiscalCompra notaFiltrada = notaFiscalCompraRepository.findById(notaId).orElseThrow(() -> new ExceptionMentoria("Nota não encontrada."));
		
		BeanUtils.copyProperties(nota, notaFiltrada, "id");
		notaFiltrada = emissaoNotaFiscalService.registrarNota(notaFiltrada);
		return ResponseEntity.ok(notaFiltrada);
	}
	
}
