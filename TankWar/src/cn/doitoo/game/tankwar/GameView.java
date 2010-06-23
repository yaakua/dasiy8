package cn.doitoo.game.tankwar;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.graphic.DoitooView;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.tankwar.event.TankTouchEvent;
import cn.doitoo.game.tankwar.task.TankSpriteTask;

public class GameView extends DoitooView {
	public static final String TAG = "GameView";
	public GameView(Context context) {	
		super(context);
		ITouchEventHandler.touchList.add(new TankTouchEvent());
        Log.d(TAG, "GameView");
	}

	@Override
	protected void initTasks(SurfaceHolder holder) {
		 Log.d(TAG, "initTasks");
		 Context context = getContext();
		 Task tankSpriteTask = new TankSpriteTask(holder,context);
         Task.add(tankSpriteTask);
         //Task.add(new TankMapTask(),tankSpriteTask);
         //...
	}



}
