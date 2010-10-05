package cn.doitoo.game.tankwar.role.building;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.tankwar.role.bullet.Bullet;
import cn.doitoo.game.tankwar.role.bullet.LightingBullet;
import cn.doitoo.game.tankwar.role.tank.Blood;
import cn.doitoo.game.tankwar.role.tank.aitank.AITank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * �������� User: ���� Date: 2010-7-6 Time: 15:46:13
 */
public abstract class Pagoda extends MovableRole {
    public static List<Pagoda> pagodas = new ArrayList<Pagoda>();
    // �������Ķ���
    private List<MovableRole> attackTanks = new ArrayList<MovableRole>();

    //������ӵ���һ��ֻ��һ����
    private Bullet bullet = null;
    /**
     * ��������
     */
    @SuppressWarnings("unused")
    private PagodaType pagodaType;
    /**
     * ���
     */
    private int range = 300;
    private Bitmap bitmap;
    // ������
    protected Blood blood;
    // ����
    protected int life = 10000;
    //������Χ
    private Rect attackRect;
    //������
    protected int defense = 10;
    //������
    private int power = 20;

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
        new PagodaThread(this).start();
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
        drawBullet(c);
        this.attack();
    }

    /**
     * ���ӵ�
     *
     * @param c
     */
    private void drawBullet(Canvas c) {
        if (bullet == null) {
            return;
        }
        if (bullet.isVisabled())
            bullet.paint(c);
        else
            bullet = null;
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
        Iterator it = attackTanks.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        for (AITank aiTank : AITank.AITanks) {
            if (!aiTank.isVisabled())
                continue;
            int x = aiTank.getX();
            int y = aiTank.getY();
            if (attackRect.contains(x, y)) {
                attackTanks.add(aiTank);
            }
        }
//        for (HeroTank heroTank : HeroTank.HeroTanks) {
//            int x = heroTank.getX();
//            int y = heroTank.getY();
//
//            if (attackRect.contains(x, y)) {
//                Bullet bullet = new LightingBullet(centerPoint.x, centerPoint.y);
//                bullet.setAttackRole(heroTank);
//                bullet.setPower(this.power);
//                bullets.add(bullet);
//            }
//        }

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

    /**
     * ���߳��������������ӵ������ٶ�
     */
    class PagodaThread extends Thread {
        private Pagoda father;

        PagodaThread(Pagoda pagoda) {
            this.father = pagoda;
        }

        @Override
        public void run() {
            while (father.isVisabled()) {
                try {
                    Thread.sleep(1500);
                    if (!father.attackTanks.isEmpty() && father.bullet == null) {
                        G.addDebugInfo("attackedTanks", father.attackTanks.size() + "");
                        MovableRole role = father.attackTanks.get(0);  //ȡ��һ�������ߵ�����Ϊ�ӵ���ʾ������
                        Point centerPoint = role.getCenterPoint();
                        LightingBullet bullet = new LightingBullet(centerPoint.x - role.getWidth(), centerPoint.y -(175-role.getHeight()*2));
                        bullet.setAttackeds(father.attackTanks);
                        bullet.setPower(father.power);
                        father.bullet = bullet;
                    } else {
                        G.addDebugInfo("attackTanks", father.attackTanks.size() + "");
                        father.bullet = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.interrupt();
        }
    }

}
