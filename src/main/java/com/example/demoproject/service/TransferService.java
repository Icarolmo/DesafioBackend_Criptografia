package com.example.demoproject.service;

import com.example.demoproject.domain.RequestTransfer;
import com.example.demoproject.domain.Transfer;
import com.example.demoproject.dto.TransferDTO;
import com.example.demoproject.repository.TransferRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private TransferRepository repository;

    @Autowired
    private SecurityComponent passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    public List<Transfer> allTransfer() {
        var allTransfer = repository.findAll();

        allTransfer.forEach(transfer -> {
            transfer.setUserDocument(passwordEncoder.decrypt(transfer.getUserDocument()));
            transfer.setCreditCardToken(passwordEncoder.decrypt(transfer.getCreditCardToken()));
        });
        return allTransfer;
    }


    public TransferDTO transferById(Long id) {
        Optional<Transfer> filteredTransfer = repository.findById(id);
        if(filteredTransfer.isPresent()) {
            return new TransferDTO(
                    filteredTransfer.get().getId(),
                    passwordEncoder.decrypt(filteredTransfer.get().getUserDocument()),
                    passwordEncoder.decrypt(filteredTransfer.get().getCreditCardToken()),
                    filteredTransfer.get().getTransferValue()
            );
        }
        return null;
    }

    public URI createURI(long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("")
                .buildAndExpand(id)
                .toUri();
    }

    public TransferDTO newTransfer(RequestTransfer data) {

        String encryptedUserDocument = passwordEncoder.encode(data.userDocument());
        String encryptedCreditCardToken = passwordEncoder.encode(data.creditCardToken());

        Transfer newTransfer = new Transfer(encryptedUserDocument, encryptedCreditCardToken, data.transferValue());

        long idNewTransfer = repository.save(newTransfer).getId();

        return new TransferDTO(idNewTransfer, data.userDocument(), data.creditCardToken(), data.transferValue());
    }

    public TransferDTO updateTransfer(Long id, RequestTransfer data) {
        Transfer transferAlter = repository.getReferenceById(id);

        transferAlter.setUserDocument(passwordEncoder.encode(data.userDocument()));
        transferAlter.setCreditCardToken(passwordEncoder.encode(data.creditCardToken()));
        transferAlter.setTransferValue(data.transferValue());

        entityManager.merge(transferAlter);

        return new TransferDTO(id, data);
    }

    public TransferDTO deleteTransfer(Long id) {
        Optional<Transfer> transferDeleted = repository.findById(id);
        if(transferDeleted.isPresent()){
            repository.deleteById(id);
            return new TransferDTO(
                    transferDeleted.get().getId(),
                    passwordEncoder.decrypt(transferDeleted.get().getUserDocument()),
                    passwordEncoder.decrypt(transferDeleted.get().getCreditCardToken()),
                    transferDeleted.get().getTransferValue());
        }
        return null;
    }

}
