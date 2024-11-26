package com.project3.project3.utility;

import java.util.List;
import java.util.Random;

public class DefaultImageUtil {

    private static final List<String> DEFAULT_IMAGES = List.of(
            "default-trail-pictures/cliff.jpeg",
            "default-trail-pictures/desert.jpeg",
            "default-trail-pictures/flowers.jpeg",
            "default-trail-pictures/landscape.jpeg",
            "default-trail-pictures/mountains.jpeg",
            "default-trail-pictures/orange.jpeg",
            "default-trail-pictures/stream.jpeg",
            "default-trail-pictures/sunrise.jpeg",
            "default-trail-pictures/sunset.jpeg"
    );

    private static final Random RANDOM = new Random();

    private DefaultImageUtil() {
        // Prevent instantiation
    }

    public static String getRandomDefaultImage() {
        return DEFAULT_IMAGES.get(RANDOM.nextInt(DEFAULT_IMAGES.size()));
    }
}


