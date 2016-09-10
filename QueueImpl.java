
import java.util.LinkedList;
public  class QueueImpl implements Queue {
    public LinkedList<String> data = new LinkedList<String>();
    public String dequeue() {return data.removeFirst();}
    public String peek() {return data.getFirst();}
    public int size() {return data.size();}
    public boolean isEmpty() {return data.isEmpty();}
	public void enqueue(String s) {
		data.addLast(s);
	}
	
	
}