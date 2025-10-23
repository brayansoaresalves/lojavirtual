package sistema.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.CategoriaProduto;
import sistema.lojavirtual.model.MarcaProduto;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.CategoriaProdutoRepository;
import sistema.lojavirtual.repository.MarcaProdutoRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;
	
	@Transactional
	public Produto registrarProduto(Produto produto) throws ExceptionMentoria {
		
		if (produto.getId() == null && produtoRepository.existsByNomeIgnoreCase(produto.getNome())) {
			throw new ExceptionMentoria("Já existe produto cadastrado com esse nome");
		}
		
		Optional<Pessoa> empresaFiltrada = pessoaRepository.findById(produto.getEmpresa().getId());
		
		if (empresaFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Não existe empresa cadastrada com o código " + produto.getEmpresa().getId());
		}
		
		Optional<MarcaProduto> marcaProdutoExistente = marcaProdutoRepository.findById(produto.getMarcaProduto().getId());
		Optional<CategoriaProduto> categoriaOptional = categoriaProdutoRepository.findById(produto.getCategoriaProduto().getId());
		
		if (marcaProdutoExistente.isEmpty()) {
			throw new ExceptionMentoria("Não existe marca cadastrada com o código " + produto.getMarcaProduto().getId());
		}
		
		if (categoriaOptional.isEmpty()) {
			throw new ExceptionMentoria("Não existe categoria cadastrada com o código " + produto.getCategoriaProduto().getId());
		}
		
		return produtoRepository.save(produto);
	}
	
	@Transactional
	public void excluirProduto(Long codigoProduto) throws ExceptionMentoria {
		Optional<Produto> produtoFiltrado = produtoRepository.findById(codigoProduto);
		
		try {
			
			if (produtoFiltrado.isEmpty()) {
				throw new ExceptionMentoria("Não existe produto cadastrado com o código " + codigoProduto);
			}
			
			produtoRepository.deleteById(codigoProduto);
			produtoRepository.flush();
			
		}catch (DataIntegrityViolationException e) {
			throw new ExceptionMentoria("Não é possivel excluir um produto vinculado. Verifique");
		}
	}
	
	public Produto buscarPorCodigo(Long codigoProduto) throws ExceptionMentoria {
		return produtoRepository.findById(codigoProduto).orElseThrow(
				() -> new ExceptionMentoria("Nao existe produto com o código " + codigoProduto));
	}

}
