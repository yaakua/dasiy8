package cn.doitoo.game.framework.event;

import android.graphics.Rect;
import android.view.MotionEvent;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * ��װonclick�¼���ֻ��ӦonClick�¼�
 * User: ����
 * Date: 2010-7-3
 * Time: 14:48:08
 */
public abstract class OnClickEventHandler extends TouchEventHandler {
    private MovableRole role;

    @Override
    public void onTouchDown(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (role != null) {
            Rect rect = role.getRect();
            if (rect.contains(x, y)) {
                onClick(event);
            }
        }
    }

    /**
     * ����¼����ѱ�֤��ǰ����������ڵ����ǰע��Ľ�ɫ��ʱ������
     *
     * @param event
     */
    public abstract void onClick(MotionEvent event);

    @Override
    public void onTouchMove(MotionEvent event) {

    }

    @Override
    public void onTouchUp(MotionEvent event) {

    }

    public void setRole(MovableRole role) {
        this.role = role;
    }
}
