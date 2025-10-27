package com.businesscenterservices.businesscenterservices.config;

import com.businesscenterservices.businesscenterservices.dto.UsersDTO;
import com.businesscenterservices.businesscenterservices.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserServices userServices;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userServices.findByEmail("admin@admin.com") == null) {
            UsersDTO adminDTO = new UsersDTO();
            adminDTO.setNomUser("Admin");
            adminDTO.setPrenomUser("Admin");
            adminDTO.setEmailUser("admin@admin.com");
            adminDTO.setPasswordUser(passwordEncoder.encode("passer@1234"));
            adminDTO.setStatus(true);
            adminDTO.setPasswordChanged(true);
            adminDTO.setArchived(false);
            
            userServices.save(adminDTO);
        }
    }
}
