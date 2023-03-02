package pp314.service;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(Long id) {
		super("User with ID " + id + " not found");
	}

	public UserNotFoundException(String username) {
		super("User with email " + username + " not found");
	}
}
