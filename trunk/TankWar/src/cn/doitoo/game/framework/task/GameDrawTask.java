package cn.doitoo.game.framework.task;

import cn.doitoo.game.framework.thread.GameDrawThread;
import android.graphics.Canvas;

/**
 * 所有需要在GameRunThread中进行的任务，都需要继承此类,
 * 绘图类task，独立于一般的Task
 * @see GameDrawThread
 * @author kui.yang
 *
 */
public  interface GameDrawTask {
	public  void draw(Canvas c);
	
}
