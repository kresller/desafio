package br.com.kresller.desafio.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kresller.desafio.exception.CustomErrorResponse;
import br.com.kresller.desafio.util.Constants;

/**
 * Classe responsavel por filtrar os requests na aplicacao
 */
@EnableAutoConfiguration
public class JWTAuthenticationFilter extends GenericFilterBean {

	/**
	 * Filtra a requisicao e valida se o Token foi passado e/ou é valido
	 */
	@Override
	public void doFilter(ServletRequest httpServletRequest, ServletResponse httpServletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		try {
			Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) httpServletRequest);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		} catch (io.jsonwebtoken.ExpiredJwtException ex) {
			CustomErrorResponse response = new CustomErrorResponse();
			response.setMessage(Constants.TOKEN_INVALID_SESSION);
			response.setErrorCode(HttpStatus.UNAUTHORIZED.value());
			
			((HttpServletResponse)httpServletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
			
			httpServletResponse.setContentType(Constants.JSON_CONTENT_TYPE);
			OutputStream out = httpServletResponse.getOutputStream();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(out, response);
			
			out.flush();
		}

	}

}
