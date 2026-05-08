package servlets;


import java.io.IOException;

import dao.ProdutoDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

@WebServlet("/ExcluirProdutoServlet")
public class ExcluirProdutoServlet extends HttpServlet {
    
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
            return;
        }
        
        try {
            Long id = Long.parseLong(idStr);
            produtoDAO.excluir(id, usuario);
            resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
        }
    }
}