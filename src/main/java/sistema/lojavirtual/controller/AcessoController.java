package sistema.lojavirtual.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sistema.lojavirtual.ExceptionMentoria;
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
	public ResponseEntity<Acesso> salvarAcesso(@RequestBody Acesso acesso) throws ExceptionMentoria {
		
		if (acesso.getId() == null) {
			List<Acesso> acessos = acessoRepository.buscarAcessoDescricao(acesso.getDescricao().toUpperCase());
			if (!acessos.isEmpty()) {
				throw new ExceptionMentoria("Já existe acesso com a descrição : " + acesso.getDescricao());
			}
		}
		
		Acesso acessoSalvo = acessoService.salvar(acesso);
		
		return new ResponseEntity<Acesso>(acessoSalvo, HttpStatus.OK);
	}


	//@Secured({"ROLE_ADMIN", "ROLE_GERENTE"})
	@DeleteMapping("/{acessoId}")
	public ResponseEntity<?> deletarAcesso(@PathVariable Long acessoId){
		Optional<Acesso> acessoEncontrado = acessoRepository.findById(acessoId);
		
		if (acessoEncontrado.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Acesso não encontrado");
		}
		
		acessoRepository.delete(acessoEncontrado.get());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/obterAcesso/{id}")
	public ResponseEntity<Acesso> obterAcesso(@PathVariable Long id) throws ExceptionMentoria{
		Acesso acesso = acessoRepository.findById(id).orElseThrow(() -> new ExceptionMentoria("Não encontrado Acesso com o codígo: " + id));
		
		
		return new ResponseEntity<Acesso>(acesso, HttpStatus.OK);
	}
	
	@GetMapping("/buscarPorDesc/{descricao}")
	public ResponseEntity<List<Acesso>> buscarPorDescricao(@PathVariable String descricao){
		List<Acesso> acessos = acessoRepository.buscarAcessoDescricao(descricao.toUpperCase());
		return new ResponseEntity<List<Acesso>>(acessos, HttpStatus.OK);
	}
}
