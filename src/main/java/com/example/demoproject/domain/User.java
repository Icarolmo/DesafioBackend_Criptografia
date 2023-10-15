package com.example.demoproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.service.annotation.GetExchange;

import static com.example.demoproject.security.Cryptography.sha256;

@Table(name = "user_transfer")
@Entity(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {

    @Column(name = "id") @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "userdocument")
    private String userDocument;

    @Column(name = "creditcardtoken")
    private String creditCardToken;

    @Column(name = "transfervalue")
    private double transferValue;

    public User(RequestUser data) throws Exception {
        this.userDocument = sha256(data.userDocument());
        this.creditCardToken = sha256(data.creditCardToken());
        this.transferValue = data.transferValue();
    }

}
