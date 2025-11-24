package com.jthread.todolist.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jthread.todolist.entity.Admin;
import com.jthread.todolist.repository.AdminRepository;

@Service
public class AdminService implements UserDetailsService {
	
	@Autowired
	AdminRepository adminRepository;
	
	  @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Admin admin = adminRepository.findByName(username);
	        System.out.println("Admin in service: "+admin);
	        if (admin != null) {
	            var springUser = User.withUsername(admin.getName())
	                    .password(admin.getPassword())
	                    .roles(admin.getRole())
	                    .build();
	            return springUser;
	        }
	        return null;
	    }

}
