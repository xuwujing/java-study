package com.pancm.others;
import java.util.Calendar;
import java.util.Date;
/**
 * @author ZERO
 * @Data 2017-6-3 下午2:37:04
 * @Description 
 */
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;  
  

/**
 * 
* @Title: TimerTest
* @Description: 定时器测试代码
* @Version:1.0.0  
* @author pancm
* @date 2017年6月13日
 */
public class TimerTest {  
    public static void main(String[] args) {  
//    	timer1();
//    	timer2();
    	timer3();
//    	timer4();
//    	timer5();
//    	timer6();
//    	timer7();
    }  
    
    
    /** 
     * 普通thread 
     * 这是最常见的，创建一个thread，然后让它在while循环里一直运行着， 
     * 通过sleep方法来达到定时任务的效果。这样可以快速简单的实现，代码如下： 
     */ 
    public static void timer1(){
    	  final long timeInterval = 1000;  
          Runnable runnable = new Runnable() {  
              public void run() {  
                  while (true) {  
                      // ------- code for task to run  
                      System.out.println("timer1 Hello !!");  
                      // ------- ends here  
                      try {  
                          Thread.sleep(timeInterval);  
                      } catch (InterruptedException e) {  
                          e.printStackTrace();  
                      }  
                  }  
              }  
          };  
          Thread thread = new Thread(runnable);  
          thread.start();  
    }
   
    /** 
     * 于第一种方式相比，优势 1>当启动和去取消任务时可以控制 2>第一次执行任务时可以指定你想要的delay时间 
     * 在实现时，Timer类可以调度任务，TimerTask则是通过在run()方法里实现具体任务。 Timer实例可以调度多任务，它是线程安全的。 
     * 当Timer的构造器被调用时，它创建了一个线程，这个线程可以用来调度任务。 下面是代码： 
     */ 
    public static void timer2(){
    	 TimerTask task = new TimerTask() {  
             @Override  
             public void run() {  
                 // task to run goes here  
                 System.out.println("timer2 Hello !!!");  
             }  
         };  
         Timer timer = new Timer();  
         long delay = 0;  
         long intevalPeriod = 1 * 1000;  
         // schedules the task to be run in an interval  
         timer.scheduleAtFixedRate(task, delay, intevalPeriod);  
    }
    
    /** 
     * ScheduledExecutorService是从Java SE5的java.util.concurrent里，做为并发工具类被引进的，这是最理想的定时任务实现方式。  
     * 相比于上两个方法，它有以下好处： 
     * 1>相比于Timer的单线程，它是通过线程池的方式来执行任务的  
     * 2>可以很灵活的去设定第一次执行任务delay时间 
     * 3>提供了良好的约定，以便设定执行的时间间隔 
     * 下面是实现代码，我们通过ScheduledExecutorService#scheduleAtFixedRate展示这个例子，通过代码里参数的控制，首次执行加了delay时间。 
     */
    public static void timer3(){
    	 Runnable runnable = new Runnable() {  
             public void run() {  
                 // task to run goes here  
                 System.out.println("timer3  Hello !!");  
             }  
         };  
         ScheduledExecutorService service = Executors  .newSingleThreadScheduledExecutor();  
         // 第二个参数为首次执行的延时时间10s,，第三个参数为定时执行的间隔时间   2s
         service.scheduleAtFixedRate(runnable, 10, 2, TimeUnit.SECONDS); 
    }
    
    
    // 设定指定任务task在指定时间time执行 schedule(TimerTask task, Date time)
    public static void timer4() {
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        public void run() {
          System.out.println("timer1()  -------设定要指定任务--------");
        }
      }, 2000);// 设定指定的时间time,此处为2000毫秒
    }
   
    // 设定指定任务task在指定延迟delay后进行固定延迟peroid的执行
    // schedule(TimerTask task, long delay, long period)
    public static void timer5() {
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        public void run() {
          System.out.println("timer2()  -------设定要指定任务--------");
        }
      }, 1000, 5000);
    }
   
    // 设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
    // scheduleAtFixedRate(TimerTask task, long delay, long period)
    public static void timer6() {
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
        public void run() {
          System.out.println("timer3()  -------设定要指定任务--------");
        }
      }, 1000, 2000);
    }
     
    // 安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．
    // Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)
    public static void timer7() {
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
      calendar.set(Calendar.MINUTE, 0);    // 控制分
      calendar.set(Calendar.SECOND, 0);    // 控制秒
   
      Date time = calendar.getTime();     // 得出执行任务的时间,此处为今天的12：00：00
   
      Timer timer = new Timer();
      timer.scheduleAtFixedRate(new TimerTask() {
        public void run() {
          System.out.println("timer4()  -------设定要指定任务--------");
        }
      }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
}
