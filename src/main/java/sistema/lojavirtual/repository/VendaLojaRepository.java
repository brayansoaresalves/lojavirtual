package sistema.lojavirtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistema.lojavirtual.model.VendaLoja;

@Repository
public interface VendaLojaRepository extends JpaRepository<VendaLoja, Long> {

}
