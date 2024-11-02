package com.project3.project3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String home() {
        return "Welcome to the API! You can access the following resources:\n" +
                "- Users: /api/users\n" +
                "   - GET: /api/users (Retrieve all users)\n" +
                "   - GET: /api/users/{id} (Retrieve user by ID)\n" +
                "   - POST: /api/users (Create a new user)\n" +
                "   - PUT: /api/users/{id} (Update user by ID)\n" +
                "   - DELETE: /api/users/{id} (Delete user by ID)\n" +
                "- Admin: /api/admin\n" +
                "   - PUT: /api/admin/{id}/{role} (Assign role to user)\n" +
                "   - DELETE: /api/admin/{id}/{role} (Remove role from user)\n" +
                "   - GET: /api/admin/users (Retrieve all users - Admin view)\n" +
                "   - GET: /api/admin/{id}/roles (Get user roles)\n" +
                "Please refer to the documentation for more details.";
    }
}
