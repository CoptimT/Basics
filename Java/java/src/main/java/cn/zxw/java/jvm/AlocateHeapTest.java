package cn.zxw.java.jvm;

import java.util.ArrayList;
import java.util.List;

public class AlocateHeapTest {

	public static void main(String[] args) {
		testEden();
	}
	/**
	 * 测试Eden区内存分配,jvisualvm.exe
	 */
	public static void testEden() {
		List<byte[]> list = new ArrayList<>();
		while(true) {
			list.add(new byte[1024*1024]);//1M
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
