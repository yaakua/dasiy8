package cn.doitoo.game.tankwar.effect;

import android.graphics.Canvas;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2010-7-4
 * Time: 16:02:35
 */
public class Effect extends MovableRole {

    /**
     * ����ʱ�䣬Ĭ��ֵΪ-1���������ʹ��
     */
    private int time = -1;

    public Effect(int x, int y) {
        super(x, y);
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public void subLife(int power) {

    }
}
