package cn.doitoo.game.framework.task;

import cn.doitoo.game.framework.thread.GameDrawThread;
import android.graphics.Canvas;

/**
 * ������Ҫ��GameRunThread�н��е����񣬶���Ҫ�̳д���,
 * ��ͼ��task��������һ���Task
 * @see GameDrawThread
 * @author kui.yang
 *
 */
public  interface GameDrawTask {
	public  void draw(Canvas c);
	
}
