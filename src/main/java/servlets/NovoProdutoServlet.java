package servlets;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import jakarta.servlet.http.HttpSession;
import model.Produto;
import model.Usuario;

@WebServlet("/NovoProdutoServlet")
public class NovoProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final ProdutoDAO produtoDAO = new ProdutoDAO();

	public NovoProdutoServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = (HttpSession) request.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		String nome = request.getParameter("nome");
		String descricao = request.getParameter("descricao");
		String precoStr = request.getParameter("preco");
		String quantidadeStr = request.getParameter("quantidadeEstoque");

		Produto produto = new Produto();
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setPreco(new BigDecimal(precoStr));
		produto.setQuantidadeEstoque(Integer.parseInt(quantidadeStr));
		produto.setUsuario(usuario);

		produtoDAO.salvar(produto);
		response.sendRedirect(request.getContextPath() + "/ListarProdutosServlet");
	}

}
