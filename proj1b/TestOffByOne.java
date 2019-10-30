import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testOffByOne(){
        assertTrue(offByOne.equalChars('a','b'));
        assertTrue(offByOne.equalChars('b','a'));
        assertTrue(offByOne.equalChars('z','y'));
        assertTrue(offByOne.equalChars('u','v'));

        assertFalse(offByOne.equalChars('a','c'));
        assertFalse(offByOne.equalChars('e','b'));
        assertFalse(offByOne.equalChars('a','A'));
        assertFalse(offByOne.equalChars('h','b'));
    }
}
// Uncomment this class once you've created your CharacterComparator interface and OffByOne class.