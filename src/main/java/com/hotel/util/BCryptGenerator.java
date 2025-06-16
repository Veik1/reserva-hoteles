package com.hotel.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("admin:   " + encoder.encode("admin"));
        System.out.println("usuario: " + encoder.encode("1234"));
    }
}