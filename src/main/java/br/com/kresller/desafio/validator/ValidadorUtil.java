package br.com.kresller.desafio.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.http.HttpStatus;

import br.com.kresller.desafio.exception.ChallengeException;

public class ValidadorUtil {

	private ValidatorFactory factory;
    private Validator validator;

    private static ValidadorUtil instance;
    
    private ValidadorUtil(){
    	factory = Validation.buildDefaultValidatorFactory();
    	validator = factory.getValidator();
    }
    
    public static ValidadorUtil getInstance(){
    	if ( instance == null ){
    		instance = new ValidadorUtil();
    	}
    	return instance;
    }
    /*
	public List<String> validate(Object object) {

		List<String> errorMessage = new LinkedList<String>();

		// Validate bean
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

		// Show errors
		for (ConstraintViolation<Object> violation : constraintViolations) {
			errorMessage.add(violation.getMessage());
		}

		return errorMessage;
	}
	*/
	public void validate(Object object){

		// Validate bean
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);

		// Show errors
		for (ConstraintViolation<Object> violation : constraintViolations) {
			throw new ChallengeException(violation.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
    
}
