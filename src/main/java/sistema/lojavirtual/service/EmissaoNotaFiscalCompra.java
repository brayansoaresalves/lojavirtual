package sistema.lojavirtual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.NotaFiscalCompra;
import sistema.lojavirtual.repository.ContaPagarRepository;
import sistema.lojavirtual.repository.NotaFiscalCompraRepository;
import sistema.lojavirtual.repository.PessoaRepository;

@Service
public class EmissaoNotaFiscalCompra {
	
	@Autowired
	private NotaFiscalCompraRepository notaFiscalCompraRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ContaPagarRepository contaPagarRepository;
	
	@Transactional
	public NotaFiscalCompra registrarNota(NotaFiscalCompra nota) throws ExceptionMentoria {
	
		
		boolean existeEmpresa = existePessoa(nota.getEmpresa().getId());
		if (!existeEmpresa) {
			throw new ExceptionMentoria("Não existe empresa cadastrada com o código " + nota.getEmpresa().getId());
		}
		
		boolean temPessoa = existePessoa(nota.getPessoa().getId());
		
		if (!temPessoa) {
			throw new ExceptionMentoria("Não existe pessoa cadastrada com o código " + nota.getPessoa().getId());
		}
		
		
		return notaFiscalCompraRepository.save(nota);
		
	}
	
	public List<NotaFiscalCompra> buscarPorNumeroNota(String numeroNota){
		return notaFiscalCompraRepository.findByNumeroNotaContainingIgnoreCase(numeroNota);
	}
	
	public List<NotaFiscalCompra> buscarNotaPorPessoa(Long notaId) throws ExceptionMentoria{
		boolean pessoaExistente = existePessoa(notaId);
		
		if (!pessoaExistente) {
			throw new ExceptionMentoria("A pessoa de código " + notaId + " não está cadastrada");
		}
		
		return notaFiscalCompraRepository.findByPessoaId(notaId);
	}
	
	public List<NotaFiscalCompra> buscarNotarPorEmpresa(Long notaId) throws ExceptionMentoria{
		boolean pessoaExistente = existePessoa(notaId);
		
		if (!pessoaExistente) {
			throw new ExceptionMentoria("A pessoa de código " + notaId + " não está cadastrada");
		}
		
		return notaFiscalCompraRepository.findByEmpresaId(notaId);
	}
	
	public List<NotaFiscalCompra> buscarPorContaPagar(Long contaPagarId) throws ExceptionMentoria {
		boolean existeContaPagar = contaPagarRepository.findById(contaPagarId).isPresent();
		
		if (!existeContaPagar) {
			throw new ExceptionMentoria("A conta de código " + contaPagarId + " não existe!!.");
		}
		
		return notaFiscalCompraRepository.findByContaPagarId(contaPagarId);
	}
	
	
	private boolean existePessoa(Long pessoaId) {
		return pessoaRepository.findById(pessoaId).isPresent(); 
	}

}
