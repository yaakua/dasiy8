package cn.doitoo.game.framework.thread;

import android.graphics.Canvas;

/**
 * ������Ҫ��GameRunThread�н��е����񣬶���Ҫ�̳д���
 * @see GameDrawThread
 * @author kui.yang
 *
 */
public abstract class GameDrawTask {
	public abstract void draw(Canvas c);
}
