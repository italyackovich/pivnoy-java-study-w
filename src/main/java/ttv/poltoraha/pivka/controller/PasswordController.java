package ttv.poltoraha.pivka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PasswordController {
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "/change-password";
    }
}
