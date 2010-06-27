package cn.doitoo.game.tankwar.event;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.tankwar.event.tank.PlayerHeroTankTask;
import cn.doitoo.game.tankwar.task.DrawMapTask;
import cn.doitoo.game.tankwar.task.TankSpriteTask;

public class TankTouchEvent extends ITouchEventHandler {
	private Rect startGameRect = new Rect(100, 150, 300, 200);// 开始键在屏幕当中的坐标范围
	
	@Override
	public void onTouchDown(MotionEvent event) {
		Task t = Task.taskByName(TankSpriteTask.class.getName());
		int ax = (int) event.getX();
		int ay = (int) event.getY();
		if(this.startGameRect.contains(ax, ay)){
			Log.d("TankTouchEvent", t==null?"null":"not null");
			if (t != null){
				t.endTask();
				//开始绘制地图task
				DrawMapTask drawMapTask = new DrawMapTask();
				Task.add(drawMapTask);
                //绘制玩家英雄坦克
                PlayerHeroTankTask playerHeroTankTask = new PlayerHeroTankTask();
                Task.add(playerHeroTankTask);
				ITouchEventHandler.touchList.add(new GestureMoveEvent());
				if (ITouchEventHandler.touchList.contains(this))
					ITouchEventHandler.touchList.remove(this);
			}
		}
	}

	@Override
	public void onTouchMove(MotionEvent event) {
		onTouch(event);

	}

	@Override
	public void onTouchUp(MotionEvent event) {
		onTouch(event);

	}

	private void onTouch(MotionEvent event) {
		
	}

}
