package Service;


import Entity.AppUser;
import Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser createUser(String username, String password, String role) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setPassword(password);
        appUser.setRole(role);
        userRepository.save(appUser);
        return appUser;
    }

    public void changePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(newPassword);
        userRepository.save(appUser);
    }
}
