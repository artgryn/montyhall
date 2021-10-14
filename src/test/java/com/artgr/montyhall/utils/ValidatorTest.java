package com.artgr.montyhall.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class ValidatorTest {

    @Autowired
    private Validator validator;

    @Test
    public void testValidateBoxName_invalidInput() {

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            validator.validateBox("box");
        });

        HttpStatus status = ((ResponseStatusException)exception).getStatus();
        assertEquals(status, HttpStatus.BAD_REQUEST);

    }
    @Test
    public void testValidateBoxName_nullInput() {

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            validator.validateBox(null);
        });

        HttpStatus status = ((ResponseStatusException)exception).getStatus();
        assertEquals(status, HttpStatus.BAD_REQUEST);

    }

    @Test
    public void testValidateBoxName_ok() {
        validator.validateBox(BoxNames.BOX_2.getName());
    }
}
