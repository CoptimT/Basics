package cn.zxw.java.jvm;

public class VMStackOOM {
	private int stackLenth = 1;
	
	public void stackLeak() {
		stackLenth++;
		stackLeak();
	}
	
	// -Xss128k
	public static void main(String[] args) {
		VMStackOOM oom = new VMStackOOM();
		try {
			oom.stackLeak();
		} catch (Throwable e) {
			System.out.println("Stack Lenth:"+oom.stackLenth);
			e.printStackTrace();
		}
	}
	/*java.lang.StackOverflowError
			at sun.nio.cs.UTF_8.updatePositions(UTF_8.java:77)
			at sun.nio.cs.UTF_8.access$200(UTF_8.java:57)
			at sun.nio.cs.UTF_8$Encoder.encodeArrayLoop(UTF_8.java:636)
			at sun.nio.cs.UTF_8$Encoder.encodeLoop(UTF_8.java:691)
	*/
}
