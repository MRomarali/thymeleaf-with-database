package ECutb.Omar.ali.thymeleafwithdatabase;

import ECutb.Omar.ali.thymeleafwithdatabase.data.AppRoleRepository;
import ECutb.Omar.ali.thymeleafwithdatabase.entity.AppRole;
import ECutb.Omar.ali.thymeleafwithdatabase.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Seeder implements CommandLineRunner {
    private AppUserService appUserService;
    private AppRoleRepository appRoleRepository;

    @Autowired
    public Seeder(AppUserService appUserService, AppRoleRepository appRoleRepository) {
        this.appUserService = appUserService;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        appRoleRepository.save(new AppRole("USER"));
        appRoleRepository.save(new AppRole("ADMIN"));

        appUserService.registerAppUser("Omar", "Ali", "Omarali@gmail.com", "admin", LocalDate.now(), true);
        appUserService.registerAppUser("Ahmed", "Ali", "Ahmedali@gmail.com", "user", LocalDate.now(), false);
    }
}
