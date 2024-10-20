package camilo.opertationquasarfire.controllers;

import org.springframework.web.bind.annotation.RestController;
import camilo.opertationquasarfire.entities.UserEntity;
import camilo.opertationquasarfire.requests.CreateUserDTO;
import camilo.opertationquasarfire.services.UserService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserEntity> postCreateUser(@Valid @RequestBody CreateUserDTO request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(this.userService.createUser(request));

    }

    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam String id) {
            return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body(this.userService.deleteUserById(id));

        
    }
    @GetMapping("/helloSecured")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<String> helloSecured() {
        return ResponseEntity
            .status(HttpStatus.ACCEPTED)
            .body("Hello Secured");
    }
    
    
}
