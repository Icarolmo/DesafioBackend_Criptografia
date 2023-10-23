package com.example.demoproject.domain;

import jakarta.validation.constraints.NotNull;

public record RequestTransfer(
        @NotNull
        String userDocument,
        @NotNull
        String creditCardToken,
        @NotNull
        double transferValue){
}
