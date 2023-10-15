package com.example.demoproject.controller;

import com.example.demoproject.domain.RequestUser;
import com.example.demoproject.domain.User;
import com.example.demoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    public ResponseEntity getAllUsers(){
        var allUserTransfer = repository.findAll();
        return ResponseEntity.ok(allUserTransfer);
    }

    @PostMapping
    public ResponseEntity newTransfer(@RequestBody @Valid RequestUser data) throws Exception {
        User newUserTransfer = new User(data);
        repository.save(newUserTransfer);
        return ResponseEntity.ok().build();
    }

}
