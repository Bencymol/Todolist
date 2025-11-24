package com.jthread.todolist.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jthread.todolist.service.AdminService;

@Configuration
public class SecurityConfig implements WebMvcConfigurer{
	
	@Value("${security.jwt.secret-key}")
    private String jwtSecretKey;
    
    @Value("${security.jwt.issuer}")
    private String jwtIssuer;
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf->csrf.disable())
                .authorizeHttpRequests(
                        auth->auth.requestMatchers("/","/login","/register")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                        .oauth2ResourceServer(oath2->oath2.jwt(
                                jwt ->jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                        .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .build();
        }
    
    @Bean
    public AuthenticationManager authenticationManager(AdminService adminService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(provider);
    }
	
	@Bean   
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("role"); // The claim name containing the roles 
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // No prefix

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
	
	@Bean
    public JwtDecoder jwtDecoder() {
        var secretKey = new SecretKeySpec(jwtSecretKey.getBytes(), "");
        return NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256).build();
    }
	
	 @Override
	  public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	      .allowedOrigins("http://localhost:5173")
	      .allowedMethods("GET","POST","PUT","PATCH","DELETE","OPTIONS")
	      .allowedHeaders("Authorization", "Content-Type")
	      .allowCredentials(true);
	  }

}
