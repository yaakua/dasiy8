package cn.doitoo.game.tankwar.role.tank;

import android.graphics.Canvas;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * 电脑坦克主类，所有不同类型的电脑坦克都需要继承此类
 * User: 阳葵
 * Date: 2010-6-27
 * Time: 10:13:14
 */
public class AITank extends MovableRole {
    public AITank(int x, int y) {
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
}
