package sistema.lojavirtual.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.ProdutoRepository;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@PostMapping
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoria{
		
		if (produtoRepository.existsByNomeIgnoreCase(produto.getNome())) {
			throw new ExceptionMentoria("Já existe produto com esse nome");
		}
		
		Optional<Pessoa> empresaVinculada = pessoaRepository.findById(produto.getEmpresa().getId());
		
		if (empresaVinculada.isEmpty()) {
			throw new ExceptionMentoria("Não existe empresa cadastrada");
		}
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.CREATED);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> editarProduto(@RequestBody @Valid Produto produto, @PathVariable Long codigo) throws ExceptionMentoria{
		Optional<Produto> produtoFiltrado = produtoRepository.findById(codigo);
		
		if (produtoFiltrado.isEmpty()) {
			throw new ExceptionMentoria("Produto não encontrado");
		}
		
		produto.setId(produtoFiltrado.get().getId());
		
		
	}

}
