package com.businesscenterservices.auth.controller;

import com.businesscenterservices.auth.dto.RoleDTO;
import com.businesscenterservices.auth.dto.UserDTO;
import com.businesscenterservices.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/by-nom")
    public ResponseEntity<UserDTO> getUserByNom(@RequestParam String nom) {
        return ResponseEntity.ok(userService.findByNom(nom));
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable String roleName) {
        return ResponseEntity.ok(userService.getUsersByRole(roleName));
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<UserDTO> archiveUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.archiveUser(id));
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<Void> assignRole(@PathVariable Long id, @RequestParam String roleName) {
        userService.assignRoleToUser(id, roleName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/roles")
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createRole(roleDTO));
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDTO>> getRoles() {
        return ResponseEntity.ok(userService.getAllRoles());
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable String roleName) {
        return ResponseEntity.ok(userService.getRoleByName(roleName));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> currentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
