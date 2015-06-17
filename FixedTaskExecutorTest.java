package com.oracle.java.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class StringTask implements Callable<String> {
		private int taskId;
		
		public StringTask(int taskId) {
			this.taskId = taskId;
		}
	
	   public String call(){
	      //Long operations
		   for (int i = 0; i < 5; i++) {
			try {
				Thread.currentThread().sleep(1000);
				System.out.println("now sleep for 1 second." + Thread.currentThread().getName() + " task id: " + this.taskId);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	      return "Run";
	   }
	}

public class FixedTaskExecutorTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long time1 = System.currentTimeMillis();
		ExecutorService pool = Executors.newFixedThreadPool(50);

		List<Future<String>> futures = new ArrayList<Future<String>>(100);
		
		for(int i = 0; i < 100; i++){
			futures.add(pool.submit(new StringTask(i)));
		}
		
		for(Future<String> future : futures){
		   String result = future.get();
		}
		
		System.out.println("cost time:" + (System.currentTimeMillis() - time1));
		pool.shutdown();
	}
}
