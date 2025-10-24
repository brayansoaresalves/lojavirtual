package sistema.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import sistema.lojavirtual.model.MarcaProduto;
import sistema.lojavirtual.repository.MarcaProdutoRepository;
import sistema.lojavirtual.service.MarcaProdutoService;

@RestController
@RequestMapping("/marcas")
public class MarcaRepository {
	
	@Autowired
	private MarcaProdutoService marcaProdutoService;
	
	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@GetMapping
	public ResponseEntity<List<MarcaProduto>> buscarTodas() {
		return ResponseEntity.ok(marcaProdutoRepository.findAll());
	}
	
	@GetMapping("/{descricao}")
	public ResponseEntity<List<MarcaProduto>> buscarPorDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(marcaProdutoRepository.findByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<MarcaProduto> registrar(@Valid @RequestBody MarcaProduto marcaProduto) throws ExceptionMentoria {
		MarcaProduto marcaRecebida = marcaProdutoService.registrar(marcaProduto);
		return new ResponseEntity<MarcaProduto>(marcaRecebida, HttpStatus.CREATED);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<MarcaProduto> atualizar(@Valid @RequestBody MarcaProduto marcaProduto, @PathVariable Long codigo) throws ExceptionMentoria{
		
		MarcaProduto marca = marcaProdutoRepository.findById(codigo).orElse(null);
		
		if (marca == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(marcaProduto, marca, "id");
		marca = marcaProdutoService.registrar(marca);
		
		return ResponseEntity.ok(marca);
		
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> deletar(@PathVariable Long codigo) throws ExceptionMentoria{
		marcaProdutoService.excluirMarca(codigo);
		return ResponseEntity.noContent().build();
	}

}
