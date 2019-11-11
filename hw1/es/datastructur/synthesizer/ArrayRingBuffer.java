package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[])new Object[capacity];
        fillCount = 0;
        first = 0;
        last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer overflow");
        }
        rb[last] = x;
        last = (last + 1) % capacity();
        fillCount += 1;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        T returnItem = rb[first];
        first = (first + 1) % capacity();
        fillCount -= 1;
        return returnItem;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer underflow");
        }
        return rb[first];
    }

    @Override
    public int capacity() {
        return rb.length;
    }

    @Override
    public int fillCount() {
        return fillCount;
    }


    private class ArrayRingBufferIterator implements Iterator<T> {
        int pos = first;
        int curLen = 0;

        @Override
        public boolean hasNext() {
            return curLen < fillCount;
        }

        @Override
        public T next() {
            curLen += 1;
            int temp = pos;
            pos = (pos + 1)%capacity();
            return rb[temp];
        }
    }

    @Override
    public Iterator iterator() {
        return new ArrayRingBufferIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>)o;
        if (this.fillCount != other.fillCount) {
            return false;
        }
        ArrayRingBufferIterator iterator0 = (ArrayRingBufferIterator)this.iterator();
        ArrayRingBufferIterator iterator1 = (ArrayRingBufferIterator)other.iterator();

        while (iterator0.hasNext()) {
            if (!iterator0.next().equals(iterator1.next())) {
                return false;
            }
        }
        return  true;
    }
}
