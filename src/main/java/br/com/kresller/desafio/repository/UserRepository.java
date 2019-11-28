package br.com.kresller.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.kresller.desafio.entity.User;

/**
 * Interface responsavel por persistir os dados da entidade User
 */
public interface UserRepository extends JpaRepository<User, Integer>{
	
	/**
	 * Tenta consultar o usuario e retorna se encontrado
	 * @param email do usuario
	 * @return Usuario
	 */
	@Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    public User findByEmail(@Param("email") String email);
	
	/**
	 * Tenta consultar o usuario e retorna se encontrado
	 * @param login do usuario
	 * @return Usuario
	 */
	@Query("SELECT u FROM User u WHERE LOWER(u.login) = LOWER(:login)")
    public User findByLogin(@Param("login") String login);
	
}
