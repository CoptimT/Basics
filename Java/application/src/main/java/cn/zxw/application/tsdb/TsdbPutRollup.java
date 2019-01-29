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
 */
public class TsdbPutRollup {
	static String url = "http://172.17.171.15:4242/api/put?details";
	static String metric = "system.if.bytes.out";
	static int seconds = 600;//单位秒，运行时长
	
	public static void main(String[] args) {
		// HTTP请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		
		long startTime = System.currentTimeMillis()/1000; //程序运行开始时间
		long timestamp = 0; //写入数据的时间
		Random random=new Random();
		
		while ((timestamp - startTime) < seconds) {
			timestamp = System.currentTimeMillis()/1000;
			if(timestamp % 10 == 0) {
				JSONArray arr = new JSONArray();
				//确定写入value大小，随机值
				int value = random.nextInt(10);
				for (int i = 1; i < 5; i++) {
					String tagk1 = "host";
					String tagv1 = "web0"+i;
					for(int j = 0; j < 2; j++) {
						String tagk2 = "colo";
						String tagv2 = j==0?"lga":"sjc";
						JSONObject json = new JSONObject();
						json.put("metric", metric);
						json.put("timestamp", timestamp);
						json.put("value", value);
						JSONObject tags = new JSONObject();
						tags.put(tagk1, tagv1);
						tags.put(tagk2, tagv2);
						tags.put("interface", "eth0");
						json.put("tags", tags);
						arr.add(json);
					}
				}
				try {
					List<String> res = HttpUtil.sendPostWithCode(url, arr.toJSONString(), headers);
					if (!"200".equals(res.get(0))) {
						System.out.println(res.get(0) + " -> " + res.get(1));
					}else {
						System.out.println(timestamp);
					}
				} catch (Exception e) {
					System.out.println("Http Rrror: " + e.getLocalizedMessage());
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
		}
		System.out.println("total run " + (timestamp - startTime) + " seconds from "+startTime +" to "+timestamp);
	}

}
