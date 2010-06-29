package cn.doitoo.game.tankwar.event;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.enums.GameStatus;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.framework.thread.GameDrawThread;
import cn.doitoo.game.tankwar.task.tank.PlayerHeroTankTask;
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
				SurfaceHolder holder = (SurfaceHolder)G.get("holder");
				//������Ϸ���߳�
				GameDrawThread gameDrawThread0 = GameDrawThread.getInstance(holder);
				
				//��ʼ���Ƶ�ͼtask
				DrawMapTask drawMapTask = new DrawMapTask();
                //�������Ӣ��̹��
				PlayerHeroTankTask playerHeroTankTask = new PlayerHeroTankTask();
				
				//����������
				gameDrawThread0.add(drawMapTask).add(playerHeroTankTask);
				gameDrawThread0.setGameStauts(GameStatus.RUNING);				
			
				ITouchEventHandler.touchList.add(new GestureMoveEvent());
				if (ITouchEventHandler.touchList.contains(this))
					ITouchEventHandler.touchList.remove(this);
				
				gameDrawThread0.start();
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
