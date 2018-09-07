package cn.zxw.application.jython;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.python.core.PySystemState;
import org.python.util.PythonInterpreter;

public class JythonEnvironment {
	private static JythonEnvironment INSTANCE = new JythonEnvironment();

	private JythonEnvironment() {}

	public static JythonEnvironment getInstance() {
		return INSTANCE;
	}

	/**
	 * 获取python解释器
	 */
	public PythonInterpreter getPythonInterpreter() {
		Properties postProperties = new Properties();
		postProperties.put("python.home", "D:\\ProgramFiles\\Python27\\Lib");
		postProperties.put("python.console.encoding", "UTF-8");
		postProperties.put("python.security.respectJavaAccessibility", "false");
		postProperties.put("python.import.site", "false");
		Properties preProperties = System.getProperties();
		PySystemState.initialize(preProperties, postProperties);
		PythonInterpreter inter = new PythonInterpreter(null, new PySystemState());
		return inter;
	}

	public void execute(String scriptFile, Map<String, String> properties) {
		final PythonInterpreter inter = getPythonInterpreter();
		// 设置python属性,python脚本中可以使用
		for (Entry<String, String> entry : properties.entrySet()) {
			inter.set(entry.getKey(), entry.getValue());
		}
		try {
			// 通过python解释器调用python脚本
			inter.execfile(scriptFile);
		} catch (Exception e) {
			System.out.println("ExecPython encounter exception:" + e);
		}
	}
	
	public static void main(String[] args) {
		String scriptFile = "D:\\GitHub\\Java\\java_unusual\\src\\main\\java\\cn\\zxw\\java\\jython\\test.py";
		Map<String,String> properties = new HashMap<String,String>();
		properties.put("userName", "Demo-2018");
		
		JythonEnvironment.getInstance().execute(scriptFile, properties);

	}
}
