package cn.doitoo.game.tankwar.role.tank;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.CoordinateUtil;

import java.util.*;

/**
 * Ӣ��̹�����࣬���в�ͬ���͵�Ӣ��̹�˶���Ҫ�̳д��� User: ���� Date: 2010-6-27 Time: 10:12:58
 */
public abstract class HeroTank extends MovableRole {
    private static List<Bitmap> LeftBitMapList = new ArrayList<Bitmap>();
    private static List<Bitmap> RightBitMapList = new ArrayList<Bitmap>();
    private static List<Bitmap> UpBitMapList = new ArrayList<Bitmap>();
    private static List<Bitmap> DownBitMapList = new ArrayList<Bitmap>();
    protected int height;
    protected int width;

    protected Context context;

    // bitmaps �����ϡ��ҡ��µ�˳���ʼ��̹�˵��ĸ�����
    private Bitmap[] bitmaps = null;
    // Ӣ��̹���ƶ�·���±꼯��
    private List pathList;


    /**
     * ��ʼ��Ӣ��̹��
     *
     * @param x ��ʼ��X����
     * @param y ��ʼ��Y����
     */
    public HeroTank(int x, int y) {
        super(x, y);
        context = G.getContext();
        bitmaps = getBitmaps();
        if (bitmaps.length < 4) {
            throw new ViewException("HeroTank �����������ʼ���ĸ������ͼƬ");
        }

        int tankElementWidth = (Integer) G.get("tankElementWidth");

        height = tankElementWidth;
        width = tankElementWidth;

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

    private int distanceX = 0;
    private int distanceY = 0;
    private int pathListIndex = 0;

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
            pathListIndex = 0;
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
        int x = getX();
        int y = getY();

        // ������Ч������Ӧ������
        this.paintEffects(c);

        // ����������ת����Ļ����
        Point worldPoint = new Point(x, y);
        CoordinateUtil.world2screen(worldPoint);
        x = worldPoint.x;
        y = worldPoint.y;
        // Ҫ��ʾ��ͼƬ����
        Rect srcRect = new Rect(0, 0, w, h);
        // Ŀ�����ֵĵط����ڵľ���
        Rect dst = new Rect(x, y, x + w, y + h);
        c.drawBitmap(src, srcRect, dst, null);
        srcRect = null;
        dst = null;
    }

    /**
     * ���� �����ϡ��ҡ��µ�˳���ʼ��̹�˵��ĸ�����
     *
     * @return ����̹���ĸ������ͼƬ��Դ
     */
    public abstract Bitmap[] getBitmaps();

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setPathList(List pathList) {
        this.pathList = pathList;
        pathListIndex = 0;
    }


}
