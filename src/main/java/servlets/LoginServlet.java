package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import jakarta.servlet.http.HttpSession;
import model.Usuario;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UsuarioDAO usuarioDAO = new UsuarioDAO();

	public LoginServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String senha = request.getParameter("senha");

		if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
			request.setAttribute("erro", "Email e senha são obrigatórios");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		boolean autenticado = usuarioDAO.autenticar(email, senha);

		if (autenticado) {
			Usuario usuario = usuarioDAO.buscarPorEmail(email);
			HttpSession session = (HttpSession) request.getSession(true);
			session.setAttribute("usuarioLogado", usuario);
			session.setMaxInactiveInterval(30 * 60); // 30 minutos
			response.sendRedirect(request.getContextPath() + "/ListarProdutosServlet");
		} else {
			request.setAttribute("erro", "Email ou senha inválidos");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

}
