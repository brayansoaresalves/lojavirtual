package sistema.lojavirtual.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.Cupom;
import sistema.lojavirtual.model.FormaPagamento;
import sistema.lojavirtual.model.ItemVendaLoja;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.VendaLoja;
import sistema.lojavirtual.repository.CupomRepository;
import sistema.lojavirtual.repository.FormaPagamentoRepository;
import sistema.lojavirtual.repository.NotaFiscalVendaRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.VendaLojaRepository;

@Service
@RequiredArgsConstructor
public class EmissaoVendaLojaService {
	
	private final VendaLojaRepository vendaLojaRepository;
	private final PessoaRepository pessoaRepository;
	private final FormaPagamentoRepository formaPagamentoRepository;
	private final CupomRepository cupomRepository;
	private final NotaFiscalVendaRepository notaFiscalVendaRepository;
	
	@Transactional
	public VendaLoja emitir(VendaLoja venda) throws ExceptionMentoria {
		
		Pessoa pessoaFisicaFiltrada = pessoaRepository.findById(venda.getPessoa().getId())
				.orElseThrow(() -> new ExceptionMentoria("Cliente não localizado"));
		
		Pessoa empresaFiltrada = pessoaRepository.findById(venda.getEmpresa().getId())
				.orElseThrow(() -> new ExceptionMentoria("Empresa não localizada"));
		
		FormaPagamento formaPagamento = formaPagamentoRepository.findById(venda.getFormaPagamento().getId())
				.orElseThrow(() -> new ExceptionMentoria("Forma de pagamento não cadastrada"));
		
		if (venda.getCupom() != null) {
			Cupom cupom = cupomRepository.findById(venda.getCupom().getId())
					.orElseThrow(() -> new ExceptionMentoria("Cupom não localizado"));
		}
		
		venda.getNotaFiscalVenda().setEmpresa(venda.getEmpresa());
		venda.getNotaFiscalVenda().setVendaLoja(venda);
		
		for (ItemVendaLoja item : venda.getItens()) {
			item.setEmpresa(venda.getEmpresa());
			item.setVendaLoja(venda);
		}
		
		venda = vendaLojaRepository.saveAndFlush(venda);

		return venda;
		
		
	}
	
	

}
