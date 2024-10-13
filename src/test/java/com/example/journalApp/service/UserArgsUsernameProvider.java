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


                Arguments.of("e"),
                Arguments.of("c"),
                Arguments.of("g"),
                Arguments.of("b"),
                Arguments.of("f"),
                Arguments.of("h")

        );
    }
}
