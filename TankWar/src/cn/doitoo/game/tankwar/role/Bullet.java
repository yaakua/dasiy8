package cn.doitoo.game.tankwar.role;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;
import cn.doitoo.game.tankwar.role.tank.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: 阳葵
 * Date: 2010-7-7
 * Time: 16:26:51
 */
public class Bullet extends MovableRole {
    private Bitmap bitmap = null;
    private Tank tank = null; //子弹攻击的对象，子弹运动轨迹依此坦克的位置而定
    public static List<Bullet> bullets = new ArrayList<Bullet>();

    public Bullet(int x, int y) {
        super(x, y);
        Context context = G.getContext();
        bitmap = Util.getBitMapById(context, R.drawable.bullet);
        bullets.add(this);
        this.setSpeed(20);
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public void move() {
        if (tank == null) return;
        int tx = tank.getX();
        int ty = tank.getY();
        int x = this.getX();
        int y = this.getY();

        int speed = this.getSpeed();
        if (tx > x) {
            x += speed;
        } else if (tx < x) {
            x -= speed;
        }

        if (ty > y) {
            y += speed;
        } else if (ty < y) {
            y -= speed;
        }

        if (tank.getRect().contains(x, y)) {
            //TODO 增加爆炸效果
            this.setVisabled(false);
        }
        this.setPosition(x, y);
    }

    @Override
    public void paint(Canvas c) {
        this.move();
        // 由世界坐标转成屏幕坐标
        Point screenPoint = this.getScreenPoint();
        c.drawBitmap(bitmap, screenPoint.x, screenPoint.y, null);
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
