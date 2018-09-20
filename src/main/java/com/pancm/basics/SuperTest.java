package com.pancm.basics;

/**
 * 
* @Title: SuperTest
* @Description: super测试
* @Version:1.0.0  
* @author pancm
* @date 2017-5-24
 */
public class SuperTest extends Person8 {
    SuperTest() {
        super(); // 调用父类构造函数（1）
        prt("A chinese.");// (4)
    }
 
    SuperTest(String name) {
        super(name);// 调用父类具有相同形参的构造函数（2） 调用了Person中的Person(String name)方法
        prt("his name is:" + name);
    }
 
    SuperTest(String name, int age) {
        this(name);// 调用当前具有相同形参的构造函数（3） 调用了SuperTest(String name)
        prt("his age is:" + age);
    }
 
    public static void main(String[] args) {
        SuperTest cn = new SuperTest(); // A Person.   A chinese.
        cn = new SuperTest("kevin");    //A person name is:kevin   his name is:kevin
        cn = new SuperTest("kevin", 22);//A person name is:kevin  his name is:kevin  his age is:22
    }
}


class Person8 {
	 
    public static void prt(String s) {
        System.out.println(s);
    }
 
    Person8() {
        prt("A Person.");
    }
 
    Person8(String name) {
        prt("A person name is:" + name);
 
    }
}