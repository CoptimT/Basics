package cn.zxw.application.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class HttpUtil {
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url 请求的URL
     * @param headers 请求消息头
     * @return 响应结果
     * @throws Exception 异常抛出
     */
    public static String sendGet(String url, Map<String, String> headers) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            if(headers != null) {
            	for(String key : headers.keySet()) {
            		connection.setRequestProperty(key, headers.get(key));
                }
            }
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	throw e;
        }finally {// 使用finally块来关闭输入流
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param, Map<String, String> headers) throws Exception {
    	List<String> list = sendPostWithCode(url,param,headers);
    	return list.get(1);
    }
    
    /**
     * 向指定 URL发送POST方法的请求
     * 
     * @param url 请求的 URL
     * @param param 请求参数
     * @param headers 请求消息头
     * @return 响应结果
     * @throws Exception 异常抛出
     */
    public static List<String> sendPostWithCode(String url, String param, Map<String, String> headers) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        List<String> res = new ArrayList<String>();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            if(headers != null) {
            	for(String key : headers.keySet()) {
            		conn.setRequestProperty(key, headers.get(key));
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            InputStream is = null;
            if(conn.getResponseCode() >= 400) {
            	is = conn.getErrorStream();
            }else {
            	is = conn.getInputStream();
            }
            in = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            res.add(conn.getResponseCode()+"");
            res.add(result);
        } catch (Exception e) {
        	throw e;
        }finally{//使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return res;
    }
    
    public static String getRequest(String url) throws ClientProtocolException, IOException{
		// 创建HttpClient实例
		HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		String res =null;
		if (entity != null) {
			InputStream instream = entity.getContent();
			res = IOUtils.toString(instream);
			// Do not need the rest
			httpget.abort();
		}
		return res;
	}
}