package es.jesusvegapic.TFGstreaming.user.domain.services;

import es.jesusvegapic.TFGstreaming.user.data.daos.UserRepository;
import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Qualifier("jesusvegapic.users")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("email not found." + email));
        return this.userBuilder(user.getEmail(), user.getPasswd(), new Role[]{Role.AUTHENTICATED}, user.getActive());
    }

    private org.springframework.security.core.userdetails.User userBuilder(String email, String password, Role[] roles,
                                                                           boolean active) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.withPrefix()));
        }
        return new org.springframework.security.core.userdetails.User(email, password, active, true,
                true, true, authorities);
    }
}
