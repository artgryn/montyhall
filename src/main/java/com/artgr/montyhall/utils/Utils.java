package com.artgr.montyhall.utils;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@UtilityClass
public class Utils {

    public static String getRandomBox(final List<String> boxes) {
        val random = new Random();
        return boxes.get(random.nextInt(boxes.size()));
    }

    public static List<String> getNotUsedBoxes(final Collection usedBoxes) {
        return BoxNames.ALL_BOXES.stream()
                                 .filter(element -> !usedBoxes.contains(element))
                                 .collect(Collectors.toList());
    }
}
