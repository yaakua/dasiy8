package cn.doitoo.game.framework.role;

import java.util.LinkedList;
import java.util.List;

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

	public abstract float getWidth();

	public abstract float getHeight();
	
	public abstract void move();
	
	/**
	 * ����Ļ�ϻ滭��ɫ��������ʵ��
	 * 
	 * @param c
	 */
	public abstract void paint(Canvas c);

    public void stay(){
        this.x = this.oldX;
        this.y = this.oldY;
    }

	/**
	 * �ȱ����ɫ�ı�ǰ�������ٸı��ɫ���꣬
	 * @param x ��ɫ�µ�X����
	 * @param y ��ɫ�µ�Y����
	 */
	public void setPosition(float x, float y) {
		this.oldX = getX();
		this.oldY = getY();
		this.x = x;
        this.y = y;

	}

	public float getX() {
		return x;
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
