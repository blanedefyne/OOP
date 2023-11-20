package ru.nsu.zelenin.substring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


class SubstringFinderTest {

    @Test
    public void findRepeatingString() {
        List<Long> result = new ArrayList<>();
        result.add(0L);
        result.add(1L);
        result.add(2L);
        result.add(3L);
        SubstringFinder finder = new SubstringFinder(2);
        assertEquals(result, finder.find("aaaaa.txt", "aa"));
    }

    @Test
    public void findUnicodeSymbols() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(1);
        result.add(6L);
        assertEquals(result, finder.find("japanese.txt", "ていま"));
    }

    @Test
    public void findSubstringOnBufferBounds() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(10);
        result.add(6L);
        result.add(105L);
        assertEquals(result, finder.find("lyrics.txt", "police"));
    }

    @Test
    public void findChessSubstring() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(10);
        result.add(19L);
        assertEquals(result, finder.find("chess.txt", "♛♜♜"));
    }

    @Test
    public void findSomePlants() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(10);
        result.add(2L);
        assertEquals(result, finder.find("plants.txt", "❀ ❦❀"));
    }

    @Test
    public void checkDifferentBufLen() {
        SubstringFinder finder = new SubstringFinder(5);
        List<Long> result1 = finder.find("lyrics.txt", "bed");
        finder.setBufLen(91);
        List<Long> result2 = finder.find("lyrics.txt", "bed");
        assertEquals(result1, result2);
    }

    @Test
    public void checkIfThereIsNoSubstring() {
        SubstringFinder finder = new SubstringFinder(21);
        assertEquals(new ArrayList<>(), finder.find("noSubstring.txt", "something"));
    }

    /**
     * 16106127360 == 15 gb in bytes.
     * + 78 bytes we added before cycle == 16106127438
     * by the end of cycle fileSizeInBytes == -2, so we added 2 extra bytes
     * 16106127438 + 2 == 16106127440
     * and then + substring.length (it's 13)
     * and + 13
     */
    @Test
    public void checkOn15Gb() {
        File bigText = new File(getClass()
                .getClassLoader()
                .getResource("")
                .getPath() + "/large.txt");;
        long fileSizeInBytes = 15L * 1024 * 1024 * 1024;
        SubstringFinder finder = new SubstringFinder(150);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bigText))) {
            String string = "american dragon jake long ";
            writer.write(string);
            writer.write("jessica jones");
            writer.write(string);
            writer.write("jessica jones");

            while (fileSizeInBytes > 0) {

                writer.write(string);

                fileSizeInBytes -= string.length();
            }
            writer.write("jessica jones");
            writer.write("jessica jones");
            writer.write("jessica jones");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Long> res = finder.find("large.txt", "jessica jones");
        bigText.delete();

        List<Long> expected = new ArrayList<>();
        expected.add(26L);
        expected.add(65L);
        expected.add(16106127440L);
        expected.add(16106127453L);
        expected.add(16106127466L);

        assertEquals(expected, res);
    }

}