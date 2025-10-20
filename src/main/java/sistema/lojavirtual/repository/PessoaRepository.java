package sistema.lojavirtual.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.PessoaFisica;
import sistema.lojavirtual.model.PessoaJuridica;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	@Query("select pj from PessoaJuridica pj where pj.cnpj = ?1")
	Optional<PessoaJuridica> existeCnpjCadastrado(String cnpj);
	
	@Query("select pf from PessoaFisica pf where pf.cpf = ?1")
	Optional<PessoaFisica> existeCpfCadastrado(String cpf);
	
	List<PessoaFisica> findByNomeContainingIgnoreCase(String nome);
	
	List<PessoaJuridica> findByRazaoContainingIgnoreCase(String razao);

}
