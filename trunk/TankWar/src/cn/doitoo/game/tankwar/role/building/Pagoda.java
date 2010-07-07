package cn.doitoo.game.tankwar.role.building;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.role.Bullet;
import cn.doitoo.game.tankwar.role.tank.Tank;

import java.util.ArrayList;
import java.util.List;

/**
 * ��������
 * User: ����
 * Date: 2010-7-6
 * Time: 15:46:13
 */
public abstract class Pagoda extends MovableRole {
    private PagodaType pagodaType;
    /**
     * ���
     */
    private int range = 150;
    private Bitmap bitmap;
    private List<Tank> tanks = new ArrayList<Tank>();
    private Rect attackRect = null;

    public enum PagodaType {
        Player, Opponent
    }

    public abstract PagodaType getPagodaType();

    public Pagoda(int x, int y) {
        super(x, y);
        this.pagodaType = this.getPagodaType();
        bitmap = getBitmap();
        int left = this.getX() - (range / 2 - this.getWidth() / 2);
        int top = this.getY() - (range / 2 - this.getHeight() / 2);
        int right = left + range;
        int bottom = top + range;
        attackRect = new Rect(left, top, right, bottom);
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
        // ����������ת����Ļ����
        Point screenPoint = this.getScreenPoint();
        c.drawBitmap(bitmap, screenPoint.x, screenPoint.y, null);
    }

    /**
     * ���㵱ǰ�����Ƿ���Ҫ����
     */
    private void attack() {
        if (!tanks.isEmpty()) {
            for (Tank tank : tanks) {
                int x = tank.getX();
                int y = tank.getY();
//                if (this.getPagodaType().equals(Pagoda.PagodaType.Player) && tank.getTankType().equals(Tank.TankType.OpponentAiTank) && attackRect.contains(x, y)) {
                //�����ӵ�������̹������
                if (attackRect.contains(x, y)) {
                    Bullet bullet = new Bullet(this.getX(), this.getY());
                    bullet.setTank(tank);
                    bullet.setVisabled(true);
                    break;
                }
            }
        }
        //�ӵ�������̷�Χʱɾ���ӵ�
//        for (Bullet bullet : Bullet.bullets) {
//            if (!attackRect.contains(bullet.getX(), bullet.getY())) bullet.setVisabled(false);
//        }
    }

    public void setTanks(List<Tank> tanks) {
        this.tanks = tanks;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
