package cn.doitoo.game.framework.thread;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.enums.GameStatus;

public class GameDrawThread extends Thread {
	private List<GameDrawTask> GameRunThreads = new LinkedList<GameDrawTask>();
	/**
	 * 游戏状态 @see {@link GameStatus}
	 */
	private GameStatus gameStauts;

	SurfaceHolder holder;
	Canvas canvas;

	public GameDrawThread(SurfaceHolder holder) {
		super();
		this.holder = holder;
	}

	@Override
	public void run() {
		super.run();
		while (this.gameStauts.equals(GameStatus.RUNING)) {
			try {
				Thread.sleep(50);
				synchronized (holder) {
					canvas = holder.lockCanvas();
					for (GameDrawTask thread : GameRunThreads) {
						thread.draw(canvas);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				holder.unlockCanvasAndPost(canvas);
			}
		}

	}

	@Override
	public synchronized void start() {
		super.start();
	}

	public GameStatus getGameStauts() {
		return gameStauts;
	}

	public void setGameStauts(GameStatus gameStauts) {
		this.gameStauts = gameStauts;
	}

	/**
	 * 将任务添加至线程运行集合当中
	 * 
	 * @param gameThread任务
	 */
	public GameDrawThread add(GameDrawTask gameThread) {
		GameRunThreads.add(gameThread);
		return this;
	}

}
