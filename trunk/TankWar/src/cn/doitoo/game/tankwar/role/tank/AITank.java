package cn.doitoo.game.tankwar.role.tank;

import android.graphics.Canvas;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * ����̹�����࣬���в�ͬ���͵ĵ���̹�˶���Ҫ�̳д���
 * User: ����
 * Date: 2010-6-27
 * Time: 10:13:14
 */
public class AITank extends MovableRole{
    public AITank(float x, float y) {
        super(x, y);
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void paint(Canvas c) {

    }
}
