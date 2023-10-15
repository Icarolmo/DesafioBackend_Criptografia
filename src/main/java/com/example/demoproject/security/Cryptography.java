package com.example.demoproject.security;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Cryptography {
    public static String sha256(String message) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA256");
        md.reset();
        md.update(message.getBytes("utf8"));
        return String.format("%064x", new BigInteger(1, md.digest()));
    }
}
