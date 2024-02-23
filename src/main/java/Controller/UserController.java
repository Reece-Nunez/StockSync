package Controller;

import Dto.UserDTO;
import Entity.AppUser;
import Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        AppUser appUser = userService.createUser(userDTO.getUsername(), userDTO.getRole());
        return userService.convertToDTO(appUser);
    }

    @PutMapping("/{id}/password")
    public void setPassword(@PathVariable long id, @RequestBody String newPassword) {
        AppUser appUser = userService.findById(id);
        userService.changePassword(appUser, newPassword);
    }

    @PutMapping("/{id}/password")
    public void changePassword(@PathVariable long id, @RequestBody String newPassword) {
        AppUser appUser = userService.findById(id);
        userService.changePassword(appUser, newPassword);
    }
}
