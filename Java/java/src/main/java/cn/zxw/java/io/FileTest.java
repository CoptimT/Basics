package cn.zxw.java.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileTest {
	
	public static void writeListToFile(List<String> set,String fileName) {
		try{
			File file = new File(fileName);
			if (!file.exists()) {
				if(!file.getParentFile().exists()){
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String s:set){
				bw.write(s + "\r\n");
			}
			bw.close();
			fw.close();
		}catch(IOException e1){
			e1.printStackTrace();
		}
	}
}
