package com.businesscenterservices.auth.service;

import com.businesscenterservices.auth.dto.RoleDTO;
import com.businesscenterservices.auth.dto.UserDTO;
import com.businesscenterservices.auth.entity.Role;
import com.businesscenterservices.auth.entity.User;
import com.businesscenterservices.auth.repository.RoleRepository;
import com.businesscenterservices.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userRepository.findByEmailUser(userDTO.getEmail())
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
                });

        User user = mapToEntity(new User(), userDTO, true);
        User saved = userRepository.save(user);
        return mapToDto(saved);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        if (!existing.getEmailUser().equals(userDTO.getEmail())) {
            userRepository.findByEmailUser(userDTO.getEmail())
                    .ifPresent(other -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
                    });
        }

        User updated = mapToEntity(existing, userDTO, false);
        return mapToDto(userRepository.save(updated));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmailUser(email)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findByNom(String nom) {
        return userRepository.findByNomUser(nom)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByRole(String roleName) {
        return userRepository.findByArchivedFalseAndStatusTrueAndRoles_NomRoles(roleName).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO archiveUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
        user.setArchived(true);
        user.setStatus(false);
        return mapToDto(userRepository.save(user));
    }

    @Override
    public void assignRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
        Role role = roleRepository.findByNomRoles(roleName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rôle introuvable"));
        user.setRoles(role);
        userRepository.save(user);
    }

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        roleRepository.findByNomRoles(roleDTO.getNomRoles())
                .ifPresent(existing -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Rôle déjà existant");
                });
        Role role = new Role();
        role.setNomRoles(roleDTO.getNomRoles());
        Role saved = roleRepository.save(role);
        return modelMapper.map(saved, RoleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findByNomRoles(roleName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rôle introuvable"));
        return modelMapper.map(role, RoleDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Aucun utilisateur connecté");
        }

        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }

        return findByEmail(username);
    }

    private User mapToEntity(User target, UserDTO source, boolean isNew) {
        target.setNomUser(source.getNom());
        target.setPrenomUser(source.getPrenom());
        target.setAdresseUser(source.getAdresse());
        target.setTelephoneUser(source.getTelephone());
        target.setSexeUser(source.getSexe());
        target.setDateNaissanceUser(source.getDateNaissance());
        target.setNumeroPieceIdentiteUser(source.getNumeroPieceIdentite());
        target.setEmailUser(source.getEmail());
        target.setArchived(source.isArchived());
        target.setStatus(source.isStatus());
        target.setPasswordChanged(source.isPasswordChanged());

        if (source.getPassword() != null && !source.getPassword().isBlank()) {
            target.setPasswordUser(passwordEncoder.encode(source.getPassword()));
            target.setPasswordChanged(true);
        } else if (isNew) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe est obligatoire");
        }

        Role role = source.getRole() != null ? resolveRole(source.getRole()) : target.getRoles();
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le rôle est obligatoire");
        }
        target.setRoles(role);
        return target;
    }

    private Role resolveRole(RoleDTO roleDTO) {
        if (roleDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le rôle est obligatoire");
        }

        if (roleDTO.getId() != null) {
            return roleRepository.findById(roleDTO.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rôle introuvable"));
        }
        if (roleDTO.getNomRoles() != null) {
            return roleRepository.findByNomRoles(roleDTO.getNomRoles())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Rôle introuvable"));
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informations de rôle incomplètes");
    }

    private UserDTO mapToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNomUser());
        dto.setPrenom(user.getPrenomUser());
        dto.setAdresse(user.getAdresseUser());
        dto.setTelephone(user.getTelephoneUser());
        dto.setSexe(user.getSexeUser());
        dto.setDateNaissance(user.getDateNaissanceUser());
        dto.setNumeroPieceIdentite(user.getNumeroPieceIdentiteUser());
        dto.setEmail(user.getEmailUser());
        dto.setArchived(user.isArchived());
        dto.setStatus(user.isStatus());
        dto.setPasswordChanged(user.isPasswordChanged());
        if (user.getRoles() != null) {
            dto.setRole(new RoleDTO(user.getRoles().getId(), user.getRoles().getNomRoles()));
        }
        dto.setPassword(null); // ne jamais exposer le mot de passe
        return dto;
    }
}
