package sistema.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import sistema.lojavirtual.model.AvaliacaoProduto;
import sistema.lojavirtual.model.dto.AvaliacaoDTO;
import sistema.lojavirtual.repository.AvaliacaoProdutoRepository;
import sistema.lojavirtual.service.AvaliacaoProdutoService;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoProdutoController {
	
	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	
	@Autowired
	private AvaliacaoProdutoService avaliacaoProdutoService;
	
	@GetMapping
	public ResponseEntity<List<AvaliacaoProduto>> todas(){
		return ResponseEntity.ok(avaliacaoProdutoRepository.findAll());
	}
	
	@GetMapping("/produto/{produtoId}/{empresaId}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarPorProdutoEEmpresa(Long produtoId, Long empresaId){
		return ResponseEntity.ok(avaliacaoProdutoRepository.findByProdutoIdAndEmpresaId(produtoId, empresaId));
	}
	
	@GetMapping("/produto/{produtoId}/{pessoaid}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarPorProdutoEPessoa(Long produtoId, Long pessoaId){
		return ResponseEntity.ok(avaliacaoProdutoRepository.findByProdutoIdAndPessoaId(produtoId, pessoaId));
	}
	
	@GetMapping("/pessoa/{pessoaId}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarPorPessoa(Long pessoaId){
		return ResponseEntity.ok(avaliacaoProdutoRepository.findByPessoaId(pessoaId));
	}
	
	@GetMapping("/produto/{produtoId}")
	public ResponseEntity<List<AvaliacaoProduto>> buscarPorProduto(Long produtoId){
		return ResponseEntity.ok(avaliacaoProdutoRepository.findByProdutoId(produtoId));
	}
	
	@PostMapping
	public ResponseEntity<AvaliacaoDTO> registrar(@RequestBody @Valid AvaliacaoProduto avaliacao) throws ExceptionMentoria{
		avaliacao = avaliacaoProdutoService.salvarAvaliacao(avaliacao);
		AvaliacaoDTO avaliacaoDTO = new AvaliacaoDTO();
		avaliacaoDTO.setId(avaliacao.getId());
		avaliacaoDTO.setDescricao(avaliacao.getDescricao());
		avaliacaoDTO.setNota(avaliacao.getNota());
		avaliacaoDTO.setPessoa(avaliacao.getPessoa().getId());
		avaliacaoDTO.setEmpresa(avaliacao.getEmpresa().getId());
		avaliacaoDTO.setProduto(avaliacao.getProduto().getId());
		return ResponseEntity.ok(avaliacaoDTO);
	}
	
	@DeleteMapping("/remover/{avaliacaoId}")
	public ResponseEntity<?> removerAvaliacao(@PathVariable Long avaliacaoId) throws ExceptionMentoria{
		avaliacaoProdutoService.removerAvaliacao(avaliacaoId);
		return ResponseEntity.noContent().build();
	}

}
