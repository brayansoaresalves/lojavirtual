package sistema.lojavirtual.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.NotaFiscalCompra;
import sistema.lojavirtual.model.NotaItemProduto;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.NotaFiscalCompraRepository;
import sistema.lojavirtual.repository.NotaItemProdutoRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.ProdutoRepository;

@Service
public class NotaItemCompraService {
	
	@Autowired
	private NotaItemProdutoRepository notaItemProdutoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional
	public NotaItemProduto registrarItem(NotaItemProduto notaItem) throws ExceptionMentoria {
		
		Optional<NotaFiscalCompra> notaFiscalFiltrada = notaFiscalCompraRepository.findById(notaItem.getNotaFiscalCompra().getId());
		
		if (notaFiscalFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Nota Fiscal não encontrada");
		}
		
		Optional<Produto> produtoFiltrado = produtoRepository.findById(notaItem.getProduto().getId());
		
		if (produtoFiltrado.isEmpty()) {
			throw new ExceptionMentoria("Produto de " + notaItem.getProduto().getId() + "não cadastrado");
		}
		
		Optional<Pessoa> pessoaFiltrada = pessoaRepository.findById(notaItem.getEmpresa().getId());
		
		if (pessoaFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Empresa de código" + notaItem.getEmpresa().getId() + " não está cadastrada");
		}
		
		if (notaItem.getId() == null) {
			List<NotaItemProduto> notaExistente = notaItemProdutoRepository
					.findByProdutoIdAndNotaFiscalCompraId(notaItem.getProduto().getId(), notaItem.getNotaFiscalCompra().getId());
			if (!notaExistente.isEmpty()) {
				throw new ExceptionMentoria("Já existe esse produto cadastrado para esta nota.");
			}
		}
		
		return notaItemProdutoRepository.save(notaItem);
		
	}
	
	@Transactional
	public void excluirItem(Long notaItemId) throws ExceptionMentoria {
		Optional<NotaItemProduto> itemEncontrado = notaItemProdutoRepository.findById(notaItemId);
		
		if (itemEncontrado.isEmpty()) {
			throw new ExceptionMentoria("Item não encontrado");
		}
		
		notaItemProdutoRepository.delete(itemEncontrado.get());
	}

}
