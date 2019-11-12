import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @ClassName UnionFindTest
 * @Description TODO
 * @Author hao6
 * @Data 11/1/19 5:49 PM
 * @Version 1.0
 **/
public class UnionFindTest {
    static UnionFind uf = new UnionFind(10);


    @Test
    public void testAll() {
        uf.union(0,1);
        assertTrue(uf.connected(0, 1));
        assertEquals(1, uf.find(0));

        uf.union(2,3);
        assertTrue(uf.connected(2, 3));
        assertEquals(3, uf.find(3));

        assertEquals(2, uf.sizeOf(0));
        assertEquals(2, uf.sizeOf(1));

        uf.union(0, 2);
        assertEquals(4, uf.sizeOf(1));
        assertEquals(3, uf.find(1));

        uf.union(4,5);
        uf.union(6,7);

        uf.union(4,7);
        assertEquals(7, uf.find(4));

        uf.union(2, 6);
        assertEquals(8, uf.sizeOf(3));
        assertEquals(7, uf.find(3));

        uf.connected(0, 6);


    }
}