package uk.me.paulriley.railwaychildren;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import static org.junit.Assert.*;

public class FrequencyScannerTest {
    private FrequencyScanner sut;

    @Before
    public void setUp() throws Exception {
        sut = new FrequencyScanner();
    }

    @After
    public void tearDown() throws Exception {
        sut = null;
    }

    @Test
    public void whenASingleWordNotAlreadyStoredIsSubmitted_thenEnsureThatItIsStoredAndItsCountIsSetToOne() throws Exception {
        String word = "testing";

        sut.scanLine(word);

        assertTrue(sut.wordList.containsKey(word.toLowerCase(Locale.getDefault())));
        assertEquals(1, (int) sut.wordList.get(word.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASingleWordAlreadyStoredIsSubmitted_thenEnsureThatTheNumberOfTimesThatWordHasBeenCountedIsIncreasedByOne() throws Exception {
        String word = "testing";

        sut.wordList.put(word, 1);
        sut.scanLine(word);

        assertTrue(sut.wordList.containsKey(word.toLowerCase(Locale.getDefault())));
        assertEquals(2, (int) sut.wordList.get(word.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASingleWordAlreadyStoredIsSubmittedWithDifferentCasing_thenEnsureThatTheNumberOfTimesThatWordHasBeenCountedIsIncreasedByOne() throws Exception {
        String word = "Testing";

        sut.wordList.put(word.toLowerCase(Locale.getDefault()), 1);
        sut.scanLine(word);

        assertTrue(sut.wordList.containsKey(word.toLowerCase(Locale.getDefault())));
        assertEquals(2, (int) sut.wordList.get(word.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASingleWordAlreadyStoredIsSubmittedWithPunctuation_thenEnsureThatTheNumberOfTimesThatWordHasBeenCountedIsIncreasedByOne() throws Exception {
        String word = "testing";
        String inputString = word + ",";

        sut.wordList.put(word, 1);
        sut.scanLine(inputString);

        assertTrue(sut.wordList.containsKey(word.toLowerCase(Locale.getDefault())));
        assertEquals(2, (int) sut.wordList.get(word.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASingleWordAlreadyStoredIsSubmittedWithAnEmbededApostrophe_thenEnsureThatTheNumberOfTimesThatWordHasBeenCountedIsIncreasedByOne() throws Exception {
        String word = "don't";

        sut.wordList.put(word, 1);
        sut.scanLine(word);

        assertTrue(sut.wordList.containsKey(word.toLowerCase(Locale.getDefault())));
        assertEquals(2, (int) sut.wordList.get(word.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASingleWordIsSubmittedWithAnEmbededDoubleHyphen_thenEnsureThatTheWordIsSplitOnTheHyphenAndBothWordsAreStored() throws Exception {
        String word1 = "world";
        String word2 = "it";
        String inputString = word1 + "--" + word2;

        sut.scanLine(inputString);

        assertTrue(sut.wordList.containsKey(word1.toLowerCase(Locale.getDefault())));
        assertEquals(1, (int) sut.wordList.get(word1.toLowerCase(Locale.getDefault())));
        assertTrue(sut.wordList.containsKey(word2.toLowerCase(Locale.getDefault())));
        assertEquals(1, (int) sut.wordList.get(word2.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASingleWordIsSubmittedWithAnEmbededSingleHyphen_thenEnsureThatTheWordIsNotSplitOnTheHyphenAndItIsStored() throws Exception {
        String word1 = "world";
        String word2 = "it";
        String inputString = word1 + "-" + word2;

        sut.scanLine(inputString);

        assertTrue(sut.wordList.containsKey(inputString.toLowerCase(Locale.getDefault())));
        assertEquals(1, (int) sut.wordList.get(inputString.toLowerCase(Locale.getDefault())));
        assertFalse(sut.wordList.containsKey(word1.toLowerCase(Locale.getDefault())));
        assertFalse(sut.wordList.containsKey(word2.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenASentenceContainingMultipleConsecutiveSpacesIsSubmitted_thenEnsureThatTheEmptyStringIsIgnored() throws Exception {
        String word = "don't";
        String inputString = "          " + word;

        sut.wordList.put(word, 1);
        sut.scanLine(inputString);

        assertTrue(sut.wordList.containsKey(word.toLowerCase(Locale.getDefault())));
        assertFalse(sut.wordList.containsKey(""));
        assertEquals(2, (int) sut.wordList.get(word.toLowerCase(Locale.getDefault())));
    }

    @Test
    public void whenAnEntryWithAPrimeValueIsPassedToTheDisplayPrimeMethod_thenReturnAStringThatSaysPrime() throws Exception {
        Map.Entry<String, Integer> inputString = new SimpleEntry<>("testing", 3);

        String returnValue = sut.displayPrime(inputString);

        assertEquals("prime", returnValue);
    }

    @Test
    public void whenAnEntryWithANonPrimeValueIsPassedToTheDisplayPrimeMethod_thenReturnAnEmptyString() throws Exception {
        Map.Entry<String, Integer> inputString = new SimpleEntry<>("testing", 4);

        String returnValue = sut.displayPrime(inputString);

        assertEquals("", returnValue);
    }

    @Test
    public void whenAnEntryWithAValueOf1IsPassedToTheDisplayPrimeMethod_thenReturnAnEmptyString() throws Exception {
        Map.Entry<String, Integer> inputString = new SimpleEntry<>("testing", 1);

        String returnValue = sut.displayPrime(inputString);

        assertEquals("", returnValue);
    }

    @Test
    public void whenAStringWithAValidContentIsPassedToTheValidWordMethod_thenReturnTrue() throws Exception {
        boolean returnValue = sut.validWord("a");

        assertTrue(returnValue);
    }

    @Test
    public void whenAStringWithAnInValidContentIsPassedToTheValidWordMethod_thenReturnTrue() throws Exception {
        boolean returnValue = sut.validWord("-");

        assertFalse(returnValue);
    }

    @Test
    public void whenTheDisplayFrequencyListMethodIsCalledAndTheEntryHasAPrimeValue_thenReturnAStringIndicatingThatTheValueIsPrime() throws Exception {
        Map<String, Integer> testList = new HashMap<>();
        testList.put("testing", 3);

        sut.wordList = testList;
        String returnValue = sut.displayFrequencyList();

        assertEquals("             testing\t    3\tprime\n", returnValue);
    }

    @Test
    public void whenTheDisplayFrequencyListMethodIsCalledAndTheEntryDoesntHaveAPrimeValue_thenReturnAStringWithoutPrimeIndication() throws Exception {
        Map<String, Integer> testList = new HashMap<>();
        testList.put("testing", 4);

        sut.wordList = testList;
        String returnValue = sut.displayFrequencyList();

        assertEquals("             testing\t    4\t\n", returnValue);
    }
}