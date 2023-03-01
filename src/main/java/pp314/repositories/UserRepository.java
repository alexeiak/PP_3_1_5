package pp314.repositories;

import pp314.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u join fetch u.roles where u.email=:email")
    User findByUsername(String email);

	User findByEmail(String email);


    User findById(Long id);

	void deleteById(Long id);
}
