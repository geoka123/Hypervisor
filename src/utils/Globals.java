package utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class Globals {
    public static enum OS {
        WINDOWS,
        FEDORA,
        UBUNTU
    }

    public static int computeDuration(Date from, Date to) {
        LocalDateTime start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Duration duration = Duration.between(start, end);
        int diffIn = (int) duration.getSeconds();

        return diffIn;
    }

    public static boolean checkOs(String vmOs) {
        try {
            OS.valueOf(vmOs.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("this is not a valid OS");
            return false;
        }
    }

    public static boolean containsOnlyNumbers(String str) {
        String pattern = "[0-9]+";

        if (Pattern.matches(pattern, str))
            return true;
        else
            return false;
    }

    public static <T extends Comparable<T>> void sort(ArrayList<T> a) {
        int pass = 1;
        boolean exchanges;
        do {
            exchanges = false;
            for (int i = 0; i < a.size() - pass; i++) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    T tmp = a.get(i);
                    a.set(i, a.get(i + 1));
                    a.set(i + 1, tmp);
                    exchanges = true;
                }
            }
            pass++;
        } while (exchanges);
    }
}
