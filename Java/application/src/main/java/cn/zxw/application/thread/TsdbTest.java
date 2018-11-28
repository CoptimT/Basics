package cn.zxw.application.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zxw.application.utils.HttpUtil;

/**
 * TSDB压力测试
 * java -jar test.jar http://172.17.171.15:4242/api/put?details 2 10 50 1 device
 * url threadNum metricNum tagNum seconds metric
 */
public class TsdbTest {
	/**
	 * 默认参数
	 */
	static String url = "http://localhost:4242/api/put?details";
	static int threadNum = 1;
	static int metricNum = 1;
	static int tagNum = 1;
	static int seconds = 60;
	static String device = "Temperature";
	
	public static void main(String[] args) {
		//参数传递
		url = args[0];
		threadNum = Integer.parseInt(args[1]);
		metricNum = Integer.parseInt(args[2]);
		tagNum = Integer.parseInt(args[3]);
		seconds = Integer.parseInt(args[4]);
		device = args[5];
		System.out.println(url +" - threadNum="+ threadNum 
				+" - metricNum="+ metricNum 
				+" - tagNum="+ tagNum 
				+" - seconds="+ seconds 
				+" - device="+ device);
		// 请求头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		final List<Thread> threads = new ArrayList<Thread>();
		Lock lock = new ReentrantLock();
		long starttime = System.currentTimeMillis()/1000;
		for (int t = 0; t < threadNum; t++) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					lock.lock();
					threads.add(Thread.currentThread());
					int index = threads.size();
					lock.unlock();
					int runtime = 0;//线程当前运行时间
					long timestamp = System.currentTimeMillis()/1000;
					timestamp = timestamp - 3600;
					//确定写入value大小，模拟数值周期波动，而不是随机值
					Random random=new Random();
					int value = 50;// bottom < value < top
					int flag = 1;  //-1 数值降 1数值升
					int top = 100;
					int bottom = 0;
					while(runtime < seconds) {
						for (int m = 0; m < metricNum; m++) {
							String metric = device+"-"+index+"-"+m;
							//String metric = device+index+m;
							//String metric = device;
							JSONArray arr = new JSONArray();
							for (int i = 0; i < tagNum; i++) {
								JSONObject json = new JSONObject();
								json.put("metric", metric);
								json.put("timestamp", timestamp);
								int change = random.nextInt(3);
								if(flag > 0) {
									value = value+change;
									if(value > top) {
										value = top;
										flag = -1;
									}
								}else {
									value = value-change;
									if(value < 5) {
										value = 5;
										flag = 1;
										top = random.nextInt(100);
									}
								}
								json.put("value", value);
								JSONObject tags = new JSONObject();
								tags.put("host", "web"+i);
								json.put("tags", tags);
								arr.add(json);
							}
							try {
								List<String> res = HttpUtil.sendPostWithCode(url, arr.toJSONString(), headers);
								if(!"200".equals(res.get(0))) {
									System.out.println(res.get(0)+" - " + res.get(1));
								}
							} catch (Exception e) {
								System.out.println("http error: "+e.getLocalizedMessage());
							}
						}
						while(true) {
							long now = System.currentTimeMillis()/1000;
							if(now - timestamp > 1) {
								System.out.println("slow...");
							}
							if(now - timestamp >= 1) {
								timestamp = now;
								break;
							}
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
							}
						}
						runtime++;
					}
					System.out.println(Thread.currentThread().getName() + " run "+ (System.currentTimeMillis()/1000 - starttime) +" seconds");
				}
			});
			thread.start();
		}
	}

}
