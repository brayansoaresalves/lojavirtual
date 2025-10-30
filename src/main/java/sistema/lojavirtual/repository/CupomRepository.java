package sistema.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.Cupom;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, Long> {

}
