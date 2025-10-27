package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.dto.RolesDTO;
import com.businesscenterservices.businesscenterservices.dto.UsersDTO;

import java.util.List;


public interface UserServices {

    UsersDTO addNewUser(UsersDTO userDTO);
    RolesDTO  addNewRole(RolesDTO rolesDTO);
    List<RolesDTO> getAllRoles();
    void addRoleToUsers(String nomUser, String roleName);
    UsersDTO loadUserByNomUser(String nomUser);
    List<UsersDTO> getUsersByRole(String roleName);
    RolesDTO findRolesByNom(String roleName);
    List<UsersDTO> listUsers();
    UsersDTO getUserByID(long usersId);
    UsersDTO archiveUser(Long userId);
    UsersDTO updateUser(Long usersId, UsersDTO usersDTO);
    void addAllRoles(List<RolesDTO> rolesDTOs);
    UsersDTO connectedUser();
    UsersDTO findByEmail(String email);
    UsersDTO save(UsersDTO userDTO);
}
