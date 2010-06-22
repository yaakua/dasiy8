package cn.doitoo.game.framework.task;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.doitoo.game.framework.context.GameContext;

public abstract class Task extends GameContext implements Runnable {
	/**
	 * ���Clock.time��ʱ��
	 */
	private long startTime;
	/**
	 * �����Ϊ-1,ѭ��ִ�е�ʱ����
	 */
	private long intervalTime = -1;
	
	private String name;
	
	private boolean isEnd;
	

	/**
	 * ǿ�ƽ���ʱ��
	 */
	private long forceEndTime = Long.MAX_VALUE;
	private static ExecutorService es;
	public static TaskRunner tr = TaskRunner.getInstance();
	public static LinkedList<Task> taskList = tr.taskList;
	
	protected boolean needThreadPool=true;//�Ƿ�ʹ���̳߳�
	private Task parentTask;

	public Task(){
		setName(this.getClass().getName());
	}
	
	/**
	 * ���һ������ƻ�����δ��ĳʱ������,�����ظ�����.
	 * 
	 * @param t
	 */
	public static void add(Task t) {
		tr.add(t);
	}

	/**
	 * ֻ�е���������������Żᱻִ�С�
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
	 * ��
	 */
	public void exec() {
		if (es == null) {
			es = Executors.newCachedThreadPool();// ��̬�仯���̳߳أ��ʺ϶����ڣ��첽����
		}
       if(needThreadPool)
		es.execute(this); //����������̳߳��첽��ʱִ��
       else
		run();//ͬ������ִ�У�������û��ִ���겻��ִ����һ������

	}
	
	/**
	 * ���������Լ���
	 * 
	 * @param t
	 */
	public void endTask() {
		if(this.isEnd)return;
		if(tr.taskList.contains(this))
		tr.taskList.remove(this);// TODO:���ǲ���
		this.setEnd(true);

	}



	/**
	 * δ��ʱ���ڽ�������
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
	 * �����ʵ������
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
