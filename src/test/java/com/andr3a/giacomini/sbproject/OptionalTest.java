package com.andr3a.giacomini.sbproject;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Optional;

public class OptionalTest {
    @Test
    public void givenEmptyString_whenFilteringOnOptional_theEmptyOptionalIsReturned(){
        String str = "prova";
        Optional<String> opt = Optional.ofNullable(str).filter(s -> !s.isEmpty());
        Assert.isTrue(opt.isPresent(), "prova");
    }
}
