package cn.zxw.application.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zxw.application.utils.HttpUtil;

//TSDB压力测试
public class TsdbTest {
	/**
	 * java -jar test.jar http://172.17.171.15:4242/api/put?details 2 10 50 1 device
	 * url threadNum metricNum tagNum seconds metric
	 */
	public static void main(String[] args) {
		/*String url = "http://172.17.171.15:4242/api/put?details";
		int threadNum = 20;
		int metricNum = 1;
		int tagNum = 50;
		final int seconds = 1;*/
		String url = args[0];
		int threadNum = Integer.parseInt(args[1]);
		int metricNum = Integer.parseInt(args[2]);
		int tagNum = Integer.parseInt(args[3]);
		final int seconds = Integer.parseInt(args[4]);
		String device = args[5];
		System.out.println(url +" - "+ threadNum +" - "+ metricNum +" - "+ tagNum +" - "+ seconds +" - "+ device);
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
					int runtime = 0;
					long timestamp = System.currentTimeMillis()/1000;
					while(runtime < seconds) {
						for (int m = 0; m < metricNum; m++) {
							String metric = device+"-"+index+"-"+m;
							JSONArray arr = new JSONArray();
							for (int i = 0; i < tagNum; i++) {
								JSONObject json = new JSONObject();
								json.put("metric", metric);
								json.put("timestamp", timestamp);
								json.put("value", 6);
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
