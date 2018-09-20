package com.pancm.basics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
* @Title: setTest
* @Description: 重写set中的equals和hashcode
* @Version:1.0.0  
* @author panchengming
* @date 2017年9月17日
 */
public class SetTest {
	

	public static void main(String[] args) {
		Set hashSet = new HashSet();
		Set treeSet = new TreeSet();
		Set linkedSet = new LinkedHashSet();
		
		
		set();
		hashSetTest();
		treeSet1();
		treeSet2();
	}
	
	/**
	 * set去重
	 */
	public static void set(){
		  // 初始化list
        List<String> list = new ArrayList<String>();
        list.add("Jhon");
        list.add("Jency");
        list.add("Mike");
        list.add("Dmitri");
        list.add("Mike");
        // 利用set不允许元素重复的性质去掉相同的元素
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < list.size(); i++) {
            String items = list.get(i);
            System.out.println("items:"+items);
            if (!set.add(items)) {
                // 打印重复的元素
                System.out.println("重复的数据: " + items);
            }
        }
        System.out.println("list:"+list);
	}
	
	/**
	 *  使用hashSet去重
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void hashSetTest(){
		HashSet hs = new HashSet();  
	//	TreeMap tm=new TreeMap();
        hs.add(new AA("ABC", 20));  
        hs.add(new AA("BCD", 20));  
        hs.add(new AA("CDE", 20));  
        hs.add(new AA("ABC", 20));  
        hs.add(new AA("BCD", 20));  
        Iterator it = hs.iterator();   //定义迭代器
        while (it.hasNext()) {  
            Object next = it.next();  
            System.out.println("排序之前:"+next);  
//            Entry<String, Object> me=(Entry<String, Object>) it.next();
//            tm.put(me.getKey(), me.getValue());
        }  
//        System.out.println("TreeMap排序之后:"+tm);
	}
	
	/**
	 * 一，让容器自身具备比较性，自定义比较器。
		需求：当元素自身不具备比较性，或者元素自身具备的比较性不是所需的。
		那么这时只能让容器自身具备。
		定义一个类实现Comparator 接口，覆盖compare方法。
		并将该接口的子类对象作为参数传递给TreeSet集合的构造函数。
		当Comparable比较方式，及Comparator比较方式同时存在，以Comparator
		比较方式为主
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void treeSet1(){
		 TreeSet ts = new TreeSet(new MyComparator());  
	        ts.add(new Book("think in java", 100));  
	        ts.add(new Book("java 核心技术", 75));  
	        ts.add(new Book("现代操作系统", 50));  
	        ts.add(new Book("java就业教程", 35));  
	        ts.add(new Book("think in java", 100));  
	        ts.add(new Book("ccc in java", 100));  
	  
	        System.out.println("treeSet1:"+ts); 
	}
	
	
	/**
	 *  
		二，让元素自身具备比较性。
		也就是元素需要实现Comparable接口，覆盖compareTo 方法。
		这种方式也作为元素的自然排序，也可称为默认排序。
		年龄按照搜要条件，年龄相同再比姓名。
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void treeSet2(){
		TreeSet ts = new TreeSet();  
        ts.add(new Person("aa", 20, "男"));  
        ts.add(new Person("bb", 18, "女"));  
        ts.add(new Person("cc", 17, "男"));  
        ts.add(new Person("dd", 17, "女"));  
        ts.add(new Person("dd", 15, "女"));  
        ts.add(new Person("dd", 15, "女"));  
  
        System.out.println("treeSet2:"+ts);  
        System.out.println(ts.size()); // 5  
	}
	
}



 class AA{
	private String name;
	private int age;

	public AA(String name, int age) {
		this.name=name;
		this.age=age;
	}
	
	AA() {  
		  
	    }  
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
		//重写hashCode
	 	@Override   
	    public int hashCode() {  
	        System.out.println("hashCode:" + this.name);  
	        return this.name.hashCode() + age * 37;  
	    }  
	  
	 //重写equals
	    @Override  
	    public boolean equals(Object obj) {  
	        if (obj instanceof AA) {  
	        	AA a = (AA) obj;  
	            return this.name.equals(a.name) && this.age == a.age;  
	        } else {  
	            return false;  
	        }  
	    }  
	  
	    @Override  
	    public String toString() {  
	        return "姓名:" + this.name + " 年龄:" + this.age;  
	    }  
	}

@SuppressWarnings("rawtypes")
class Person implements Comparable {  
	    private String name;   
	    private int age;  
	    private String gender;   //性别
	  
	    public Person() {  
	    }  
	  
	    public Person(String name, int age, String gender) {  
	  
	        this.name = name;  
	        this.age = age;  
	        this.gender = gender;  
	    }  
	  
	    public String getName() {  
	        return name;  
	    }  
	  
	    public void setName(String name) {  
	        this.name = name;  
	    }  
	  
	    public int getAge() {  
	        return age;  
	    }  
	  
	    public void setAge(int age) {  
	        this.age = age;  
	    }  
	  
	    public String getGender() {  
	        return gender;  
	    }  
	  
	    public void setGender(String gender) {  
	        this.gender = gender;  
	    }  
	  
	    @Override  
	    public int hashCode() {  
	        return name.hashCode() + age * 37;  
	    }  
	  
	    public boolean equals(Object obj) {  
	        System.err.println(this + "equals :" + obj);  
	        if (!(obj instanceof Person)) {  
	            return false;  
	        }  
	        Person p = (Person) obj;  
	        return this.name.equals(p.name) && this.age == p.age;  
	  
	    }  
	  
	    public String toString() {  
	        return "Person [name=" + name + ", age=" + age + ", gender=" + gender  
	                + "]";  
	    }  
	  
	    @Override  
	    public int compareTo(Object obj) {  
	        Person p = (Person) obj;  
	        System.out.println(this+" compareTo:"+p);  
	        if (this.age > p.age) {  
	            return 1;  
	        }  
	        if (this.age < p.age) {  
	            return -1;  
	        }  
	        return this.name.compareTo(p.name);  
	    }  
 }
 

@SuppressWarnings("rawtypes")
class MyComparator implements Comparator {  
	  
    public int compare(Object o1, Object o2) {  
        Book b1 = (Book) o1;  
        Book b2 = (Book) o2;  
        System.out.println(b1+" comparator "+b2);  
        if (b1.getPrice() > b2.getPrice()) {  
            return 1;  
        }  
        if (b1.getPrice() < b2.getPrice()) {  
            return -1;  
        }  
        return b1.getName().compareTo(b2.getName());  
    }  
  
}  
  
class Book {  
    private String name;  
    private double price;  
  
    public Book() {  
  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public double getPrice() {  
        return price;  
    }  
  
    public void setPrice(double price) {  
        this.price = price;  
    }  
  
    public Book(String name, double price) {  
  
        this.name = name;  
        this.price = price;  
    }  
  
    @Override  
    public String toString() {  
        return "Book [name=" + name + ", price=" + price + "]";  
    }  
  
}  


