package cn.doitoo.game.framework.graphic;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.task.Clock;
import cn.doitoo.game.framework.task.Task;

public abstract class DoitooView extends SurfaceView implements
		SurfaceHolder.Callback {

	public DoitooView(Context context) {
		super(context);
		SurfaceHolder holder = getHolder();
		this.setFocusable(true);
		holder.addCallback(this);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for (ITouchEventHandler handlerList : ITouchEventHandler.touchList)
				handlerList.onTouchDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			for (ITouchEventHandler handlerList : ITouchEventHandler.touchList)
				handlerList.onTouchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			for (ITouchEventHandler handlerList : ITouchEventHandler.touchList)
				handlerList.onTouchUp(event);
			break;
		}

		return true;
	}

	
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void surfaceCreated(SurfaceHolder holder) {
	   startGame(holder);
		
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		System.exit(1);
		
	}

	private void startGame(SurfaceHolder holder) {
		for (int i = 0; i < Task.taskList.size(); i++) {
			Task.taskList.get(i).endTask();
		}
		Task.taskList.clear();
		Clock.time = 0;
		initTasks(holder);
	}

	/**
	 * add Task here to start game
	 */
	protected abstract void initTasks(SurfaceHolder holder);

}
