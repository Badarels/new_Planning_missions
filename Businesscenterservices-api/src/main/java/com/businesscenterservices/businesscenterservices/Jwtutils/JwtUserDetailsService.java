package com.businesscenterservices.businesscenterservices.Jwtutils;

import com.businesscenterservices.businesscenterservices.entities.Users;
import com.businesscenterservices.businesscenterservices.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    public void setUtilisateurRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailUser) throws UsernameNotFoundException {
        Optional<Users> userSearch = Optional.of(usersRepository.findByEmailUser(emailUser));

        return userSearch.map(utilisateur -> {
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(utilisateur.getRoles().getNomRoles()));
            return new User(emailUser, utilisateur.getPasswordUser(), grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("L'utilisateur " + emailUser + " n'existe pas!"));
    }

}
