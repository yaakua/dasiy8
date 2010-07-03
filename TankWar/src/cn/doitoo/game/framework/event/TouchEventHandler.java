package cn.doitoo.game.framework.event;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class TouchEventHandler {

    public static List<TouchEventHandler> touchList = new ArrayList<TouchEventHandler>();

    public TouchEventHandler() {
        touchList.add(this);
    }

    public abstract void onTouchDown(MotionEvent event);

    public abstract void onTouchMove(MotionEvent event);

    public abstract void onTouchUp(MotionEvent event);

}
