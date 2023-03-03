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
    void update(User user, Long id) throws UserNotFoundException;

<<<<<<< Updated upstream
    User getById(Long id);
    User findById(Long id);
=======
    User getById(Long id) throws UserNotFoundException;
>>>>>>> Stashed changes
    User getByUsername(String userName);

    User getCurrentUser();

	boolean existsByEmail(String email);
}
