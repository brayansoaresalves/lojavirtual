package sistema.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.ContaPagar;
import sistema.lojavirtual.repository.ContaPagarRepository;
import sistema.lojavirtual.repository.PessoaRepository;

@Service
public class EmissaoContaPagarService {
	
	@Autowired
	private ContaPagarRepository contaPagarRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public List<ContaPagar> buscarPorDescricao(String descricao){
		return contaPagarRepository.findByDescricaoContainingIgnoreCase(descricao);
	}
	
	public List<ContaPagar> buscarContaPagarPorPessoa(Long pessoaId) throws ExceptionMentoria{
		boolean pessoaExistente = existePessoa(pessoaId);
		
		if (!pessoaExistente) {
			throw new ExceptionMentoria("A pessoa de código " + pessoaId + " não está cadastrada");
		}
		
		return contaPagarRepository.findByPessoaId(pessoaId);
	}
	
	public List<ContaPagar> buscarContaPagarPorFornecedor(Long pessoaId) throws ExceptionMentoria{
		boolean pessoaExistente = existePessoa(pessoaId);
		
		if (!pessoaExistente) {
			throw new ExceptionMentoria("A pessoa de código " + pessoaId + " não está cadastrada");
		}
		
		return contaPagarRepository.findByPessoaFornecedoraId(pessoaId);
	}
	
	public List<ContaPagar> buscarContaPagarPorEmpresa(Long pessoaId) throws ExceptionMentoria{
		boolean pessoaExistente = existePessoa(pessoaId);
		
		if (!pessoaExistente) {
			throw new ExceptionMentoria("A pessoa de código " + pessoaId + " não está cadastrada");
		}
		
		return contaPagarRepository.findByEmpresaId(pessoaId);
	}
	
	@Transactional
	public void excluirContaPagar(Long idContaPagar) throws ExceptionMentoria {
		ContaPagar contaSelecionada = contaPagarRepository.findById(idContaPagar).orElseThrow(() -> 
		new ExceptionMentoria("Conta nao encontrada"));
		contaPagarRepository.delete(contaSelecionada);
		contaPagarRepository.flush();
	}
	
	
	private boolean existePessoa(Long pessoaId) {
		return pessoaRepository.findById(pessoaId).isPresent(); 
	}

}
