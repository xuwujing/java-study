package com.pancm.thread.concurrent.liveLock;

import java.util.Random;

/**
 * 
* Title: Producer
* Description:
* 消息生产者 
* Version:1.0.0  
* @author pancm
* @date 2018年3月8日
 */
public  class Producer implements Runnable {
    private Drop drop;
    public Producer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        String importantInfo[] = { "第一条数据", "第二条数据", "第三条数据",
                "第四条数据" };
        Random random = new Random();
        for (int i = 0; i < importantInfo.length; i++) {
            drop.put(importantInfo[i]);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
            }
        }
        //表示已经发送完
        drop.put("DONE");
    }
}	
