package pp314.service;

import pp314.model.Role;
import pp314.model.User;
import pp314.repositories.RoleRepository;
import pp314.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}



	@Override
    @Transactional
    public void add(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
	@Override
	public User findById(Long id) {
		return userRepository.findById(id);
	}


    @Override
    public User getByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
<<<<<<< Updated upstream
    public void update(User user, Long id) {
        final User previousUser = userRepository.findById(id);

	    previousUser.setFirstName(user.getFirstName());
	    previousUser.setLastName(user.getLastName());
	    previousUser.setAge(user.getAge());
		previousUser.setRoles(user.getRoles());

	    String previousEmail = previousUser.getEmail();
	    String newEmail = user.getEmail();
	    if (previousEmail.equals(newEmail) || existsByEmail(newEmail)) {
		    previousUser.setEmail(previousEmail);
	    } else {
		    previousUser.setEmail(newEmail);
	    }

		String newPassword = user.getPassword();
	    if (newPassword.isEmpty()) {
		    previousUser.setPassword(previousUser.getPassword());
	    } else {
		    previousUser.setPassword(passwordEncoder.encode(newPassword));
	    }

        userRepository.save(previousUser);
=======
    public void update(User user, Long id) throws UserNotFoundException {
//        User oldUser = getById(user.getId());
//        if (oldUser.getPassword().equals(user.getPassword()) || "".equals(user.getPassword())) {
//            user.setPassword(oldUser.getPassword());
//        } else {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        userRepository.save(user);


	    final User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
//	    System.out.println("\n\n !!! " + user.getAge());
	    if (user.getPassword().isEmpty()) {
		    user.setPassword(existingUser.getPassword());
	    } else {
		    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    }

//	    Set<Role> roles = new HashSet<>();
//	    for (Role role : user.getRoles()) {
//		    roles.add(roleRepository.findRoleByName(role.getName()));
//	    }
//	    user.setRoles(roles);

//	    user.setEmail(user.getEmail());

	    userRepository.save(user);
>>>>>>> Stashed changes
    }


	@Override
	@Transactional
	public User getCurrentUser() {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		return getByUsername(principal.getName());
	}


<<<<<<< Updated upstream

	@Transactional
	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);

	}
=======
	@Transactional
	@Override
	public boolean existsByEmail(String email) {
		final User existingUser = userRepository.findByEmail(email);
//	    System.out.println("\n\n !!! " + user.getAge());

//		if (user.getPassword().isEmpty()) {
//			user.setPassword(existingUser.getPassword());
//		} else {
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//		}

		return userRepository.existsByEmail(email);
	}

>>>>>>> Stashed changes

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	// mapping list of roles to list of authorities
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
				       .map(role -> new SimpleGrantedAuthority(role.getName()))
				       .collect(Collectors.toList());
	}


	@Transactional
	public List<Role> loadAllRoles() {
		return roleRepository.findAll();
	}

	@Transactional
	public Set<Role> getUserRoles(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		if (user != null) {
			return user.getRoles();
		}

		return new HashSet<>();
	}

}
