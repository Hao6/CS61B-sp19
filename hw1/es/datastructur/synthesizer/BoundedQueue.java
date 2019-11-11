package es.datastructur.synthesizer;

import java.util.Iterator;

/**
 * @ClassName BoundedQueue
 * @Description 规范固定大小的队列类的方法
 * @Author hao6
 * @Data 10/30/19 11:14 AM
 * @Version 1.0
 **/
public interface BoundedQueue<T> extends Iterable<T> {
    int capacity(); // size of buffer
    int fillCount(); // number of items currently in the buffer
    void enqueue(T x); // add a item x to the end
    T dequeue();
    T peek(); // return (but not remove) item from the front

    default boolean isEmpty() {
        return fillCount() == 0;
    }
    default boolean isFull() {
        return fillCount() == capacity();
    }
    @Override
    Iterator iterator();
}
