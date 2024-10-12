package com.example.journalApp.service;

import com.example.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
//                Arguments.of(User.builder().username("Garv").password("Garv").build(),
                Arguments.of(User.builder().username("f").password("f").build()),
                Arguments.of(User.builder().username("g").password("g").build()),
                Arguments.of(User.builder().username("h").password("h").build())

       );
    }
}
