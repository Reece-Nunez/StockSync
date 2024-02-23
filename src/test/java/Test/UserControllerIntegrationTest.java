package Test;

import com.nunezdev.inventory_manager.dto.PasswordDTO;
import com.nunezdev.inventory_manager.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public UserControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    @Transactional
    public void testCreateAndSetPasswordForUser() throws Exception {
        // Step 1: Create a user
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setRoleName("USER");
        String userJson = objectMapper.writeValueAsString(userDTO);

        MvcResult createUserResult = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                        .andExpect(status().isCreated())
                        .andReturn();

        String responseString = createUserResult.getResponse().getContentAsString();
        Long userId = JsonPath.read(responseString, "$.id");

        // Step 2: Set the password for the newly created user
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setNewPassword("newSecurePassword");
        mockMvc.perform(put("/api/users/" + userId + "/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDTO)))
                        .andExpect(status().isOk());
    }

}
