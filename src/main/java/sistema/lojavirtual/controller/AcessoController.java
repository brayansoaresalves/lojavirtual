package sistema.lojavirtual.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.lojavirtual.model.Acesso;
import sistema.lojavirtual.repository.AcessoRepository;
import sistema.lojavirtual.service.AcessoService;

@RestController
@RequestMapping("/acessos")
public class AcessoController {
	
	@Autowired
	private AcessoService acessoService;
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	@PostMapping
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) {
		Acesso acessoSalvo = acessoService.salvar(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}

	
	@DeleteMapping("/{acessoId}")
	public ResponseEntity<?> deletarAcesso(@PathVariable Long acessoId){
		Optional<Acesso> acessoEncontrado = acessoRepository.findById(acessoId);
		
		if (acessoEncontrado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Acesso não encontrado");
		}
		
		acessoRepository.delete(acessoEncontrado.get());
		return ResponseEntity.noContent().build();
	}
}
