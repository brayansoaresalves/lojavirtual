package sistema.lojavirtual.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sistema.lojavirtual.model.Acesso;
import sistema.lojavirtual.repository.AcessoRepository;

@Service
public class AcessoService {
	
	@Autowired
	private AcessoRepository acessoRepository;
	
	public Acesso salvar(Acesso acesso) {
		return acessoRepository.save(acesso);
	}

}
