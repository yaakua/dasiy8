package cn.doitoo.game.framework.event;

import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class ITouchEventHandler {

    public static List<ITouchEventHandler> touchList = new ArrayList<ITouchEventHandler>();

    public ITouchEventHandler() {
        touchList.add(this);
    }

    public abstract void onTouchDown(MotionEvent event);

    public abstract void onTouchMove(MotionEvent event);

    public abstract void onTouchUp(MotionEvent event);

}
