package cn.doitoo.game.tankwar.role.bullet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import cn.doitoo.game.framework.role.MovableRole;

/**
 * Created by IntelliJ IDEA. User: 阳葵 Date: 2010-7-7 Time: 16:26:51
 */
public abstract class Bullet extends MovableRole {
    private Bitmap bitmap = null;
    private MovableRole attackRole = null; // 子弹攻击的对象，子弹运动轨迹依此坦克的位置而定
    // 攻击力,减去对方的生命值
    private int power = 10;
    //发出攻击的对象
    private MovableRole attacker;
    public Bullet(int x, int y) {
        super(x, y);
        bitmap = getBitmap();
        this.setSpeed(5);
        this.setVisabled(true);
       // new BulletThread(this).start();
    }

    public abstract Bitmap getBitmap();

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

    }

    @Override
    public void paint(Canvas c) {
        // 由世界坐标转成屏幕坐标
        Point screenPoint = this.getScreenPoint();
        c.drawBitmap(bitmap, screenPoint.x, screenPoint.y, null);
       // Log.d("bulletdrawing",screenPoint.x+":"+screenPoint.y);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public MovableRole getAttackRole() {
        return attackRole;
    }

    public void setAttackRole(MovableRole attackRole) {
        this.attackRole = attackRole;
    }

    class BulletThread extends Thread {
        private Bullet father;

        public BulletThread(Bullet father) {
            this.father = father;
        }

        @Override
        public void run() {
            while (father.isVisabled()) {
                try {
                    Thread.sleep(50);
                    move(father);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public synchronized void start() {
            super.start();
        }

        private void move(Bullet father) {
            MovableRole attackRole = father.getAttackRole();
            if (attackRole == null)
                return;
            Point tankCenterPoint = attackRole.getCenterPoint();
            int tx = tankCenterPoint.x;
            int ty = tankCenterPoint.y;
            int x = father.getX();
            int y = father.getY();

            int speed = father.getSpeed();
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

            if (attackRole.getRect().contains(x, y)) {
                attackRole.subLife(father.power);
                attackRole.setAttack(true);
                attackRole.setAttacker(father.getAttacker());
                father.setVisabled(false);
            }
            father.setAttackRole(attackRole);
            father.setPosition(x, y);
        }
    }

    @Override
    public void subLife(int power) {

    }

    public MovableRole getAttacker() {
        return attacker;
    }

    public void setAttacker(MovableRole attacker) {
        this.attacker = attacker;
    }

}
