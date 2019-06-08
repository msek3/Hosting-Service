package siwz.controller;

import siwz.exception.FileNotExistingException;
import siwz.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class HomeController {

    private FileService fileService;

    @Autowired
    public HomeController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public String home(){
        return "home";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<String> sendFile(@RequestBody MultipartFile file, Principal principal){
        Long id = fileService.save(file, principal.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.ACCEPT, "accepted")
                .body(id.toString());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Resource> downloadFile(@PathVariable String id) {
        Resource file = fileService.loadFileById(Long.valueOf(id));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @ExceptionHandler(FileNotExistingException.class)
    public ResponseEntity<String> fileNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("file doesnt exists");
    }

}
