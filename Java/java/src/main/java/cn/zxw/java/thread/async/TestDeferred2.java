package cn.zxw.java.thread.async;

import com.stumbleupon.async.Callback;
import com.stumbleupon.async.Deferred;

public class TestDeferred2 {

	public static void main(String[] args) throws Exception {
		System.out.println("张三向李四借钱");
		long l = lend().joinUninterruptibly();
		System.out.println("张三向李四借钱" + l);
		System.out.println("张三开始打牌");
	}
	public static Deferred<Long> lend() {
		System.out.println("李四说回家看看家里还有多少钱");
		/**
		 * Callback -- A simple 1-argument callback interface. 
		Callbacks are typically created as anonymous classes. In order to make debugging easier, it is recommended 
		to override the Object.toString method so that it returns a string that briefly explains what the callback does.
		If you use Deferred with DEBUG logging turned on, this will be really useful to understand what's in the 
		callback chains.
		Type Parameters:
			<R> The return type of the callback.
			<T> The argument type of the callback.
		 */
		class GetCB implements Callback<Long, Long> {
			@Override
			public Long call(Long arg) throws Exception {
				return arg;
			}
		}
		/**
		 * addCallback -- Registers a callback. 
		  1.If the deferred result is already available and isn't an exception, the callback is 
		    executed immediately from this thread. 
		  2.If the deferred result is already available and is an exception, the callback is discarded. 
		  3.If the deferred result is not available, this callback is queued and will be invoked from 
		    whichever thread gives this deferred its initial result by calling callback.
		 */
		return check().addCallback(new GetCB());
	}
	public static Deferred<Long> check() {
		System.out.println("李四在家查看还有多少钱");
		try {Thread.sleep(10*1000);} catch (Exception e) {}
		/**
		  Constructs a Deferred with a result that's readily available. 
		  This is equivalent to writing: 
			   Deferred<T> d = new Deferred<T>();
			   d.callback(result);
		  Callbacks added to this Deferred will be immediately called.
		 */
		return Deferred.fromResult(new Long(1000));
	}
	/**
	 *  张三向李四借钱
		李四说回家看看家里还有多少钱
		李四在家查看还有多少钱
		张三向李四借钱1000
		张三开始打牌
	 */
}
