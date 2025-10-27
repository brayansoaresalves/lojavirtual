package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.ContaPagar;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
	
	List<ContaPagar> findByDescricaoContainingIgnoreCase(String descricao);
	
	List<ContaPagar> findByPessoaId(Long pessoaId);
	
	List<ContaPagar> findByPessoaFornecedoraId(Long pessoaFornecedoraId);
	
	List<ContaPagar> findByEmpresaId(Long empresaId);
	
	boolean existsByDescricaoContainingIgnoreCaseAndEmpresaIdAndPessoaIdAndPessoaFornecedoraId(String descricao, Long empresaId, Long pessoaId, Long fornecedorId);

}
