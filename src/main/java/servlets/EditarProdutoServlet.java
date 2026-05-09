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

@WebServlet("/EditarProdutoServlet")
public class EditarProdutoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ProdutoDAO produtoDAO = new ProdutoDAO();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		String idStr = req.getParameter("id");
		if (idStr == null || idStr.trim().isEmpty()) {
			resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
			return;
		}

		try {
			Long id = Long.parseLong(idStr);
			Produto produto = produtoDAO.buscarPorId(id, usuario);

			if (produto == null) {
				req.setAttribute("erro", "Produto não encontrado");
				req.getRequestDispatcher("/listar-produtos.jsp").forward(req, resp);
				return;
			}

			// Envia o produto para o formulário
			req.setAttribute("produto", produto);
			req.getRequestDispatcher("/form-produto.jsp").forward(req, resp);

		} catch (NumberFormatException e) {
			resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession(false);
		Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

		try {
			// 1. Pegar os parâmetros
			Long id = Long.parseLong(req.getParameter("id"));
			String nome = req.getParameter("nome");
			String descricao = req.getParameter("descricao");
			BigDecimal preco = new BigDecimal(req.getParameter("preco"));
			Integer quantidadeEstoque = Integer.parseInt(req.getParameter("quantidadeEstoque"));

			// 2. BUSCAR o produto existente (IMPORTANTE!)
			Produto produto = produtoDAO.buscarPorId(id, usuario);

			if (produto == null) {
				req.setAttribute("erro", "Produto não encontrado para atualização");
				req.getRequestDispatcher("/listar-produtos.jsp").forward(req, resp);
				return;
			}

			// 3. Atualizar os dados
			produto.setNome(nome);
			produto.setDescricao(descricao);
			produto.setPreco(preco);
			produto.setQuantidadeEstoque(quantidadeEstoque);

			// 4. Salvar (USANDO UPDATE, não insert)
			produtoDAO.atualizar(produto);

			// 5. Redirecionar para lista
			resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");

		} catch (NumberFormatException e) {
			req.setAttribute("erro", "ID do produto inválido");
			req.getRequestDispatcher("/listar-produtos.jsp").forward(req, resp);
		} catch (Exception e) {
			req.setAttribute("erro", "Erro ao atualizar produto: " + e.getMessage());
			req.getRequestDispatcher("/form-produto.jsp").forward(req, resp);
		}
	}
}
