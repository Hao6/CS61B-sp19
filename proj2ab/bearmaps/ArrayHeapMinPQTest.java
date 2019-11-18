package bearmaps;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    static ArrayHeapMinPQ<String>  arrayHeapMinPQ = new ArrayHeapMinPQ<>();

    @Test
    public void testAll() {
        /*******************测试add****************************************/
        String[] strList = {"cPlusPlus","c","python","cSharp","r","java"};
        double[] priority = {5.0,6.0,8.0,4.0,3.0,7.0};

        assertEquals(0, arrayHeapMinPQ.size());
        for (int i = 0; i < strList.length; i += 1) {
            arrayHeapMinPQ.add(strList[i], priority[i]);
            assertTrue(arrayHeapMinPQ.contains(strList[i]));
            assertEquals(i+1, arrayHeapMinPQ.size());
        }
        assertEquals("r", arrayHeapMinPQ.getSmallest());
        /*************以上测试证明纯粹的添加是没有问题的**************************/

        /*******************测试contains与remove,changePriority**************/

        arrayHeapMinPQ.add("js", 1.0);
        assertEquals(arrayHeapMinPQ.getSmallest(), "js");

        arrayHeapMinPQ.changePriority("c", 4.0);
        arrayHeapMinPQ.add("scala", 3);
        assertTrue(arrayHeapMinPQ.contains("scala"));

        assertEquals(8, arrayHeapMinPQ.size());

        arrayHeapMinPQ.changePriority("cPlusPlus", 2);

        arrayHeapMinPQ.printPQ();

        assertTrue(arrayHeapMinPQ.contains("cPlusPlus"));

        arrayHeapMinPQ.removeSmallest();

        arrayHeapMinPQ.printPQ();

        arrayHeapMinPQ.add("sql", 3);

        arrayHeapMinPQ.printPQ();

        arrayHeapMinPQ.add("html", 5);
        arrayHeapMinPQ.printPQ();

        arrayHeapMinPQ.removeSmallest();
        arrayHeapMinPQ.printPQ();

        arrayHeapMinPQ.removeSmallest();
        arrayHeapMinPQ.printPQ();

        arrayHeapMinPQ.removeSmallest();
        arrayHeapMinPQ.printPQ();
        arrayHeapMinPQ.removeSmallest();
        arrayHeapMinPQ.printPQ();

        arrayHeapMinPQ.add("bash", 3);
        arrayHeapMinPQ.printPQ();

        for (int i = 0; i < 6; i += 1) {
            arrayHeapMinPQ.removeSmallest();
            arrayHeapMinPQ.printPQ();
        }
    }

    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    @Test
    public void testTimes() {
        // 测试有关时间复杂度的情况
        int N = 10000000;
        int strLen = 15;
        double maxPriority = 3000;
        Random random=new Random();
        String[] strList = new String[N];
        double[] priority = new double[N];
        for (int i = 0; i < N; i += 1) {
            strList[i] = getRandomString(StdRandom.uniform(7, strLen));
            priority[i] = StdRandom.uniform(2, maxPriority);
        }

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < N; i += 1) {
            arrayHeapMinPQ.add(strList[i], priority[i]);
        }
        long endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000.0);

        assertEquals(N, arrayHeapMinPQ.size());
    }
}
