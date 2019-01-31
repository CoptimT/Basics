package cn.zxw.java.thread.async;

import java.util.concurrent.Executors;

import com.stumbleupon.async.Deferred;

public class TestDeferred {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " start...");
		
		Deferred<String> deferred = new Deferred<String>().addBoth(str -> {
			System.out.println(Thread.currentThread().getName() + " 1 " + str.toString());
			return str;
		}).addBothDeferring(str -> {
			System.out.println(Thread.currentThread().getName() + " 2 " + str);
			
			Deferred<String> d = new Deferred<>().addBoth(s -> {
				System.out.println(Thread.currentThread().getName() + " 3 " + s.toString());
				return s.toString();
			});

			Executors.newSingleThreadExecutor().execute(() -> {
				System.out.println(Thread.currentThread().getName() + " started.");
				d.callback("hello");
			});
			return d;
		}).addBoth(str -> {
			System.out.println(Thread.currentThread().getName() + " 4 " + str.toString());
			return str;
		}).addErrback(str -> {
			System.out.println(str.toString());
			return str;
		});

		Executors.newSingleThreadExecutor().execute(() -> {
			System.out.println(Thread.currentThread().getName() + " started.");
			// deferred.callback("hello");
			deferred.callback(new NullPointerException("error."));
		});

		System.out.println(Thread.currentThread().getName() + " sleeping...");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  main start...
		main sleeping...
		pool-1-thread-1 started.
		pool-1-thread-1 1 java.lang.NullPointerException: error.
		pool-1-thread-1 2 java.lang.NullPointerException: error.
		pool-2-thread-1 started.
		pool-2-thread-1 3 hello
		pool-1-thread-1 4 hello
	 */
}
