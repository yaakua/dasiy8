package cn.doitoo.game.framework.event;

import android.view.MotionEvent;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2010-7-3
 * Time: 15:13:57
 */
public interface IOnClickListener {
    /**
     * ��������¼���������Ҫ����������¼��Ľ�ɫ����ʵ�ִ˽ӿ�
     *
     * @param event MotionEvent
     */
    public void onClick(MotionEvent event);

}
