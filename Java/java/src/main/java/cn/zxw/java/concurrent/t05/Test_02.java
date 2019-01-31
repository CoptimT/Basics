/**
 * 内部类实现单例
 */
package cn.zxw.java.concurrent.t05;

public class Test_02 {

	private Test_02(){}
	
	private static class Inner{
		private static Test_02 t = new Test_02();
	}
	
	public static Test_02 getInstance(){
		return Inner.t;
	}
}
