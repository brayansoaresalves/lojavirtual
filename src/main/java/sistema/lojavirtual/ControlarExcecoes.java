package sistema.lojavirtual;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.mail.MessagingException;
import sistema.lojavirtual.model.dto.ObjetoErroDTO;
import sistema.lojavirtual.service.ServiceSendEmail;

@RestControllerAdvice
@ControllerAdvice
public class ControlarExcecoes extends ResponseEntityExceptionHandler{
	
	@Autowired
	private ServiceSendEmail serviceSendEmail;
	
	@ExceptionHandler({ExceptionMentoria.class})
	public ResponseEntity<Object> handleExceptionCustom(ExceptionMentoria ex){
		ObjetoErroDTO dto = new ObjetoErroDTO();
		dto.setError(ex.getMessage());
		dto.setCode(HttpStatus.OK.toString());
		
		return new ResponseEntity<Object>(dto, HttpStatus.OK);
		
	}
	
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		String msg = "";
		
		if (ex instanceof MethodArgumentNotValidException) {
			List<ObjectError> lista = ((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors();
			
			for (ObjectError error : lista) {
				msg += error.getDefaultMessage() + "\n";
			}
		} else if (ex instanceof HttpMessageNotReadableException) {
			msg = "Não está enviando dados para o body corpo da requisição";
		}
		else {
			msg = ex.getMessage();
		}
		
		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(status.value() + "==> " + HttpStatus.valueOf(status.value()).getReasonPhrase());
		/*ex.printStackTrace();
		
		try {
			serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "brayan.iub10@gmail.com");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
	protected ResponseEntity<Object> handleExceptionDataIntegraty(Exception ex){
		
		ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();
		String msg = "";
		
		if (ex instanceof SQLException) {
			msg = "Erro de Chave Estrangeira do banco " + ((SQLException)ex).getCause().getCause().getMessage();
		}else if (ex instanceof DataIntegrityViolationException)  {
			msg = "Erro de Integridade do banco " + ((DataIntegrityViolationException)ex).getCause().getCause().getMessage();
		}else if (ex instanceof ConstraintViolationException)  {
			msg = "Erro de SQL do Banco" + ((ConstraintViolationException)ex).getCause().getCause().getMessage();
		}else {
			msg = ex.getMessage();
		}
		
		objetoErroDTO.setError(msg);
		objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		
		try {
			serviceSendEmail.enviarEmailHtml("Erro na loja virtual", ExceptionUtils.getStackTrace(ex), "brayan.iub10@gmail.com");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
