package cn.doitoo.game.framework.graphic;



import java.util.List;

import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.task.Clock;
import cn.doitoo.game.framework.task.Task;
import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public  abstract class DoitooView extends SurfaceView implements SurfaceHolder.Callback {
	private  List<ITouchEventHandler> touchEventHandlerList;

	public DoitooView(Context context,List<ITouchEventHandler> touchEventHandlerList) {
		super(context);
	    this.touchEventHandlerList =touchEventHandlerList;
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(this.touchEventHandlerList==null)return true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for(ITouchEventHandler handlerList :touchEventHandlerList)
				handlerList.onTouchDown(event);
			break;
		case MotionEvent.ACTION_MOVE:
			for(ITouchEventHandler handlerList :touchEventHandlerList)
				handlerList.onTouchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			for(ITouchEventHandler handlerList :touchEventHandlerList)
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
