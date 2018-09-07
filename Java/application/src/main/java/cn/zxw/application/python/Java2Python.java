package cn.zxw.application.python;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Java2Python {

	public static void main(String[] args) {
		try {
			System.out.println("start");
			test2();
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//执行python脚本,传递参数
	public static void test2() throws Exception {
		String[] args = new String[] { "python",
				"D:\\GitHub\\Java\\java_unusual\\src\\main\\java\\cn\\zxw\\java\\python\\Java2Python2.py", 
				"hello"};
		Process pr = Runtime.getRuntime().exec(args);

		BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
		pr.waitFor();
	}
}
