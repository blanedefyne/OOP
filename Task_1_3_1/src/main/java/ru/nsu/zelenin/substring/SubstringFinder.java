package ru.nsu.zelenin.substring;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class for finding substring in a file.
 * Reads file by parts (buffers)
 * (minimal length of buffer == length of substring)
 * and unite current buffer and storedBuffer (previously read)
 * find indexes in that buffer
 * the current buffer becomes storedBuffer
 */

public class SubstringFinder {
    /**
     * bufLen - length of our buffer
     * buffer - the buffer itself
     * storedBuffer - second buffer, we will use both (concatenated)
     * readCount - how many symbols have we read by this iteration
     * readBuffers - how many buffers have we already read
     * (we can get number of symbols like : bufLen * readBuffers)
     * stIndex - start index of our substring entry
     * subPointer - index for our substring
     * inSubString - just boolean flag
     * i - "global index" - how many symbols we read at all
     */
    private int bufLen;
    private char[] buffer;
    private char[] storedBuffer;
    private int readCount;
    private long readBuffers = 0;
    private long stIndex = 0;
    private int subPointer = 0;
    private boolean inSubString = false;
    private long i = 0;

    /**
     * Class constructor.
     * we can assign our own length of buffer
     *
     * @param bufLen - length of our buffer
     */
    public SubstringFinder(int bufLen) {
        this.bufLen = bufLen;
        this.storedBuffer = new char[0];
    }

    /**
     * Method sets new length of our buffer.
     *
     * @param newLen - new length of our buffer
     */
    public void setBufLen(int newLen) {
        this.bufLen = newLen;
        this.readBuffers = 0;
        this.i = 0;
        this.inSubString = false;
        this.stIndex = 0;
        this.subPointer = 0;
    }

    /**
     * Method finds all the entries of substring in some string.
     * (in the beginning we convert substring into byte array
     * and then into string with UTF-8 encoding;
     * now we work this string)
     *
     * @param filename - name of some file in resources directory
     * @param subStr - substring itself
     * @return res - ArrayList of all indexes of substrings
     */
    public List<Long> find(String filename, String subStr) {
        try (InputStream is =
                     SubstringFinder.class.getClassLoader().getResourceAsStream(filename);
             InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(is),
                     StandardCharsets.UTF_8)
        ) {

            byte[] arr = subStr.getBytes();
            String subString = new String(arr, StandardCharsets.UTF_8);

            List<Long> res = new ArrayList<>();
            if (bufLen < subString.length()) {
                bufLen = subString.length();
            }
            buffer = new char[bufLen];
            try {
                while ((readCount = reader.read(buffer)) != -1) {
                    findAnswer(subString, res);
                    readBuffers++;
                }
                return res;
            } catch (OutOfMemoryError e) {
                return res;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method-helper - performs a substring-search.
     * Was made just to simplify the program looks
     *
     * @param subString - our substring
     * @param res - ArrayList of all indexes
     */
    public void findAnswer(String subString, List<Long> res) {
        if (readCount < bufLen) {
            buffer = Arrays.copyOf(buffer, readCount);
        }
        char[] workingBuffer = (new String(storedBuffer) + new String(buffer)).toCharArray();
        int workLen = workingBuffer.length;
        while (i - bufLen * (readBuffers - 1) < workLen) {
            if (workingBuffer[(int) (i - bufLen * (readBuffers - 1))]
                    == subString.charAt(subPointer++)) {
                if (!inSubString) {
                    stIndex = i;
                    inSubString = true;
                }
                if (subPointer == subString.length()) {
                    res.add(stIndex);
                    subPointer = 0;
                    inSubString = false;
                    i = stIndex;
                }
            } else {
                if (inSubString) {
                    inSubString = false;
                    i = stIndex;
                }
                subPointer = 0;
            }
            i++;
        }
        storedBuffer = Arrays.copyOf(buffer, bufLen);
    }

}
