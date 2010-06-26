package cn.doitoo.game.tankwar.event;

import android.view.MotionEvent;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.LinearSolver;

public class GestureMoveEvent extends ITouchEventHandler {

    private float preX = -1;
    private float preY = -1;
    private DoitooMap map;
    private float maxWidth;
    private float maxHeight;


    public GestureMoveEvent() {
        map = (DoitooMap) G.get(DoitooMap.class.getName());
        float screenHeight = (Float) G.get("screenHeight");
        float screenWidth = (Float) G.get("screenWidth");
        maxWidth = screenWidth - map.getWidth();
        maxHeight = screenHeight - map.getHeight();
    }

    @Override
    public void onTouchDown(MotionEvent event) {
        preX = event.getX();
        preY = event.getY();
    }

    @Override
    public void onTouchMove(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        float deltaX = x - preX;
        float deltaY = y - preY;

        if(Math.abs(deltaX)<2&&Math.abs(deltaY)<2)return;

        float px = Math.min(Math.max(map.getX() + deltaX, maxWidth), 0);
        float py = Math.min(Math.max(map.getY() + deltaY, maxHeight), 0);
        map.setPosition(px, py);
        preX = x;
        preY = y;
    }

    @Override
    public void onTouchUp(MotionEvent event) {
        // TODO Auto-generated method stub

    }

}
