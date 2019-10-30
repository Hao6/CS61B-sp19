import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @ClassName TestOffByN
 * @Description TODO
 * @Author hao6
 * @Data 10/28/19 8:54 PM
 * @Version 1.0
 **/
public class TestOffByN {

    static OffByN offBy2 = new OffByN(2);
    static OffByN offBy4 = new OffByN(4);

    @Test
    public void equalChars() {
        assertTrue(offBy2.equalChars('d','f'));
        assertTrue(offBy4.equalChars('e','a'));
        assertFalse(offBy4.equalChars('d','f'));

    }
}