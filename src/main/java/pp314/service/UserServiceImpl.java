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
    public User getById(Long id) {
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
    public void update(User user, Long id) {
        User oldUser = getById(user.getId());
        if (oldUser.getPassword().equals(user.getPassword()) || "".equals(user.getPassword())) {
            user.setPassword(oldUser.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }


	@Override
	@Transactional
	public User getCurrentUser() {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		return getByUsername(principal.getName());
	}


//    @Override
//    public UserDetails loadUserByUsername(String firstName) throws UsernameNotFoundException {
//        Optional<User> userPrimary = getByUsername(firstName);
//        if (userPrimary == null) {
//            throw new UsernameNotFoundException(firstName + " not found");
//        }
//        return userPrimary.get();
//    }

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
	public Set<Role> getUserRoles(Long id) {
		User user = userRepository.findById(id);

		if (user != null) {
			return user.getRoles();
		}

		return new HashSet<>();
	}

}
