package pp314.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "users")
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "password")
    private String password;

    @Email
    @NotNull
    @Column(name = "email", unique = true, length = 50, nullable = false)
    private String email;

    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 20, message = "Name to short (2) or long (30)")
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @NotEmpty(message = "The field cannot be empty")
    @Size(min = 2, max = 20, message = "Name to short (2) or long (30)")
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Min(value = 0, message = "Age must be greater than 0" )
    @Column(name = "age", nullable = false)
    private int age;

	@ManyToMany
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public User() {
    }

    public User(String password, String email, String firstName, String lastName, int age, Set<Role> roles) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.roles = roles;
    }


    public Long getId() {
        return id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	@Override
    public String getUsername() {
        return email;
    }

	@Override
    public String getPassword() {
        return password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public String getEmail() {
        return email;
    }

	public void setEmail(String email) {
        this.email = email;
    }

	public Set<Role> getRoles() {
        return roles;
    }

	public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

	public String getRolesToString() {
        String s = getRoles().toString().replaceAll("^\\[|\\]$", "");
        return s.replace("ROLE_", "");
    }

    public String getFirstName() {
        return firstName;
    }

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

	public String getLastName() {
        return lastName;
    }

	public void setLastName(String lastName) {
        this.lastName = lastName;
    }

	public int getAge() {
        return age;
    }

	public void setAge(int age) {
        this.age = age;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return  roles;
    }

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

	@Override
    public boolean isAccountNonLocked() {
        return true;
    }

	@Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                age == user.age &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, email, firstName, lastName, age, roles);
    }
}