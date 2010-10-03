package cn.doitoo.game.tankwar.role.tank;

import android.graphics.*;
import android.util.Log;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ̹�����࣬���в�ͬ���͵�̹�˶���Ҫ�̳д��� User: ���� Date: 2010-6-27 Time: 10:12:58
 */
public abstract class Tank extends MovableRole {
    private List<Bitmap> LeftBitMapList = new ArrayList<Bitmap>();
    private List<Bitmap> RightBitMapList = new ArrayList<Bitmap>();
    private List<Bitmap> UpBitMapList = new ArrayList<Bitmap>();
    private List<Bitmap> DownBitMapList = new ArrayList<Bitmap>();
    protected int height;
    protected int width;
    @SuppressWarnings("unused")
    private TankType tankType;
    //̹������
    private int life = 1000;
    //������
    private int defense = 5;
    //������
    private int power = 15;
    //̹�˵�������
    private Blood blood;

    // Ӣ��̹���ƶ�·���±꼯��
    @SuppressWarnings("unchecked")
    protected List pathList;
    //�Ƿ��Ѹ����ƶ�·��
    private boolean hasChangePathList;

    //��������
    private Point endPoint;

    public enum TankType {
        PlayerAiTank, PlayerHeroTank, OpponentAiTank
    }

    public abstract TankType getTankType();

    /**
     * ��ʼ��Ӣ��̹��
     *
     * @param x ��ʼ��X����
     * @param y ��ʼ��Y����
     */
    public Tank(int x, int y) {
        super(x, y);
        this.tankType = getTankType();
        // bitmaps �����ϡ��ҡ��µ�˳���ʼ��̹�˵��ĸ�����

        initSprintImage();
        blood = new Blood(x, y, width, 5, life);
        blood.setRole(this);
    }

    /**
     * ��ʼ�������ɫͼƬ
     */
    private void initSprintImage() {
        Bitmap[] bitmaps = getBitmaps();
        height = this.getHeight();
        width = this.getWidth();
        Bitmap sourceImage = bitmaps[0];
        int sourceWidth = sourceImage.getWidth();
        int sourceHeight = sourceImage.getHeight();

        int rows = sourceHeight / height;
        int cols = sourceWidth / width;
        int w = width;
        int h = height;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = j * width;
                int top = i * height;
                Bitmap bitmap = Bitmap.createBitmap(sourceImage, left, top, w, h);
                switch (i) {
                    case 0:
                        DownBitMapList.add(bitmap);
                        break;
                    case 1:
                        LeftBitMapList.add(bitmap);
                        break;
                    case 2:
                        RightBitMapList.add(bitmap);
                        break;
                    case 3:
                        UpBitMapList.add(bitmap);
                        break;
                }
            }
        }
        int[] step = {0, 1, 2};
        this.setStep_array(step);
        Paint paint = new Paint();
        this.setPaint(paint);
    }

    private void initSprintImage2() {
        Bitmap[] bitmaps = getBitmaps();
        if (bitmaps.length < 4) {
            throw new ViewException("Tank �����������ʼ���ĸ������ͼƬ");
        }

        height = this.getHeight();
        width = this.getWidth();

        Bitmap left_source = bitmaps[0];
        Bitmap right_source = bitmaps[1];
        Bitmap up_source = bitmaps[2];
        Bitmap down_source = bitmaps[3];

        int sourceWidth = left_source.getWidth();
        int sourceHeight = left_source.getHeight();

        int rows = sourceHeight / height;
        int cols = sourceWidth / width;
        int w = width;
        int h = height;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = j * width;
                int top = i * height;
                Bitmap leftmap = Bitmap.createBitmap(left_source, left, top, w, h);
                Bitmap rightmap = Bitmap.createBitmap(right_source, left, top, w, h);
                Bitmap upmap = Bitmap.createBitmap(up_source, left, top, w, h);
                Bitmap downmap = Bitmap.createBitmap(down_source, left, top, w, h);
                LeftBitMapList.add(leftmap);
                RightBitMapList.add(rightmap);
                UpBitMapList.add(upmap);
                DownBitMapList.add(downmap);
            }
        }
    }

    protected int distanceX = 0;
    protected int distanceY = 0;
    protected int pathListIndex = 0;

    @Override
    public void move() {
        if (pathList != null && !pathList.isEmpty() && pathListIndex < pathList.size()) {
            int node = (Integer) pathList.get(pathListIndex);
            Point nextNode = CoordinateUtil.node2WorldPoint(node);
            int x = this.getX();
            int y = this.getY();
            int speed = this.getSpeed();
            distanceX = nextNode.x - x;
            distanceY = nextNode.y - y;
            int absDistanceX = Math.abs(distanceX);
            int absDistanceY = Math.abs(distanceY);
            int max = Math.max(absDistanceX, absDistanceY);
            move_direct direct = this.getDirection();
            if (max == absDistanceX && distanceX > 0) {
                direct = MovableRole.move_direct.RIGHT;
                x += speed;
            } else if (max == absDistanceX && distanceX < 0) {
                direct = MovableRole.move_direct.LEFT;
                x -= speed;
            } else if (max == absDistanceY && distanceY > 0) {
                direct = MovableRole.move_direct.DOWN;
                y += speed;
            } else if (max == absDistanceY && distanceY < 0) {
                direct = MovableRole.move_direct.UP;
                y -= speed;
            }

            // �ж��Ƿ���Ҫ�л�����һ���ڵ�
            if ((x > nextNode.x && direct.equals(MovableRole.move_direct.RIGHT)) || (y > nextNode.y && direct.equals(MovableRole.move_direct.DOWN))) {
                x = nextNode.x;
                y = nextNode.y;
                pathListIndex++;
            }
            if (x < nextNode.x && direct.equals(MovableRole.move_direct.LEFT) || (y < nextNode.y && direct.equals(MovableRole.move_direct.UP))) {
                x = nextNode.x;
                y = nextNode.y;
                pathListIndex++;
            }

            this.setDirection(direct);
            this.setPosition(x, y);
        } else {
            pathList = null;
        }
    }

    @Override
    public void paint(Canvas c) {
        if (c == null) {
            throw new NullPointerException();
        }
        int step = this.getStep();
        int index = this.getStep_array()[step];

        Bitmap src = null;
        MovableRole.move_direct direction = this.getDirection();
        if (direction.equals(MovableRole.move_direct.DOWN)) {
            src = DownBitMapList.get(index);
        } else if (direction.equals(MovableRole.move_direct.LEFT)) {
            src = LeftBitMapList.get(index);
        } else if (direction.equals(MovableRole.move_direct.RIGHT)) {
            src = RightBitMapList.get(index);
        } else if (direction.equals(MovableRole.move_direct.UP)) {
            src = UpBitMapList.get(index);
        }
        int w = this.getWidth();
        int h = this.getHeight();

        // ������Ч������Ӧ������
        this.paintEffects(c);

        // ����������ת����Ļ����
        Point screenPoint = this.getScreenPoint();
        int x = screenPoint.x;
        int y = screenPoint.y;

        // Ҫ��ʾ��ͼƬ����
        Rect srcRect = new Rect(0, 0, w, h);
        // Ŀ�����ֵĵط����ڵľ���
        Rect dst = new Rect(x, y, x + w, y + h);
        c.drawBitmap(src, srcRect, dst, this.getPaint());
        srcRect = null;
        dst = null;
        blood.paint(c);
    }

    /**
     * ���ݹ������뵱ǰ̹�˵ķ��������������ȥ������
     *
     * @param power �����Ĺ�����
     */
    public void subLife(int power) {
        if (power > defense) {
            power -= defense;
            life -= power;
        }
        if (life <= 0) {
            this.setVisabled(false);
        } else {
            blood.setCurrentLife(life);
            this.setLife(life);
        }
        Log.d("tank is attack", this.isAttack() + "");
    }

    /**
     * ���� �����ϡ��ҡ��µ�˳���ʼ��̹�˵��ĸ�����
     *
     * @return ����̹���ĸ������ͼƬ��Դ
     */
    public abstract Bitmap[] getBitmaps();


    @SuppressWarnings("unchecked")
    public void setPathList(List pathList) {
        this.pathList = pathList;
        pathListIndex = 0;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public boolean isHasChangePathList() {
        return hasChangePathList;
    }

    public void setHasChangePathList(boolean hasChangePathList) {
        this.hasChangePathList = hasChangePathList;
    }
}
