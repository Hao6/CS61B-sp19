import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator cc = new OffByOne();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome("cattac"));
        assertTrue(palindrome.isPalindrome("c"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("cc"));
        assertFalse(palindrome.isPalindrome("ca"));
    }

    @Test
    public void testIsPalindrome1() {
        assertFalse(palindrome.isPalindrome("cattac", cc));
        assertTrue(palindrome.isPalindrome("c", cc));
        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("cd", cc));
        assertFalse(palindrome.isPalindrome("ca", cc));
        assertTrue(palindrome.isPalindrome("cdd", cc));
        assertTrue(palindrome.isPalindrome("cdb", cc));
        assertTrue(palindrome.isPalindrome("ADCB", cc));
    }
}
// Uncomment this class once you've created your Palindrome class. */