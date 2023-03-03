package pp314.controller;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestParam;
import pp314.model.*;
import pp314.service.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRESTController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminRESTController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


	@GetMapping("/users")
    public ResponseEntity<List<User>> showUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> showUser(@PathVariable("id") Long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }


    @GetMapping("/current")
    public ResponseEntity<User> showAuthUser() {
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<HttpStatus> saveNewUser(
            @RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<> (HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<> (HttpStatus.OK);
    }


    @PatchMapping("/users/{id}")
<<<<<<< Updated upstream
    public ResponseEntity<HttpStatus> userSaveEdit(@RequestBody @NotNull User user, @PathVariable Long id) {
=======
    public ResponseEntity<HttpStatus> userSaveEdit(@RequestBody @NotNull User user, @PathVariable Long id) throws UserNotFoundException {
        user.setId(id);
>>>>>>> Stashed changes
        userService.update(user, id);
        return new ResponseEntity<> (HttpStatus.OK);
    }

	@SneakyThrows
	@PostMapping("/check-email")
	public ResponseEntity<Boolean> checkEmail(@RequestBody String jsonEmail) {
		JSONObject jsonObject = new JSONObject(jsonEmail);
		String email = jsonObject.getString("email");
		boolean emailExists = userService.existsByEmail(email);

		return ResponseEntity.ok(emailExists);
	}

}
