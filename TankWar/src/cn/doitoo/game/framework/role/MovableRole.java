package cn.doitoo.game.framework.role;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
/**
 * TODO add other functions 
 * @author Oliver O
 *
 */
public abstract class MovableRole {
	private float oldX;

	private float oldY;

	private float x;

	private float y;
	
	private boolean isMoving;

	public enum move_direct {
		LEFT, UP, DOWN, RIGHT
	}

	public static List<MovableRole> movableRoleList = new LinkedList<MovableRole>();

	public MovableRole() {

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

}
