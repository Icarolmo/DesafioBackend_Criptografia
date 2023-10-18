package com.example.demoproject.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "user_transfer")
@Entity(name = "transfer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transfer {

    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userdocument")
    private String userDocument;

    @Column(name = "creditcardtoken")
    private String creditCardToken;

    @Column(name = "transfervalue")
    private double transferValue;

    public Transfer(String userDocEncode, String creditCardTokenEncode, double transferValueData){
        this.userDocument = userDocEncode;
        this.creditCardToken = creditCardTokenEncode;
        this.transferValue = transferValueData;
    }
}
