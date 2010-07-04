package cn.doitoo.game.framework.role;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.OnClickEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;

import java.util.*;

/**
 * TODO add other functions
 *
 * @author Oliver O
 */
public abstract class MovableRole {
    private int oldX;

    private int oldY;

    private int x;

    private int y;

    private boolean isMoving;

    protected DoitooMap map;

    public OnClickEventHandler onClickEventHandler;
    private Paint paint = null;
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

    /**
     * ��ɫ��ӵ�е���Ч����
     */
    private Map<String, MovableRole> animations = new HashMap<String, MovableRole>();

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
        if (step >= step_array.length) {
            step = 0;
        } else {
            step++;
        }
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

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

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

    /**
     * Ϊ��ɫ��ӵ���¼���ԭ��Ϊ��Ϊ��ǰ��ɫ���һ���¼������� ���ڷ���������ӵ�ǰ��ɫ����
     * ����������¼�ʱ���жϵ�ǰ�Ƿ�������ǰ��ɫ�ϣ�����ǵ�ǰ��ɫ������õ�ǰ��ɫ�ĵ���¼�����
     *
     * @param onClickEventHandler ����¼���ֻ��������
     */
    public void setOnClickEventHandler(OnClickEventHandler onClickEventHandler) {
        this.onClickEventHandler = onClickEventHandler;
        onClickEventHandler.setRole(this);
    }

    /**
     * ɾ����Ч����
     */
    public void deleteAnimation(String keyCode) {
        Set<String> keys = animations.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key.equals(keyCode)) {
                iterator.remove();
            }
        }
    }

    /**
     * �����Ѵ��ڵ���Ч����
     *
     * @param keyCode
     * @return
     */
    public MovableRole getAnimation(String keyCode) {
        Set<String> keys = animations.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            if (key.equals(keyCode)) {
                return (MovableRole) animations.get(key);
            }
        }
        return null;
    }

    /**
     * �����Ч����
     *
     * @param movableRole
     */
    public void addAnimation(String key, MovableRole movableRole) {
        animations.put(key, movableRole);
    }

    public Map<String, MovableRole> getAnimations() {
        return animations;
    }

    /**
     * ������ɫ���ж���Ч��
     *
     * @param c
     */
    public void paintAnimation(Canvas c) {
        Set<String> keys = this.getAnimations().keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            MovableRole moveRole = this.getAnimations().get(key);
            if (moveRole.isMoving())
                moveRole.setPosition(x, y);
            moveRole.paint(c);
        }
    }
}
