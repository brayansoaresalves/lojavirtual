package sistema.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.AvaliacaoProduto;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.AvaliacaoProdutoRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.ProdutoRepository;

@Service
public class AvaliacaoProdutoService {
	
	@Autowired
	private AvaliacaoProdutoRepository avaliacaoProdutoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public AvaliacaoProduto salvarAvaliacao(AvaliacaoProduto avaliacaoProduto) throws ExceptionMentoria {
		
		Pessoa pessoaFisica = pessoaRepository.findById(avaliacaoProduto.getPessoa().getId())
				.orElseThrow(() -> new ExceptionMentoria("Informar o cliente da avaliação"));
		
		Pessoa empresa = pessoaRepository.findById(avaliacaoProduto.getEmpresa().getId())
				.orElseThrow(() -> new ExceptionMentoria("Informar a empresa da avaliação"));
		
		
		Produto produto = produtoRepository.findById(avaliacaoProduto.getProduto().getId())
				.orElseThrow(() -> new ExceptionMentoria("Informar o produto a ser avaliado"));
		
		return avaliacaoProdutoRepository.saveAndFlush(avaliacaoProduto);
			
	}
	
	@Transactional
	public void removerAvaliacao(Long avaliacaoId) throws ExceptionMentoria {
		AvaliacaoProduto avaliacao = avaliacaoProdutoRepository.findById(avaliacaoId)
				.orElseThrow(() -> new ExceptionMentoria("Não encontrada nenhuma avaliação"));
		avaliacaoProdutoRepository.delete(avaliacao);
		avaliacaoProdutoRepository.flush();
	}

}
