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
            Produto produto = produtoDAO.buscarPorId(id, usuario);
            
            if (produto == null) {
                req.setAttribute("erro", "Produto não encontrado");
                req.getRequestDispatcher("/listar-produtos.jsp").forward(req, resp);
                return;
            }
            
            req.setAttribute("produto", produto);
            req.getRequestDispatcher("/form-produto.jsp").forward(req, resp);
            
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        HttpSession session = req.getSession(false);
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String nome = req.getParameter("nome");
            String descricao = req.getParameter("descricao");
            BigDecimal preco = new BigDecimal(req.getParameter("preco"));
            Integer quantidadeEstoque = Integer.parseInt(req.getParameter("quantidadeEstoque"));
            
            Produto produto = produtoDAO.buscarPorId(id, usuario);
            
            if (produto == null) {
                req.setAttribute("erro", "Produto não encontrado");
                req.getRequestDispatcher("/listar-produtos.jsp").forward(req, resp);
                return;
            }
            
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            produto.setQuantidadeEstoque(quantidadeEstoque);
            
            produtoDAO.salvar(produto);
            
            resp.sendRedirect(req.getContextPath() + "/ListarProdutosServlet");
            
        } catch (Exception e) {
            req.setAttribute("erro", "Erro ao atualizar produto: " + e.getMessage());
            req.getRequestDispatcher("/form-produto.jsp").forward(req, resp);
        }
    }
}