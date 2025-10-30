package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.AvaliacaoProduto;

@Repository
public interface AvaliacaoProdutoRepository extends JpaRepository<AvaliacaoProduto, Long> {
	
	List<AvaliacaoProduto> findByProdutoId(Long produtoId);
	
	List<AvaliacaoProduto> findByProdutoIdAndEmpresaId(Long produtoId, Long empresaId);
	
	List<AvaliacaoProduto> findByProdutoIdAndPessoaId(Long produtoId, Long pessoaId);
	
	List<AvaliacaoProduto> findByPessoaId(Long pessoaId);

}
