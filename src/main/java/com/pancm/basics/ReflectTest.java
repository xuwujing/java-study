package com.pancm.basics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;  
  
  
/**
 * 
* Title: ReflectTest
* Description:
* 反射测试 
* 
* 反射技术可以对一个类进行解剖。
　 优点:大大的增强了程序的扩展性。
* Version:1.0.0  
* @author pancm
* @date 2018年2月9日
 */
public class ReflectTest {  
  
    public static void main(String[] args) throws Exception {  
        Class clazz = User.class;//获得User的类名，返回reflect.User  
        Object obj = create(clazz);//创建User的一个对象  
        System.out.println(obj);//输出对象，会调用对象的toString方法  
        System.out.println("---------");  
        invoke1(obj, "getMessage");//调用User对象的getMessage方法  
    }  
    /* 
    **根据类名，new一个对象，并返回*/  
    static Object create(Class clazz) throws Exception {  
        //如果clazz含有无参数的构造方法，可以如下方式实例化  
        //clazz.newInstance();  
        //根据类名和参数（类型、个数），找到相应的构造方法-下面创建构造方法参数为String的构造方法  
        Constructor con=clazz.getConstructor(String.class);  
        //实例化对象  
        Object obj=con.newInstance("哈哈");  
        //返回对象  
        return obj;  
    }  
    /* 
    **根据对象，方法名（字符串），来调用方法*/  
    static void invoke1(Object obj, String methodName)throws Exception{  
        //getDeclaredMethods可以获取类本身（不包括父类）所有方法的名字（包括私有方法）**一般不用这种方法，私有的属性一般不能修改  
        Method[] ms = obj.getClass().getDeclaredMethods();  
        //getMethods可以获取类本身，以及父类的方法的名字，但不包括私有的方法  
        ms = obj.getClass().getMethods();  
        for (Method m : ms) {  
            //如果方法名字匹配，则反射调用方法  
            if (methodName.equals(m.getName()))  
                m.invoke(obj, null);  
        }  
        /* 
        **防止方法重载，可用下面的方式（可以指明参数）--与上面的for循环（无法防止方法重载）一个效果 
        **Method m = obj.getClass().getMethod(methodName, null); 
        **m.invoke(obj, null); 
        */  
    }  
      
    /* 
    **根据类名获取类的属性（一般不直接操作属性）*/  
    static void field(Class clazz) throws Exception {  
        Field[] fs = clazz.getDeclaredFields();  
        //fs = clazz.getFields();  
        for (Field f : fs)  
            System.out.println(f.getName());  
    }  
    /* 
    **根据类名获取类的注解*/  
    static void annon(Class clazz) throws Exception {  
        Annotation[] as = clazz.getAnnotations();  
        for (Annotation a : as)  
            System.out.println(((Member) a).getName());  
    }  
  
}  
