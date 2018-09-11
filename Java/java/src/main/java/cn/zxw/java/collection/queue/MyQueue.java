package cn.zxw.java.collection.queue;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue {
	//LinkedList类实现了Queue接口，因此我们可以把LinkedList当成Queue来用。
	private static Queue<String> queue = new LinkedList<String>();
	
	public static void main(String[] args) {
		queue.offer("1");
		System.out.println("size="+queue.size());
		System.out.println(queue.poll());
		queue.offer("2");
		queue.offer("3");
		System.out.println("size="+queue.size());
		System.out.println(queue.poll());
		System.out.println(queue.poll());
	}

}
