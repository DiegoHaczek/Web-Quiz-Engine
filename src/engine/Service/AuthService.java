package engine.Service;

import engine.Entity.User;
import engine.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> register(String email, String password) {
        if(userRepository.existsByUsername(email)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"");
        };
        User newUser = new User(email,password);
        userRepository.save(newUser);
        return ResponseEntity.ok("");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Not found " + username));
        return new User(user);
    }
}
