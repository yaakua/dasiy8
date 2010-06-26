package cn.doitoo.game.tankwar.event;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;

public class MapMoveEvent extends ITouchEventHandler {
	private DoitooMap map = null;
	private GestureDetector gestureDetector;

	public MapMoveEvent() {
		map = (DoitooMap) G.get(DoitooMap.class.getName());
		gestureDetector = new GestureDetector(new MyGestureDetector());
	}

	@Override
	public void onTouchDown(MotionEvent event) {
	}

	@Override
	public void onTouchMove(MotionEvent event) {
		if (map == null) {
			throw new ViewException("map is null");
		}
		gestureDetector.onTouchEvent(event);
		// if (gestureDetector.onTouchEvent(event))
		// return true;
		// else
		// return false;
	}

	@Override
	public void onTouchUp(MotionEvent event) {

	}

	class MyGestureDetector extends SimpleOnGestureListener {
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			Log.i("Fling","onFling");
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				Log.i("Fling", "left");
			}

			return super.onFling(e1, e2, velocityX, velocityY);
		}

	}

}
