package sistema.lojavirtual.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import sistema.lojavirtual.model.ImagemProduto;
import sistema.lojavirtual.model.dto.ImagemProdutoDTO;
import sistema.lojavirtual.repository.ImagemProdutoRepository;
import sistema.lojavirtual.service.ImagemProdutoService;

@RestController
@RequestMapping("/imagens")
public class ImagemProdutoController {
	
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	@Autowired
	private ImagemProdutoService imagemProdutoService;
	
	@GetMapping("/porProduto/{produtoId}")
	public ResponseEntity<List<ImagemProduto>> buscarPorProduto(@PathVariable Long produtoId){
		return ResponseEntity.ok(imagemProdutoRepository.findByProdutoId(produtoId));
	}
	
	@PostMapping
	public ResponseEntity<ImagemProdutoDTO> registrar(@RequestBody @Valid ImagemProduto imagemProduto) throws ExceptionMentoria{
		ImagemProduto imagem = imagemProdutoService.salvarImagem(imagemProduto);
		ImagemProdutoDTO imagemProdutoDTO = new ImagemProdutoDTO();
		imagemProdutoDTO.setId(imagem.getId());
		imagemProdutoDTO.setImagemOriginal(imagem.getImagemOriginal());
		imagemProdutoDTO.setImagemMiniatura(imagem.getImagemMiniatura());
		imagemProdutoDTO.setProduto(imagem.getProduto().getId());
		imagemProdutoDTO.setEmpresa(imagem.getEmpresa().getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(imagemProdutoDTO);
	}
	
	@DeleteMapping("/excluindoImagem/{imagemSelecionadaId}")
	public ResponseEntity<?> excluirImagem(@PathVariable Long imagemSelecionadaId) throws ExceptionMentoria{
		imagemProdutoService.removerImagem(imagemSelecionadaId);
		return ResponseEntity.noContent().build();
	}

}
