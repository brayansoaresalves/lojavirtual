package sistema.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.CategoriaProduto;
import sistema.lojavirtual.repository.CategoriaProdutoRepository;

@RestController
public class CategoriaProdutoController {
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@PostMapping("/salvarCategoria")
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

}
