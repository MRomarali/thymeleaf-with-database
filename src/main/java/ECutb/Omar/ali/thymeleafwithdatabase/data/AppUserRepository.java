package ECutb.Omar.ali.thymeleafwithdatabase.data;

import ECutb.Omar.ali.thymeleafwithdatabase.entity.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser,Integer> {
    Optional<AppUser> findByEmailIgnoreCase(String email);
}
