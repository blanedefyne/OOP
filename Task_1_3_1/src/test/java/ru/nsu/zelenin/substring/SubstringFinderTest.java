package ru.nsu.zelenin.substring;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubstringFinderTest {

    @Test
    public void findRepeatingString() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(2);
        result.add(0L);
        result.add(1L);
        result.add(2L);
        result.add(3L);
        assertEquals(result, finder.find("aaaaa.txt", "aa"));
    }

    @Test
    public void findUnicodeSymbols() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(1);
        result.add(6L);
        assertEquals(result, finder.find("japanese.txt","ていま"));
    }

    @Test
    public void findSubstringOnBufferBounds() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(10);
        result.add(6L);
        result.add(108L);
        assertEquals(result, finder.find("lyrics.txt", "police"));
    }

    @Test
    public void findChessSubstring() {
        List<Long> result = new ArrayList<>();
        SubstringFinder finder = new SubstringFinder(10);
        result.add(22L);
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


}