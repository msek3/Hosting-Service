package siwz.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String encodePassword(String password){
        return encoder.encode(password);
    }
}