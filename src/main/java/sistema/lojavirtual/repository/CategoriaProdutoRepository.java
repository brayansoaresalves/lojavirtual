package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.CategoriaProduto;

@Repository
public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Long> {
	
	@Query(nativeQuery = true, value = "select count(1) > 0 from categoria_produto where upper(descricao) = ?1")
	boolean existeCategoriaCadastrada(String nomeCategoria);
	
	List<CategoriaProduto> findByDescricaoContainingIgnoreCase(String descricao);

}
