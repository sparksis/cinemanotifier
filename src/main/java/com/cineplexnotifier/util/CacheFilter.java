package com.cineplexnotifier.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
@WebFilter(urlPatterns="/*")
public class CacheFilter implements Filter,ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) throws IOException {
		response.getHeaders().add("Cache-Control", "max-age=600");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = ((HttpServletResponse)response);
				
		filterChain.doFilter(request, response);
		httpResponse.setHeader("Cache-Control", "max-age=600");
	}

	@Override
	public void destroy() {	}

	@Override
	public void init(FilterConfig arg0) throws ServletException { }

}