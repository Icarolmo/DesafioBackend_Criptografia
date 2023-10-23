package com.example.demoproject.dto;

import com.example.demoproject.domain.RequestTransfer;
import com.example.demoproject.domain.Transfer;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {

    private long id;

    private String userDocument;

    private String creditCardToken;

    private double transferValue;

    public TransferDTO(Transfer data){
        this.id = data.getId();
        this.userDocument = data.getUserDocument();
        this.creditCardToken = data.getCreditCardToken();
        this.transferValue = data.getTransferValue();
    }

    public TransferDTO(long id,RequestTransfer data){
        this.id = id;
        this.userDocument = data.userDocument();
        this.creditCardToken = data.creditCardToken();
        this.transferValue = data.transferValue();
    }

}
