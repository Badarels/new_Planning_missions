package com.businesscenterservices.businesscenterservices.services;

import com.businesscenterservices.businesscenterservices.controllers.Exceptions.EntityNotFoundException;
import com.businesscenterservices.businesscenterservices.dto.RolesDTO;
import com.businesscenterservices.businesscenterservices.dto.UsersDTO;
import com.businesscenterservices.businesscenterservices.entities.Roles;
import com.businesscenterservices.businesscenterservices.entities.Users;
import com.businesscenterservices.businesscenterservices.repositories.RoleRepository;
import com.businesscenterservices.businesscenterservices.repositories.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log
public class UserServicesImpl implements UserServices {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsersDTO addNewUser(UsersDTO userDTO) {
        // Encoder le mot de passe avec BCrypt

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userDTO.getPasswordUser());
        userDTO.setPasswordUser(hashedPassword);
        Users user = modelMapper.map(userDTO, Users.class);
        return modelMapper.map(usersRepository.save(user), UsersDTO.class);
    }

    @Override
    public RolesDTO addNewRole(RolesDTO rolesDTO) {
        Roles role = modelMapper.map(rolesDTO, Roles.class);
        return modelMapper.map(roleRepository.save(role), RolesDTO.class);
    }

    @Override
    public List<RolesDTO> getAllRoles() {
        List<Roles> rolesEntities = roleRepository.findAll();
        return rolesEntities.stream()
                .map(role -> modelMapper.map(role, RolesDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addRoleToUsers(String nomUser, String roleName) {
        Users user = usersRepository.findByNomUser(nomUser);
        Roles role = roleRepository.findByNomRoles(roleName);
        user.setRoles(role);
    }

    @Override
    public void addAllRoles(List<RolesDTO> rolesDTOs) {
        List<Roles> roles = rolesDTOs.stream()
                .map(roleDTO -> modelMapper.map(roleDTO, Roles.class))
                .collect(Collectors.toList());
        roleRepository.saveAll(roles);
    }

    @Override
    public UsersDTO loadUserByNomUser(String nomUser) {
        Users user = usersRepository.findByNomUser(nomUser);
        return modelMapper.map(user, UsersDTO.class);
    }

    @Override
    public List<UsersDTO> getUsersByRole(String roleName) {
        List<Users> users = usersRepository.findByRole(roleName).orElse(new ArrayList<>());
        return users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RolesDTO findRolesByNom(String roleName) {
        Roles role = roleRepository.findByNomRoles(roleName);
        return modelMapper.map(role, RolesDTO.class);
    }

    @Override
    public List<UsersDTO> listUsers() {
        List<Users> users = usersRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UsersDTO.class))
                .collect(Collectors.toList());
    }


   /* @Override
    public UsersDTO getUserByID(Long id) {
        Optional<Users> user = usersRepository.findById(id);
        return modelMapper.map(user, UsersDTO.class);
    }*/

    @Override
    public UsersDTO  getUserByID(long usersId){
        Optional<Users> userOptional = usersRepository.findById(usersId);
        return userOptional.map(user -> modelMapper.map(user, UsersDTO.class)).orElse(null);
    }

    public UsersDTO connectedUser() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String login = "";
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails user = (UserDetails) authentication.getPrincipal();
                login = user.getUsername();
            }
            if (authentication.getPrincipal() instanceof String)
                login = (String) authentication.getPrincipal();

            Users user = usersRepository.findByEmailUser(login);
            return modelMapper.map(user, UsersDTO.class);
        } catch (Exception e) {
            log.severe(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public UsersDTO archiveUser(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);
        if (user.isPresent()) {
            Users users = user.get();
            users.setArchived(true); // Update only the archived field
            usersRepository.save(users);
            return modelMapper.map(users, UsersDTO.class);
        } else {
            // Gérer le cas où l'utilisateur avec l'ID spécifié n'est pas trouvé!!!
            throw new EntityNotFoundException("Utilisateur avec l'ID " + userId + "non trouvé.");
        }
    }

    @Override
    public UsersDTO updateUser(Long usersId, UsersDTO usersDTO) {
        Optional<Users> users = usersRepository.findById(usersId);
        Roles role = modelMapper.map(usersDTO.getRoles(), Roles.class);

        if (users.isPresent()) {
            Users userToUpdate = users.get();
            userToUpdate.setNomUser(usersDTO.getNomUser());
            userToUpdate.setPrenomUser(usersDTO.getPrenomUser());
            userToUpdate.setAdresseUser(usersDTO.getAdresseUser());
            userToUpdate.setDateNaissanceUser(usersDTO.getDateNaissanceUser());
            userToUpdate.setRoles(role);
            userToUpdate.setEmailUser(usersDTO.getEmailUser());
            userToUpdate.setNumeroPieceIdentiteUser(usersDTO.getNumeroPieceIdentiteUser());
            userToUpdate.setSexeUser(usersDTO.getSexeUser());
            userToUpdate.setTelephoneUser(usersDTO.getTelephoneUser());
            // Vérifiez si le mot de passe a été fourni dans UsersDTO
            if (usersDTO.getPasswordUser() != null && !usersDTO.getPasswordUser().isEmpty()) {
                // Encodez le nouveau mot de passe avec BCrypt
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(usersDTO.getPasswordUser());
                userToUpdate.setPasswordUser(hashedPassword);
            }

            // Mettez à jour l'utilisateur dans la base de données
            Users user = usersRepository.save(userToUpdate);
            return modelMapper.map(user, UsersDTO.class);
        } else {
            throw  new EntityNotFoundException("Utilisateur avec l'ID: " +usersId+" est introuvable");
        }
    }

    @Transactional
    public void updatePassword(String username, String encodedPassword) {
        Users user = usersRepository.findByEmailUser(username);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'email: " + username);
        }
        user.setPasswordUser(encodedPassword);
        usersRepository.save(user);
    }

    @Override
    public UsersDTO findByEmail(String email) {
        Users user = usersRepository.findByEmailUser(email);
        return user != null ? modelMapper.map(user, UsersDTO.class) : null;
    }

    @Override
    public UsersDTO save(UsersDTO userDTO) {
        Users user = modelMapper.map(userDTO, Users.class);
        return modelMapper.map(usersRepository.save(user), UsersDTO.class);
    }
}

