package com.pancm.thread.concurrent.liveLock;

import java.util.Random;


public  class Consumer implements Runnable {
    private Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }
    public void run() {
        Random random = new Random();
//        String message="";
//        do{
//        	message= drop.take();
//        	System.out.format("MESSAGE RECEIVED: %s%n", message);
//        	try {
//                Thread.sleep(random.nextInt(1000));
//            } catch (InterruptedException e) {
//            	e.printStackTrace();
//            }
//        } while(!message.equals("DONE"));
        
        for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
            System.out.format("MESSAGE RECEIVED: %s%n", message);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
        }
    }
}
