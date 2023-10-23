package com.example.demoproject.controller;

import com.example.demoproject.domain.RequestTransfer;
import com.example.demoproject.dto.TransferDTO;
import com.example.demoproject.service.TransferService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferService service;

    @GetMapping("/all")
    public ResponseEntity getAllTransfer(){
        try{
            return ResponseEntity.ok().body(service.allTransfer());
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity getTranferById(@RequestParam(name = "id") String id) {
        try{
            TransferDTO response = service.transferById(Long.parseLong(id));
            if(response != null){
                return ResponseEntity.ok().body(response);
            }
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity newTransfer(@RequestBody @Valid RequestTransfer data){
        try{
            TransferDTO responseBody = service.newTransfer(data);
            URI uriCreate = service.createURI(responseBody.getId());
            return ResponseEntity.created(uriCreate).body(responseBody);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e);
        }
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity updateTransfer(@RequestParam(name = "id") String id, @RequestBody @Valid RequestTransfer data) {
        try{
            TransferDTO responseBody = service.updateTransfer(Long.parseLong(id), data);
            return ResponseEntity.created(service.createURI(responseBody.getId())).body(responseBody);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e);
        }
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity deleteTransfer(@RequestParam(name = "id") String id){
        try{
            TransferDTO responseBody = service.deleteTransfer(Long.parseLong(id));
            if(responseBody == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(responseBody);
        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e);
        }
    }

}
