package cn.zxw.application.jython;

import java.util.Properties;

import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class JythonUtils {
 
	/**
	 * 初始化jython
	 */
	public static PythonInterpreter jythonInit() {
		// 初始化site 配置
		Properties props = new Properties();
		//python Lib 或 jython Lib,根据系统中该文件目录路径
		props.put("python.home", "D:\\ProgramFiles\\Python27\\Lib");
		props.put("python.console.encoding", "UTF-8");
		props.put("python.security.respectJavaAccessibility", "false");
		props.put("python.import.site", "false");
		Properties preprops = System.getProperties();
		PythonInterpreter.initialize(preprops, props, new String[0]);
		// 创建PythonInterpreter 对象
		PythonInterpreter interp = new PythonInterpreter();
		return interp;
	}

	public static void main(String[] args) {
		PythonInterpreter interp = jythonInit();
		// python文件名
		String filePath = "D:\\GitHub\\Java\\java_unusual\\src\\main\\java\\cn\\zxw\\java\\jython\\JythonUtils.py";
		//执行脚本
		interp.execfile(filePath);
		//加载方法
		PyFunction func = (PyFunction) interp.get("sayHi", PyFunction.class);
		// 执行无参方法，返回PyObject
		PyObject pyobj = func.__call__();
		System.out.println("PyObject = "+pyobj);
		// 执行无参方法，返回String
		PyFunction func1 = (PyFunction) interp.get("getName", PyFunction.class);
		PyObject pyobj1 = func1.__call__();
		String resultStr = (String) pyobj1.__tojava__(String.class);
		System.out.println("resultStr = "+resultStr);
		// 执行有参方法，返回String
		String paramName = "name";
		PyFunction func2 = (PyFunction) interp.get("sayMsg", PyFunction.class);
		PyObject pyobj2 = func2.__call__(new PyString(paramName));
		String resultStr2 = (String) pyobj2.__tojava__(String.class);
		System.out.println("resultStr2 = "+resultStr2);
	}
	
}
