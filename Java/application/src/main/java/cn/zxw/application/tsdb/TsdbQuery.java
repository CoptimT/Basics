package cn.zxw.application.tsdb;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zxw.application.utils.HttpUtil;

/**
 * 测试查询性能
 * java -jar application-0.0.1-SNAPSHOT-jar-with-dependencies.jar 24 1542193200 count 24
 * threadCount startTimeStamp aggregator hourThreadNum
 */
public class TsdbQuery {
	//默认参数
	public static String url = "http://localhost:4242/api/query";
	public static int threadCount = 24;//线程并发数
	public static long startTimeStamp = 1542193200;//查询开始时间戳
	public static String aggregator = "none";
	public static int hourThreadNum = 24;//每小时数据多少线程查(多于一小时则查下一小时)
	
	public static Map<String, String> headers = new HashMap<>();
	public static AtomicLong totalDpsNum = new AtomicLong(0);
	public static long totalUseTime = 0;
	
	public static void main(String[] args) {
		System.out.println("参数：线程并发数, 查询开始时间戳 , 聚合函数, 每小时数据多少线程查(多于一小时则查下一小时)");
		//自定义参数
		/*url = "http://172.17.222.11:4242/api/query";
		threadCount = 24;
		startTimeStamp = 1542434400;
		aggregator = "none";
		hourThreadNum = 24;*/
		
		//参数传递
		threadCount = Integer.parseInt(args[0]);
		startTimeStamp = Long.parseLong(args[1]);
		aggregator = args[2];
		hourThreadNum = Integer.parseInt(args[3]);
		
		System.out.println("  线程并发数 = "+threadCount+"\r\n  查询开始时间戳 = "+startTimeStamp
				+"\r\n  聚合函数 = "+aggregator+"\r\n  每小时数据多少线程查 = "+hourThreadNum);
		
		headers.put("Content-Type", "application/json");
		final CountDownLatch latch = new CountDownLatch(threadCount);
		final CyclicBarrier barrier = new CyclicBarrier(threadCount);
		
		for (int i = 0; i < threadCount; i++) {
			int multi = i / hourThreadNum;
			long start = startTimeStamp + multi*3600;
			new QueryRequest(barrier,latch,i,start,aggregator,hourThreadNum).start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("线程已经执行完毕！开始计算");
		long dps = totalDpsNum.get();
		System.out.println(dps+"*1000/"+totalUseTime+"="+((dps*1000)/totalUseTime));
	}
	
	public static synchronized void maxTime(long time) {
		if(time > totalUseTime) {
			totalUseTime = time;
		}
	}
	
	static class QueryRequest extends Thread{
		CyclicBarrier barrier = null;
		CountDownLatch latch = null;
		int index = 0;
		long startTimeStamp = 0;
		String aggregator = "none";
		int hourThreadNum = 0;
		
		public QueryRequest(CyclicBarrier barrier,CountDownLatch latch,int index,long startTimeStamp,String aggregator,int hourThreadNum) {
			this.barrier = barrier;
			this.latch = latch;
			this.index = index;
			this.startTimeStamp = startTimeStamp;
			this.aggregator = aggregator;
			this.hourThreadNum = hourThreadNum;
		}
		
		@Override
		public void run() {
			try {
				String param = "{\r\n" + 
						"	\"start\": 1542193200,\r\n" + 
						"	\"end\": 1542196790,\r\n" + 
						"   \"msResolution\": true," +
						"	\"showStats\":true,\r\n" + 
						"	\"queries\": [{\r\n" + 
						"		\"aggregator\": \"none\",\r\n" + 
						"		\"metric\": \"Temperature\",\r\n" + 
						"		\"filters\": [{\r\n" + 
						"			\"type\": \"literal_or\",\r\n" + 
						"			\"tagk\": \"host\",\r\n" + 
						"			\"filter\": \"device0001|device0002|device0003\",\r\n" + 
						"			\"groupBy\": false\r\n" + 
						"		}]\r\n" + 
						"	}]\r\n" + 
						"}";
				JSONObject paramJson = JSONObject.parseObject(param);
				paramJson.put("start", this.startTimeStamp);
				paramJson.put("end", this.startTimeStamp+3590);
				paramJson.getJSONArray("queries").getJSONObject(0).put("aggregator", this.aggregator);
				
				String filter = "";
				int tagNum = 24 / hourThreadNum;//每个线程查几个tag
				int theradOrder = index % hourThreadNum;//该小时的第几个线程
				for(int i=1;i<=tagNum;i++) {
					int webNum = (theradOrder*tagNum)+i;
					filter+="web"+webNum+"|";
				}
				filter=filter.substring(0, filter.length()-1);
				int hourOrder = (index / hourThreadNum)+1;//第几个小时
				System.out.println(hourOrder+": "+filter);
				
				paramJson.getJSONArray("queries").getJSONObject(0)
				         .getJSONArray("filters").getJSONObject(0)
				         .put("filter", filter);
				param = paramJson.toJSONString();
				
				//多线程同时执行
				barrier.await();
				long startTime = System.currentTimeMillis();
				String response = HttpUtil.sendPost(url, param, headers);
				long endTime = System.currentTimeMillis();
				long useTime = endTime - startTime;
				Object json = JSON.parse(response.trim());
				long dpsNum = 0;
				String resType = null;
				int arrSize = 1;
				if (json instanceof JSONObject) {
					JSONObject jsonObj = (JSONObject) json;
					JSONObject dps = jsonObj.getJSONObject("dps");
					resType = "JSONObject";
					if(dps != null) {
						if("none".equals(this.aggregator)) {
							dpsNum+=dps.size();
						}else {
							for(Object count : dps.values()) {
								dpsNum+=((BigDecimal)count).longValue();
							}
						}
					}
				}else if (json instanceof JSONArray) {
					JSONArray jsonArray = (JSONArray) json;
					resType = "JSONArray";
					arrSize = jsonArray.size();
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObj = jsonArray.getJSONObject(i);
						JSONObject dps = jsonObj.getJSONObject("dps");
						if(dps != null) {
							if("none".equals(this.aggregator)) {
								dpsNum+=dps.size();
							}else {
								for(Object count : dps.values()) {
									dpsNum+=((BigDecimal)count).longValue();
								}
							}
						}
					}
				}
				String res1 = "QueryRequest("+index+") ";
				String res2 = resType+"("+arrSize+") ";
				String res3 = startTime+"-"+endTime+"="+useTime+" ";
				String res4 = "total="+dpsNum+" ";
				System.out.println(res1 + res2 + res3 + res4);
				
				totalDpsNum.addAndGet(dpsNum);
				maxTime(useTime);
				long dps = totalDpsNum.get();
				System.out.println(dps+"*1000/"+totalUseTime+"="+((dps*1000)/totalUseTime));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//多线程都结束后开始执行计算
			latch.countDown();
		}
		
	}

}
