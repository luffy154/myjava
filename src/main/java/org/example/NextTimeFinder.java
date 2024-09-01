package org.example;

import java.util.*;

    /*警察在侦破一个案件时，得到了线人给出的可能犯罪时间，形如 “HH:MM” 表示的时刻。

    根据警察和线人的约定，为了隐蔽，该时间是修改过的，解密规则为：利用当前出现过的数字，
    构造下一个距离当前时间最近的时刻，则该时间为可能的犯罪时间。每个出现数字都可以被无限次使用。*/

public class NextTimeFinder {

    public static String findNextTime(String currentTime) {
        // Parse current time
        int currentHour = Integer.parseInt(currentTime.substring(0, 2));
        int currentMinute = Integer.parseInt(currentTime.substring(3, 5));

        // Get the digits from the current time
        Set<Character> digits = new HashSet<>();
        for (char c : currentTime.toCharArray()) {
            if (Character.isDigit(c)) {
                digits.add(c);
            }
        }

        // Generate possible times and find the next closest time
        String nextTime = null;
        int minDifference = Integer.MAX_VALUE;

        for (int h1 : getValidDigits(digits)) {
            for (int h2 : getValidDigits(digits)) {
                for (int m1 : getValidDigits(digits)) {
                    for (int m2 : getValidDigits(digits)) {
                        int hour = h1 * 10 + h2;
                        int minute = m1 * 10 + m2;

                        if (hour < 24 && minute < 60) {
                            String time = String.format("%02d:%02d", hour, minute);
                            int diff = getTimeDifference(currentHour, currentMinute, hour, minute);

                            if (diff > 0 && (diff < minDifference || nextTime == null)) {
                                nextTime = time;
                                minDifference = diff;
                            }
                        }
                    }
                }
            }
        }

        return nextTime != null ? nextTime : "No valid time";
    }

    private static List<Integer> getValidDigits(Set<Character> digits) {
        List<Integer> validDigits = new ArrayList<>();
        for (char c : digits) {
            validDigits.add(c - '0');
        }
        return validDigits;
    }

    private static int getTimeDifference(int currentHour, int currentMinute, int hour, int minute) {
        int currentMinutes = currentHour * 60 + currentMinute;
        int newMinutes = hour * 60 + minute;
        return (newMinutes - currentMinutes + 1440) % 1440; // Minutes in a day
    }

    public static void main(String[] args) {
        String currentTime = "12:34";
        System.out.println("Next time: " + findNextTime(currentTime));
    }
}
