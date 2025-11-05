package sistema.lojavirtual.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.VendaLoja;

@Repository
public interface VendaLojaRepository extends JpaRepository<VendaLoja, Long> {
	
	VendaLoja findByIdAndExcluidoFalse(Long id);
	
	List<VendaLoja> findByPessoaIdAndExcluidoFalse(Long pessoaId);
	
	List<VendaLoja> findByEnderecoCobrancaIdAndExcluidoFalse(Long enderecoId);
	
	List<VendaLoja> findByEnderecoEntregaIdAndExcluidoFalse(Long enderecoId);
	
	List<VendaLoja> findByDataVendaBetweenAndExcluidoFalse(Date dataInicial, Date dataFinal);
	
	List<VendaLoja> findDistinctByItensProdutoIdAndExcluidoFalse(Long produtoId);
	
	List<VendaLoja> findDistinctByItensProdutoNomeContainingIgnoreCaseAndExcluidoFalse(String nome);

}
