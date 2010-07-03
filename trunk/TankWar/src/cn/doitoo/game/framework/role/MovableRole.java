package cn.doitoo.game.framework.role;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.IOnClickEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;

import java.util.LinkedList;
import java.util.List;

/**
 * TODO add other functions
 *
 * @author Oliver O
 */
public abstract class MovableRole extends IOnClickEventHandler {
    private int oldX;

    private int oldY;

    private int x;

    private int y;

    private boolean isMoving;

    protected DoitooMap map;

    /**
     * ��ɫ��������
     */
    private int[] step_array = {0};
    /**
     * ��ǰ��ɫ���������Ĳ���
     */
    private int step;

    /**
     * �Ƿ���ʾ
     */
    private boolean isVisabled;

    /**
     * ��ɫ�ƶ�����
     */
    private move_direct direction;

    /**
     * �Ƿ�ѡ��
     */
    private boolean isSelected;

    public enum move_direct {
        LEFT, UP, DOWN, RIGHT
    }

    /**
     * �ƶ��ٶ�
     */
    private int speed = 5;

    public static List<MovableRole> movableRoleList = new LinkedList<MovableRole>();

    public MovableRole(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        movableRoleList.add(this);
        map = G.getDoitooMap();
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void move();

    /**
     * ����Ļ�ϻ滭��ɫ��������ʵ��
     *
     * @param c
     */
    public abstract void paint(Canvas c);

    public void stay() {
        this.x = this.oldX;
        this.y = this.oldY;
    }

    /**
     * �ڽ�ɫ���������л�����һ֡
     * <p/>
     * �����ɫ��Ҫ������ʾ����ÿ�λ�̹��֮ǰ����Ҫ���ô˷���
     */
    private void nextFrame() {
        if (step >= step_array.length) {
            step = 0;
        } else {
            step++;
        }
    }

    /**
     * �ȱ����ɫ�ı�ǰ�������ٸı��ɫ���꣬
     *
     * @param x ��ɫ�µ�X����
     * @param y ��ɫ�µ�Y����
     */
    public void setPosition(int x, int y) {
        this.oldX = getX();
        this.oldY = getY();
        this.x = x;
        this.y = y;

    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setMoving(boolean isMoving) {
        this.isMoving = isMoving;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isVisabled() {
        return isVisabled;
    }

    public void setVisabled(boolean isVisabled) {
        this.isVisabled = isVisabled;
    }

    public int[] getStep_array() {
        return step_array;
    }

    public void setStep_array(int[] step_array) {
        this.step_array = step_array;
    }

    public int getStep() {
        this.nextFrame();
        if (step >= this.getStep_array().length) step = 0;
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public move_direct getDirection() {
        return direction;
    }

    public void setDirection(move_direct direction) {
        this.direction = direction;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    /**
     * ��ǰ����¼����е�ǰ��ɫʱ��ӦonClick�¼�
     *
     * @param event �û�����¼�
     */
    @Override
    public void onTouchDown(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = this.getRect();
        if (rect.contains(x, y))
            this.onClick(event);
    }

    public abstract void onClick(MotionEvent event);

    /**
     * �Ե�ǰ��ɫ��X,Y���꼰�߿�Ϊ��׼���õ�һ�����Ρ�
     *
     * @return �뵱ǰ��ɫͬ�߿�ľ��Ρ�
     */
    public Rect getRect() {
        int left = this.getX();
        int top = this.getY();
        int right = left + this.getWidth();
        int bottom = top + this.getHeight();
        return new Rect(left, top, right, bottom);
    }

}
