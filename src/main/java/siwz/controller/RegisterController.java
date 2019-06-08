package siwz.controller;

import siwz.model.AppUser;
import siwz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String register(){
        return "register";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody AppUser user, HttpServletRequest request){
        if(userService.getUserByUsernameOrEmail(user.getName(), user.getEmail()) == null){
            String passwordToLogin = new String(user.getPassword());
            userService.saveUser(user);
            login(request, user.getName(), passwordToLogin);
            return ResponseEntity.ok("created");
        }
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
                .body("username or email already exists");
    }

    private void login(HttpServletRequest request,String username, String password){
        try {
            request.login(username, password);
        } catch (ServletException e){

        }
    }
}
