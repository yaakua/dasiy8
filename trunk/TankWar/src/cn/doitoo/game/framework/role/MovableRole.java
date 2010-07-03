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
    private boolean isVisabled;

    /**
     * 角色移动方向
     */
    private move_direct direction;

    /**
     * 是否被选中
     */
    private boolean isSelected;

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
        movableRoleList.add(this);
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
     * 在角色动画当中切换至下一帧
     * <p/>
     * 如果角色需要动画显示，则每次画坦克之前都需要调用此方法
     */
    private void nextFrame() {
        if (step >= step_array.length) {
            step = 0;
        } else {
            step++;
        }
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
     * 当前点击事件点中当前角色时响应onClick事件
     *
     * @param event 用户点击事件
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

}
