package cn.doitoo.game.tankwar.role.bullet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

import java.util.ArrayList;
import java.util.List;

public class LightingBullet extends Bullet {
    //�ӵ�ͼƬ����
    private static Bitmap bitmap = null;
    //�ӵ�ͼƬ�ֽ��ļ���
    private static List<Bitmap> animations = new ArrayList<Bitmap>();
    //��ǰ��ʾ�Ķ���ͼƬ
    private Bitmap drawMap;
    //��������
    private List<MovableRole> attackeds = new ArrayList<MovableRole>();

    public LightingBullet(int x, int y) {
        super(x, y);
        if (animations.isEmpty()) {
            //���ӵ��뱬ըЧ������һ����������
            createAnimations(animations, bitmap, this.getWidth(), this.getHeight());
            createAnimations(animations, Util.getBitMapById(G.getContext(), R.drawable.yellowfireexplode01), 192, 192);
        }
        this.setStep_array(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        new LightingBulletThread(this).start();
    }

    private void createAnimations(List<Bitmap> animations, Bitmap bitmap
            , int w, int h) {
        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();
        int rows = sourceHeight / h;
        int cols = sourceWidth / w;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = j * w;
                int top = i * h;
                Bitmap map = Bitmap.createBitmap(bitmap, left, top, w, h);
                animations.add(map);
            }
        }
    }

    @Override
    public Bitmap getBitmap() {
        Context context = G.getContext();
        if (bitmap == null) {
            bitmap = Util.getBitMapById(context, R.drawable.linghtbullet02);
        }
        return bitmap;
    }

    @Override
    public int getWidth() {
        return 100;
    }

    @Override
    public int getHeight() {
        return 175;
    }

    @Override
    public void paint(Canvas c) {
        // ����������ת����Ļ����
        Point screenPoint = this.getScreenPoint();
        if (drawMap == null) {
            drawMap = animations.get(0);
        }
        c.drawBitmap(drawMap, screenPoint.x, screenPoint.y, null);
    }

    /**
     * ���߳��������ӵ��Ķ�����ʾ
     */
    class LightingBulletThread extends Thread {
        private LightingBullet father;

        public LightingBulletThread(LightingBullet father) {
            this.father = father;
        }

        @Override
        public void start() {
            super.start();
        }

        @Override
        public void run() {
            while (father.isVisabled()) {
                try {
                    Thread.sleep(200);
                    int step = father.getStep_array()[father.getStep()];
                    father.drawMap = animations.get(step);
                    if (step != 0 && step != 1) {
                        MovableRole attackRole = father.getAttackRole();
                        Point centerPoint = attackRole.getCenterPoint();
                        father.setPosition(centerPoint.x - father.getWidth() / 2, centerPoint.y - father.getHeight());
                    }
                    if (step > 5) { //��ըЧ����ʾλ��
                        father.setPosition(father.getX() - 52, father.getY() + 80);
                    }
                    //G.addDebugInfo("step", step + "");
                    //�ӵ�������ʾ��ɺ󣬼��ٱ������ߵ�����ֵ
                    if (step == 10) {
                        //G.addDebugInfo("bullet", "���ڼ�������ֵ");
                        father.subLife(father.getPower());
                        father.setVisabled(false);
                        /* YellowFireExplode explode = new YellowFireExplode(father.getX(), father.getY());
                        explode.setOwner(father);*/
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.interrupt();
        }

    }

    @Override
    public void subLife(int power) {
        for (MovableRole attackRole : this.attackeds) {
            attackRole.subLife(power);
            attackRole.setAttack(true);
            attackRole.setAttacker(this.getAttacker());
        }
    }

    public void setAttackeds(List<MovableRole> attackeds) {
        this.attackeds = attackeds;
    }
}
