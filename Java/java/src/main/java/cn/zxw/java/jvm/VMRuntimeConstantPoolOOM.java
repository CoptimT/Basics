package cn.zxw.java.jvm;

import java.util.ArrayList;
import java.util.List;

public class VMRuntimeConstantPoolOOM {
	// -XX:PermSize=10M -XX:MaxPermSize=10M
	//Java HotSpot(TM) 64-Bit Server VM warning: ignoring option PermSize=5M; support was removed in 8.0
	//Java HotSpot(TM) 64-Bit Server VM warning: ignoring option MaxPermSize=5M; support was removed in 8.0
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		int i = 0;
		while(true) {
			list.add(String.valueOf(i++).intern());
		}
	}
}
