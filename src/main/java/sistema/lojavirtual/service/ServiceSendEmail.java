package sistema.lojavirtual.service;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class ServiceSendEmail {
    
    // seu email Gmail
    private String username = "brayan.itb19@rede.ulbra.br"; 
    
    // aqui deve ser a "Senha de App" gerada no Gmail, não a senha normal
    private String senha = "xashzvbfganqfqml";
    
    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) 
            throws UnsupportedEncodingException, MessagingException {
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true"); // autenticação habilitada
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, senha);
            }
        });
        
        session.setDebug(true);
        
        Address[] toUser = InternetAddress.parse(emailDestino);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username, "Envio automático do sistema", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, toUser);
        message.setSubject(assunto);
        message.setContent(mensagem, "text/html; charset=UTF-8");
        
        Transport.send(message);
    }

}
