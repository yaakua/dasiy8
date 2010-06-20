package cn.doitoo.game.framework.task;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

public abstract class Task implements Runnable {
	/**
	 * ���Clock.time��ʱ��
	 */
	private long startTime;
	/**
	 * �����Ϊ-1,ѭ��ִ�е�ʱ����
	 */
	private long intervalTime = -1;
	/**
	 * ǿ�ƽ���ʱ��
	 */
	private long forceEndTime = Long.MAX_VALUE;
	private static ExecutorService es;
	public static TaskRunner tr = TaskRunner.getInstance();
	public static LinkedList<Task> taskList = tr.taskList;
	
	protected boolean needThreadPool=true;//�Ƿ�ʹ���̳߳�

	/**
	 * ���һ������ƻ�����δ��ĳʱ������,�����ظ�����.
	 * 
	 * @param t
	 */
	public static void add(Task t) {
		tr.add(t);
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
		doTask();//ͬ������ִ�У�������û��ִ���겻��ִ����һ������

	}
	
	/**
	 * ���������Լ���
	 * 
	 * @param t
	 */
	public void endTask() {
		tr.taskList.remove(this);// TODO:���ǲ���

	}

	/**
	 * ��������һ�������������������Լ���
	 * 
	 * @param t
	 */
	public void endTask(Task t) {
		tr.taskList.remove(t);// TODO:���ǲ���

	}

	/**
	 * ��δ��ʱ���ڽ���һ������
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
	 * �����ʵ������
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
