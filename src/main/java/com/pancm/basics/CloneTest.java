package com.pancm.basics;

/**
 * 
* @Title: CloneTest
* @Description: 克隆测试
* @Version:1.0.0  
* @author pancm
* @date 2017-7-24
 */
public class CloneTest {  
    public static void main(String args[]) {  
    	
    	/*
    	 * 浅拷贝1
    	 */
        Student stu1 = new Student();  
        stu1.setNumber(12345);  
        Student stu2 = (Student)stu1.clone();  

        System.out.println("学生1:" + stu1.getNumber());  //12345 
        System.out.println("学生2:" + stu2.getNumber());  //12345
        
        stu2.setNumber(54321);  
        //说明拷贝成功
        System.out.println("学生1:" + stu1.getNumber());  //12345
        System.out.println("学生2:" + stu2.getNumber());  //54321
        
        System.out.println(stu1==stu2);//false
        
         
        /*
         *  浅拷贝2
         *  是浅复制只是复制了addr变量的引用，并没有真正的开辟另一块空间，将值复制后再将引用返回给新对象。
    		所以，为了达到真正的复制对象，而不是纯粹引用复制。
         */
        Address addr = new Address();  
        addr.setAdd("杭州市");  
        Student2 student1 = new Student2();  
        student1.setNumber(123);  
        student1.setAddr(addr);  
        Student2 student2 = (Student2)stu1.clone();  
         
         //学生1:123,地址:杭州市
        System.out.println("学生1:" + student1.getNumber() + ",地址:" + student1.getAddr().getAdd());  
        //学生2:123,地址:杭州市
        System.out.println("学生2:" + student2.getNumber() + ",地址:" + student2.getAddr().getAdd());  
        
        addr.setAdd("深圳市");
        //学生1:123,地址:深圳市
        System.out.println("学生1:" + student1.getNumber() + ",地址:" + student1.getAddr().getAdd());  
        //学生1:123,地址:深圳市
        System.out.println("学生2:" + student2.getNumber() + ",地址:" + student2.getAddr().getAdd());  
   
        
         /*
          * 浅克隆3
          * 是浅复制只是复制了addr变量的引用，并没有真正的开辟另一块空间，将值复制后再将引用返回给新对象。
		            所以，为了达到真正的复制对象，而不是纯粹引用复制。
          */
        Address3 addr3 = new Address3();  
        addr.setAdd("杭州市");  
        Student3 st1 = new Student3();  
        st1.setNumber(123);  
        st1.setAddr(addr3);  
        Student3 st2 = (Student3)stu1.clone();  
         
         //学生1:123,地址:杭州市
        System.out.println("学生1:" + st1.getNumber() + ",地址:" + st1.getAddr().getAdd());  
        //学生2:123,地址:杭州市
        System.out.println("学生2:" + st2.getNumber() + ",地址:" + st2.getAddr().getAdd());  
        
        addr3.setAdd("武汉市");
        //学生1:123,地址:武汉市
        System.out.println("学生1:" + st1.getNumber() + ",地址:" + st1.getAddr().getAdd());  
        //学生1:123,地址:杭州市
        System.out.println("学生2:" + st2.getNumber() + ",地址:" + st2.getAddr().getAdd());  
        
        
    }  
}  


/*
 * 浅克隆1
 */
//被复制的类需要实现Cloneable接口 重写Object方法
class Student implements Cloneable{  
  private int number;  

  public int getNumber() {  
      return number;  
  }  

  public void setNumber(int number) {  
      this.number = number;  
  }  

  @Override  
  public Object clone() {  
      Student stu = null;  
      try{  
          stu = (Student)super.clone();  
      }catch(CloneNotSupportedException e) {  
          e.printStackTrace();  
      }  
      return stu;  
  }  
}  



/*
 * 浅克隆2
 */
//该对象不继承Cloneable
class Address  {  
	    private String add;  
	  
  public String getAdd() {  
       return add;  
   }  
 
   public void setAdd(String add) {  
       this.add = add;  
   }         
}  
 
//此对象依旧继承 Cloneable
class Student2 implements Cloneable{  
   private int number;  
 
   private Address addr;  
     
   public Address getAddr() {  
       return addr;  
   }  
 
   public void setAddr(Address addr) {  
       this.addr = addr;  
   }  
 
   public int getNumber() {  
       return number;  
   }  
 
   public void setNumber(int number) {  
       this.number = number;  
   }  
     
   @Override  
   public Object clone() {  
       Student2 stu = null;  
       try{  
           stu = (Student2)super.clone();  
       }catch(CloneNotSupportedException e) {  
           e.printStackTrace();  
       }  
       return stu;  
   }  
}  


/*
 * 浅克隆3
 */
//该对象继承Cloneable
class Address3 implements Cloneable {  
	private String add;  
  public String getAdd() {  
       return add;  
   }  
 
   public void setAdd(String add) {  
       this.add = add;  
   }   
   
   @Override 
   public Object clone(){
  	 Address3 addr=null;
  	 try{
  		 addr=(Address3)super.clone();
  	 }catch(CloneNotSupportedException e){
  		 e.printStackTrace();
  	 }
  	return addr;
   }
}  
 
//此对象依旧继承 Cloneable
class Student3 implements Cloneable{  
   private int number;  
 
   private Address3 addr;  
     
   public Address3 getAddr() {  
       return addr;  
   }  
 
   public void setAddr(Address3 addr) {  
       this.addr = addr;  
   }  
 
   public int getNumber() {  
       return number;  
   }  
 
   public void setNumber(int number) {  
       this.number = number;  
   }  
     
   @Override  
   public Object clone() {  
       Student3 stu = null;  
       try{  
           stu = (Student3)super.clone();   //浅复制 
       }catch(CloneNotSupportedException e) {  
           e.printStackTrace();  
       }  
       stu.addr = (Address3)addr.clone();   //深度复制 
       return stu;  
   }  
}

