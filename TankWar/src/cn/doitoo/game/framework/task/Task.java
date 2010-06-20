package cn.doitoo.game.framework.task;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

public abstract class Task implements Runnable {
	/**
	 * 相对Clock.time的时间
	 */
	private long startTime;
	/**
	 * 如果不为-1,循环执行的时间间隔
	 */
	private long intervalTime = -1;
	/**
	 * 强制结束时间
	 */
	private long forceEndTime = Long.MAX_VALUE;
	private static ExecutorService es;
	public static TaskRunner tr = TaskRunner.getInstance();
	public static LinkedList<Task> taskList = tr.taskList;
	
	protected boolean needThreadPool=true;//是否使用线程池

	/**
	 * 添加一个任务计划，在未来某时刻运行,或者重复运行.
	 * 
	 * @param t
	 */
	public static void add(Task t) {
		tr.add(t);
	}

	/**
	 * 。
	 */
	public void exec() {
		if (es == null) {
			es = Executors.newCachedThreadPool();// 动态变化的线程池，适合短周期，异步任务。
		}
       if(needThreadPool)
		es.execute(this); //将任务放入线程池异步延时执行
       else
		doTask();//同步立即执行，在任务没有执行完不会执行下一个任务。

	}
	
	/**
	 * 立即结束自己。
	 * 
	 * @param t
	 */
	public void endTask() {
		tr.taskList.remove(this);// TODO:考虑并发

	}

	/**
	 * 立即结束一个任务，这个任务可以是自己。
	 * 
	 * @param t
	 */
	public void endTask(Task t) {
		tr.taskList.remove(t);// TODO:考虑并发

	}

	/**
	 * 在未来时间内结束一个任务
	 * 
	 * @param t
	 * @param time
	 */
	public void endTask(Task t, long time) {

		t.setForceEndTime(time);
	}

	public void sendMessage(Task t, Object msg) {
		t.receiveMessage(this, msg);

	}

	public void receiveMessage(Task sender, Object msg) {
	};

	/**
	 * 任务的实际内容
	 */
	protected abstract void doTask();

	public void run() {//AOP
		
		doTask();
		//Log.v("Task",this.getClass() + " lastStartTime:" + getStartTime()
				//+ " now :" + Clock.time);

	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setIntervalTime(long intervalTime) {
		this.intervalTime = intervalTime;
	}

	public long getIntervalTime() {
		return intervalTime;
	}

	public void setForceEndTime(long forceEndTime) {
		if (forceEndTime < Clock.time)
			throw new RuntimeException("time is less than clock time");
		this.forceEndTime = forceEndTime;
	}

	public long getForceEndTime() {
		return forceEndTime;
	}
}
