package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	
	List<Produto> findByNomeContainingIgnoreCase(String nome);
	
	boolean existsByNomeIgnoreCase(String nome);
}
