package ru.savostikov.advent.solvers.years.fifteen.day4;

import ru.savostikov.advent.enumerations.Day;
import ru.savostikov.advent.enumerations.Year;
import ru.savostikov.advent.meta.AdventPuzzle;
import ru.savostikov.advent.solvers.PuzzleSolver;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@AdventPuzzle(year = Year.TWO_K_FIFTEEN, day = Day.FOUR, skip = true)
public class TheIdealStockingStuffer {

    public static class Part1 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            int result = 0;
            MessageDigest md5 = getMD5Instance();
            String md5Hash = TheIdealStockingStuffer.toHex(calculateMD5Hash(input + result++, md5));

            while (!md5Hash.startsWith("00000")) {
                md5Hash = TheIdealStockingStuffer.toHex(calculateMD5Hash(input + ++result, md5));
            }

            return result;
        }
    }

    public static class Part2 implements PuzzleSolver {

        @Override
        public long solve(String input) {
            int result = 0;
            MessageDigest md5 = getMD5Instance();
            String md5Hash = TheIdealStockingStuffer.toHex(calculateMD5Hash(input + result++, md5));

            while (!md5Hash.startsWith("000000")) {
                md5Hash = TheIdealStockingStuffer.toHex(calculateMD5Hash(input + ++result, md5));
            }

            return result;
        }
    }

    private static MessageDigest getMD5Instance() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] calculateMD5Hash(String input, MessageDigest md5) {
        md5.reset();
        md5.update(input.getBytes());
        return md5.digest();
    }

    private static String toHex(byte[] bytes) {
        var builder = new StringBuilder();
        for (var b : bytes) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return builder.toString();
    }
}
