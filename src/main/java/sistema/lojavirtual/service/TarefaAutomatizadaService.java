package sistema.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import sistema.lojavirtual.model.Usuario;
import sistema.lojavirtual.repository.UsuarioRepository;

@Service
public class TarefaAutomatizadaService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@Scheduled(initialDelay = 2000, fixedDelay = 86400000)
	//@Scheduled(cron = "0 0 11 * * *", zone = "America/Sao_Paulo")/*Vai rodar todo dia 11 da manha horario br/sp*/
	public void notificarUserTrocaSenha() throws UnsupportedEncodingException, MessagingException, InterruptedException {
		
		List<Usuario> usuarios = usuarioRepository.usuarioSenhaVencida();
		
		for (Usuario usuario : usuarios) {
			StringBuilder msg = new StringBuilder();
			msg.append("Ola, ").append(usuario.getPessoa().getNome()).append("<br/>");
			msg.append("Está na hora de trocar sua senha, já passou 90 dias de validade.").append("<br/>");
			msg.append("Troca a sua senha no sistema - Teste");
			
			serviceSendEmail.enviarEmailHtml("Troca de senha", msg.toString(), usuario.getLogin());
			
			Thread.sleep(3000);
		}
	}

}
