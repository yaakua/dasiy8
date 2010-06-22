package cn.doitoo.game.framework.task;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.doitoo.game.framework.context.GameContext;

public abstract class Task extends GameContext implements Runnable {
	/**
	 * 相对Clock.time的时间
	 */
	private long startTime;
	/**
	 * 如果不为-1,循环执行的时间间隔
	 */
	private long intervalTime = -1;
	
	private String name;
	
	private boolean isEnd;
	

	/**
	 * 强制结束时间
	 */
	private long forceEndTime = Long.MAX_VALUE;
	private static ExecutorService es;
	public static TaskRunner tr = TaskRunner.getInstance();
	public static LinkedList<Task> taskList = tr.taskList;
	
	protected boolean needThreadPool=true;//是否使用线程池
	private Task parentTask;

	public Task(){
		setName(this.getClass().getName());
	}
	
	/**
	 * 添加一个任务计划，在未来某时刻运行,或者重复运行.
	 * 
	 * @param t
	 */
	public static void add(Task t) {
		tr.add(t);
	}

	/**
	 * 只有当父任务结束后，它才会被执行。
	 * @param t
	 * 
	 * @param parentTask
	 */
	public static void add(Task t,Task parentTask){
		tr.add(t);
		t.setParentTask(parentTask);
	}
	
	


	public static Task taskByName(String name){
		for(Task task:taskList){
              if(task.getName().equals(name))return task;			
		}
		return null;
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
		run();//同步立即执行，在任务没有执行完不会执行下一个任务。

	}
	
	/**
	 * 立即结束自己。
	 * 
	 * @param t
	 */
	public void endTask() {
		if(this.isEnd)return;
		if(tr.taskList.contains(this))
		tr.taskList.remove(this);// TODO:考虑并发
		this.setEnd(true);

	}



	/**
	 * 未来时间内结束任务
	 * 
	 * @param t
	 * @param time
	 */
	public void endTask(long time) {
		if(isEnd)return;
		setForceEndTime(time);
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
	   if(parentTask!=null&&parentTask.isEnd)
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




	public void setName(String name) {
		this.name = name;
	}




	public String getName() {
		return name;
	}

	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	public boolean isEnd() {
		return isEnd;
	}

	private void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public Task getParentTask() {
		return parentTask;
	}
}
