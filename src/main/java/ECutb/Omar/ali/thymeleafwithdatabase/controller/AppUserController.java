package ECutb.Omar.ali.thymeleafwithdatabase.controller;

import ECutb.Omar.ali.thymeleafwithdatabase.data.AppUserRepository;
import ECutb.Omar.ali.thymeleafwithdatabase.dto.CreateAppUserForm;
import ECutb.Omar.ali.thymeleafwithdatabase.dto.UpdateAppUserForm;
import ECutb.Omar.ali.thymeleafwithdatabase.entity.AppUser;
import ECutb.Omar.ali.thymeleafwithdatabase.service.AppUserService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

public class AppUserController {
    private AppUserRepository appUserRepository;
    private AppUserService appUserService;

    public AppUserController(AppUserRepository appUserRepository, AppUserService appUserService) {
        this.appUserRepository = appUserRepository;
        this.appUserService = appUserService;
    }
    @GetMapping("users/register/form")
    public String getForm(Model model){
        model.addAttribute("form", new CreateAppUserForm());
        return "user-form";
    }

    @PostMapping("users/register/process")
    public String formProcess(@Valid @ModelAttribute("form") CreateAppUserForm form, BindingResult bindingResult){
        if (appUserRepository.findByEmailIgnoreCase(form.getEmail()).isPresent()){
            FieldError error = new FieldError("form", "email", "Email is already in use");
            bindingResult.addError(error);
        }
        if (!form.getPassword().equalsIgnoreCase(form.getPasswordConfirm())){
            FieldError error = new FieldError("form", "passwordConfirm", "Your confirm did'nt match password");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()){
            return "user-form";
        }
        AppUser user = appUserService.registerAppUser(form.getFirstName(), form.getLastName(), form.getEmail(), form.getPassword(), LocalDate.now(), form.isAdmin());
        if (form.isAdmin()){
            return "redirect:/"+ user.getUserId();
        }else {
            return "redirect:/";
        }
    }

    @GetMapping("users/{id}")
    public String getUserView(@PathVariable(name = "id") int id, Model model){
        AppUser appUser = appUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("user-view");
        return "user-view";
    }
    @GetMapping("users/{id}/update")
    public String getUpdateForm(@PathVariable(name = "id")int id, Model model){
        UpdateAppUserForm appUserForm = new UpdateAppUserForm();
        AppUser appUser = appUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        appUserForm.setEmail(appUser.getEmail());
        appUserForm.setFirstName(appUser.getFirstName());
        appUserForm.setLastName(appUser.getLastName());
        appUserForm.setUserId(appUser.getUserId());
        model.addAttribute("form", appUserForm);

        return "update-form";
    }

    @PostMapping("users/{id}/update")
    public String processUpdate(
            @PathVariable("id") int id,
            @Valid @ModelAttribute("form") UpdateAppUserForm form,
            BindingResult bindingResult){
        AppUser original = appUserRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        Optional<AppUser> optional = appUserRepository.findByEmailIgnoreCase(form.getEmail());
        if (optional.isPresent() && !form.getEmail().equalsIgnoreCase(original.getEmail())){
            FieldError error = new FieldError("form", "email", "Email is already in use");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()){
            return "update-form";
        }
        original.setEmail(form.getEmail());
        original.setFirstName(form.getFirstName());
        original.setLastName(form.getLastName());
        appUserRepository.save(original);
        return "redirect:/"+original.getUserId();
    }
        @GetMapping("login")
    public String getLoginForm(){
        return "login-form";
        }
    }