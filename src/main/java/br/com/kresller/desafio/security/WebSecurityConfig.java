package br.com.kresller.desafio.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.kresller.desafio.exception.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Bean
    public JWTLoginFilter jWTLoginFilter() throws Exception {
        return new JWTLoginFilter("/api/signin", authenticationManager());
    }
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/user").permitAll()
			.antMatchers("/api/users").permitAll()
			.antMatchers("/api/users/*").permitAll()
			.antMatchers("/api/signin").permitAll()
			.anyRequest().authenticated()
			.and()
			// filtra requisições de login
			.addFilterBefore(jWTLoginFilter() ,	                UsernamePasswordAuthenticationFilter.class)
			// filtra outras requisições para verificar a presença do JWT no header
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			
			.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
			
	}
	
	
	@Bean
	RestAuthenticationEntryPoint authenticationEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		
		// cria uma conta default
		auth.inMemoryAuthentication()
			.withUser("admin")
			.password("password")
			.roles("ADMIN");
		
		
		*/
		
		auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
        .usersByUsernameQuery(
            "select login, password, true from user where login=?")
        .authoritiesByUsernameQuery("select FIRST_NAME, 'ADMIN' as authority from user where login=?");
        //.passwordEncoder(new BCryptPasswordEncoder());
		
	}
}