package es.datastructur.synthesizer;

import edu.princeton.cs.introcs.StdAudio;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @ClassName TestHarpString
 * @Description TODO
 * @Author hao6
 * @Data 10/30/19 8:24 PM
 * @Version 1.0
 **/
public class TestHarpString {
    @Test
    public void testPluckTheAString() {
        double CONCERT_A = 440.0;
        HarpString aString = new HarpString(CONCERT_A);
        aString.pluck();
        for (int i = 0; i < 50000; i += 1) {
            StdAudio.play(aString.sample());
            aString.tic();
        }
    }
}