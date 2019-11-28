package br.com.kresller.desafio.exception;

/**
 * Classe responsavel por customizar a mensagem de erro de retorno
 */
public class CustomErrorResponse {

	private int errorCode;
	private String message;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
