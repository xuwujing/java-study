package com.pancm.design.singleton;
/**
 * @author ZERO
 * @Data 2017-6-7 下午4:08:26
 * @Description 
 */

/** 
 * 方法一
 * 单例模式的实现：饿汉式,线程安全 但效率比较低 
 * 
 * 方法一就是传说的中的饿汉模式
 * 优点是：写起来比较简单，而且不存在多线程同步问题，避免了synchronized所造成的性能问题；
 * 缺点是：当类SingletonTest被加载的时候，会初始化static的instance，静态变量被创建并分配内存空间，
 * 从这以后，这个static的instance对象便一直占着这段内存（即便你还没有用到这个实例），
 * 当类被卸载时，静态变量被摧毁，并释放所占有的内存，因此在某些特定条件下会耗费内存。
 * 
 */  
 class SingletonTest1 {  

    // 定义一个私有的构造方法
    private SingletonTest1() {  
    }  

    // 将自身的实例对象设置为一个属性,并加上Static和final修饰符
    private static final SingletonTest1 instance = new SingletonTest1();  

    // 静态方法返回该类的实例
    public static SingletonTest1 getInstance() {  
        return instance;  
    }  
}
 
 
 /**  
  * 方法二
  * 单例模式的实现：饱汉式,非线程安全   
  * 方法二就是传说的中的饱汉模式
  * 优点是：写起来比较简单，当类SingletonTest被加载的时候，静态变量static的instance未被创建并分配内存空间，
  * 当getInstance方法第一次被调用时，初始化instance变量，并分配内存，因此在某些特定条件下会节约了内存；
  * 缺点是：并发环境下很可能出现多个SingletonTest实例。  
  */  
 class SingletonTest2 {
     // 定义私有构造方法（防止通过 new SingletonTest()去实例化）
     private SingletonTest2() {   
     }   

     // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
     private static SingletonTest2 instance;   

     // 定义一个静态的方法（调用时再初始化SingletonTest，但是多线程访问时，可能造成重复初始化问题）
     public static SingletonTest2 getInstance() {   
         if (instance == null) {
			instance = new SingletonTest2();
		}   
         return instance;   
     }   
 }  
 
 
 /**  
  *方法三
  * 单例模式的实现：饱汉式,线程安全简单实现   
  * 方法三为方法二的简单优化
  *	优点是：使用synchronized关键字避免多线程访问时，出现多个SingletonTest实例。
  *	缺点是：同步方法频繁调用时，效率略低  
  */  
    class SingletonTest3 {
     // 定义私有构造方法（防止通过 new SingletonTest()去实例化）
     private SingletonTest3() {   
     }   
     // 定义一个SingletonTest类型的变量（不初始化，注意这里没有使用final关键字）
     private static SingletonTest3 instance;   

     // 定义一个静态的方法（调用时再初始化SingletonTest，使用synchronized 避免多线程访问时，可能造成重的复初始化问题）
     public static synchronized  SingletonTest3 getInstance() {   
         if (instance == null) {
			instance = new SingletonTest3();
		}   
         return instance;   
     }   
   } 
 
    
    /**
     * 方法四
     *   静态内部类
     * 这种写法仍然使用JVM本身机制保证了线程安全问题；由于SingletonTest5是私有的， 除了getInstance()之外没有办法访问它，
     * 因此它是懒汉式的；同时读取实例的时候不会进行同步，没有性能缺陷；也不依赖JDK版本。
     */
   class  SingletonTest4 {
	   private SingletonTest4(){
	   }
	   private static class SingletonTest5{
	       private static SingletonTest4 instance = new SingletonTest4();
	    }
	    public static final SingletonTest4 getInstance(){
	        return SingletonTest5.instance;
	    }
   }
    
    
    /**  
     * 方法五 双重锁
     * 单例模式最优方案
     * 线程安全  并且效率高  
     * 方法四为单例模式的最佳实现。内存占用低，效率高，线程安全，多线程操作原子性。
     */  
    class SingletonTest6 { 
        // 定义一个私有构造方法
        private SingletonTest6() { 
        }   
        //定义一个静态私有变量(不初始化，不使用final关键字，使用volatile保证了多线程访问时instance变量的可见性，避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用)
        private static volatile SingletonTest6 instance;  
        //定义一个共有的静态方法，返回该类型实例
        public static SingletonTest6 getIstance() { 
            // 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
            if (instance == null) {
                //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
                synchronized (SingletonTest6.class) {
                    //未初始化，则初始instance变量
                    if (instance == null) {
                        instance = new SingletonTest6();   
                    }   
                }   
            }   
            return instance;   
        }   
    }  
    
    /**
     * 方法六，枚举单例模式
     * 1.从Java1.5开始支持; 
     * 2.无偿提供序列化机制; 
     * 3.绝对防止多次实例化，即使在面对复杂的序列化或者反射攻击的时候; 
     * 自由序列化，线程安全，保证单例
     */
     enum SingletonTest7{
    	INSTANCE;
     }
    
 public class SingletonTest {
	public static void main(String[] args) {
	}
}
