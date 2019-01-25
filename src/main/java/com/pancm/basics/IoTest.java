package com.pancm.basics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

/**
* @Title: IoTest
* @Description: 
* io字符流和字节流测试
* @Version:1.0.0  
* @author pancm
* @date 2018年6月11日
*/
public class IoTest {

	public static void main(String[] args)  {
		try {
//			test();
//			test2();
//			test3();
//			test4();
			test5();
//			test6();
//			test7();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	/**
	 * 字符流
	 * @throws IOException
	 */
	private static void test() throws IOException {
		   String str;
		    // 使用 System.in 创建 BufferedReader 
		    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		    System.out.println("输入字符, 输入 'quit' 退出。");
		    // 读取字符
		    do {
		       str=br.readLine();
		       System.out.println("您输入的字符是:"+str);
		    } while(!str.equals("quit"));
		    br.close();
	}
	
	/**
	 * 字节流
	 * 创建一个文件并新增一条记录
	 * @throws IOException
	 */
	private static void test2() throws IOException {
		String path="E:/test/hello.txt";
		String str="hello world";
		//创建一个文件并向文件中写数据 需要文件夹存在
		OutputStream output = new FileOutputStream(path);
		output.write(str.getBytes());
		output.close();
	}	
	
	/**
	 * 写入和读取文件
	 * @throws IOException
	 */
	private static void test3() throws IOException {
		//创建要操作的文件路径和名称  
        String path ="E:/test/hello.txt";
        String str="你好!";
        FileWriter fw = new FileWriter(path);  
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(str);  
        bw.close();
        fw.close();  
        
        FileReader fr = new FileReader(path);  
        BufferedReader br=new BufferedReader(fr);
        StringBuffer sb=new StringBuffer();
  		while(br.ready()){
  			sb.append((char)br.read());
  		}
        System.out.println("输出:"+sb.toString());
        br.close();
        fr.close();
	}	
	
	
	
	
	/**
	 * 字节流
	 * 创建一个文件并读取记录 防止乱码
	 * @throws IOException
	 */
	private static void test4() throws IOException {
		String path="E:/test/hello.txt";
		String path2="E:/test/你好.txt";
		String str="你好!";
		//从文件读取数据
		InputStream input = new FileInputStream(path);
		InputStreamReader reader = new InputStreamReader(input, "UTF-8");
	    StringBuffer sb=new StringBuffer();
		while(reader.ready()){
			sb.append((char)reader.read());
		}
		
		input.close();
		reader.close();
		
		//创建一个文件并向文件中写数据
		OutputStream output = new FileOutputStream(path2);
		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(sb+str);
		
		writer.close();
		output.close();
		
		//从文件读取数据
		InputStream input2 = new FileInputStream(path2);
		InputStreamReader reader2 = new InputStreamReader(input2, "UTF-8");
	    StringBuffer sb2=new StringBuffer();
		while(reader2.ready()){
			sb2.append((char)reader2.read());
		}
		System.out.println("输出:"+sb2);
		input2.close();
		reader2.close();
	}	
	
	
	
	/**
	 * 字符流
	 * 写入和读取文件
	 * @throws IOException
	 */
	private static void test6() throws IOException {
		//创建要操作的文件路径和名称  
        //其中，File.separator表示系统相关的分隔符，Linux下为：/  Windows下为：\\  
        String path ="E:/test2/hello.txt";
        String str="hello world";
        FileWriter fw = new FileWriter(path);  
        //以path为路径创建一个新的FileWriter对象  
        //如果需要追加数据，而不是覆盖，则使用FileWriter（path，true）构造方法  
        //将字符串写入到流中，\r\n表示换行想有好的  
        fw.write(str);  
        fw.close();  
        
        FileReader fr = new FileReader(path);  
        StringBuffer sb=new StringBuffer();
  		while(fr.ready()){
  			sb.append((char)fr.read());
  		}
        System.out.println("输出:"+sb.toString());
        fr.close();
	}
	
	
	/**
	 * 字符流
	 * 写入和读取文件
	 * @throws IOException
	 */
	private static void test7() throws IOException {
		//创建要操作的文件路径和名称  
        String path ="E:/test2/hello.txt";
        Properties prop=new Properties();
        prop.setProperty("name", "zz");
        FileWriter fw = new FileWriter(path);  
        BufferedWriter bw=new BufferedWriter(fw);
        bw.write(prop.toString());  
        bw.close();
        fw.close();  
        
        FileReader fr = new FileReader(path);  
        BufferedReader br=new BufferedReader(fr);
        StringBuffer sb=new StringBuffer();
  		while(br.ready()){
  			sb.append((char)br.read());
  		}
        System.out.println("输出:"+sb.toString());
        br.close();
        fr.close();
	}
	
	
	
	/**
	 * 新建文件夹和文件
	 * @throws IOException
	 */
	private static void test5() throws IOException {
		String path="E:/test/test2";
		String path2="E:/test/test3/test3";
		String path3="E:/test/test2/test2.txt";
		File f = new File(path);
		File f2 = new File(path2);
		File f3 = new File(path3);
		System.out.println(f.exists());
		//创建文件夹
		System.out.println("="+f.mkdir());
		//创建文件夹和所有父文件夹
		System.out.println("=="+f2.mkdirs());
		//创建一个文本
		System.out.println("==="+f3.createNewFile());
		//获取名称
		System.out.println("==="+f3.getName());
		//获取父级名称
		System.out.println("==="+f3.getParent());
		//获取当前路径
		System.out.println("==="+f3.getPath());
		//判断是否是目录
		System.out.println("=="+f2.isDirectory());
		System.out.println("==="+f3.isDirectory());
		//删除该文件
		System.out.println("==="+f3.delete());
		
	}	
	
}
