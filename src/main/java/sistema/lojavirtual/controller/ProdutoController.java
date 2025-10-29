package sistema.lojavirtual.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.ProdutoRepository;
import sistema.lojavirtual.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<Produto>> buscar() {
		return ResponseEntity.ok(produtoRepository.findAll());
	}
	
	@GetMapping("/porNome/{nome}")
	public ResponseEntity<List<Produto>> buscarPorNome(String nome) {
		return ResponseEntity.ok(produtoRepository.findByNomeContainingIgnoreCase(nome));
	}
	
	@PostMapping
	public ResponseEntity<Produto> salvarProduto(@RequestBody @Valid Produto produto) throws ExceptionMentoria, UnsupportedEncodingException, MessagingException{
		
		Produto produtoSalvo = produtoService.registrarProduto(produto);
		
		return new ResponseEntity<Produto>(produtoSalvo, HttpStatus.CREATED);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> editarProduto(@RequestBody @Valid Produto produto, @PathVariable Long codigo) 
			throws ExceptionMentoria, UnsupportedEncodingException, MessagingException{
		
		Produto produtoFiltrado = produtoService.buscarPorCodigo(codigo);
		
		if (produtoFiltrado == null) {
			return ResponseEntity.notFound().build();
		}
		
		BeanUtils.copyProperties(produto, produtoFiltrado, "id");
		produtoFiltrado = produtoService.registrarProduto(produtoFiltrado);
		
		return ResponseEntity.ok(produtoFiltrado);
		
	}
	
	@DeleteMapping("/{codigoProduto}")
	public ResponseEntity<?> deletarProduto(@PathVariable Long codigoProduto) throws ExceptionMentoria{
		Produto produtoFiltrado = produtoService.buscarPorCodigo(codigoProduto);
		
		if (produtoFiltrado == null) {
			return ResponseEntity.notFound().build();
		}
		
		produtoService.excluirProduto(codigoProduto);
		return ResponseEntity.noContent().build();
		
	}

}
