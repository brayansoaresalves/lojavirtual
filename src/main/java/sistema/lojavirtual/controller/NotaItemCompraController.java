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
import sistema.lojavirtual.model.NotaItemProduto;
import sistema.lojavirtual.repository.NotaItemProdutoRepository;
import sistema.lojavirtual.service.NotaItemCompraService;

@RestController
@RequestMapping("/itens")
public class NotaItemCompraController {
	
	@Autowired
	private NotaItemCompraService notaItemCompraService;
	
	@Autowired
	private NotaItemProdutoRepository notaItemProdutoRepository;
	
	@PostMapping
	public ResponseEntity<NotaItemProduto> salvarItem(@RequestBody @Valid NotaItemProduto notaItem) throws ExceptionMentoria{
		NotaItemProduto notaItemSalvo = notaItemCompraService.registrarItem(notaItem);
		return ResponseEntity.ok(notaItemSalvo);
	}
	
	@PutMapping("/{notaItemId}")
	public ResponseEntity<?> atualizarItem(@Valid @RequestBody NotaItemProduto notaItem, @PathVariable Long notaItemId) throws ExceptionMentoria{
		NotaItemProduto itemFiltrado = notaItemProdutoRepository.findById(notaItemId).orElseThrow(
				() -> new ExceptionMentoria("Item não localizado"));
		BeanUtils.copyProperties(notaItem, itemFiltrado, "id");
		itemFiltrado = notaItemCompraService.registrarItem(notaItem);
		return ResponseEntity.ok(itemFiltrado);
		
	}
	
	@DeleteMapping("/{notaItemId}")
	public ResponseEntity<?> removerItem(@PathVariable Long notaItemId) throws ExceptionMentoria{
		notaItemCompraService.excluirItem(notaItemId);
		return ResponseEntity.noContent().build();
		
	}
	
	@GetMapping("/produto/{produtoId}")
	public ResponseEntity<List<NotaItemProduto>> buscandoItemNotaPorProduto(@PathVariable Long produtoId){
		return ResponseEntity.ok(notaItemProdutoRepository.findByProdutoId(produtoId));
	}
	
	@GetMapping("/nota/{notaId}")
	public ResponseEntity<List<NotaItemProduto>> buscandoItemPelaNota(@PathVariable Long notaId){
		return ResponseEntity.ok(notaItemProdutoRepository.findByNotaFiscalCompraId(notaId));
	}
	
	@GetMapping("/empresa/{empresaId}")
	public ResponseEntity<List<NotaItemProduto>> buscandoItemPelaEmpresa(@PathVariable Long empresaId){
		return ResponseEntity.ok(notaItemProdutoRepository.findByEmpresaId(empresaId));
	}

}
