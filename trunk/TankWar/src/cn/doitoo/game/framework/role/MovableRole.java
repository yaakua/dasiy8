package cn.doitoo.game.framework.role;

import java.util.LinkedList;
import java.util.List;

import cn.doitoo.game.framework.context.GameContext;

import android.graphics.Canvas;
/**
 * TODO add other functions 
 * @author Oliver O
 *
 */
public abstract class MovableRole{
	private float oldX;

	private float oldY;

	private float x;

	private float y;
	
	private boolean isMoving;
	
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

	public enum move_direct {
		LEFT, UP, DOWN, RIGHT
	}

	public static List<MovableRole> movableRoleList = new LinkedList<MovableRole>();

	public MovableRole(float x,float y) {
		super();
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		movableRoleList.add(this);
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

	public void setOldY(float oldY) {
		this.oldY = oldY;
	}

	public float getOldY() {
		return oldY;
	}

	public void setOldX(float oldX) {
		this.oldX = oldX;
	}

	public float getOldX() {
		return oldX;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
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
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

}
