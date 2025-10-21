package sistema.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public Produto registrarProduto(Produto produto) {
		
		if (produto.getId() == null && produtoRepository.existsByNomeIgnoreCase(produto.getNome())) {
			
		}
		
	}

}
