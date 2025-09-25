package sistema.lojavirtual.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.lojavirtual.model.dto.LoginDTO;
import sistema.lojavirtual.security.JwtUtil;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping
	public ResponseEntity<?> logar(@RequestBody LoginDTO loginDTO){
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
					loginDTO.getPassword()));
			
			String token = jwtUtil.generateToken(loginDTO.getUsername());
			
			return ResponseEntity.ok(token);
			
		}catch (BadCredentialsException e) {
			return ResponseEntity.status(401).body("Usuário ou senha inválidos");
		}
	}

}
