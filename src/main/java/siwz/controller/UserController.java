package siwz.controller;

import siwz.model.AppUser;
import siwz.service.FileService;
import siwz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/userinfo")
public class UserController {

    private UserService userService;
    private FileService fileService;

    @Autowired
    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public String userinfo(){
        return "userinfo";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/details")
    public @ResponseBody AppUser getUserDetails(Principal principal){
        AppUser user = userService.getUserByUsername(principal.getName());
        user.setFiles(fileService.getFilesByUser(user));
        return user;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("isAdmin")
    public ResponseEntity isAdmin(Principal principal){
        AppUser user = userService.getUserByUsername(principal.getName());
        boolean admin =
                user.getRoles()
                    .stream()
                    .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
        if (admin) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(admin);
        }
    }
}
