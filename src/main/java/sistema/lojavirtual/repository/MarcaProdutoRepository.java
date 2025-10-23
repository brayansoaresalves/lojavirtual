package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.MarcaProduto;

@Repository
public interface MarcaProdutoRepository extends JpaRepository<MarcaProduto, Long> {
	
	List<MarcaProduto> findByDescricaoContainingIgnoreCase(String descricao);
	
	boolean existsByDescricaoIgnoreCase(String descricao);

}
