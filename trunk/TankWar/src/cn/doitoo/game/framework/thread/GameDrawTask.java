package cn.doitoo.game.framework.thread;

import android.graphics.Canvas;

/**
 * 所有需要在GameRunThread中进行的任务，都需要继承此类
 * @see GameDrawThread
 * @author kui.yang
 *
 */
public abstract class GameDrawTask {
	public abstract void draw(Canvas c);
}
