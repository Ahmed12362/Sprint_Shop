package com.example.Sprint.service.user;

import com.example.Sprint.dto.UserDto;
import com.example.Sprint.exception.AlreadyExists;
import com.example.Sprint.model.Role;
import com.example.Sprint.model.User;
import com.example.Sprint.repository.RoleRepository;
import com.example.Sprint.repository.UserRepository;
import com.example.Sprint.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public UserDto registerAsUser(UserDto userDto) {
        userRepository.findByUsername(userDto.getUsername())
                .ifPresent(s -> {
                    throw new AlreadyExists("user already exists");
                });
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role(null, "USER")));

        user.setRoles(List.of(role));
        userRepository.save(user);
        return userDto;
    }

    @Override
    public String login(UserDto userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDto.getUsername(),
                userDto.getPassword()
        ));
        return jwtUtil.generateToken(userDto.getUsername());
    }
}
