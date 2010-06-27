package cn.doitoo.game.tankwar.role.tank;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.exception.ViewException;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.role.MovableRole;
import cn.doitoo.game.framework.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Ӣ��̹�����࣬���в�ͬ���͵�Ӣ��̹�˶���Ҫ�̳д���
 * User: ����
 * Date: 2010-6-27
 * Time: 10:12:58
 */
public abstract class HeroTank extends MovableRole {
    private static List<Bitmap> LeftBitMapList = new ArrayList<Bitmap>();
    private static List<Bitmap> RightBitMapList = new ArrayList<Bitmap>();
    private static List<Bitmap> UpBitMapList = new ArrayList<Bitmap>();
    private static List<Bitmap> DownBitMapList = new ArrayList<Bitmap>();
    protected float height;
    protected float width;

    protected Context context;
    private DoitooMap map;
    // bitmaps �����ϡ��ҡ��µ�˳���ʼ��̹�˵��ĸ�����
    private Bitmap[] bitmaps = null;
    //Ӣ��̹���ƶ�·���±꼯��
    private List pathList;
    private int mapCols;

    /**
     * ��ʼ��Ӣ��̹��
     *
     * @param x ��ʼ��X����
     * @param y ��ʼ��Y����
     */
    public HeroTank(float x, float y) {
        super(x, y);
        map = (DoitooMap) G.get(DoitooMap.class.getName());
        mapCols = map.getMapRect()[0].length;
        context = (Context) G.get("context");
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

        int rows = sourceHeight / (int) height;
        int cols = sourceWidth / (int) width;
        int w = (int) width;
        int h = (int) height;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = j * (int) width;
                int top = i * (int) height;
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
        if (pathList != null && !pathList.isEmpty()&&pathListIndex<=pathList.size()) {
            int node = (Integer) pathList.get(pathListIndex);
            Point point = Util.convertNode2Point(node, mapCols);
            int x = (int) this.getX();
            int y = (int) this.getY();
            int speed = this.getSpeed();
            distanceX = point.x - x;
            distanceY = point.y - y;
            int max = Math.max(distanceX, distanceY);
            move_direct direct ;
            if (max == distanceX && distanceX > 0) {
                direct = MovableRole.move_direct.RIGHT;
                x += speed;
            } else if (max == distanceX && distanceX < 0) {
                direct = MovableRole.move_direct.LEFT;
                x-=speed;
            } else if (max == distanceY && distanceY > 0) {
                direct = MovableRole.move_direct.DOWN;
                y+=speed;
            } else {
                direct = MovableRole.move_direct.UP;
                y-=speed;
            }
            if(x>point.x||y>point.y){
                x = point.x;
                y = point.y;
                pathListIndex++;
            }
            this.setDirection(direct);
            this.setPosition(x,y);
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
        int w = (int) this.getWidth();
        int h = (int) this.getHeight();
        int x = (int) getX();
        int y = (int) getY();
        //����������ת����Ļ����
        Point worldPoint = new Point(x, y);
        Util.world2screen(map, worldPoint);
        x = worldPoint.x;
        y = worldPoint.y;
        //Ҫ��ʾ��ͼƬ����
        Rect srcRect = new Rect(0, 0, w, h);
        //Ŀ�����ֵĵط����ڵľ���
        Rect dst = new Rect(x, y, x + w, y + h);
        c.drawBitmap(src, srcRect, dst, null);
    }

    /**
     * ���� �����ϡ��ҡ��µ�˳���ʼ��̹�˵��ĸ�����
     *
     * @return ����̹���ĸ������ͼƬ��Դ
     */
    public abstract Bitmap[] getBitmaps();

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    public List getPathList() {
        return pathList;
    }

    public void setPathList(List pathList) {
        this.pathList = pathList;
        pathListIndex=0;
    }
}
