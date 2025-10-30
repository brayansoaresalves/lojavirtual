package sistema.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.ImagemProduto;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.ImagemProdutoRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.ProdutoRepository;

@Service
public class ImagemProdutoService {
	
	@Autowired
	private ImagemProdutoRepository imagemProdutoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public ImagemProduto salvarImagem(ImagemProduto imagem) throws ExceptionMentoria {
		Produto produto = produtoRepository.findById(imagem.getProduto().getId())
				.orElseThrow(() -> new ExceptionMentoria("Produto não cadastrado na base"));
		Pessoa empresa = pessoaRepository.findById(imagem.getEmpresa().getId())
				.orElseThrow(() -> new ExceptionMentoria("Empresa não cadastrada"));
		return imagemProdutoRepository.saveAndFlush(imagem);
		
	}
	
	@Transactional
	public void removerImagem(Long imagemProdutoId) throws ExceptionMentoria {
		ImagemProduto imagemSelecionada = imagemProdutoRepository.findById(imagemProdutoId)
				.orElseThrow(() -> new ExceptionMentoria("Não foi localizada a foto."));
		imagemProdutoRepository.delete(imagemSelecionada);
		imagemProdutoRepository.flush();
	}

}
