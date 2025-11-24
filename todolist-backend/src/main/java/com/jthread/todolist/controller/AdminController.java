package com.jthread.todolist.controller;

import java.time.Instant;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jthread.todolist.dto.LoginDto;
import com.jthread.todolist.dto.RegisterDto;
import com.jthread.todolist.entity.Admin;
import com.jthread.todolist.repository.AdminRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class AdminController {
	
	 @Autowired
	    private AdminRepository adminRepository;
	    
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    
	    @Value("${security.jwt.secret-key}")
	    private String jwtSecretKey;
	    
	    @Value("${security.jwt.issuer}")
	    private String jwtIssuer;
	    
	
	
	private String createJwtToken(Admin admin) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(24 * 3600))
                .subject(admin.getName())
                .claim("role", admin.getRole().toUpperCase())
                .build();
        var encoder = new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(),claims);
        System.out.println("name"+ admin.getName());
        return encoder.encode(params).getTokenValue();
    }
	
	@PostMapping("/register")
    public String register(
            @RequestBody RegisterDto registerDto){
        var bCryptEncoder = new BCryptPasswordEncoder();
        Admin admin = new Admin();    
        admin.setName(registerDto.getUsername());           
        admin.setRole(registerDto.getRole());
        admin.setPassword(bCryptEncoder.encode(registerDto.getPassword()));
        
        adminRepository.save(admin);
        
        return "Successfully Registered";
    }
    
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginDto loginDto){
        var response = new HashMap<String, Object>();
        
        try {   
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (loginDto.getUsername(), loginDto.getPassword())
                );
        Admin admin = adminRepository.findByName(loginDto.getUsername());
        System.out.println("Admin: "+admin);
        String jwtToken = createJwtToken(admin);
        System.out.println("token "+ jwtToken);
        response.put("token", jwtToken);
        response.put("user", admin);
        
        
    }catch (Exception e) {
        System.out.println("::::::::: "+e.getMessage());
    }
        return ResponseEntity.ok(response);
        
    }

}
