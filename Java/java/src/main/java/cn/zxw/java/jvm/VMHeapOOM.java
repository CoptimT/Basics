package cn.zxw.java.jvm;

import java.util.ArrayList;
import java.util.List;

public class VMHeapOOM {
	// -Xmx20m -Xms20m -XX:+HeapDumpOnOutOfMemoryError
	public static void main(String[] args) {
		List<byte[]> list = new ArrayList<>();
		while(true) {
			list.add(new byte[1024*1024]);//1M
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/*Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
			at cn.zxw.java.jvm.VMHeapOOM.main(VMHeapOOM.java:11)
	*/
}
