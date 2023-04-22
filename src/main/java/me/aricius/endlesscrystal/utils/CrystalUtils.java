package me.aricius.endlesscrystal.utils;

import java.text.NumberFormat;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.regex.Pattern;

public final class CrystalUtils {

    private static NumberFormat formatter = NumberFormat.getInstance();
    private static String decimal;
    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    public static String formatPoints(long points) {
        if (formatter != null) {
            return formatter.format(points);
        } else {
            return String.valueOf(points);
        }
    }

    public static char getDecimalSeparator() {
        if (decimal == null || decimal.trim().isEmpty())
            return '.';
        return decimal.charAt(0);
    }

    public static String formatPointsShorthand(long points) {
        if (points == Long.MIN_VALUE) return formatPointsShorthand(Long.MIN_VALUE + 1);
        if (points < 0) return "-" + formatPointsShorthand(-points);
        if (points < 1000) return Long.toString(points);

        Map.Entry<Long, String> entry = suffixes.floorEntry(points);
        Long divideBy = entry.getKey();
        String suffix = entry.getValue();

        long truncated = points / (divideBy / 10);
        return ((truncated / 10D) + suffix).replaceFirst(Pattern.quote("."), getDecimalSeparator() + "");
    }
}