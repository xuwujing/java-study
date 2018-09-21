package com.pancm.basics;

/**
 * 
* @Title: PackagingTest
* @Description:
* 封装 
* @Version:1.0.0  
* @author pancm
* @date 2018年3月27日
 */
public class PackagingTest {
    
    public static void main(String[] args) {
    	User9 user=new User9();
  //这里会报错，因为id和name是私有的，用于保护该数据
//      user.id=10;
//      user.name="张三";
        user.setId(1);
        user.setName("张三");
        System.out.println(user.getId());
        System.out.println(user.getName());
    }

}

class User9{
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}