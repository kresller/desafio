package br.com.kresller.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.kresller.desafio.entity.Car;

/**
 * Interface responsavel por persistir os dados da entidade Car
 */
public interface CarRepository extends JpaRepository<Car, Integer>{
	
	/**
	 * Tenta consultar o carro e retorna se encontrado
	 * @param placa do carro (licensePlate)
	 * @return Carro
	 */
	@Query("SELECT c FROM Car c WHERE LOWER(c.licensePlate) = LOWER(:licensePlate)")
    public Car findByLicensePlate(@Param("licensePlate") String licensePlate);

}
