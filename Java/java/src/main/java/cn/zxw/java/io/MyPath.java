package cn.zxw.java.io;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

public class MyPath {

	/**
	 * 获取项目所在路径(包括jar) D:\test\target\classes
	 */
	public static void getProjectPath() {
		try {
			URL url = MyPath.class.getProtectionDomain().getCodeSource().getLocation();
			String filePath = URLDecoder.decode(url.getPath(), "utf-8");
			System.out.println(filePath);
			if (filePath.endsWith(".jar"))
				filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
			File file = new File(filePath);
			filePath = file.getAbsolutePath();
			System.out.println(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取项目所在路径 D:\test\target\classes
	 */
	public static void getRealPath() {
		try {
			String realPath = MyPath.class.getClassLoader().getResource("").getFile();
			File file = new File(realPath);
			realPath = file.getAbsolutePath();
			System.out.println(realPath);
			realPath = URLDecoder.decode(realPath, "utf-8");
			System.out.println(realPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getAppPath(Class<?> cls) {
		try {
			// 检查用户传入的参数是否为空
			if (cls == null)
				throw new java.lang.IllegalArgumentException("参数不能为空！");

			ClassLoader loader = cls.getClassLoader();
			// 获得类的全名，包括包名
			String clsName = cls.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (clsName.startsWith("java.") || clsName.startsWith("javax.")) {
				throw new java.lang.IllegalArgumentException("不要传送系统类！");
			}
			// 将类的class文件全名改为路径形式
			String clsPath = clsName.replace(".", "/") + ".class";

			// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
			URL url = loader.getResource(clsPath);
			// 从URL对象中获取路径信息
			String realPath = url.getPath();
			// 去掉路径信息中的协议名"file:"
			int pos = realPath.indexOf("file:");
			if (pos > -1) {
				realPath = realPath.substring(pos + 5);
			}
			// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
			pos = realPath.indexOf(clsPath);
			realPath = realPath.substring(0, pos - 1);
			// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
			if (realPath.endsWith("!")) {
				realPath = realPath.substring(0, realPath.lastIndexOf("/"));
			}
			File file = new File(realPath);
			realPath = file.getAbsolutePath();

			realPath = URLDecoder.decode(realPath, "utf-8");
			System.out.println(realPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		getProjectPath();
		System.out.println("--------------------");
		getRealPath();
		System.out.println("--------------------");
		getAppPath(MyPath.class);
	}

}