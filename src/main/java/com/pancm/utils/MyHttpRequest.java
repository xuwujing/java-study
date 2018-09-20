package com.pancm.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author xuwujing
 * @Data 2017-5-12 上午11:57:52
 * @Description  http请求工具类
 */
public class MyHttpRequest {
   
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset         
     * 			   发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
	@SuppressWarnings("rawtypes")
	public static String sendGet(String url, Map<String,Object> map,String charset){
		  StringBuffer sb=new StringBuffer();
		  //构建请求参数
		  if(map!=null&&map.size()>0){
			  Iterator it=map.entrySet().iterator(); //定义迭代器
			  while(it.hasNext()){
				 Map.Entry  er= (Entry) it.next();
				 sb.append(er.getKey());
				 sb.append("=");
				 sb.append(er.getValue());
				 sb.append("&");
			 }
		  }
	   return  sendGet(url,sb.toString(), charset);
	}
	
	
	/**
     * 向指定URL发送POST方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset         
     * 			   发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
	public static String sendPost(String url, Map<String,Object> map,String charset){
		  StringBuffer sb=new StringBuffer();
		  //构建请求参数
		  if(map!=null&&map.size()>0){
	            for (Entry<String, Object> e : map.entrySet()) {  
	            	sb.append(e.getKey());  
	            	sb.append("=");  
	            	sb.append(e.getValue());  
	            	sb.append("&");  
	            }  
		  }
	   return  sendPost(url,sb.toString(),charset);
	}
	
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset         
     * 			   发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String charset) {
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset); 
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(60);
            conn.setReadTimeout(60);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
            in = new BufferedReader(new InputStreamReader(
            		conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
            	sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset         
     * 			   发送和接收的格式       
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接 
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);  
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setReadTimeout(60);
            conn.setConnectTimeout(60);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 建立实际的连接
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
            	sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送 POST请求出现异常!"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
    
    public static void main(String[] args) {
		String getUrl="http://int.dpool.sina.com.cn/iplookup/iplookup.php";
		String postUrl="http://gc.ditu.aliyun.com/geocoding";
		String param="format=json&ip=218.4.255.255";
		String param1="a=苏州市";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("format", "json");
		map.put("ip", "218.4.255.255");
		Map<String,Object> map1=new HashMap<String,Object>();
		map1.put("a", "苏州市");
		System.out.println("Get请求1:"+sendGet(getUrl, param,"utf-8"));
		System.out.println("Get请求2:"+sendGet(getUrl, map,"utf-8"));
		System.out.println("Post请求1:"+sendPost(postUrl, param1,"utf-8"));
		System.out.println("Post请求2:"+sendPost(postUrl, map1,"utf-8"));
	}
    
}