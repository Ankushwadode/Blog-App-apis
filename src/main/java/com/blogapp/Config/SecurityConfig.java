package com.blogapp.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogapp.Security.CustomUserDetailsService;
import com.blogapp.Security.JwtAuthEntryPoint;
import com.blogapp.Security.JwtAuthFilter;

@Configuration
@EnableWebSecurity 
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
public class SecurityConfig{
	
	@Autowired
	private JwtAuthFilter authFilter;
	
	@Autowired
	private JwtAuthEntryPoint authEntryPoint;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	public static final String[] PUBLIC_URLS = {
			"/api/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable()).cors(C -> C.disable())
        	.formLogin(f ->f.disable())  
                .authorizeHttpRequests(
                        r -> {
                            try {
                                r.antMatchers(PUBLIC_URLS).permitAll() 
                                 .antMatchers("/api/**").authenticated() 
                                 .antMatchers(HttpMethod.GET).permitAll()
                                        .and()
                                        .exceptionHandling(e -> {
                                            try {
                                                e.authenticationEntryPoint(authEntryPoint)
                                                .and()
                                                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                                            } catch (Exception e1) { 
                                                e1.printStackTrace();
                                            }
                                        });
                            } catch (Exception e) {
                            	e.printStackTrace();
                            }
                        }
                );	
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
//	@Bean
//	public  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
//		return httpSecurity.csrf(c -> c.disable())
//				.authorizeHttpRequests(ahr -> ahr.antMatchers("/api/auth/login").permitAll()) 
//				.authorizeHttpRequests(ahr -> ahr.antMatchers("/api/**").authenticated()) 
//				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//				.build();
//	}
	
    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
         AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
         
         authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder()); 
         
         return authenticationManagerBuilder.build();
     }

}