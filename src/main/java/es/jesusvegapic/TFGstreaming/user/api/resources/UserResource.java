package es.jesusvegapic.TFGstreaming.user.api.resources;

import es.jesusvegapic.TFGstreaming.user.api.dtos.TokenDto;
import es.jesusvegapic.TFGstreaming.user.api.dtos.UserDto;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.domain.exceptions.BadRequestException;
import es.jesusvegapic.TFGstreaming.user.domain.services.JwtService;
import es.jesusvegapic.TFGstreaming.user.domain.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String CLIENTS = "/clients";
    public static final String TOKEN = "/token";
    public static final String EMAIL_ID= "/{email}";

    private UserService userService;
    private JwtService jwtService;

    @Autowired
    public UserResource(UserService userService , JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Optional<TokenDto> login(@AuthenticationPrincipal User activeUser) {
       if(activeUser != null) return userService.login(activeUser.getUsername()).map(TokenDto::new);
       else throw new BadRequestException("Faltan campos en el login de usuario.");
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createUser(@Valid @RequestBody UserDto creationUserDto) {
        this.userService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = CLIENTS)
    public void registerUser(@Valid @RequestBody UserDto creationUserDto) {
       creationUserDto.doDefault();
        if(creationUserDto.getRole().equals(Role.CLIENT)) {
            this.userService.createUser(creationUserDto.toUser(), Role.CLIENT);
        } else {
            throw new BadRequestException("No se pueden registrar usuarios que no sean clientes.");
        }
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(EMAIL_ID)
    public UserDto readUser(@PathVariable String email) {
        return new UserDto(this.userService.readByEmailAssured(email));
    }

    private Role extractRoleClaims() {
        List<String> roleClaims = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        System.out.println(roleClaims);
        return Role.of(roleClaims.get(0)); // it must only be a role
    }

}
