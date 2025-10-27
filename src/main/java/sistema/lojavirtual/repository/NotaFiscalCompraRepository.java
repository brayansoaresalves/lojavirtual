package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.NotaFiscalCompra;

@Repository
public interface NotaFiscalCompraRepository extends JpaRepository<NotaFiscalCompra, Long> {

	List<NotaFiscalCompra> findByPessoaId(Long pessoaId);
	
	List<NotaFiscalCompra> findByEmpresaId(Long empresaId);
	
	List<NotaFiscalCompra> findByNumeroNotaContainingIgnoreCase(String numeroNota);
	
	List<NotaFiscalCompra> findByContaPagarId(Long contaPagarId);
	
	
}
