package br.com.kresller.desafio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.kresller.desafio.entity.Car;
import br.com.kresller.desafio.entity.User;
import br.com.kresller.desafio.exception.ChallengeException;
import br.com.kresller.desafio.repository.CarRepository;
import br.com.kresller.desafio.repository.UserRepository;
import br.com.kresller.desafio.validator.ValidadorUtil;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	CarRepository repositoryCar;
	
	public UserService() {
		super();
	}

	public List<User> getAllUsers() {
		return repository.findAll();
	}

	public User save(User user){
		User retorno = null;
		ValidadorUtil.getInstance().validate(user);
		
		retorno = repository.save(user);
		
		if (user.getCars() != null) {
			for (Car c : user.getCars()) {
				c.setUserId(retorno.getId());
				repositoryCar.save(c);
			}
		}
		
		return retorno;
	}
	
	public User update(User user){
		User retorno = null;
		ValidadorUtil.getInstance().validate(user);
		retorno = repository.save(user);
		return retorno;
	}

	public User findOne(Integer id) {
		return repository.findOne(id);
	}

	public void delete(Integer id) {
		User user = findOne(id);
		
		if (user.getCars() != null) {
			for (Car c : user.getCars()) {
				repositoryCar.delete(c);
			}
		}
		
		
		repository.delete(user);
	}
	
	public User findByLogin(String login){
		return repository.findByLogin(login);
	}
	
	public void updateUserVisit(String login){
		User usr = repository.findByLogin(login);
		usr.setLastLogin(new Date(System.currentTimeMillis()));
		repository.save(usr);
	}
	
}
