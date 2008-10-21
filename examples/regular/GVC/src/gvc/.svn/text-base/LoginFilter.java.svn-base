package gvc;

import gvc.bean.LoginBean;
import gvc.login.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Root
 *
 */
public class LoginFilter implements Filter {
	private static String redirectPage = "/GVC/pages/login/information.jsf";
	
	public LoginFilter() {}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String relativePath = request.getServletPath() + request.getPathInfo();
		String uri = request.getRequestURI();
		LoginBean loginBean =
			(LoginBean) request.getSession().getAttribute("loginBean");
		
		if(uri.matches(".+/user/.*")) {
			if(loginBean == null || !loginBean.isAuthenticated()) {
				response.sendRedirect(redirectPage);
				return;
			}
		} else if(uri.matches(".+/admin/.*")) {
			if(loginBean == null || !loginBean.isAuthenticated() ||
					!loginBean.getUser().getGroup().equals(User.ADMIN_GROUP)) {
				response.sendRedirect(redirectPage);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

}
