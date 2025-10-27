package com.businesscenterservices.businesscenterservices.controllers;

import com.businesscenterservices.businesscenterservices.dto.RolesDTO;
import com.businesscenterservices.businesscenterservices.dto.UsersDTO;
import com.businesscenterservices.businesscenterservices.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserServices userServices;

    @PostMapping("/ajoutUsers")
    public ResponseEntity<UsersDTO> addNewUser(@RequestBody UsersDTO userDTO) {
        UsersDTO savedUser = userServices.addNewUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @PutMapping("/{usersId}")
    public ResponseEntity<UsersDTO> updateUsers(@PathVariable Long usersId,@RequestBody UsersDTO usersDTO){
        UsersDTO saveUser= userServices.updateUser(usersId, usersDTO);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @PostMapping("/addRole")
    public ResponseEntity<RolesDTO> addNewRole(@RequestBody RolesDTO rolesDTO) {
        RolesDTO savedRole = userServices.addNewRole(rolesDTO);
        return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RolesDTO>> getAllRoles() {
        List<RolesDTO> roles = userServices.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/addRoleToUser")
    public ResponseEntity<Void> addRoleToUser(@RequestParam String nomUser, @RequestParam String roleName) {
        userServices.addRoleToUsers(nomUser, roleName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/addAllRoles")
    public ResponseEntity<Void> addAllRoles(@RequestBody List<RolesDTO> rolesDTOs) {
        userServices.addAllRoles(rolesDTOs);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getUserByNomUser")
    public ResponseEntity<UsersDTO> getUserByNomUser(@RequestParam String nomUser) {
        UsersDTO userDTO = userServices.loadUserByNomUser(nomUser);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/getUsersByRole")
    public ResponseEntity<List<UsersDTO>> getUsersByRole(@RequestParam String roleName) {
        List<UsersDTO> users = userServices.getUsersByRole(roleName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getRoleByNom")
    public ResponseEntity<RolesDTO> getRoleByNom(@RequestParam String roleName) {
        RolesDTO roleDTO = userServices.findRolesByNom(roleName);
        return new ResponseEntity<>(roleDTO, HttpStatus.OK);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<List<UsersDTO>> listUsers() {
        List<UsersDTO> users = userServices.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UsersDTO> getUserByID(@PathVariable Long userId) {
        UsersDTO userDTO = userServices.getUserByID(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/connectedUser")
    public ResponseEntity<UsersDTO> connectedUser() {
        UsersDTO userDTO = userServices.connectedUser();
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/archive/{userId}") // Utilisation de @PathVariable
    public ResponseEntity<UsersDTO> archiveUser(@PathVariable Long userId) {
        UsersDTO archivedUser = userServices.archiveUser(userId);
        if (archivedUser != null) {
            return ResponseEntity.ok(archivedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
