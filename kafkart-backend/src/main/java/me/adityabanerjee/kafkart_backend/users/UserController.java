package me.adityabanerjee.kafkart_backend.users;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequestBody body) {
        return service.login(body.email(), body.password());
    }
}
