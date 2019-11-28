package br.com.kresller.desafio;

import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import br.com.kresller.desafio.entity.Car;
import br.com.kresller.desafio.security.AccountCredentials;
import br.com.kresller.desafio.util.Constants;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarTest {

	static Car newCar = new Car();
	static String token;

	static {
		newCar.setYear(2018);
		newCar.setLicensePlate("PDV-0625");
		newCar.setModel("Audi");
		newCar.setColor("White");
	}

	/**
	 * Cria um novo usuário e valida se ele foi persistido no banco de dados com
	 * sucesso
	 */
	@Test
	public void test01Me() throws Exception {
		AccountCredentials credentials = new AccountCredentials();
		credentials.setUsername("kss");
		credentials.setPassword("123456");

		ResponseEntity<String> response = DefaultTest.getInstance().postForEntity("signin", credentials);

		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

		token = response.getHeaders().get(Constants.HEADER_AUTHORIZATION).get(0);

		DefaultTest.getInstance().addToken(token);

		response = DefaultTest.getInstance().exchange("me");

		JSONObject jsonUser = new JSONObject(response.getBody());

		newCar.setUserId(jsonUser.getInt("id"));

		Assert.assertEquals(credentials.getUsername(), jsonUser.getString("login"));
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	/**
	 * Valida o tamanho da lista de usuário cadastrados no banco de dados.
	 */
	@Test
	public void test02SizeListCars() throws Exception {

		ResponseEntity<String> response = DefaultTest.getInstance().exchange("cars");
				
		org.json.JSONArray jo = new org.json.JSONArray(response.getBody());
		Assert.assertEquals(2, jo.length());

	}
	
	@Test
	public void test03NewCar() throws Exception {
		ResponseEntity<String> response = DefaultTest.getInstance().postForEntity("cars", newCar);

		JSONObject jsonCar = new JSONObject(response.getBody());
		Assert.assertEquals(newCar.getYear(), jsonCar.getInt("year"));
		Assert.assertEquals(newCar.getLicensePlate(), jsonCar.getString("licensePlate"));
		Assert.assertEquals(newCar.getModel(), jsonCar.getString("model"));
		Assert.assertEquals(newCar.getColor(), jsonCar.getString("color"));

		newCar.setId(jsonCar.getInt("id"));

		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	/**
	 * Valida o tamanho da lista de usuário cadastrados no banco de dados.
	 */
	@Test
	public void test04SizeListCars() throws Exception {

		ResponseEntity<String> response = DefaultTest.getInstance().exchange("cars");
				
		org.json.JSONArray jo = new org.json.JSONArray(response.getBody());
		Assert.assertEquals(3, jo.length());

	}
	
	@Test
	public void test05NewCarSameLicensePlate() throws Exception {
		DefaultTest.getInstance().addToken(token);
		try {
			DefaultTest.getInstance().postForEntity("cars", newCar);
		} catch (HttpClientErrorException ex) {
			JSONObject json = new JSONObject(ex.getResponseBodyAsString());
			Assert.assertEquals(Constants.ERROR_LICENSE_PLATE_EXISTS, json.getString("message"));
			Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), json.getInt("errorCode"));
		}
	}

	@Test
	public void test06NewCarWithoutLicensePlate() throws Exception {
		DefaultTest.getInstance().addToken(token);
		newCar.setLicensePlate(null);
		try {
			DefaultTest.getInstance().postForEntity("cars", newCar);
		} catch (HttpClientErrorException ex) {
			JSONObject json = new JSONObject(ex.getResponseBodyAsString());
			Assert.assertEquals(Constants.ERROR_MISSING_FIELDS, json.getString("message"));
			Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), json.getInt("errorCode"));
		}
	}

}
