package ECutb.Omar.ali.thymeleafwithdatabase.service;

import ECutb.Omar.ali.thymeleafwithdatabase.data.AppRoleRepository;
import ECutb.Omar.ali.thymeleafwithdatabase.data.AppUserRepository;
import ECutb.Omar.ali.thymeleafwithdatabase.entity.AppRole;
import ECutb.Omar.ali.thymeleafwithdatabase.entity.AppUser;
import ECutb.Omar.ali.thymeleafwithdatabase.security.AppUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin) {
        AppUser newUser = new AppUser(
                firstName,
                lastName,
                bCryptPasswordEncoder.encode(password),
                email,
                regDate
        );
        Set<AppRole> roles = new HashSet<>();
        if (isAdmin){
            AppRole userRole = appRoleRepository.findByRole("admin").orElseThrow(IllegalArgumentException::new);
        }
        AppRole userRole = appRoleRepository.findByRole("user").orElseThrow(IllegalArgumentException::new);
        roles.add(userRole);
        newUser.setRoleSet(roles);
        return appUserRepository.save(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = appUserRepository.findByEmailIgnoreCase(email);
        AppUser appUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " could not be found"));
        Collection<GrantedAuthority> authorities = new HashSet<>();
        for (AppRole role : appUser.getRoleSet()){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
            System.err.println(authority.getAuthority());
            authorities.add(authority);
        }
        return new AppUserPrincipal(appUser, authorities);
    }
}
