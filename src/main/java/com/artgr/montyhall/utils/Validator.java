package com.artgr.montyhall.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Validator {

    public void validateBox(final String boxName) {

        if (boxName == null || !BoxNames.ALL_BOXES.contains(boxName.toLowerCase())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid box name");
        }
    }
}
