package br.com.kresller.desafio;

import java.util.Date;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.kresller.desafio.entity.User;
import br.com.kresller.desafio.security.AccountCredentials;
import br.com.kresller.desafio.util.Constants;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest {
	
	static User newUser = new User();
	
	static{
	newUser.setFirstName("UserTestCase FirstName");
	newUser.setLastName("UserTestCase LastName");
	newUser.setEmail("usertestcase@Testcase.com");
	newUser.setLogin("usertestcase");
	newUser.setPassword("usertestcasepassword");
	newUser.setPhone("(81)09876-5432");
	newUser.setBirthday(new Date("05/01/1984"));
	}

	@Test
	public void test01Login() throws Exception {
		AccountCredentials credentials = new AccountCredentials();
		credentials.setUsername("kss");
		credentials.setPassword("123456");
		ResponseEntity<String> response = DefaultTest.getInstance().postForEntity("signin", credentials);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		
		String token = response.getHeaders().get(Constants.HEADER_AUTHORIZATION).get(0);

		DefaultTest.getInstance().addToken(token);
		
	}
	
	@Test
	public void test02InvalidLogin() throws Exception {
		AccountCredentials credentials = new AccountCredentials();
		credentials.setUsername("kss");
		credentials.setPassword("1234567");
		try {
			DefaultTest.getInstance().postForEntity("signin", credentials);
		} catch (HttpClientErrorException ex) {
			Assert.assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
		}

	}
	
	/**
	 * Valida o tamanho da lista de usuário cadastrados no banco de dados.
	 */
	@Test
	public void test03SizeListUsers() throws Exception {

		ResponseEntity<String> response = DefaultTest.getInstance().exchange("users");
				
		org.json.JSONArray jo = new org.json.JSONArray(response.getBody());
		Assert.assertEquals(2, jo.length());

	}
	
	
	/**
	 * Cria um novo usuário e valida se ele foi persistido no banco de dados com sucesso.
	 */
	@Test
	public void test04CreateNewUser() throws Exception {
		
		
		ResponseEntity<String> result = DefaultTest.getInstance().postForEntity("users", newUser);
		
		JSONObject jsonUser = new JSONObject(result.getBody());
		Assert.assertEquals(newUser.getFirstName(), jsonUser.getString("firstName"));
		Assert.assertEquals(newUser.getLastName(), jsonUser.getString("lastName"));
		Assert.assertEquals(newUser.getEmail(), jsonUser.getString("email"));
		Assert.assertEquals(newUser.getLogin(), jsonUser.getString("login"));
		Assert.assertEquals(newUser.getPassword(), jsonUser.getString("password"));
		Assert.assertEquals(newUser.getPhone(), jsonUser.getString("phone"));
		
		newUser.setId(jsonUser.getInt("id"));

		Assert.assertEquals(HttpStatus.CREATED.value(), result.getStatusCode().value());
	}
	/**
	 * Tenta cadastrar o mesmo usuario apenas com o login diferente
	 * @result Retorna erro de usuario já existe
	 */
	@Test
	public void test05CreateSameEmailUser() throws Exception {
		
		newUser.setLogin("user9999");
		try {
			DefaultTest.getInstance().postForEntity("users", newUser);
		} catch (HttpClientErrorException ex) {
			JSONObject json = new JSONObject(ex.getResponseBodyAsString());
			Assert.assertEquals(Constants.ERROR_EMAIL_ALREADY_EXISTS, json.getString("message"));
			Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), json.getInt("errorCode"));
		}
	}

	/**
	 * Tenta cadastrar o mesmo usuario apenas com o login diferente
	 * @result Retorna erro de email já existe
	 */
	@Test
	public void test06CreateSameLoginUser() throws Exception {
		newUser.setEmail("user9999@");
		try {
			DefaultTest.getInstance().postForEntity("users", newUser);
		} catch (HttpClientErrorException ex) {
			JSONObject json = new JSONObject(ex.getResponseBodyAsString());
			Assert.assertEquals(Constants.ERROR_LOGIN_ALREADY_EXISTS, json.getString("message"));
			Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), json.getInt("errorCode"));
		}
	}
	
	/**
	 * Tenta cadastrar um usuario sem FirstName
	 * @result Usuário não cadastrado por falta de campos
	 */
	@Test
	public void test07CreateMissingFieldUser() throws Exception {
		newUser.setFirstName(null);
		try {
			DefaultTest.getInstance().postForEntity("users", newUser);
		} catch (HttpClientErrorException ex) {
			JSONObject json = new JSONObject(ex.getResponseBodyAsString());
			Assert.assertEquals(Constants.ERROR_MISSING_FIELDS, json.getString("message"));
			Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), json.getInt("errorCode"));
		}
	}
	
	/**
	 * Cria um novo usuário e valida se ele foi persistido no banco de dados com sucesso.
	 */
	@Test
	public void test08FindUserById() throws Exception {
		newUser.setFirstName("UserTestCase FirstName");
		
		ResponseEntity<String> result = DefaultTest.getInstance().exchange("users/"+newUser.getId());
		
		JSONObject jsonUser = new JSONObject(result.getBody());
		Assert.assertEquals(newUser.getFirstName(), jsonUser.getString("firstName"));
		Assert.assertEquals(newUser.getLastName(), jsonUser.getString("lastName"));
		Assert.assertEquals(newUser.getEmail(), jsonUser.getString("email"));
		Assert.assertEquals(newUser.getLogin(), jsonUser.getString("login"));
		Assert.assertEquals(newUser.getPassword(), jsonUser.getString("password"));
		Assert.assertEquals(newUser.getPhone(), jsonUser.getString("phone"));

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void test09DeleteUser() throws Exception {
		ResponseEntity<String> result = DefaultTest.getInstance().delete("users/2", null);
		Assert.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
	}
	
	@Test
	public void test10UpdateUser() throws Exception {
		newUser.setFirstName("FirstName Nome Atualizado");
		newUser.setLastName("LastName Atualizado");

		ResponseEntity<String> result = DefaultTest.getInstance().putForEntity("users/"+newUser.getId(), newUser);
		
		JSONObject json = new JSONObject(result.getBody());
		Assert.assertEquals(newUser.getFirstName(), json.getString("firstName"));
		Assert.assertEquals(newUser.getLastName(), json.getString("lastName"));
	}
	
	@Test
	public void test11MyUser() throws Exception {
		ResponseEntity<String> result = DefaultTest.getInstance().exchange("me"); // Usuario cadastrado no banco e logado
		JSONObject jsonUser = new JSONObject(result.getBody());
		Assert.assertEquals("kresller", jsonUser.getString("firstName"));
		Assert.assertEquals("silva", jsonUser.getString("lastName"));
		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void test11MyUserWithoutToken() throws Exception {
		DefaultTest.getInstance().removeToken();
		try {
			ResponseEntity<String> result = DefaultTest.getInstance().exchange("me"); // Usuario cadastrado no banco e logado
		} catch (HttpClientErrorException ex) {
			JSONObject json = new JSONObject(ex.getResponseBodyAsString());
			Assert.assertEquals(Constants.TOKEN_NOT_SEND, json.getString("message"));
			Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), json.getInt("errorCode"));
		}
	}
	
}
