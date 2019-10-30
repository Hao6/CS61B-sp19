import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
/**
 * @ClassName TestArrayDequeGold
 * @Description TODO
 * @Author hao6
 * @Data 10/29/19 4:25 PM
 * @Version 1.0
 **/
public class TestArrayDequeGold {
    static StudentArrayDeque<Integer> SADeque = new StudentArrayDeque<>();
    static ArrayDequeSolution<Integer> ADSolution = new ArrayDequeSolution<>();

    @Test
    public void testNoName(){
        StdRandom.setSeed(46758);
        int sequenceLen = 8;
        for (int i = 0; i < sequenceLen; i++){
//            Integer num = StdRandom.uniform(0, 10);
//            SADeque.addFirst(num);
//            ADSolution.addFirst(num);
            Integer num1 = 9;
            SADeque.addFirst(num1);
            ADSolution.addFirst(num1);
        }
        for(int i = 0; i < sequenceLen/2; i++){
            assertEquals(ADSolution.removeFirst(), SADeque.removeFirst());
            assertEquals("\naddFirst(9)\naddFirst(9)\naddFirst(9)\naddFirst(9)" +
                            "\naddFirst(9)\naddFirst(9)\naddFirst(9)\naddFirst(9)" +
                            "\nremoveFast()\nremoveLast()",
                    ADSolution.removeLast(), SADeque.removeLast());
        }
    }
}
