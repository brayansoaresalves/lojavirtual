package sistema.lojavirtual.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.MarcaProduto;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.repository.MarcaProdutoRepository;
import sistema.lojavirtual.repository.PessoaRepository;

@Service
public class MarcaProdutoService {
	
	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Transactional
	public MarcaProduto registrar(MarcaProduto marcaProduto) throws ExceptionMentoria {
		
		Optional<Pessoa> empresaFiltrada = pessoaRepository.findById(marcaProduto.getEmpresa().getId());
		
		if (empresaFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Não existe empresa de código " + marcaProduto.getEmpresa().getId() + "cadastrada.");
		}
		
		if (marcaProduto.getId() == null && marcaProdutoRepository.existsByDescricaoIgnoreCase(marcaProduto.getDescricao())) {
			throw new ExceptionMentoria("Já existe marca com essa descrição cadastrada");
		}
		
		return marcaProdutoRepository.save(marcaProduto);
		
	}
	
	@Transactional
	public void excluirMarca(Long codigoMarca) throws ExceptionMentoria {
		
		MarcaProduto marcaFiltrada = marcaProdutoRepository.findById(codigoMarca).orElseThrow(
				() -> new ExceptionMentoria("Marca não encontrada"));
		marcaProdutoRepository.delete(marcaFiltrada);
	}

}
