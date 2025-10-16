package com.whuuno.inventory.controller;

import com.whuuno.inventory.dto.JwtResponse;
import com.whuuno.inventory.dto.LoginRequest;
import com.whuuno.inventory.dto.RegisterRequest;
import com.whuuno.inventory.model.User;
import com.whuuno.inventory.security.JwtTokenProvider;
import com.whuuno.inventory.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and Registration APIs")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Operation(
            summary = "User login",
            description = "Authenticate user and return JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        User user = userService.findByUsername(loginRequest.getUsername());

        JwtResponse jwtResponse = new JwtResponse(jwt, user.getUsername(), user.getEmail());

        return ResponseEntity.ok(jwtResponse);
    }

    @Operation(
            summary = "Register new user",
            description = "Create a new user account"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Username or email already exists"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Username is already taken!");
            return ResponseEntity.badRequest().body(error);
        }

        if (userService.existsByEmail(registerRequest.getEmail())) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Email is already in use!");
            return ResponseEntity.badRequest().body(error);
        }

        User user = new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword()
        );

        userService.registerUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}