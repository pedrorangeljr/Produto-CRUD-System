package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UsuarioDAO;
import model.Usuario;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
	private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            req.setAttribute("erro", "Email e senha são obrigatórios");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        
        boolean autenticado = usuarioDAO.autenticar(email, senha);
        
        if (autenticado) {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);
            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            session.setMaxInactiveInterval(30 * 60);
            resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
        } else {
            req.setAttribute("erro", "Email ou senha inválidos");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}