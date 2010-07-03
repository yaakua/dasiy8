package cn.doitoo.game.framework.event;

import android.view.MotionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-7-3
 * Time: 15:13:57
 */
public interface IOnClickListener {
    /**
     * 监听点击事件，所有需要监听点击的事件的角色可以实现此接口
     *
     * @param event MotionEvent
     */
    public void onClick(MotionEvent event);

}
