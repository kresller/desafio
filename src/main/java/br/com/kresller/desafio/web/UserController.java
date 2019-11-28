package br.com.kresller.desafio.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.kresller.desafio.entity.User;
import br.com.kresller.desafio.service.UserService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService service;
	
	@RequestMapping(value = "/users"
			, method = RequestMethod.GET
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<List<User>> listar() {
		return new ResponseEntity<List<User>>(service.getAllCars(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/users"
			, method = RequestMethod.POST
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return new ResponseEntity<User>(service.save(user), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/users/{id}"
			, method = RequestMethod.GET
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> createUser(@PathVariable("id") Integer id) {
		return new ResponseEntity<User>(service.findOne(id), HttpStatus.OK);
	}
}
