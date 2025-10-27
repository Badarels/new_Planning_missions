package com.businesscenterservices.businesscenterservices.fixtures;

import com.businesscenterservices.businesscenterservices.dto.RolesDTO;
import com.businesscenterservices.businesscenterservices.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class UtilisateurFixtures {

    private UserServicesImpl userServices;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UtilisateurFixtures(@Lazy UserServicesImpl userServices, PasswordEncoder passwordEncoder) {
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
    }

    public void addDefaultRoles() {
        List<RolesDTO> rList = userServices.getAllRoles();
        if (rList == null || rList.isEmpty()) {
            RolesDTO[] defaultRoles = {
                    new RolesDTO(null, "SUPER_ADMIN"),
                    new RolesDTO(null, "ADMIN"),
                    new RolesDTO(null, "AGENTS")
            };
            userServices.addAllRoles(Arrays.asList(defaultRoles));
        }
    }


   /* public void addDefaultSuperAdmin() {
        // 1. Gestion des exceptions
        try {
            // 2. Utilisation de l'PasswordEncoder (assurez-vous de l'avoir configuré)
            PasswordEncoder passwordEncoder = passwordEncoder(); // Remplacez par l'obtention correcte de votre PasswordEncoder

            // 3. Conversion de la date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date birthDate = dateFormat.parse("12-02-1995");

            RolesDTO roles = userServices.findRolesByNom("SUPER_ADMIN");
            List<UsersDTO> users =  userServices.getUsersByRole("SUPER_ADMIN");

            if (roles != null && users.size() <= 0) {
                UsersDTO defaultUserDTO = new UsersDTO(
                        null, "Ndiaye", "mamadou", "Pikine",
                        "781253628", "Masculin", birthDate,
                        "3658-5897-1879-1996", passwordEncoder.encode("passer@12"),
                        "doumams@gmail.com", true, false, true, roles, null);

                userServices.addNewUser(defaultUserDTO);
                System.out.println("default admin added successfully");
            }
        } catch (ParseException e) {
            // Gestion de l'exception (remplacez par votre propre logique)
            e.printStackTrace();
        }
    }*/


}
