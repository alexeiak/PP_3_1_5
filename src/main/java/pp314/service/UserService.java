package pp314.service;

import pp314.model.Role;
import pp314.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<Role> getAllRoles();

    void add(User user);
    List<User> getAllUsers();
    void delete(Long id);
    void update(User user, Long id);

    User getById(Long id);
    User getByUsername(String userName);

    User getCurrentUser();
}
