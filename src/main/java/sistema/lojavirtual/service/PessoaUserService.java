package sistema.lojavirtual.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sistema.lojavirtual.model.PessoaJuridica;
import sistema.lojavirtual.model.Usuario;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.UsuarioRepository;
import sistema.lojavirtual.security.JwtUtil;

@Service
public class PessoaUserService {

    private final JwtUtil jwtUtil;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

    PessoaUserService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
	
	public PessoaJuridica salvarPessoaJurdica(PessoaJuridica juridica) {
		
		juridica = pessoaRepository.save(juridica);
		
		for (int i = 0; i<juridica.getEnderecos().size(); i++) {
			juridica.getEnderecos().get(i).setPessoa(juridica);
			juridica.getEnderecos().get(i).setEmpresa(juridica);
		}
		
		juridica = pessoaRepository.save(juridica);
		
		Usuario usuarioPj = usuarioRepository.findUserByPessoa(juridica.getId(), juridica.getEmail());
		
		if (usuarioPj == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("alter table usuarios_acessos drop constraint " + constraint);
			}
			
			usuarioPj = new Usuario();
			usuarioPj.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPj.setEmpresa(juridica);
			usuarioPj.setPessoa(juridica);
			usuarioPj.setLogin(juridica.getEmail());
			
			String senha = "123456";
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);
			
			usuarioPj.setSenha(senhaCriptografada);
			usuarioPj = usuarioRepository.save(usuarioPj);
			
			usuarioRepository.insereAcessoUserPj(usuarioPj.getId());
		}
		
		return juridica;
		
	}
	

}
