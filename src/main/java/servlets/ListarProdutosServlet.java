package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ProdutoDAO;
import jakarta.servlet.http.HttpSession;
import model.Produto;
import model.Usuario;

@WebServlet("/ListarProdutosServlet")
public class ListarProdutosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final ProdutoDAO produtoDAO = new ProdutoDAO();

	public ListarProdutosServlet() {
		super();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        HttpSession session = (HttpSession) request.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        
        List<Produto> produtos = produtoDAO.listarPorUsuario(usuario);
        request.setAttribute("produtos", produtos);
        request.getRequestDispatcher("/listar-produtos.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
