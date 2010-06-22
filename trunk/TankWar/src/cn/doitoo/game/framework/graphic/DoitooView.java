package cn.doitoo.game.framework.graphic;



import java.util.List;

import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.task.Clock;
import cn.doitoo.game.framework.task.Task;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public  abstract class DoitooView extends SurfaceView implements SurfaceHolder.Callback {
    
	public DoitooView(Context context) {
		super(context);
	  
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for(ITouchEventHandler handlerList :ITouchEventHandler.touchList)
				handlerList.onTouchDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			for(ITouchEventHandler handlerList :ITouchEventHandler.touchList)
				handlerList.onTouchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			for(ITouchEventHandler handlerList :ITouchEventHandler.touchList)
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
		  Log.d("surfaceCreated", "surfaceCreated");
         startGame(holder);

	}

	private void startGame(SurfaceHolder holder) {
		for(int i=0;i<Task.taskList.size();i++){
			Task.taskList.get(i).endTask();
		}
		Task.taskList.clear();
		Clock.time = 0;
		initTasks(holder);
	}
    /**
     * add Task here to start game
     */
	protected abstract void initTasks(SurfaceHolder holder) ;

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
