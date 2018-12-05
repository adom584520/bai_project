
package com.pbtd.manager.util;

public class  WorkThread extends Thread {
	
	  @Override
	    public void run() {
	        try {
	            System.out.println(getName() + "run start.");
	            //模拟完成子任务执行的时间
	            sleep(1000);
	            System.out.println(getName() + "run finished.");
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }


	 public static void main(String[] args) {
	        WorkThread workThread1 = new WorkThread();
	        WorkThread workThread2 = new WorkThread();
	        workThread1.start();
	        workThread2.start();
	      /*  //阻塞Main线程，执行子线程workThread1和workThread2，完毕后继续执行后续的逻辑
	        try {
	            workThread1.join();
	            workThread2.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }*/
	 
	        System.out.println("run next process.");
	    }



}
