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
     * 角色动画步骤
     */
    private int[] step_array = {0};
    /**
     * 当前角色所处动画的步数
     */
    private int step;

    /**
     * 是否显示
     */
    private boolean isVisabled = true;

    /**
     * 角色移动方向
     */
    private move_direct direction;

    /**
     * 是否被选中
     */
    private boolean isSelected;

    /**
     * 角色所拥有的特效动画
     */
    private Map<String, Effect> effects = new HashMap<String, Effect>();

    public enum move_direct {
        LEFT, UP, DOWN, RIGHT
    }

    /**
     * 移动速度
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
     * 在屏幕上绘画角色，由子类实现
     *
     * @param c
     */
    public abstract void paint(Canvas c);

    public void stay() {
        this.x = this.oldX;
        this.y = this.oldY;
    }

    /**
     * 先保存角色改变前的坐标再改变角色坐标，
     *
     * @param x 角色新的X坐标
     * @param y 角色新的Y坐标
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
     * 以当前角色的X,Y坐标及高宽为标准，得到一个矩形。
     *
     * @return 与当前角色同高宽的矩形。
     */
    public Rect getRect() {
        int left = this.getX();
        int top = this.getY();
        int right = left + this.getWidth();
        int bottom = top + this.getHeight();
        return new Rect(left, top, right, bottom);
    }

    /**
     * 为角色添加点击事件，原理为：为当前角色添加一个事件分派器 ，在分派器里添加当前角色对象
     * 当触发点击事件时，判断当前是否点击到当前角色上，如果是当前角色，则调用当前角色的点击事件方法
     *
     * @param onClickEventHandler 点击事件，只处理点击。
     */
    public void setOnClickEventHandler(OnClickEventHandler onClickEventHandler) {
        this.onClickEventHandler = onClickEventHandler;
        onClickEventHandler.setRole(this);
    }

    /**
     * 删除特效动画
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
     * 查找已存在的特效动画
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
     * 添加特效动画
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
     * 画出角色所有动画效果
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
            } else if (time <= -1) {  //如果持续时间为-1，则代表一直使用
                effect.paint(c);
            } else {
                iterator.remove();
            }
        }
    }

    /**
     * 获取当前角色位置对应的屏幕坐标
     *
     * @return 角色在屏幕的坐标
     */
    public Point getScreenPoint() {
        int x = getX();
        int y = getY();
        // 由世界坐标转成屏幕坐标
        Point worldPoint = new Point(x, y);
        CoordinateUtil.world2screen(worldPoint);
        return worldPoint;
    }
}
