package main.controllers;

import main.entity.User;
import main.repository.UserRepository;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRep;

    @Autowired
    PasswordEncoder pwdEncoder;

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody AuthRequest request)
    {
        try
        {
            String name = request.getUserName();
            String password = request.getPassword();
            Optional<User> optionalUser = userRep.findUserByUserName(name);
            if (optionalUser.isEmpty())
            {
                throw new UsernameNotFoundException("User not found");
            }
            if (!pwdEncoder.matches(password, optionalUser.get().getPassword()))
            {
                throw new UsernameNotFoundException("User not found");
            }
            String token = jwtTokenProvider.createToken(
                    name,
                    optionalUser.get().getRoles()
            );
            Map<Object, Object> model = new HashMap<>();
            model.put("userName", name);
            model.put("token", token);

            return ResponseEntity.ok(model);
        }
        catch(AuthenticationException e)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
