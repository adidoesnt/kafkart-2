package me.adityabanerjee.kafkart_backend.users;

import java.util.Optional;

import me.adityabanerjee.kafkart_backend.utils.BcryptUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    private boolean validatePassword(String password, User user) {
        return BcryptUtils.checkPassword(password, user.passwordHash());
    }

    public User login(String email, String password) {
        Optional<User> user = repository.findByEmail(email);

        if(user.isEmpty()) {
            throw new UserNotFoundException();
        }

        if (!validatePassword(password, user.get())) {
            throw new IncorrectPasswordException();
        }

        return user.get();
    }
}
