package com.engine;


// Each thread has an object of SendMessage
public class MyRunnable implements Runnable {

	public void run() {
		new SendMessage().sendMessage();
	}
}
