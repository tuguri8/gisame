package me.gisa.api.common.utils;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class ShortUrlBuilder {
    public String shorten(String url, int length) {
        String shorten = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            long md5 = new BigInteger(1, digest.digest()).longValue();
            String smd5 = convertTo62Base(Math.abs(md5));
            shorten = smd5.substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return shorten;
    }

    public String shorten(int length) {
        String shorten = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(UUID.randomUUID().toString().getBytes());
            long md5 = new BigInteger(1, digest.digest()).longValue();
            String smd5 = convertTo62Base(Math.abs(md5));
            shorten = smd5.substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return shorten;
    }

    private String convertTo62Base(long toBeConverted) {
        String[] elements = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "0", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
            "Y", "Z"
        };
        StringBuilder convertedString = new StringBuilder();
        int numOfDiffChars = elements.length;
        if (toBeConverted < numOfDiffChars + 1 && toBeConverted > 0) {
            convertedString.append(elements[(int) (toBeConverted - 1)]);
        } else if (toBeConverted > numOfDiffChars) {
            long mod = 0;
            long multiplier = 0;
            boolean determinedTheLength = false;
            for (int j = 20; j >= 0; j--) {
                multiplier = (long) (toBeConverted / Math.pow(numOfDiffChars, j));
                if (multiplier > 0 && toBeConverted >= numOfDiffChars) {
                    convertedString.append(elements[(int) multiplier]);
                    determinedTheLength = true;
                } else if (determinedTheLength && multiplier == 0) {
                    convertedString.append(elements[0]);
                } else if (toBeConverted < numOfDiffChars) {
                    convertedString.append(elements[(int) mod]);
                }
                mod = (long) (toBeConverted % Math.pow(numOfDiffChars, j));
                toBeConverted = mod;
            }
        }
        return convertedString.toString() + convertedString.toString();
    }
}
