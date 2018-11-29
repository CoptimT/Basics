package cn.zxw.application.tsdb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zxw.application.utils.HttpUtil;

/**
 * TSDB Put 压力测试
 * 
 * 该类特点：
 * 1.可以指定线程数，metric数，tag数，三者相乘即每秒压力
 * 2.支持Int,String
 * 3.一个时序，只对应一个metric一个tag
 * 4.tag数：每个metric标签数也即时序数，同时是每个PUT请求DataPoint数
 * 
 * 数据格式：
 * metric = metricPrifix + "thd" + thdIndex + "mtc" + metricIndex
 * tagk: host, tagv: web + tagIndex
 * 
 * java -jar test.jar http://172.17.171.15:4242/api/put?details 2 10 50 1 device int
 * url threadNum metricNum tagNum seconds metric dataType
 */
public class TsdbPutHistory {
	//默认参数
	static String url = "http://localhost:4242/api/put?details";
	static int threadNum = 1;
	static int metricNum = 1;
	static int tagNum = 1;
	static long seconds = 60;
	static String metricPrifix = "Temperature";
	static String dataType = "int";
	static long starttime = 1542643200000L;
	
	static Random random=new Random();
	static long sleepMillis = 10;
	
	public static void main(String[] args) {
		try {
			//参数传递
			url = args[0];
			threadNum = Integer.parseInt(args[1]);
			metricNum = Integer.parseInt(args[2]);
			tagNum = Integer.parseInt(args[3]);
			seconds = Integer.parseInt(args[4]);
			metricPrifix = args[5];
			dataType = args[6];
			starttime = Long.parseLong(args[7]);
			//自定义
			/*url = "http://172.17.171.15:8300/put?details";
			threadNum = 1;
			metricNum = 1;
			tagNum = 1;
			seconds = 60;
			metricPrifix = "Temp";
			dataType = "int";
			starttime = 1542643200000L;*/
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}finally {
			System.out.println(url 
					+"\r\n - threadNum="+ threadNum 
					+"\r\n - metricNum="+ metricNum 
					+"\r\n - tagNum="+ tagNum 
					+"\r\n - seconds="+ seconds 
					+"\r\n - metricPrifix="+ metricPrifix
					+"\r\n - dataType="+ dataType);
		}
		// 请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Transfer-Encoding", "chunked");
		//获取线程编号
		final AtomicInteger threadIndex = new AtomicInteger(0);
		
		for (int t = 0; t < threadNum; t++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					int thdIndex = threadIndex.incrementAndGet();
					//long starttime = System.currentTimeMillis()/1000;
					long timestamp = starttime;
					long runtime = 0;
					
					int top = 100;
					int bottom = 5;
					int flag = 1;  //-1 数值降, 1数值升
					
					while(runtime < seconds) {
						int value = 50;//bottom < value < top
						if(dataType.equals("int")) {//确定写入value大小，模拟数值周期波动，而不是随机值
							int change = random.nextInt(3);
							if(flag > 0) {
								value = value+change;
								if(value > top) {
									value = top;
									flag = -1;
								}
							}else {
								value = value-change;
								if(value < bottom) {
									value = bottom;
									flag = 1;
									top = random.nextInt(100);
								}
							}
						}
						
						for (int metricIndex = 0; metricIndex < metricNum; metricIndex++) {
							String metric = metricPrifix + "thd" + thdIndex + "mtc" + metricIndex;
							JSONArray arr = new JSONArray();
							for (int tagIndex = 0; tagIndex < tagNum; tagIndex++) {
								JSONObject json = new JSONObject();
								json.put("metric", metric);
								json.put("timestamp", timestamp);
								if(dataType.equals("int")) {
									json.put("value", value);
								}else if(dataType.equals("string")) {
									json.put("type", "string");
									json.put("value", metric);
								}
								JSONObject tags = new JSONObject();
								tags.put("host", "web"+tagIndex);
								json.put("tags", tags);
								arr.add(json);
							}
							try {
								List<String> res = HttpUtil.sendPostWithCode(url, arr.toJSONString(), headers);
								if(!"200".equals(res.get(0))) {
									System.out.println(metric + " - " + res.get(0)+" - " + res.get(1));
								}
							} catch (Exception e) {
								System.out.println("Http Error: " + metric + " - " + e.getLocalizedMessage());
								e.printStackTrace();
							}
						}
						/*while(true) {
							long now = System.currentTimeMillis()/1000;
							if(now - timestamp > 1) {
								System.out.println(Thread.currentThread().getName() + " put slow than expect...");
							}
							if(now - timestamp >= 1) {
								timestamp = now;
								break;
							}
							try {
								Thread.sleep(sleepMillis);
							} catch (InterruptedException e) {
							}
						}*/
						timestamp += 60;
						runtime = (timestamp - starttime)/1000;
						if(runtime % 10 == 0) {
							System.out.println(Thread.currentThread().getName() + " run " + runtime + "/" + seconds);
						}
						try {
							Thread.sleep(sleepMillis);
						} catch (InterruptedException e) {
						}
					}
					System.out.println(Thread.currentThread().getName() + " run "+ (System.currentTimeMillis()/1000 - starttime) +" seconds");
				}
			});
			thread.start();
		}
	}

}
