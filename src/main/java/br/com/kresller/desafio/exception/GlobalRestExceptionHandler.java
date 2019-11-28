package br.com.kresller.desafio.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ChallengeException.class)
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(ChallengeException ex, WebRequest request) {

        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setMessage(ex.getMessage());
        errors.setErrorCode(ex.getHttpStatus().value());

        return new ResponseEntity<CustomErrorResponse>(errors, ex.getHttpStatus());

    }
	
}
