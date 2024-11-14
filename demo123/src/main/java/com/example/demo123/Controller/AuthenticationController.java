package com.example.demo123.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo123.Config.JwtUtil;
import com.example.demo123.Model.LoginResponse;
import com.example.demo123.Model.User;
import com.example.demo123.Model.UserLoginRequest;
import com.example.demo123.Repository.UserRepository;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest loginRequest) {
        logger.info("Received login request with username: {}", loginRequest.getUsername());
        try {
            // Authenticate the user
            @SuppressWarnings("unused")
			Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            // If authentication is successful, generate JWT token
            String token = jwtUtil.generateToken(loginRequest.getUsername());
            User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

            // Log the user's roles
            logger.info("Roles for user {}: {}", loginRequest.getUsername(), user.getRoles());

            // Check if the user has an admin role
            boolean isAdmin = user.getRoles().stream()
                                   .anyMatch(role -> role.getName().equals("ADMIN"));
            logger.info("Is the user {} an admin? {}", loginRequest.getUsername(), isAdmin);

            // Return the response with the token and isAdmin flag
            return ResponseEntity.ok(new LoginResponse(token, isAdmin));

        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials for username: {}", loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            logger.error("An error occurred during login: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
