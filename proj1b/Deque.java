/**
 * @ClassName Deque
 * @Description TODO
 * @Author hao6
 * @Data 10/27/19 6:53 PM
 * @Version 1.0
 **/
public interface Deque<T> {
    public void addFirst(T item);
    public void addLast(T item);
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);

    default public boolean isEmpty(){
        return (size() == 0);
    }
}
