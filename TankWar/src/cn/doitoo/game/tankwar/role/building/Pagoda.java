package cn.doitoo.game.tankwar.role.building;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.role.Bullet;
import cn.doitoo.game.tankwar.role.tank.Blood;
import cn.doitoo.game.tankwar.role.tank.HeroTank;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 炮塔基类
 * User: 阳葵
 * Date: 2010-7-6
 * Time: 15:46:13
 */
public abstract class Pagoda extends MovableRole {
    //炮塔所属的子弹
    private List<Bullet> bullets = new ArrayList<Bullet>();
    /**
     * 炮塔类型
     */
    private PagodaType pagodaType;
    /**
     * 射程
     */
    private int range = 150;
    private Bitmap bitmap;

    //生命条
    private Blood blood;

    //生命
    private int life = 10000;

    public enum PagodaType {
        Player, Opponent
    }

    public abstract PagodaType getPagodaType();

    public Pagoda(int x, int y) {
        super(x, y);
        this.pagodaType = this.getPagodaType();
        bitmap = getBitmap();
        blood = new Blood(x, y, bitmap.getWidth(), 5, life);
        blood.setRole(this);
    }

    /**
     * 计算炮塔攻击范围
     *
     * @return 炮塔攻击范围
     */
    private Rect computeAttackRect() {
        int left = this.getX() - (range / 2 - this.getWidth() / 2);
        int top = this.getY() - (range / 2 - this.getHeight() / 2);
        int right = left + range;
        int bottom = top + range;
        return new Rect(left, top, right, bottom);
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
        attack();
        // 由世界坐标转成屏幕坐标
        Point screenPoint = this.getScreenPoint();
        c.drawBitmap(bitmap, screenPoint.x, screenPoint.y, null);
        blood.paint(c);
        drawBullets(c);
    }

    private void drawBullets(Canvas c) {
        //画子弹
        if (this.bullets.isEmpty()) {
            return;
        }
        Iterator iterator = this.bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = (Bullet) iterator.next();
            if (bullet.isVisabled())
                bullet.paint(c);
            else {
                iterator.remove();
            }
        }
    }

    /**
     * 计算当前炮塔是否需要攻击
     */
    private void attack() {
        if (AITank.AITanks.isEmpty() && HeroTank.HeroTanks.isEmpty()) {
            return;
        }
        Rect attackRect = this.computeAttackRect();
        for (AITank aiTank : AITank.AITanks) {
            int x = aiTank.getX();
            int y = aiTank.getY();
//                if (this.getPagodaType().equals(Pagoda.PagodaType.Player) && aiTank.getTankType().equals(Tank.TankType.OpponentAiTank) && attackRect.contains(x, y)) {
            //发射子弹，减少坦克生命
            if (attackRect.contains(x, y)) {
                Bullet bullet = new Bullet(this.getX(), this.getY());
                bullet.setTank(aiTank);
                this.bullets.add(bullet);
            }
        }
        for (HeroTank heroTank : HeroTank.HeroTanks) {
            int x = heroTank.getX();
            int y = heroTank.getY();
//                if (this.getPagodaType().equals(Pagoda.PagodaType.Player) && heroTank.getTankType().equals(Tank.TankType.OpponentAiTank) && attackRect.contains(x, y)) {
            //发射子弹，减少坦克生命
            if (attackRect.contains(x, y)) {
                Bullet bullet = new Bullet(this.getX(), this.getY());
                bullet.setTank(heroTank);
                this.bullets.add(bullet);
            }
        }
        //当子弹超出攻击范围时不再显示
        Iterator iterator = this.bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = (Bullet) iterator.next();
            if (!attackRect.contains(bullet.getX(), bullet.getY())
                    || bullet.getTank() == null || !bullet.getTank().isVisabled())
                iterator.remove();
        }
    }

    public void setRange(int range) {
        this.range = range;
    }

}
