package cn.zxw.java.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest {

	public static void main(String[] args) {
		int N = 4;
        CyclicBarrier barrier  = new CyclicBarrier(N,new Runnable() {
        	//1.从结果可以看出，当四个线程都到达barrier状态后，会从四个线程中选择一个去执行Runnable。
			@Override
			public void run() {
				System.out.println("当前线程"+Thread.currentThread().getName());
			}
		});
        for(int i=0;i<N;i++) {
        	new Writer(barrier).start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2.CyclicBarrier重用");
        for(int i=0;i<N;i++) {
            new Writer(barrier).start();
        }
	}
	static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
 
        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(3000); //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待..."+System.currentTimeMillis());
                //cyclicBarrier.await();
                cyclicBarrier.await(1000,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }

}
