package br.com.kresller.desafio;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.kresller.desafio.entity.User;
import br.com.kresller.desafio.security.AccountCredentials;
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
	public void test1Login() throws Exception {
		AccountCredentials credentials = new AccountCredentials();
		credentials.setUsername("kss");
		credentials.setPassword("123456");
		ResponseEntity<String> response = DefaultTest.getInstance().postForEntity("signin", credentials);
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void test2InvalidLogin() throws Exception {
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
	public void test3SizeListUsers() throws Exception {

		ResponseEntity<String> response = DefaultTest.getInstance().exchange("users");
				
		org.json.JSONArray jo = new org.json.JSONArray(response.getBody());
		Assert.assertEquals(2, jo.length());

	}

}
