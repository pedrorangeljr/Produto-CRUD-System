package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AutenticacaoFilter implements Filter {
    
    private static final String[] PUBLIC_PATHS = {"/index.jsp", "/cadastro.jsp", "/LoginServlet", "/CadastroUsuarioServlet", "/css/"};
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        
        String path = req.getRequestURI().substring(req.getContextPath().length());
        
        boolean isPublicPath = false;
        for (String publicPath : PUBLIC_PATHS) {
            if (path.startsWith(publicPath)) {
                isPublicPath = true;
                break;
            }
        }
        
        if (isPublicPath || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }
        
        if (session != null && session.getAttribute("usuarioLogado") != null) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }
}