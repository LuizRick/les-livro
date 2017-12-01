package web.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AutorizadorInterceptor implements HandlerInterceptor {
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		String uri =  request.getRequestURI();
		if(uri.indexOf("/public/") >= 0
				|| uri.indexOf("login.xhtml") >= 0
				|| uri.indexOf("frmCadastrar.jsp") >= 0
				|| uri.indexOf("loginCliente") >= 0
				|| uri.contains("javax.faces.resource"))
			return true;
		if(request.getSession()
				.getAttribute("username") != null)
			return true;
		
		if(request.getSession().getAttribute("cliente") != null)
			return true;
		
		response.sendRedirect("/les-web/login.xhtml");
		return false;
	}
}
