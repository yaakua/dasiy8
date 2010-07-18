package cn.doitoo.game.tankwar.role.building;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.role.bullet.Bullet;
import cn.doitoo.game.tankwar.role.bullet.LightingBullet;
import cn.doitoo.game.tankwar.role.tank.Blood;
import cn.doitoo.game.tankwar.role.tank.HeroTank;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * �������� User: ���� Date: 2010-7-6 Time: 15:46:13
 */
public abstract class Pagoda extends MovableRole {
    // �����������ӵ�
    private List<Bullet> bullets = new ArrayList<Bullet>();
    public static List<Pagoda> pagodas = new ArrayList<Pagoda>();
    /**
     * ��������
     */
    @SuppressWarnings("unused")
    private PagodaType pagodaType;
    /**
     * ���
     */
    private int range = 400;
    private Bitmap bitmap;

    // ������
    protected Blood blood;

    // ����
    protected int life = 10000;

    private Rect attackRect;
    protected int defense = 10;
    private int power = 10;

    public enum PagodaType {
        Player, Opponent
    }

    public abstract PagodaType getPagodaType();

    public Pagoda(int x, int y) {
        super(x, y);
        this.setPagodaType(this.getPagodaType());
        bitmap = getBitmap();
        blood = new Blood(x, y, bitmap.getWidth(), 5, life);
        blood.setRole(this);
        attackRect = computeAttackRect();
        pagodas.add(this);
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
        // ����������ת����Ļ����
        Point screenPoint = this.getScreenPoint();
        c.drawBitmap(bitmap, screenPoint.x, screenPoint.y, null);
        blood.paint(c);
        drawBullets(c);
        attack();
    }

    private void drawBullets(Canvas c) {
        // ���ӵ�
        if (bullets.isEmpty()) {
            return;
        }
        Iterator<Bullet> bulletIt = bullets.iterator();
        while (bulletIt.hasNext()) {
            Bullet bullet = bulletIt.next();
            if (bullet.isVisabled())
                bullet.paint(c);
            else if (!attackRect.contains(bullet.getX(), bullet.getY()) || bullet.getAttackRole() == null || !bullet.getAttackRole().isVisabled() || !bullet.isVisabled())
                bulletIt.remove();
        }
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getRange() {
        return range;
    }

    /**
     * ���㵱ǰ�����Ƿ���Ҫ����
     */
    private void attack() {
        if (AITank.AITanks.isEmpty()) {
            return;
        }
        attackRect = computeAttackRect();
        Point centerPoint = this.getCenterPoint();
        for (AITank aiTank : AITank.AITanks) {
            int x = aiTank.getX();
            int y = aiTank.getY();
            // if (this.getPagodaType().equals(Pagoda.PagodaType.Player) &&
            // aiTank.getTankType().equals(Tank.TankType.OpponentAiTank) &&
            // attackRect.contains(x, y)) {
            // �����ӵ�������̹������
            if (attackRect.contains(x, y)) {
                Bullet bullet = new LightingBullet(centerPoint.x, centerPoint.y);
                bullet.setAttackRole(aiTank);
                bullet.setPower(this.power);
                bullets.add(bullet);
            }
        }
        for (HeroTank heroTank : HeroTank.HeroTanks) {
            int x = heroTank.getX();
            int y = heroTank.getY();
            // if (this.getPagodaType().equals(Pagoda.PagodaType.Player) &&
            // heroTank.getTankType().equals(Tank.TankType.OpponentAiTank) &&
            // attackRect.contains(x, y)) {
            // �����ӵ�������̹������
            if (attackRect.contains(x, y)) {
                Bullet bullet = new LightingBullet(centerPoint.x, centerPoint.y);
                bullet.setAttackRole(heroTank);
                bullet.setPower(this.power);
                bullets.add(bullet);
            }
        }

    }

    /**
     * ��������������Χ
     *
     * @return ����������Χ
     */
    private Rect computeAttackRect() {
        int left = this.getX() - (range / 2 - this.getWidth() / 2);
        int top = this.getY() - (range / 2 - this.getHeight() / 2);
        int right = left + range;
        int bottom = top + range;
        return new Rect(left, top, right, bottom);
    }

    public void setPagodaType(PagodaType pagodaType) {
        this.pagodaType = pagodaType;
    }

    public void setLife(int life) {
        this.life = life;
    }

}
