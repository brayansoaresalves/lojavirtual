package sistema.lojavirtual.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.Cupom;
import sistema.lojavirtual.model.FormaPagamento;
import sistema.lojavirtual.model.ItemVendaLoja;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.StatusRastreio;
import sistema.lojavirtual.model.VendaLoja;
import sistema.lojavirtual.repository.CupomRepository;
import sistema.lojavirtual.repository.FormaPagamentoRepository;
import sistema.lojavirtual.repository.NotaFiscalVendaRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.StatusRastreioRepository;
import sistema.lojavirtual.repository.VendaLojaRepository;

@Service
@RequiredArgsConstructor
public class EmissaoVendaLojaService {
	
	private final VendaLojaRepository vendaLojaRepository;
	private final PessoaRepository pessoaRepository;
	private final FormaPagamentoRepository formaPagamentoRepository;
	private final CupomRepository cupomRepository;
	private final NotaFiscalVendaRepository notaFiscalVendaRepository;
	private final StatusRastreioRepository statusRastreioRepository;
	private final JdbcTemplate jdbcTemplate;
	
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
		StatusRastreio statusRastreio = new StatusRastreio();
		statusRastreio.setCentroDistribuicao("LOJA LOCAL");
		statusRastreio.setCidade("ITUMBIARA");
		statusRastreio.setEmpresa(venda.getEmpresa());
		statusRastreio.setEstado("GO");
		statusRastreio.setStatus("Início Compra");
		statusRastreio.setVendaLoja(venda);

		statusRastreioRepository.save(statusRastreio);
		
		return venda;
		
		
	}
	

	public void removerVendaEAssociacao(Long vendaId) throws ExceptionMentoria {
		jdbcTemplate.update("UPDATE nota_fiscal_venda SET venda_loja_id = NULL WHERE venda_loja_id = ?", vendaId);
		jdbcTemplate.update("DELETE FROM nota_fiscal_venda WHERE venda_loja_id = ?", vendaId);
		jdbcTemplate.update("DELETE FROM item_venda_loja WHERE venda_loja_id = ?", vendaId);
		jdbcTemplate.update("DELETE FROM status_rastreio WHERE venda_loja_id = ?", vendaId);
		jdbcTemplate.update("DELETE FROM venda_loja WHERE id = ?", vendaId);

		
	}
	
	public void exclusaoTotalVenda(Long vendaId) {
		String sql = "update venda_loja set excluido = true where id = '"+vendaId+"'";
		jdbcTemplate.execute(sql);
	}
	
	public void retornarVenda(Long vendaId) {
		String sql = "update venda_loja set excluido = false where id = '"+vendaId+"'";
		jdbcTemplate.execute(sql);
	}
	
	

}
