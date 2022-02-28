package ECutb.Omar.ali.thymeleafwithdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String home(){
        return "index";
    }
}
