package servlets;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProdutoDAO;
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		try {
			// Pegar parâmetros
			String nome = req.getParameter("nome");
			String descricao = req.getParameter("descricao");
			BigDecimal preco = new BigDecimal(req.getParameter("preco"));
			Integer quantidadeEstoque = Integer.parseInt(req.getParameter("quantidadeEstoque"));

			// Criar NOVO produto (sem ID)
			Produto produto = new Produto();
			produto.setNome(nome);
			produto.setDescricao(descricao);
			produto.setPreco(preco);
			produto.setQuantidadeEstoque(quantidadeEstoque);
			produto.setUsuario(usuario);

			// SALVAR (INSERT)
			produtoDAO.salvar(produto);

			System.out.println("✅ Produto criado com sucesso: " + produto.getNome());

			// Redirecionar para lista
			resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");

		} catch (Exception e) {
			System.err.println("❌ Erro ao criar produto: " + e.getMessage());
			e.printStackTrace();
			req.setAttribute("erro", "Erro ao criar produto: " + e.getMessage());
			req.getRequestDispatcher("/form-produto.jsp").forward(req, resp);
		}
	}

}
