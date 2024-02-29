package Test;

import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.impl.UserServiceImpl;
import com.nunezdev.inventory_manager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest(classes = UserServiceTest.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Setup
        String username = "testUser";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";
        Role role = new Role(); // Assuming you have a Role entity or enum; set it appropriately
        role.setName("USER"); // or role = Role.USER; depending on your Role implementation

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // Execute
        AppUser createdUser = userServiceImpl.createUser(username, role, rawPassword);

        // Verify
        verify(userRepository, times(1)).save(any(AppUser.class));
        verify(passwordEncoder, times(1)).encode(rawPassword);

        // Assert
        assertNotNull(createdUser);
        assertEquals(username, createdUser.getUsername());
        assertEquals(encodedPassword, createdUser.getPassword());
        assertEquals(role, createdUser.getRole());
    }
}
