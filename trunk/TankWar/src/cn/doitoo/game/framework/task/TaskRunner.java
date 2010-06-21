package cn.doitoo.game.framework.task;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * how to use:
 * Task.add(task);
 * the task will be run at the startTime
 * @author Oliver O
 *
 */
public class TaskRunner extends TimerTask {

	public LinkedList<Task> taskList = new LinkedList<Task>();

	private static TaskRunner tr;
	
	private Timer t;
	
	private TaskRunner(){
		
	}
	public static TaskRunner getInstance(){
		if(tr ==null)	tr = new TaskRunner();
		return tr;
	}

	public void add(Task task) {
		if (t == null) {
		   t = new Timer();
		   t.scheduleAtFixedRate(this, 0, 1);//1 ms
		}
		insert(task);
	}

	

	private void insert(Task task) {
		synchronized (taskList) {
			int index = -1;
			for (int i = 0; i < taskList.size(); i++) {
				Task t = taskList.get(i);
				if (task.getStartTime() <= t.getStartTime()) {
					index=i;
					break;
				}
				
			}
			if(index!=-1){
				taskList.add(index,task);
			}
			else {// insert into the end
				taskList.add(task);
				
			}
		
		}
		
		if(!taskList.contains(task)){
			throw new RuntimeException(task.getClass().getName()+" insert failed!");
		}
	}
	


	@Override
	public void run() {
		Clock.time += 1;
		synchronized (taskList) {
		int size = taskList.size();
		
		for (int i = 0; i < size; i++) {
			Task t = taskList.get(i);
		 
		 // Log.v(this.getClass().getCanonicalName(), "t:"+t.getClass().getName()+"  startTime:"+t.getStartTime()+" interval:"+t.getIntervalTime());
		
			if(Clock.time>=t.getForceEndTime()){
			    t.setEnd(true);
				//Log.v(this.getClass().getName(), "end remove task "+t.getClass().getName());
				taskList.remove(t);
				continue;
			}
			if (t.getStartTime() <= Clock.time) {
				
			//	Log.v(this.getClass().getName(), "start remove task "+t.getClass().getName());
				taskList.remove(t);
				t.exec();
				if (t.getIntervalTime() >= 0) {
					t.setStartTime(Clock.time + t.getIntervalTime());
					insert(t);
				}

			} else {
				
				break;
			}
		}

	}
		
	}
	
}
