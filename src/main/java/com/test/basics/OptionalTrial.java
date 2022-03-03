package com.test.basics;

import java.util.Optional;

public class OptionalTrial {

    public static void main(String[] args) {
        getString().ifPresent(System.out::println);
    }

    private static Optional<String> getString() {
        return Optional.ofNullable("Abc");
    }
}
