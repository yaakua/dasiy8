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
	private Rect startGameRect = new Rect(100, 150, 300, 200);// ��ʼ������Ļ���е����귶Χ
	
	@Override
	public void onTouchDown(MotionEvent event) {
		Task t = Task.taskByName(TankSpriteTask.class.getName());
		int ax = (int) event.getX();
		int ay = (int) event.getY();
		if(this.startGameRect.contains(ax, ay)){
			Log.d("TankTouchEvent", t==null?"null":"not null");
			if (t != null){
				t.endTask();
				//��ʼ���Ƶ�ͼtask
				DrawMapTask drawMapTask = new DrawMapTask();
				Task.add(drawMapTask);
                //�������Ӣ��̹��
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
