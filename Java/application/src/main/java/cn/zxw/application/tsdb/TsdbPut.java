package cn.zxw.application.tsdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zxw.application.utils.HttpUtil;

/**
 * OpenTSDB写入一些数据
 * java -jar application-0.0.1-SNAPSHOT-jar-with-dependencies.jar 24 7200 60
 * java -jar application-0.0.1-SNAPSHOT-jar-with-dependencies.jar 24 7200 60 Temperature http://172.17.171.15:4242/api/put?details
 * tagNum seconds interval metric url
 */
public class TsdbPut {
	/**
	 * 默认参数
	 */
	static String url = "http://localhost:4242/api/put?details";
	static String metric = "Temperature";
	static int tagNum = 1; //标签数，即tsuid数
	static int seconds = 60;//单位秒，运行时长
	static int interval = 60;//单位毫秒，数据间隔时长
	
	public static void main(String[] args) {
		if(args.length == 0) {
			System.out.println("请输入参数：\r\n"
					+ "tagNum,seconds,interval\r\n"
					+ "tagNum,seconds,interval,metric,url");
			return;
		}
		//参数传递
		tagNum = Integer.parseInt(args[0]);
		seconds = Integer.parseInt(args[1]);
		interval = Integer.parseInt(args[2]);
		if(args.length > 3) {
			metric = args[3];
			url = args[4];
		}
		System.out.println(" - url = "+ url 
				+"\r\n - metric = "+ metric 
				+"\r\n - tagNum = "+ tagNum 
				+"\r\n - seconds= "+ seconds
				+"\r\n - interval= "+ interval);
		// HTTP请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		long startTime = System.currentTimeMillis()/1000; //程序运行开始时间
		long currTime = startTime; //程序运行当前时间
		long tmpTime = 0; //临时变量
		long timestamp = 0; //写入数据的时间
		Random random=new Random();
		
		while ((currTime - startTime) < seconds) {
			JSONArray arr = new JSONArray();
			timestamp = System.currentTimeMillis();
			//确定写入value大小，随机值
			int value = random.nextInt(100);
			for (int i = 0; i < tagNum; i++) {
				JSONObject json = new JSONObject();
				json.put("metric", metric);
				json.put("timestamp", timestamp);
				json.put("value", value);
				JSONObject tags = new JSONObject();
				tags.put("host", "web" + i);
				json.put("tags", tags);
				arr.add(json);
			}
			try {
				List<String> res = HttpUtil.sendPostWithCode(url, arr.toJSONString(), headers);
				if (!"200".equals(res.get(0))) {
					System.out.println(res.get(0) + " -> " + res.get(1));
				}
			} catch (Exception e) {
				System.out.println("Http Rrror: " + e.getLocalizedMessage());
			}
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
			}
			tmpTime = System.currentTimeMillis()/1000;
			if(currTime != tmpTime) {//运行超过1秒
				System.out.println("run "+(currTime - startTime)+"/"+seconds);
				currTime = tmpTime;
			}
		}
		System.out.println("total run " + (currTime - startTime) + " seconds from "+startTime +" to "+currTime);
	}

}
