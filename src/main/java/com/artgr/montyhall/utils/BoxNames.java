package com.artgr.montyhall.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum BoxNames {

    BOX_1("box_1"),
    BOX_2("box_2"),
    BOX_3("box_3");

    private final String name;
    public static final List<String> ALL_BOXES = new ArrayList<>();

    static {
        ALL_BOXES.addAll(Arrays.asList(BOX_1.getName(), BOX_2.getName(), BOX_3.getName()));
    }
}
