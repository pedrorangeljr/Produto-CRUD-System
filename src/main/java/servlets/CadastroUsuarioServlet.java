package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import model.Usuario;

@WebServlet("/CadastroUsuarioServlet")
public class CadastroUsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final UsuarioDAO usuarioDAO = new UsuarioDAO();

	public CadastroUsuarioServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String confirmacaoSenha = request.getParameter("confirmacaoSenha");

		// Validações
		if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty() || senha == null
				|| senha.trim().isEmpty()) {
			request.setAttribute("erro", "Todos os campos são obrigatórios");
			request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
			return;
		}

		if (!senha.equals(confirmacaoSenha)) {
			request.setAttribute("erro", "Senhas não coincidem");
			request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
			return;
		}

		if (senha.length() < 6) {
			request.setAttribute("erro", "Senha deve ter no mínimo 6 caracteres");
			request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
			return;
		}

		// Verifica se email já existe
		Usuario existente = usuarioDAO.buscarPorEmail(email);
		if (existente != null) {
			request.setAttribute("erro", "Email já cadastrado");
			request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
			return;
		}

		Usuario novoUsuario = new Usuario();
		novoUsuario.setNome(nome);
		novoUsuario.setEmail(email);
		novoUsuario.setSenhaHash(UsuarioDAO.hashSenha(senha));
		novoUsuario.setAtivo(true);

		usuarioDAO.salvar(novoUsuario);

		request.setAttribute("sucesso", "Cadastro realizado com sucesso! Faça login.");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}
}
