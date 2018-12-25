package com.pancm.question;

import java.util.BitSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @Title: QuestionTest4
* @Description: 三个线程，打印ABC
* @Version:1.0.0  
* @author pancm
* @date 2018年12月25日
*/
public class QuestionTest4 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 只打印一次

//		printOnceLatch();
		System.out.println("");
//		try {
//			printOnceLatch2();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		System.out.println("");

		// 十次
//		printTenTimesSemaphore();
//		printTenTimes();
		printTenTimesWaitNotif();
		
	}

	// 利用jdk1.5的CountDownLatch方法实现
	private static void printOnceLatch() {
		final CountDownLatch countDownLatchForB = new CountDownLatch(1); // 为 B 准备的闭锁，只有该闭锁倒数到 0 ，B 才可以运行
		final CountDownLatch countDownLatchForC = new CountDownLatch(1); // 为 C 准备的闭锁，只有该闭锁倒数到 0 ，C 才可以运行
		final Thread threadA = new Thread() {
			@Override
			public void run() {
				System.out.print("A");
				// B 要在 A 运行完才能运行，就由 A 来倒数
				countDownLatchForB.countDown();
			}
		};
		final Thread threadB = new Thread() {
			@Override
			public void run() {
				try {
					// 等待 A （使用给 B 的闭锁）倒数
					countDownLatchForB.await();
					System.out.print("B");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					countDownLatchForC.countDown();
				}
			}
		};
		final Thread threadC = new Thread() {
			@Override
			public void run() {
				try {
					countDownLatchForC.await();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					System.out.print("C");
				}
			}
		};
		threadA.start();
		threadB.start();
		threadC.start();
	}

//利用join来进行
	private static void printOnceLatch2() throws InterruptedException {
		final Thread threadA = new Thread() {
			@Override
			public void run() {
				System.out.print("A");
			}
		};
		final Thread threadB = new Thread() {
			@Override
			public void run() {
				try {
					System.out.print("B");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		final Thread threadC = new Thread() {
			@Override
			public void run() {
				try {
					System.out.print("C");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		};
		threadA.start();
		threadA.join();
		threadB.start();
		threadB.join();
		threadC.start();
		threadC.join();
	}

	/*
	 * 使用信号量 Semaphore 类，三个线程每一个都有一个信号量，信号量等于 0 将被阻塞， 等于 1
	 * 可以运行，因为每次只能有一个线程在运行，因此信号量总和应该为 1 ，相当于一个令牌在传递一样。
	 */
	private static void printTenTimesSemaphore() {
		final Semaphore semaphoreForA = new Semaphore(1); // A 最开始执行, 所以是 1
		final Semaphore semaphoreForB = new Semaphore(0);
		final Semaphore semaphoreForC = new Semaphore(0);
		final Thread threadA = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						semaphoreForA.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						System.out.print("A");
						semaphoreForB.release(); // 给 B 的信号加 1 ,让 B 可以获得信号去执行
					}
				}
			}
		};
		final Thread threadB = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						semaphoreForB.acquire(); // 申请信号, 只有当信号量大于 0 时才不会被阻塞
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						System.out.print("B");
						semaphoreForC.release(); // 给 C 的信号加 1, 让 C 可以获得信号执行
					}
				}
			}
		};
		final Thread threadC = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					try {
						semaphoreForC.acquire();
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						System.out.print("C");
						semaphoreForA.release();
					}
				}
			}
		};
		threadA.start();
		threadB.start();
		threadC.start();
	}

	/**
	 * 使用 j.u.c 包中的原子变量，该变量的效果实际和令牌一样，只是根据该变量的状态来判断当前线程是不是可以运行，
	 * 代码用到了 yield 方法，该方法可以让当前线程主动放弃执行权。
	 */
	private static void printTenTimes() {
		final AtomicInteger token = new AtomicInteger(0);
		final Thread threadA = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					while (token.get() % 3 != 0) {
						yield(); // 不是 A 运行的时候, 让出执行权
					}
					System.out.print("A");
					token.incrementAndGet(); // 由于增加 1 后被 3 模余数 1, 而被 3 模余数 1 是 B 可以运行的, 此步相当于把令牌传递给 B
				}
			}
		};
		final Thread threadB = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					while (token.get() % 3 != 1) {
						yield();
					}
					System.out.print("B");
					token.incrementAndGet();
				}
			}
		};
		final Thread threadC = new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					while (token.get() % 3 != 2) {
						yield();
					}
					System.out.print("C");
					token.incrementAndGet();
				}
			}
		};
		threadA.start();
		threadB.start();
		threadC.start();
	}

	/**
	 * 使用 wait 和 notify 方法实现
	 * 使用到了 BitSet 类，其中的 mutex 变量作为三个线程的互斥锁
	 */
	private static void printTenTimesWaitNotif () {
	    final BitSet mutex = new BitSet (3);
	    final Thread threadA = new Thread () {
	        @Override
	        public void run () {
	            for (int i = 0; i < 10; i++) {
	                try {
	                    synchronized (mutex) {
	                        while (!mutex.get (0)) {
	                            mutex.wait ();
	                        }
	                        System.out.print ("A");
	                        mutex.clear (0);
	                        mutex.set (1);
	                        mutex.notify ();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace ();
	                }
	            }
	        }
	    };
	    final Thread threadB = new Thread () {
	        @Override
	        public void run () {
	            for (int i = 0; i < 10; i++) {
	                try {
	                    synchronized (mutex) {
	                        while (!mutex.get (1)) {
	                            mutex.wait ();
	                        }
	                        System.out.print ("B");
	                        mutex.clear (1);
	                        mutex.set (2);
	                        mutex.notify ();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace ();
	                }
	            }
	        }
	    };
	    final Thread threadC = new Thread () {
	        @Override
	        public void run () {
	            for (int i = 0; i < 10; i++) {
	                try {
	                    synchronized (mutex) {
	                        while (!mutex.get (2)) {
	                            mutex.wait ();
	                        }
	                        System.out.print ("C");
	                        mutex.clear (2);
	                        mutex.set (0);
	                        mutex.notify ();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace ();
	                }
	            }
	        }
	    };
	    threadA.start ();
	    threadB.start ();
	    threadC.start ();
	    synchronized (mutex) {
	        mutex.set (0);
	        mutex.notify ();
	    }
	}
	
}
