package br.com.kresller.desafio.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kresller.desafio.exception.CustomErrorResponse;
import br.com.kresller.desafio.service.UserService;
import br.com.kresller.desafio.util.Constants;
/**
 * Classe responsavel por filtrar o login na aplicacao
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
	@Autowired
	UserService userService;
	
	protected JWTLoginFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	/**
	 * Filtra a requisicao e efetua o login do usuario, validando suas informacoes na base de dados
	 * @return um Authentication com o token valido
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		AccountCredentials credentials = new ObjectMapper().readValue(request.getInputStream(),
				AccountCredentials.class);

		Authentication authentication = null;

		authentication = getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
				credentials.getUsername(), credentials.getPassword(), Collections.<GrantedAuthority>emptyList()));
				
		userService.updateUserVisit(credentials.getUsername());
		
		return authentication;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication auth) throws IOException, ServletException {
		TokenAuthenticationService.addAuthentication(response, auth.getName());
	}
	
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse httpServletResponse, AuthenticationException failed)
			throws IOException, ServletException{
		
		CustomErrorResponse customResponse = new CustomErrorResponse();
		customResponse.setMessage(Constants.ERROR_INVALID_LOGIN);
		customResponse.setErrorCode(HttpStatus.UNAUTHORIZED.value());
		
		httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		httpServletResponse.setContentType(Constants.JSON_CONTENT_TYPE);
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, customResponse);
		
		out.flush();
	}

}