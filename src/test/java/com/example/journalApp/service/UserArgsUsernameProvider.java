package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgsUsernameProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(


                Arguments.of("Garv"),
                Arguments.of("Aman"),
                Arguments.of("Ini"),
                Arguments.of("Ayushi")

        );
    }
}
