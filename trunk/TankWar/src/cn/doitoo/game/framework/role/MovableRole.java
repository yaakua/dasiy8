package cn.doitoo.game.framework.role;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.OnClickEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.util.CoordinateUtil;
import cn.doitoo.game.tankwar.effect.Effect;

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
    private boolean isVisabled = true;

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
    private Map<String, Effect> effects = new HashMap<String, Effect>();

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
//        movableRoleList.add(this);
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
    public void deleteEffect(String keyCode) {
        Set<String> keys = effects.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
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
    public MovableRole getEffectByKey(String keyCode) {
        Set<String> keys = effects.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (key.equals(keyCode)) {
                return effects.get(key);
            }
        }
        return null;
    }

    /**
     * �����Ч����
     *
     * @param effect
     */
    public void addEffect(String key, Effect effect) {
        effects.put(key, effect);
    }

    public Map<String, Effect> getEffects() {
        return effects;
    }

    /**
     * ������ɫ���ж���Ч��
     *
     * @param c
     */
    public void paintEffects(Canvas c) {
        Set<String> keys = this.getEffects().keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Effect effect = this.getEffects().get(key);
            if (effect.isMoving())
                effect.setPosition(x, y);
            int time = effect.getTime();
            if (time > 0) {
                effect.paint(c);
                effect.setTime(--time);
                Log.d("effectTime:", time + "");
            } else if (time <= -1) {  //�������ʱ��Ϊ-1�������һֱʹ��
                effect.paint(c);
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * ��ȡ��ǰ��ɫλ�ö�Ӧ����Ļ����
     *
     * @return ��ɫ����Ļ������
     */
    public Point getScreenPoint() {
        int x = getX();
        int y = getY();
        // ����������ת����Ļ����
        Point worldPoint = new Point(x, y);
        CoordinateUtil.world2screen(worldPoint);
        return worldPoint;
    }
}
