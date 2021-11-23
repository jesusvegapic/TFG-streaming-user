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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// In profile prod select core and angular origins.
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@PreAuthorize("hasRole('ADMIN') or hasRole('MOD')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String CLIENTS = "/clients";
    public static final String TOKEN = "/token";

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
       return userService.login(activeUser.getUsername())
                .map(TokenDto::new);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createUser(@Valid @RequestBody UserDto creationUserDto) {
        this.userService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = CLIENTS)
    public void registerUser(@Valid @RequestBody UserDto creationUserDto) {
        if(creationUserDto.getRole().equals(Role.CLIENT)) {
            this.userService.createUser(creationUserDto.toUser(), Role.CLIENT);
        } else {
            throw new BadRequestException("No se pueden registrar usuarios que no sean clientes.");
        }
    }



    private Role extractRoleClaims() {
        List<String> roleClaims = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        System.out.println(roleClaims);
        return Role.of(roleClaims.get(0)); // it must only be a role
    }

}
