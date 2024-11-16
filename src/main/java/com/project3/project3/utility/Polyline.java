package com.project3.project3.utility;

import java.util.List;

public class Polyline {

    public static String encode(List<List<Double>> coordinates) {
        StringBuilder encodedString = new StringBuilder();
        int prevLat = 0;
        int prevLng = 0;

        for (List<Double> point : coordinates) {
            int lat = (int) Math.round(point.get(0) * 1e5);
            int lng = (int) Math.round(point.get(1) * 1e5);

            encodedString.append(encodeValue(lat - prevLat));
            encodedString.append(encodeValue(lng - prevLng));

            prevLat = lat;
            prevLng = lng;
        }

        return encodedString.toString();
    }

    private static String encodeValue(int value) {
        value = value < 0 ? ~(value << 1) : (value << 1);
        StringBuilder encoded = new StringBuilder();
        while (value >= 0x20) {
            encoded.append((char) ((0x20 | (value & 0x1f)) + 63));
            value >>= 5;
        }
        encoded.append((char) (value + 63));
        return encoded.toString();
    }
}

