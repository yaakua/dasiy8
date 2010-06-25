package cn.doitoo.game.tankwar;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.context.GameContext;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.graphic.DoitooView;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.tankwar.event.TankTouchEvent;
import cn.doitoo.game.tankwar.task.TankSpriteTask;

public class GameView extends DoitooView {
	public static final String TAG = "GameView";
	public GameView(Context context) {	
		super(context);
        Log.d(TAG, "GameView");
	}

	@Override
	protected void initTasks(SurfaceHolder holder) {
		 Log.d(TAG, "initTasks");
		 //第一步必须 初始化 GameContext
		 Context context = getContext();
		 GameContext.init(holder, context);
		 ITouchEventHandler.touchList.add(new TankTouchEvent());
		 
		 Task tankSpriteTask = new TankSpriteTask();
         Task.add(tankSpriteTask);
	}



}
