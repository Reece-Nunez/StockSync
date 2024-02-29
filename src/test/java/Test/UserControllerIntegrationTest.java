package Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nunezdev.inventory_manager.controller.UserController;
import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private UserDTO userDTO;
    private AppUser appUser;

    @BeforeEach
    void setUp() {
        // Initialize your DTOs and entities here
        userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setRoleName("USER");

        appUser = new AppUser();
        appUser.setId(1L);
        appUser.setUsername(userDTO.getUsername());
        // Assuming Role is an entity or enum that you set based on the roleName
        Role role = new Role(); // Simplified, adjust according to your Role entity
        role.setName(userDTO.getRoleName());
        appUser.setRole(role);

        when(userService.convertToEntity(any(UserDTO.class))).thenReturn(appUser);
        when(userService.createUser(anyString(), any(Role.class), anyString())).thenReturn(appUser);
        when(userService.findById(any(Long.class))).thenReturn(appUser);
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
    }

    @Test
    public void testCreateAndSetPasswordForUser() throws Exception {
        // Mock the behavior to simulate user creation and password setting
        when(userService.findByUsername("testuser")).thenReturn(appUser);

        // Step 1: Create a user - Simulate by directly calling userService
        AppUser createdUser = userService.createUser("testuser", new Role(), "password123");

        // Convert to JSON manually since this step would normally be done by your controller
        String userJson = objectMapper.writeValueAsString(userService.convertToDTO(createdUser));

        // Assuming /api/users endpoint for creating a user and /api/users/{id}/password for setting a password
        // Note: The actual user creation logic might be done in the controller, affecting how you test it

        MvcResult createUserResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        // Step 2: Set the password for the newly created user
        mockMvc.perform(put("/api/users/" + createdUser.getId() + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"newPassword\":\"newSecurePassword\"}"))
                .andExpect(status().isOk());
    }
}
