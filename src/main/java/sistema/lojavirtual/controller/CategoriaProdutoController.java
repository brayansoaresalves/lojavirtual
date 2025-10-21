package sistema.lojavirtual.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.CategoriaProduto;
import sistema.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@GetMapping("/porDescricao/{descricao}")
	public ResponseEntity<List<CategoriaProduto>> buscarCategoriaPorDescricao(String descricao){
		return ResponseEntity.ok(categoriaProdutoRepository.findByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<CategoriaProduto> salvarCategoria(@Valid @RequestBody CategoriaProduto categoriaProduto) throws ExceptionMentoria{
		
		if (categoriaProduto.getEmpresa() == null || (categoriaProduto.getEmpresa().getId() == null || 
				categoriaProduto.getEmpresa().getId() < 0)) {
			throw new ExceptionMentoria("Empresa deve ser informada");
		}
		
		if (categoriaProduto.getId() == null && categoriaProdutoRepository.existeCategoriaCadastrada(categoriaProduto.getDescricao().toUpperCase())) {
			throw new ExceptionMentoria("Não pode cadastrar categoria com o mesmo nome");
		}
		
		CategoriaProduto categoriaSalva = categoriaProdutoRepository.save(categoriaProduto);
		
		return ResponseEntity.ok(categoriaSalva);
		
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<?> deletarCategoria(@PathVariable Long codigo){
		Optional<CategoriaProduto> categoriaFiltrada = categoriaProdutoRepository.findById(codigo);
		
		if (categoriaFiltrada.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		categoriaProdutoRepository.delete(categoriaFiltrada.get());
		
		return ResponseEntity.noContent().build();
		
	}

}
