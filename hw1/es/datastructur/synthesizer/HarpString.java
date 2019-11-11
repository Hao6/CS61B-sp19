package es.datastructur.synthesizer;

import java.util.HashSet;
import java.util.Set;
/**
 * @ClassName Harp
 * @Description TODO
 * @Author hao6
 * @Data 10/30/19 8:16 PM
 * @Version 1.0
 **/
public class HarpString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. */
    private static final int SR = 44100*2;      // Sampling Rate
    private static final double DECAY = 0.996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public HarpString(double frequency) {
        buffer = new ArrayRingBuffer<Double>((int)Math.round(SR / frequency));
        while (!buffer.isFull()) {
            buffer.enqueue(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        //       Make sure that your random numbers are different from each
        //       other.
        Set<Double> s = new HashSet<>(buffer.capacity());
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        while (!buffer.isFull()) {
            double newNoise = Math.random() - 0.5;
            if (!s.contains(newNoise)) {
                s.add(newNoise);
                buffer.enqueue(newNoise);
            }
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        Double curSample = buffer.dequeue();
        buffer.enqueue(-DECAY * 0.5 * (curSample + buffer.peek()));
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
