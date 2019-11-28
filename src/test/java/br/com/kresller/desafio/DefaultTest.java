package br.com.kresller.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
@SuppressWarnings({"unchecked","rawtypes"})
public class DefaultTest {

	protected String INITIAL_URL = "http://localhost:8080/api/";

	protected static RestTemplate restTemplate;

	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public static void setRestTemplate(RestTemplate restTemplate) {
		DefaultTest.restTemplate = restTemplate;
	}

	protected static HttpHeaders headers;

	protected static HttpEntity entity;

	private static DefaultTest instance;
	private DefaultTest() {
		SpringApplication.run(Application.class, new String[] {});
		
		restTemplate = new RestTemplate();
		
		headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		entity = new HttpEntity(headers);
	}
	
	public static DefaultTest getInstance(){
		if (instance == null){
			instance = new DefaultTest();
		}
		return instance;
	}
	
	public ResponseEntity<String> exchange(String url){
		entity = new HttpEntity(headers);
		return restTemplate.exchange(INITIAL_URL + url, HttpMethod.GET, entity, String.class);
	}
	
	
	public ResponseEntity<String> postForEntity(String url, Object request){
		entity = new HttpEntity(request, headers);
		return restTemplate.postForEntity(INITIAL_URL + url, entity, String.class);
	}
	
	public ResponseEntity<String> putForEntity(String url, Object request){
		entity = new HttpEntity(request, headers);
		return restTemplate.exchange(INITIAL_URL + url, HttpMethod.PUT, entity, String.class);
	}

	public ResponseEntity<String> delete(String url, Object request){
		entity = new HttpEntity(request, headers);
		return restTemplate.exchange(INITIAL_URL + url, HttpMethod.DELETE, entity, String.class);
	}
	
	public ResponseEntity<String> delete(String url){
		return restTemplate.exchange(INITIAL_URL + url, HttpMethod.DELETE, entity, String.class);
	}
	
	public void addToken(String token){
		headers.add("authorization", token);
	}
	
	public void removeToken(){
		headers.remove("authorization");
	}
	
}
