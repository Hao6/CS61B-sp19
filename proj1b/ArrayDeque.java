/**
 * @ClassName ArrayDeque
 * @Description TODO
 * @Author hao6
 * @Data 10/27/19 9:40 PM
 * @Version 1.0
 **/
public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;

    private int nextFirst; //双端队列的头
    private int nextLast;  //双端队列的尾

    private float resizeFactor = 0.25f; // 扩容因子

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8]; //初始大小为8

        nextFirst = 4;
        nextLast = 5;
    }


    public ArrayDeque(ArrayDeque other) {
        this();
        if (other != null) {
            size = other.size;
            nextFirst = other.nextFirst;
            nextLast = other.nextLast;
            items = (T[]) new Object[other.items.length];

            // 复制有效区间内的元素
            if (nextFirst < nextLast) {
                System.arraycopy(other.items, nextFirst + 1, items, nextFirst + 1, nextLast-(nextFirst + 1));
            } else if (nextFirst > nextLast) {
                System.arraycopy(other.items, 0, items, 0, nextLast); //复制前半部分
                System.arraycopy(other.items, nextFirst + 1, items, nextFirst + 1,
                        items.length-(nextFirst + 1)); // 复制后半部分
            }
        }
    }

    private int calNewNextLast(int nextFirst, int itemsLen, int curSize) {
        return (nextFirst + 1 + curSize) % itemsLen;
    }

    // 对ArrayDeque进行扩大容量
    private void increaseSize(){
        T[] newItems = (T[]) new Object[(int) ((size + 1) * (1 + resizeFactor))]; // 扩大容量
        // 计算新的nextFirst与nextLast，使用何种策略呢，使nextFirst保持不变，变化nextLast
        // 计算新的nextLast
        int newNextLast = calNewNextLast(nextFirst, newItems.length, size);

        if (nextFirst < newNextLast) {
            System.arraycopy(items, nextFirst + 1, newItems, nextFirst + 1, items.length-(nextFirst + 1));
            System.arraycopy(items, 0, newItems, items.length, nextLast);
        } else if (nextFirst > newNextLast) {
            System.arraycopy(items, nextFirst + 1, newItems, nextFirst + 1, items.length-(nextFirst + 1)); //复制前半部分
            System.arraycopy(items, 0, newItems, items.length, nextLast - newNextLast);
            System.arraycopy(items, nextLast - newNextLast, newItems, 0, newNextLast);
        }
        nextLast = newNextLast;
        items = newItems;
    }

    @Override
    public void addFirst(T item) {
        if (nextFirst == nextLast) { // 数组满了，需要扩容, equals (size+1) == items.length
            increaseSize();
        }
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size += 1;
    }

    @Override
    public void addLast(T item){
        if (nextFirst == nextLast) {
            increaseSize();
        }
        items[nextLast] = item;
        nextLast = (nextLast+1)%items.length;
        size += 1;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        if (nextFirst < nextLast) {
            for(int i=nextFirst+1;i<nextLast;i++){
                System.out.print(items[i]);
                System.out.print(" ");
            }
            System.out.println();
        }else{
            for(int i= nextFirst+1; i<items.length; i++){
                System.out.print(items[i]);
                System.out.print(" ");
            }
            for(int i=0; i<nextLast;i++){
                System.out.print(items[i]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    // 缩小容量
    private void decreaseSize(){
        int arrayLen = items.length;

        if ((arrayLen*1.0)/size >= 1.25){ //超过1.25倍阈值
            if (arrayLen < (int)(size*1.25) && (int)(size*1.25) > 8){ // 有缩小的空间
                int newArrayLen = (int)(size*1.25); // 缩小到原来的1.25倍
                // 保持nextFirst不变，计算nextLast的位置
                int newNextLast = calNewNextLast(nextFirst, newArrayLen, size);

                T[] newItems = (T[])new Object[newArrayLen];

                if (nextFirst < newNextLast) {
                    if (nextFirst < nextLast) {
                        System.arraycopy(items, nextFirst+1, newItems, nextFirst+1, size);
                    }else if (nextFirst > nextLast) {
                        System.arraycopy(items, nextFirst+1, newItems, nextFirst+1,
                                arrayLen-(nextFirst+1)); //复制后半部分
                        System.arraycopy(items, 0, newItems, arrayLen, nextLast); //复制前半部分
                    }
                } else if (nextFirst > newNextLast) {
                    if (nextFirst < nextLast){
                        System.arraycopy(items, nextFirst+1, newItems, nextFirst+1,
                                newArrayLen-(nextFirst+1));
                        System.arraycopy(items, newArrayLen, newItems, 0, nextLast-newArrayLen-2);
                    }else if (nextFirst > nextLast) { //nextLast < newNextLast
                        System.arraycopy(items, nextFirst+1, newItems, nextFirst+1,
                                newArrayLen-(nextFirst+1));
                        System.arraycopy(items, newArrayLen, newItems, 0, newNextLast - nextLast);
                        System.arraycopy(items, 0, newItems, newNextLast - nextLast, nextLast);
                    }
                }
                items = newItems;
                nextLast = newNextLast;
            }
        }
    }

    @Override
    public T removeFirst(){
        if (size == 0) {
            return null;
        }else{
            T returnData = items[(nextFirst+1)%items.length];
            nextFirst = (nextFirst+1)%items.length;
            size -= 1;
            // 考虑缩小容量
            decreaseSize();
            return returnData;
        }
    }

    @Override
    public T removeLast(){
        if (size == 0) {
            return null;
        }else{
            T retutnData = items[(nextLast-1+items.length) % items.length];
            nextLast = (nextLast-1+items.length) % items.length;
            size -= 1;
            // 考虑缩小容量,如果items.length / size >= 1.5, 则进行缩小容量操作,缩小到1.25的比例
            decreaseSize();
            return retutnData;
        }
    }

    @Override
    public T get(int index){
        if (index < 0 || index >= size) {
            return null;
        }else{
            int tempIndex = (nextFirst+1)%items.length;
            while (index > 0){
                tempIndex = (tempIndex+1)%items.length;
                index --;
            }
            return items[tempIndex];
        }
    }
}
