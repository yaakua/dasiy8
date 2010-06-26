package cn.doitoo.game.framework.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * how to use: Task.add(task); the task will be run at the startTime
 * 
 * @author Oliver O
 * 
 */
public class TaskRunner extends TimerTask {
    /**
     * ����ִ�е�������У����Ƕ�̬�仯�ģ����ڶ�����ǰ�˵���������ִ�У��Դ�����
     */
	public LinkedList<Task> taskList = new LinkedList<Task>();
	/**
	 * ��ɾ���������
	 */
	private List<Task> removeList = new ArrayList<Task>();
	/**
	 * ��Ҫ���µ���λ�õĶ���
	 */
	private List<Task> replaceList = new ArrayList<Task>();

	private static TaskRunner tr;

	private Timer t;


	private TaskRunner() {

	}

	public static TaskRunner getInstance() {
		if (tr == null)
			tr = new TaskRunner();
		return tr;
	}

	public void add(Task task) {
		if (t == null) {
			t = new Timer();
			t.scheduleAtFixedRate(this, 0, 1);// 1 ms
		}
		insert(task);
	}

	/**
	 * ���ǰ�����������ʱ���������
	 * 
	 * @param task
	 */
	private void insert(Task task) {
		synchronized (taskList) {
			int index = -1;
			for (int i = 0; i < taskList.size(); i++) {
				Task t = taskList.get(i);
				if (task.getStartTime() <= t.getStartTime()) {
					index = i;
					break;
				}

			}
			if (index != -1) {
				taskList.add(index, task);
			} else {// insert into the end
				taskList.add(task);

			}

			if (!taskList.contains(task)) {
				throw new RuntimeException(task.getClass().getName()
						+ " insert failed!");
			}
		}

	}

	@Override
	public void run() {
		Clock.time += 1;
		synchronized (taskList) {
			removeList.clear();
			replaceList.clear();
			int size = taskList.size();
			for (int i = 0; i < size; i++) {
				Task t = taskList.get(i);
				if (Clock.time >= t.getForceEndTime()) {
					t.setEnd(true);
				}
				if (t.isEnd()) {
					removeList.add(t);
					continue;
				}

				if (t.getStartTime() <= Clock.time) {
					removeList.add(t);
					t.exec();
					if (t.getIntervalTime() >= 0) {
						t.setStartTime(Clock.time + t.getIntervalTime());
						replaceList.add(t);
					}

				} else {

					break;
				}
			}

			for (Task removeTask : removeList) {
				taskList.remove(removeTask);

			}

			for (Task replaceTask : replaceList) {
				insert(replaceTask);
			}

		}

	}

}
