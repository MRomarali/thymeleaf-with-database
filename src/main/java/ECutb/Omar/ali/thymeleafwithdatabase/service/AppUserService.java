package ECutb.Omar.ali.thymeleafwithdatabase.service;

import ECutb.Omar.ali.thymeleafwithdatabase.entity.AppUser;

import java.time.LocalDate;

public interface AppUserService {
    AppUser registerAppUser(String firstName, String lastName, String email, String password, LocalDate regDate, boolean isAdmin);
}
