package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.model.FormaPagamento;

@Repository
@Transactional
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
	
	List<FormaPagamento> findByEmpresaId(Long empresaId);

}
