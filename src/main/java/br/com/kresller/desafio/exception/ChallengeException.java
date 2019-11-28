package br.com.kresller.desafio.exception;

import org.springframework.http.HttpStatus;

/**
 * Classe de Erro, responsavel por manter a mensagem e o httpStatus
 */
public class ChallengeException extends RuntimeException {
	
	private HttpStatus httpStatus;

	private static final long serialVersionUID = 1L;

	public ChallengeException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.httpStatus = httpStatus;
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	
	
}
