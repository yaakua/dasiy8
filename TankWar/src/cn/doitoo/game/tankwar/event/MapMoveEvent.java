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
    /**
     * 当前屏幕高度
     */
    private float screenHeight;
    /**
     * 当前屏幕宽度
     */
    private float screenWidth;
    /**
     * 触摸误差范围
     */
    private int touchRect = 5;

    /**
     * 地图移动速度
     */
    private int speed = 10;

    public MapMoveEvent() {
        map = (DoitooMap) G.get(DoitooMap.class.getName());
        gestureDetector = new GestureDetector(new MyGestureDetector());
        screenHeight = (Float) G.get("screenHeight");
        screenWidth = (Float) G.get("screenWidth");

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
        public boolean onSingleTapUp(MotionEvent e) {
            Log.i("Fling", "onSingleTapUp");
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.i("Fling", "onLongPress");
            super.onLongPress(e);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.i("Fling", "onScroll");
            float x = map.getX();
            float y = map.getY();

            if (distanceX > 0 && Math.abs(distanceX) > touchRect) {  //Left
                x -= speed;
//                if (x >= screenWidth - map.getWidth())
//                    x = (screenWidth - map.getWidth());
                Log.d("Fling", "left");
            } else if (distanceX < 0 && Math.abs(distanceX) > touchRect) { //right
                x += speed;
//                if (x <= 0) x = 0;
                Log.d("Fling", "right");
            } else if (distanceY > 0 && Math.abs(distanceY) > touchRect) {   //top
                y -= speed;
//                if (y >= screenHeight - map.getHeight())
//                    y = (screenHeight - map.getHeight());

                Log.d("Fling", "top");
            } else if (distanceY < 0 && Math.abs(distanceY) > touchRect) {    //bottom
                 y += speed;
//                if (y <= 0) y = 0;
                Log.d("Fling", "bottom");
            }
            map.setPosition(x, y);

            /*else
            if (distanceX < 0 && distanceY < 0) {
                Log.i("Fling", "right bottom");
                map.setX(map.getX() + 5);
//                map.setY(map.getY() + 5);
            } else if (distanceX > 0 && distanceY > 0) {
                map.setX(map.getX() - 5);
//                map.setY(map.getY() - 5);
                Log.i("Fling", "left top");
            } else if (distanceX < 0 && distanceY > 0) {
//                map.setX(map.getX() + 5);
                map.setY(map.getY() - 5);
                Log.i("Fling", "right top");
            } else if (distanceX > 0 && distanceY < 0) {
//                map.setX(map.getX() - 5);
                map.setY(map.getY() + 5);
                Log.i("Fling", "left bottom");
            }*/

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.i("Fling", "onShowPress");
            super.onShowPress(e);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.i("Fling", "onDown");
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i("Fling", "onDoubleTap");
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i("Fling", "onDoubleTapEvent");
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.i("Fling", "onSingleTapConfirmed");
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.i("Fling", "onFling");
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.i("Fling", "left");
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }

}
