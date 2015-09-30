package com.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//this class create as many threads as numbers of cores of cpu are available.
//Each thread maintains a queue of DatabaseRow and send mails one by one
public class MainExecutor extends Thread {

	@Override
	public void run() {
		mainExecutor();
	}

	void mainExecutor() {

		int nofprocessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(nofprocessors);
		System.out.println(nofprocessors);

		for (int i = 0; i < nofprocessors; i++) {
			
			Runnable worker = new MyRunnable();
			executor.execute(worker);
		}
		try {
			
			// shut down will not allow any new task to accept 
			// but it will wait for already submitted task to finish
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.HOURS);
		} catch (InterruptedException ex) {
			System.out.println(ex.getMessage());
		} finally {
			System.out.println("No emails left, job completed");
			System.exit(0);
		}
	}
}