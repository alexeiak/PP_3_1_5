package pp314.repositories;

import org.springframework.data.repository.query.Param;
import pp314.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u join fetch u.roles where u.email=:email")
    User findByUsername(String email);
	User findByEmail(String email);
	Optional<User> findById(Long id);

	void deleteById(Long id);

	boolean existsByEmail(String email);
}

