package com.example.demo123.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo123.Model.Role;
import com.example.demo123.Model.User;
import com.example.demo123.Repository.RoleRepository;
import com.example.demo123.Repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	public void init() {
		// Create and save roles
		Role userRole = new Role();
		userRole.setName("USER");
		roleRepository.save(userRole);

		Role adminRole = new Role();
		adminRole.setName("ADMIN");
		roleRepository.save(adminRole);

		// Create and save users with roles
		User user = new User();
		user.setUsername("user");
		user.setPassword("$2a$12$OBZ3/R3ytqUVApQPCfqyz.d8xj16ztmke1HVH6g5XelLjC.tBdtrm"); // example hashed password
		user.getRoles().add(userRole); // Assign role to user
		userRepository.save(user);

		User admin = new User();
		admin.setUsername("admin");
		admin.setPassword("$2a$12$f57sh5hPAK1UemTe9jrmIuYkb6HLPGzcDtK0v20zuwsjfMfsoCJwW"); // example hashed password
		admin.getRoles().add(adminRole); // Assign role to admin
		userRepository.save(admin);
	}
}
