package com.pancm.others;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
* @Title: JsoupHtml
* @Description: 爬虫测试
* 使用 jsoup 
* @Version:1.0.0  
* @author pancm
* @date 2018年3月21日
 */
public class JsoupHtml {
    
    private String url="http://sou.zhaopin.com/jobs/searchresult.ashx?jl=";  //智联招聘网站
    private  String city="深圳"; //搜索工作的城市
    private  String keywords="java";  //搜索工作的关键字
    public JsoupHtml(String city,String keywords){        
        this.city=city;
        this.keywords =keywords;
        
    }
    
    public void getZhiLianWork(){
        try {
            for (int i=0;i<10;i++) {
                    System.out.println("*********开始遍历第"+(i+1)+"页的求职信息*********");
                    Document doc = Jsoup.connect(url+city+"&kw="+keywords+"&p="+(i+1)+"&isadv=0").get();                    
                    Element content = doc.getElementById("newlist_list_content_table"); 
                    if(content == null){
                    	continue;
                    }
                    Elements zwmcEls = content.getElementsByClass("zwmc");
                    Elements gsmcEls = content.getElementsByClass("gsmc");            
                    Elements zwyxEls = content.getElementsByClass("zwyx");            
                    Elements gzddEls = content.getElementsByClass("gzdd");            
                    Elements gxsjEls = content.getElementsByClass("gxsj");
                    for(int j = 0;j<zwmcEls .size();j++){
                        
                        System.out.println(
                                zwmcEls.get(j).tagName("a").text()+"*****"+gsmcEls.get(j).tagName("a").text()+
                                "*****"+zwyxEls.get(j).tagName("a").text()+"*****"+gzddEls.get(j).tagName("a").text()+
                                "*****"+gxsjEls.get(j).tagName("a").text());
                        System.out.println();
                }
                    System.out.println("*********结束遍历第"+(i+1)+"页的求职信息*********");
            
            }
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {    
        
        JsoupHtml jHtml = new JsoupHtml("上海", "java");
        jHtml.getZhiLianWork();
        
    }

}