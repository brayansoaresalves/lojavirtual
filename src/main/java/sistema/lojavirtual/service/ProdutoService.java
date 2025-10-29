package sistema.lojavirtual.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import sistema.lojavirtual.ExceptionMentoria;
import sistema.lojavirtual.model.CategoriaProduto;
import sistema.lojavirtual.model.MarcaProduto;
import sistema.lojavirtual.model.Pessoa;
import sistema.lojavirtual.model.Produto;
import sistema.lojavirtual.repository.CategoriaProdutoRepository;
import sistema.lojavirtual.repository.MarcaProdutoRepository;
import sistema.lojavirtual.repository.PessoaRepository;
import sistema.lojavirtual.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private MarcaProdutoRepository marcaProdutoRepository;

	@Autowired
	private CategoriaProdutoRepository categoriaProdutoRepository;

	@Autowired
	private ServiceSendEmail serviceSendEmail;

	@Transactional
	public Produto registrarProduto(Produto produto)
			throws ExceptionMentoria, UnsupportedEncodingException, MessagingException {

		Base64.Decoder decoder = Base64.getDecoder();
		Base64.Encoder encoder = Base64.getEncoder();

		if (produto.isNovo() && produtoRepository.existsByNomeIgnoreCase(produto.getNome())) {
			throw new ExceptionMentoria("Já existe produto cadastrado com esse nome");
		}

		Optional<Pessoa> empresaFiltrada = pessoaRepository.findById(produto.getEmpresa().getId());

		if (empresaFiltrada.isEmpty()) {
			throw new ExceptionMentoria("Não existe empresa cadastrada com o código " + produto.getEmpresa().getId());
		}

		Optional<MarcaProduto> marcaProdutoExistente = marcaProdutoRepository
				.findById(produto.getMarcaProduto().getId());
		Optional<CategoriaProduto> categoriaOptional = categoriaProdutoRepository
				.findById(produto.getCategoriaProduto().getId());

		if (marcaProdutoExistente.isEmpty()) {
			throw new ExceptionMentoria(
					"Não existe marca cadastrada com o código " + produto.getMarcaProduto().getId());
		}

		if (categoriaOptional.isEmpty()) {
			throw new ExceptionMentoria(
					"Não existe categoria cadastrada com o código " + produto.getCategoriaProduto().getId());
		}

		if (produto.isNovo()) {

			for (int i = 0; i < produto.getImagens().size(); i++) {
				produto.getImagens().get(i).setProduto(produto);
				produto.getImagens().get(i).setEmpresa(produto.getEmpresa());

				String base64Image = "";

				if (produto.getImagens().get(i).getImagemOriginal().contains("data:image")) {
					base64Image = produto.getImagens().get(i).getImagemOriginal().split(",")[1];
				} else {
					base64Image = produto.getImagens().get(i).getImagemOriginal();
				}

				byte[] imagesBytes = decoder.decode(base64Image);
				try {
					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagesBytes));
					if (bufferedImage != null) {

						int largura = 800; 
						int altura = 600;

					
						int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_RGB : bufferedImage.getType();

						BufferedImage resizedImage = new BufferedImage(largura, altura, type);
						Graphics2D g = resizedImage.createGraphics();
						g.drawImage(bufferedImage, 0, 0, largura, altura, null);
						g.dispose();

						ByteArrayOutputStream baos = new ByteArrayOutputStream();


						ImageIO.write(resizedImage, "png", baos);

						String miniImgBase64 = "data:image/png;base64," + encoder.encodeToString(baos.toByteArray());

						produto.getImagens().get(i).setImagemMiniatura(miniImgBase64);

						baos.close();

					} else {
						System.err.println("Erro: Não foi possível ler a imagem Base64. (Index: " + i + ")");

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		produto = produtoRepository.save(produto);

		if (produto.getAlertaQuantidadeEstoque() && produto.getQuantidadeEstoque() <= 5) {
			StringBuilder html = new StringBuilder();
			html.append("<h2>").append("Produto: " + produto.getNome())
					.append(" com estoque baixo: " + produto.getQuantidadeEstoque() + "</h2>");
			html.append("<p> Id Produto.:").append(produto.getId()).append("</p>");

			if (empresaFiltrada.get().getEmail() != null) {
				serviceSendEmail.enviarEmailHtml("Produto sem estoque", html.toString(),
						empresaFiltrada.get().getEmail());
			}
		}

		return produto;
	}

	@Transactional
	public void excluirProduto(Long codigoProduto) throws ExceptionMentoria {
		Optional<Produto> produtoFiltrado = produtoRepository.findById(codigoProduto);

		try {

			if (produtoFiltrado.isEmpty()) {
				throw new ExceptionMentoria("Não existe produto cadastrado com o código " + codigoProduto);
			}

			produtoRepository.deleteById(codigoProduto);
			produtoRepository.flush();

		} catch (DataIntegrityViolationException e) {
			throw new ExceptionMentoria("Não é possivel excluir um produto vinculado. Verifique");
		}
	}

	public Produto buscarPorCodigo(Long codigoProduto) throws ExceptionMentoria {
		return produtoRepository.findById(codigoProduto)
				.orElseThrow(() -> new ExceptionMentoria("Nao existe produto com o código " + codigoProduto));
	}

}
