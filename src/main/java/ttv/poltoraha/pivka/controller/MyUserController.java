package ttv.poltoraha.pivka.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttv.poltoraha.pivka.entity.MyUser;
import ttv.poltoraha.pivka.repository.MyUserRepository;
import ttv.poltoraha.pivka.service.MyUserPasswordService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class MyUserController {
    private final MyUserPasswordService myUserPasswordService;
    private final MyUserRepository myUserRepository;

    @PostMapping("/passwords")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        MyUser myUser = myUserRepository.findById(user.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        myUserPasswordService.changePassword(newPassword, myUser);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("redirectUrl", "/");

        return ResponseEntity.ok(response);
    }
}
