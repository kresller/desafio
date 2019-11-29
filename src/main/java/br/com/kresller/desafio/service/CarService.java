package br.com.kresller.desafio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.kresller.desafio.entity.Car;
import br.com.kresller.desafio.entity.User;
import br.com.kresller.desafio.exception.ChallengeException;
import br.com.kresller.desafio.repository.CarRepository;
import br.com.kresller.desafio.util.Constants;
import br.com.kresller.desafio.validator.ValidadorUtil;

@Service
public class CarService {
	
	@Autowired
	CarRepository repository;

	public CarService() {
		super();
	}

	public List<Car> getAllCars() {
		return repository.findAll();
	}
	
	public Car findOne(Integer id) {
		updateCarUse(id);
		return repository.findOne(id);
	}
	
	public Car save(Car car){
		Car retorno = null;
		ValidadorUtil.getInstance().validate(car);
		
		if ( repository.findByLicensePlate(car.getLicensePlate()) != null) throw new ChallengeException(Constants.ERROR_LICENSE_PLATE_EXISTS, HttpStatus.BAD_REQUEST);
		
		retorno = repository.save(car);
		return retorno;
	}
	
	public void delete(Integer id) {
		Car car = findOne(id);
		repository.delete(car);
	}
	
	public Car update(Car car){
		Car retorno = null;
		ValidadorUtil.getInstance().validate(car);
		retorno = repository.save(car);
		return retorno;
	}
	
	public void updateCarUse(int id){
		Car car = repository.findOne(id);
		car.setQtdUso(car.getQtdUso()+1);
		repository.save(car);
	}
	
}
