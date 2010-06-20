package cn.doitoo.game.framework.event;

import android.view.MotionEvent;

public interface ITouchEventHandler {

	void onTouchDown(MotionEvent event);

	void onTouchMove(MotionEvent event);

	void onTouchUp(MotionEvent event);
  
}
