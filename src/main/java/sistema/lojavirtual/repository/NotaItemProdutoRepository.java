package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.NotaItemProduto;

@Repository
public interface NotaItemProdutoRepository extends JpaRepository<NotaItemProduto, Long> {
	
	List<NotaItemProduto> findByProdutoIdAndNotaFiscalCompraId(Long idProduto, Long idNotaFiscal);
	
	List<NotaItemProduto> findByProdutoId(Long idProduto);
	
	List<NotaItemProduto> findByNotaFiscalCompraId(Long notaFiscalId);
	
	List<NotaItemProduto> findByEmpresaId(Long empresaId);

}
