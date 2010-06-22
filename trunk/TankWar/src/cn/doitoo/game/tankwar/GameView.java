package cn.doitoo.game.tankwar;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.graphic.DoitooView;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.event.TankTouchEvent;
import cn.doitoo.game.tankwar.task.TankMapTask;
import cn.doitoo.game.tankwar.task.TankSpriteTask;

public class GameView extends DoitooView {

	public GameView(Context context) {	
		super(context);
		ITouchEventHandler.touchList.add(new TankTouchEvent());
        Log.d("GameView", "GameView");
	}

	@Override
	protected void initTasks(SurfaceHolder holder) {
		 Log.d("initTasks", "initTasks");
		 Task tankSpriteTask = new TankSpriteTask(holder, Util.getBitMapById(getContext(), R.drawable.loadmain));
         Task.add(tankSpriteTask);
         //Task.add(new TankMapTask(),tankSpriteTask);
         //...
	}



}
