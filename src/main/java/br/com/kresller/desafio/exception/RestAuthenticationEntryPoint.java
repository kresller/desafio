package br.com.kresller.desafio.exception;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.kresller.desafio.util.Constants;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		CustomErrorResponse response = new CustomErrorResponse();
		response.setMessage(Constants.TOKEN_NOT_SEND);
		response.setErrorCode(HttpStatus.UNAUTHORIZED.value());
		
		httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		httpServletResponse.setContentType("application/json");
		OutputStream out = httpServletResponse.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(out, response);
		
		
		
		out.flush();
	}
	
	
}