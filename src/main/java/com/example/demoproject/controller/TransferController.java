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

import static com.example.demoproject.security.Cryptography.sha256;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private TransferRepository repository;

    @Autowired
    private EntityManager entityManager;

    @GetMapping
    public ResponseEntity getAllTransfer(){
        var allTransfer = repository.findAll();
        return ResponseEntity.ok(allTransfer);
    }

    @GetMapping("/search")
    public ResponseEntity getAllTransferByFilter(@RequestParam(name = "id") String id) {
        var filteredTransfer = repository.findById(id);
        return ResponseEntity.ok(filteredTransfer);
    }

    @PostMapping("/create")
    public ResponseEntity newTransfer(@RequestBody @Valid RequestTransfer data) throws Exception {
        Transfer newTransfer = new Transfer(data);
        Transfer responseTransfer = repository.save(newTransfer);

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
        transferAlter.setUserDocument(sha256(data.userDocument()));
        transferAlter.setCreditCardToken(sha256(data.creditCardToken()));
        transferAlter.setTransferValue(data.transferValue());

        entityManager.merge(transferAlter);
        return ResponseEntity.ok("Ok");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTransfer(@RequestParam(name = "id") String id){
        repository.deleteById(id);
        return ResponseEntity.ok("Ok");
    }

}
