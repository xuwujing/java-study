package com.pancm.basics;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
* Title: ServletTest
* Description: 
* 部署在tomcat之后，在web.xml添加:
*   <servlet>
    <servlet-name>testServlet</servlet-name>
    <servlet-class>com.pancm.test.servletTest.testServlet</servlet-class>
  </servlet>

  <servlet-mapping>
    <servlet-name>testServlet</servlet-name>
    <url-pattern>/test.do</url-pattern>
  </servlet-mapping>
  然后启动tomcat，在浏览器输入 ip:端口/项目名/设置的地址        
  就可以访问了
* Version:1.0.0  
* @author pancm
* @date 2018年3月20日
 */
public class ServletTest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回结果
	 */
	 private String result = null;
	 
	 private long count=1; 

	 /**
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   System.out.println("============");
	    System.out.println(req.getParameter("param"));
	    PrintWriter printWriter=resp.getWriter();
		result = "这是第"+count+"次响应！";
		try {
			resp.setCharacterEncoding("utf-8");
			 count++;
			 printWriter.print(result);
		} catch (Exception e) {
			result="第"+count+"次请求错误！";
			printWriter.print(result);
		}finally{
			req=null;
			printWriter=null;
			resp=null;
		}
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
