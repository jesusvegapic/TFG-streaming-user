package es.jesusvegapic.TFGstreaming.user.domain.services;

import es.jesusvegapic.TFGstreaming.user.data.daos.UserRepository;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import es.jesusvegapic.TFGstreaming.user.domain.exceptions.ConflictException;
import es.jesusvegapic.TFGstreaming.user.domain.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public Optional<String> login(String email) {
        return this.userRepository.findByEmail(email)
                .map(user -> jwtService.createToken(user.getEmail(), user.getName(), user.getRole().name()));
    }

    public void createUser(User user, Role roleClaim) {
        if(!authorizedRoles(roleClaim).contains(user.getRole())) {
            throw new ForbiddenException("Insufficient role to create this user: " + user);
        }
        this.noExistByEmail(user.getEmail());
        user.setRegistrationDate(LocalDateTime.now());
        this.userRepository.save(user);
    }

    private List<Role> authorizedRoles(Role roleClaim) {
        if(Role.ADMIN.equals(roleClaim)) {
            return List.of(Role.ADMIN, Role.MOD, Role.CLIENT);
        } else if(Role.MOD.equals(roleClaim) || Role.CLIENT.equals(roleClaim)) {
            return List.of(Role.CLIENT);
        } else
            return List.of();
    }

    private void noExistByEmail(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new ConflictException("The email already exist: " + email);
        }
    }

}
