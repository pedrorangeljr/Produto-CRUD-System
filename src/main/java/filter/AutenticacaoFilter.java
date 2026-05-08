package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AutenticacaoFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	private static final String[] PUBLIC_PATHS = { "/index.jsp", "/cadastro.jsp", "/LoginServlet",
			"/CadastroUsuarioServlet", "/css/" };

	public AutenticacaoFilter() {
		super();

	}

	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		String path = req.getRequestURI().substring(req.getContextPath().length());

		boolean isPublicPath = false;

		for (String publicPach : PUBLIC_PATHS) {

			if (path.startsWith(publicPach)) {

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

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
