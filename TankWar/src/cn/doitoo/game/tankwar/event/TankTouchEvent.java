package cn.doitoo.game.tankwar.event;

import android.view.MotionEvent;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.tankwar.task.TankSpriteTask;

public class TankTouchEvent extends ITouchEventHandler {

	@Override
	public void onTouchDown(MotionEvent event) {
		onTouch(event);
		// todo
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
		Task t = Task.taskByName(TankSpriteTask.class.getName());
		if (t != null)
			t.endTask();
		if (ITouchEventHandler.touchList.contains(this))
			ITouchEventHandler.touchList.remove(this);

	}

}
