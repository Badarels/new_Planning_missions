package com.businesscenterservices.businesscenterservices;

import com.businesscenterservices.businesscenterservices.fixtures.UtilisateurFixtures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BusinesscenterservicesApplication implements CommandLineRunner {

	@Autowired
	private UtilisateurFixtures utilisateurFixtures;

	public static void main(String[] args){
		SpringApplication.run(BusinesscenterservicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*utilisateurFixtures.addDefaultRoles();
		utilisateurFixtures.addDefaultSuperAdmin();*/
	}
		/*@Bean
		CommandLineRunner Start(AccountServices accountServices){
			return args -> {
				accountServices.addNewRole(new Roles(null,"Super Admin"));
				accountServices.addNewRole(new Roles(null,"Admin"));
				accountServices.addNewRole(new Roles(null, "User"));
				accountServices.addNewRole(new Roles(null, "Agent"));

				accountServices.addNewUser(new Users(null,"Diop","Alioune", "thies",  "774858979", new Date(27-02-1998), "425656899", "passer@1", "rels@gmail.com",new ArrayList<>()));
				accountServices.addNewUser(new Users(null,  "Ndiaye","Doudou", "Dakar",  "773568979", new Date(8-10-1994), "74856878", "passer@2", "doud@gmail.com",new ArrayList<>()));
				accountServices.addNewUser(new Users(null,"Sock","Badara", "THies",  "789858979", new Date(12-05-1988), "69356802", "passer@3", "line@gmail.com",new ArrayList<>()));
				accountServices.addNewUser(new Users(null,"Diouf","Mor", "Mboro",  "764758970", new Date(18-05-1978), "78954284", "passer@4", "mor@gmail.com",new ArrayList<>()));
				accountServices.addNewUser(new Users(null,"Gueye","Safia", "Dakar",  "774852979", new Date(14-06-2000), "4795584", "passer@5", "safia@gmail.com",new ArrayList<>()));

				accountServices.AddRoleToUsers("Diop","Super Admin");
				accountServices.AddRoleToUsers("Diop","User");
				accountServices.AddRoleToUsers("Ndiaye","Agent");
				accountServices.AddRoleToUsers("Sock","Admin");
				accountServices.AddRoleToUsers("Sock","User");
				accountServices.AddRoleToUsers("gueye","Agent");
				accountServices.AddRoleToUsers("Diouf","Agent");

			};
		}*/

		
}
