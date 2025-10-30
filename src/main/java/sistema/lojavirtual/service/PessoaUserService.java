package sistema.lojavirtual.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.PessoaFisica;
import sistema.lojavirtual.model.PessoaJuridica;
import sistema.lojavirtual.model.Usuario;
import sistema.lojavirtual.model.dto.CepDTO;
import sistema.lojavirtual.model.dto.ConsultaCnpjDTO;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.UsuarioRepository;
import sistema.lojavirtual.security.JwtUtil;

@Service
public class PessoaUserService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	PessoaUserService(JwtUtil jwtUtil) {
	}

	public PessoaJuridica salvarPessoaJurdica(PessoaJuridica juridica) {

		for (int i = 0; i < juridica.getEnderecos().size(); i++) {
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
			// usuarioRepository.insereAcessoUserPj(usuarioPj.getId(), "ROLE_ADMIN"); FORMA
			// DINAMICA

			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
			mensagemHtml.append("<b>Login: </b>" + juridica.getEmail() + "<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/>");
			mensagemHtml.append("Obrigado!");

			try {
				serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", mensagemHtml.toString(),
						juridica.getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return juridica;

	}

	public PessoaFisica salvarPessoaFisica(PessoaFisica fisica) throws ExceptionMentoria {

		Optional<Pessoa> empresaFiltrada = pessoaRepository.findById(fisica.getEmpresa().getId());

		if (empresaFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Empresa não cadastrada");
		}

		for (int i = 0; i < fisica.getEnderecos().size(); i++) {
			fisica.getEnderecos().get(i).setPessoa(fisica);
			fisica.getEnderecos().get(i).setEmpresa(empresaFiltrada.get());
		}

		fisica = pessoaRepository.save(fisica);

		Usuario usuarioPF = usuarioRepository.findUserByPessoa(fisica.getId(), fisica.getEmail());

		if (usuarioPF == null) {
			String constraint = usuarioRepository.consultaConstraintAcesso();
			if (constraint != null) {
				jdbcTemplate.execute("alter table usuarios_acessos drop constraint " + constraint);
			}

			usuarioPF = new Usuario();
			usuarioPF.setDataAtualSenha(Calendar.getInstance().getTime());
			usuarioPF.setEmpresa(fisica);
			usuarioPF.setPessoa(fisica);
			usuarioPF.setLogin(fisica.getEmail());

			String senha = "123456";
			String senhaCriptografada = new BCryptPasswordEncoder().encode(senha);

			usuarioPF.setSenha(senhaCriptografada);
			usuarioPF = usuarioRepository.save(usuarioPF);

			usuarioRepository.insereAcessoUserPj(usuarioPF.getId());

			StringBuilder mensagemHtml = new StringBuilder();
			mensagemHtml.append("<b>Segue abaixo seus dados de acesso para a loja virtual</b>");
			mensagemHtml.append("<b>Login: </b>" + fisica.getEmail() + "<br/>");
			mensagemHtml.append("<b>Senha: </b>").append(senha).append("<br/>");
			mensagemHtml.append("Obrigado!");

			try {
				serviceSendEmail.enviarEmailHtml("Acesso Gerado para Loja Virtual", mensagemHtml.toString(),
						fisica.getEmail());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return fisica;

	}

	public CepDTO consultaCep(String cep) {
		return new RestTemplate().getForEntity("https://viacep.com.br/ws/" + cep + "/json/", CepDTO.class).getBody();
	}

	public ConsultaCnpjDTO consultaCnpjReceitaWS(String cnpj) {
		return new RestTemplate().getForEntity("https://receitaws.com.br/v1/cnpj/" + cnpj, ConsultaCnpjDTO.class)
				.getBody();
	}

}
