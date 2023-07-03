package engine.Controller;

import engine.Dto.RegisterDto;
import engine.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder encoder;

    public AuthController(AuthService authService, PasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto dto){
        System.out.println(dto);
        return authService.register(dto.email(),encoder.encode(dto.password()));
    }
}
