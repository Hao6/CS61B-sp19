/**
 * @ClassName LinkedListDuque
 * @Description TODO
 * @Author hao6
 * @Data 10/27/19 9:37 PM
 * @Version 1.0
 **/
public class LinkedListDeque<T> implements Deque<T>{
    private class TNode{
        T data;
        TNode next;
        TNode pre;

        public TNode(T d, TNode n, TNode p){
            data = d;
            next = n;
            pre = p;
        }

        public TNode(T d){
            this(d, null, null);
        }

        public TNode(){
            next = this;
            pre = this;
        }

        public TNode deepCopy(){
            return new TNode(this.data);
        }
    }
    private int size;
    private TNode sentinel;

    public LinkedListDeque() {
        sentinel = new TNode();
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque<T> other){
        this();
        if (other != null) {
            TNode tempNode = other.sentinel;
            while (tempNode.next != other.sentinel) {
                TNode newNode = tempNode.next.deepCopy();
                TNode tailNode = sentinel.pre; //尾结点

                newNode.next = tailNode.next;
                newNode.pre = tailNode;
                tailNode.next = newNode;
                sentinel.pre = newNode;
                size += 1;

                tempNode = tempNode.next;
            }
        }
    }

    @Override
    public void addFirst(T item){
        TNode newNode = new TNode(item);
        newNode.next = sentinel.next;
        newNode.pre = sentinel;
        sentinel.next.pre = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T item){
        TNode newNode = new TNode(item);
        TNode tailNode = sentinel.pre; //尾结点

        newNode.next = tailNode.next;
        newNode.pre = tailNode;
        tailNode.next = newNode;
        sentinel.pre = newNode;
        size += 1;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        TNode tempNode = sentinel;
        while (tempNode.next != sentinel) {
            System.out.print(tempNode.next.data);
            System.out.print(" ");
            tempNode = tempNode.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        if (size == 0) {
            return null;
        }else{
            TNode tempNode = sentinel.next;
            sentinel.next = tempNode.next;
            tempNode.next.pre = sentinel;
            //不是有必要的
            tempNode.next = null;
            tempNode.pre = null;

            size -= 1;
            return tempNode.data;
        }
    }

    @Override
    public T removeLast(){
        if (size == 0) {
            return null;
        }else{
            TNode tempNode = sentinel.pre;
            tempNode.pre.next = sentinel;
            sentinel.pre = tempNode.pre;
            size -= 1;
            return tempNode.data;
        }
    }

    @Override
    public T get(int index){
        if (index > size-1 || index < 0) {
            return null;
        }else{
            TNode tempNode = sentinel;
            while (index > 0) {
                tempNode = tempNode.next;
                index -= 1;
            }
            return tempNode.next.data;
        }
    }

    private T getRecursiveHelp(TNode curNode, int index){
        if (index == 0) {
            return curNode.data;
        }else{
            return getRecursiveHelp(curNode.next, index-1);
        }
    }

    public T getRecursive(int index){
        if (index > size-1 || index < 0) {
            return null;
        }else{
            return getRecursiveHelp(sentinel.next, index);
        }
    }

}
