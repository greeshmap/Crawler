public interface Queue {

    void enqueue(String s);
    Object dequeue();
    int size();
    boolean isEmpty();

}