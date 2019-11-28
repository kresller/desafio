package br.com.kresller.desafio.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.kresller.desafio.entity.Car;
import br.com.kresller.desafio.entity.User;
import br.com.kresller.desafio.service.CarService;
import br.com.kresller.desafio.service.UserService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api")
public class CarController {

	@Autowired
	CarService service;
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/cars", method = RequestMethod.GET
	 , consumes = MediaType.APPLICATION_JSON_VALUE
	 , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<List<Car>> listar(Authentication autentication) {
		return new ResponseEntity<List<Car>>(userService.findByLogin(autentication.getPrincipal().toString()).getCars(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cars"
			, method = RequestMethod.POST
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Car> cadastrarCarro(Authentication autentication, @RequestBody Car car) {
		
		User u = userService.findByLogin(autentication.getPrincipal().toString());
		car.setUserId(u.getId());
		
		return new ResponseEntity<Car>(service.save(car), HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/cars/{id}", method = RequestMethod.DELETE
	 , consumes = MediaType.APPLICATION_JSON_VALUE
	 , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "/cars/{id}", method = RequestMethod.GET
	 , consumes = MediaType.APPLICATION_JSON_VALUE
	 , produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Car> buscar(@PathVariable("id") Integer id) {
		return new ResponseEntity<Car>(service.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/cars/{id}"
			, method = RequestMethod.PUT
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable("id") Integer id) {
		car.setId(id);
		return new ResponseEntity<Car>(service.update(car), HttpStatus.OK);
	}
	
}
