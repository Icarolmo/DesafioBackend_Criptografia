package com.example.demoproject.controller;

import com.example.demoproject.domain.RequestTransfer;
import com.example.demoproject.domain.Transfer;
import com.example.demoproject.repository.TransferRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferRepository repository;

    @Autowired
    private SecurityComponent passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity getAllTransfer(){
        var allTransfer = repository.findAll();

        allTransfer.forEach(transfer -> {
            transfer.setUserDocument(passwordEncoder.decrypt(transfer.getUserDocument()));
            transfer.setCreditCardToken(passwordEncoder.decrypt(transfer.getCreditCardToken()));
        });

        return ResponseEntity.ok(allTransfer);
    }

    @GetMapping("/search")
    public ResponseEntity getAllTransferByFilter(@RequestParam(name = "id") String id) {
        var filteredTransfer = repository.findById(id);

        filteredTransfer.ifPresent(transfer -> {
            transfer.setUserDocument(passwordEncoder.decrypt(transfer.getUserDocument()));
            transfer.setCreditCardToken(passwordEncoder.decrypt(transfer.getCreditCardToken()));
        });

        return ResponseEntity.ok(filteredTransfer);
    }

    @PostMapping("/create")
    public ResponseEntity newTransfer(@RequestBody @Valid RequestTransfer data) throws Exception {

        String encryptedUserDocument = passwordEncoder.encode(data.userDocument());
        String encryptedCreditCardToken = passwordEncoder.encode(data.creditCardToken());

        Transfer newTransfer = new Transfer(encryptedUserDocument, encryptedCreditCardToken, data.transferValue());

        Transfer responseTransfer = repository.save(newTransfer);

        responseTransfer.setUserDocument(data.userDocument());
        responseTransfer.setCreditCardToken(data.creditCardToken());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(responseTransfer.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseTransfer);
    }

    @Transactional
    @PutMapping("/update")
    public ResponseEntity updateTransfer(@RequestParam(name = "id") String id, @RequestBody @Valid RequestTransfer data) throws Exception {
        Transfer transferAlter = repository.getReferenceById(id);

        transferAlter.setUserDocument(passwordEncoder.encode(data.userDocument()));
        transferAlter.setCreditCardToken(passwordEncoder.encode(data.creditCardToken()));
        transferAlter.setTransferValue(data.transferValue());

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(transferAlter.getId())
                .toUri();

        entityManager.merge(transferAlter);
        return ResponseEntity.created(uri).body(transferAlter);
    }

    @Transactional
    @DeleteMapping("/delete")
    public ResponseEntity deleteTransfer(@RequestParam(name = "id") String id){
        var transferDeleted = repository.findById(id);
        repository.deleteById(id);
        return ResponseEntity.ok().body(transferDeleted);
    }

}
