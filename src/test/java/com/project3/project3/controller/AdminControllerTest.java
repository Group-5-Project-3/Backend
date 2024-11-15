// AdminControllerTest.java

package com.project3.project3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project3.project3.model.User;
import com.project3.project3.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createUserTest() throws Exception {
        User testUser = new User("testUser", "testPassword", "test@example.com");
        Mockito.when(userService.saveUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUserTest() throws Exception {
        User updatedUser = new User("updatedUser", "newPassword", "new@example.com");
        Mockito.when(userService.updateUser(eq("1"), any(User.class))).thenReturn(Optional.of(updatedUser));

        mockMvc.perform(put("/api/admin/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void assignRoleToUserTest() throws Exception {
        User userWithRole = new User("testUser", "testPassword", "test@example.com");
        userWithRole.setRoles(Collections.singletonList("ROLE_USER"));
        Mockito.when(userService.assignRole("1", "ROLE_USER")).thenReturn(Optional.of(userWithRole));

        mockMvc.perform(put("/api/admin/1/ROLE_USER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void removeRoleFromUserTest() throws Exception {
        User userWithoutRole = new User("testUser", "testPassword", "test@example.com");
        userWithoutRole.setRoles(Collections.emptyList());
        Mockito.when(userService.removeRole("1", "ROLE_USER")).thenReturn(Optional.of(userWithoutRole));

        mockMvc.perform(delete("/api/admin/1/ROLE_USER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").isEmpty());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsersTest() throws Exception {
        User testUser = new User("testUser", "testPassword", "test@example.com");
        Mockito.when(userService.getAllUsers()).thenReturn(Collections.singletonList(testUser));

        mockMvc.perform(get("/api/admin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserRolesTest() throws Exception {
        Mockito.when(userService.getUserRoles("1")).thenReturn(Optional.of(Collections.singletonList("ROLE_ADMIN")));

        mockMvc.perform(get("/api/admin/1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("ROLE_ADMIN"));
    }
}
