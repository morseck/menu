package com.example.genietech.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * on commente la méthode du super constructeur super.configure(http) pour désactiver cette 
		 * authentification par default
		 * */
		//super.configure(http) 
		
		http.csrf().disable();//pour desactiver le csrf
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//on indique à spring security de ne plus utiliser les sessions
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/categories/**").permitAll();//permettre à tout le monde de voire la liste des categories
		http.authorizeRequests().antMatchers(HttpMethod.GET,"/products/**").permitAll();//permettre à tout le monde de voire la liste des products
		http.authorizeRequests().antMatchers("/categories/**").hasAuthority("ADMIN");//autoriser à être admin pour modifier les categories
		http.authorizeRequests().antMatchers("/products/**").hasAuthority("USER");
		http.authorizeRequests().anyRequest().authenticated();//pour que toutes les requêtes nécessitent une authentification
		//le premier filter qui sera appeler avant tout autre
		http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	
	
}
