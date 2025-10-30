package com.businesscenterservices.auth.service;

import com.businesscenterservices.auth.dto.RoleDTO;
import com.businesscenterservices.auth.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO findByEmail(String email);

    UserDTO findByNom(String nom);

    List<UserDTO> getUsersByRole(String roleName);

    UserDTO archiveUser(Long id);

    void assignRoleToUser(Long userId, String roleName);

    RoleDTO createRole(RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    RoleDTO getRoleByName(String roleName);

    UserDTO getCurrentUser();
}
