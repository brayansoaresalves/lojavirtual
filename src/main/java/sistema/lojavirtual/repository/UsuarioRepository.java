package sistema.lojavirtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import sistema.lojavirtual.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Query(value = "select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);
	
	@Query(value = "select * from usuario where data_atual_senha <= current_date - interval '90 day'", nativeQuery = true)
	List<Usuario> usuarioSenhaVencida();

	@Query("select u from Usuario u where u.pessoa.id = ?1 or u.login = ?2")
	Usuario findUserByPessoa(Long id, String email);

	@Query(value = "select constraint_name from information_schema.constraint_column_usage "
			+ "where table_name = 'usuarios_acessos' and column_name = 'acesso_id' and "
			+ "constraint_name <> 'unique_acesso_user' ", nativeQuery = true)
	String consultaConstraintAcesso();

	@Transactional
	@Modifying
	@Query(value = "insert into usuarios_acessos (usuario_id, acesso_id) " +
	               "select ?1, id from acesso where descricao = 'ROLE_USER'", 
	       nativeQuery = true)
	void insereAcessoUserPj(Long id);

}
