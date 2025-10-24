package sistema.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.ContaPagar;

@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {

}
