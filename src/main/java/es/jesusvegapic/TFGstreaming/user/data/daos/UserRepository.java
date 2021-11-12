package es.jesusvegapic.TFGstreaming.user.data.daos;

import es.jesusvegapic.TFGstreaming.user.data.model.Role;
import es.jesusvegapic.TFGstreaming.user.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional< User > findByEmail(String email);

    List< User >  findByRoleIn(Collection<Role> roles);
}
