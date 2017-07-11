package org.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest1 {

	private static Lock lock = new ReentrantLock();
	private static Condition condition = lock.newCondition();

	public static void main(String[] args) {
		ThreadA ta = new ThreadA("线程A");
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + "线程启动线程A!");
			ta.start();
			System.out.println(Thread.currentThread().getName() + "线程阻塞!");
			condition.await();
			System.out.println(Thread.currentThread().getName() + "线程继续执行!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	static class ThreadA extends Thread {

		public ThreadA(String name) {
			super(name);
		}

		@Override
		public void run() {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "开始执行!");
				System.out.println(Thread.currentThread().getName() + "唤醒其它线程!");
				condition.signal();
			} finally {
				lock.unlock();
			}
		}

	}

}