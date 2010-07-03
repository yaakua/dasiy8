package cn.doitoo.game.framework.event;

import android.view.MotionEvent;

/**
 * 包装onclick事件，只响应onClick事件
 * User: 阳葵
 * Date: 2010-7-3
 * Time: 14:48:08
 */
public abstract class OnClickEventHandler extends TouchEventHandler {


    @Override
    public void onTouchMove(MotionEvent event) {

    }

    @Override
    public void onTouchUp(MotionEvent event) {

    }
}
