package cn.doitoo.game.framework.thread;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.enums.GameStatus;
import cn.doitoo.game.framework.task.GameDrawTask;

public class GameDrawThread extends Thread {
	private List<GameDrawTask> drawTaskList = new LinkedList<GameDrawTask>();
	/**
	 * 游戏状态 @see {@link GameStatus}
	 */
	private GameStatus gameStauts;

	private  SurfaceHolder holder;
	
	private static GameDrawThread drawThread;
	Canvas canvas;
	
	private GameDrawThread(){
		
	}
	private GameDrawThread(SurfaceHolder holder){
		setName("GameDrawThread");
		this.holder = holder;
	}
  
	public static GameDrawThread getInstance(SurfaceHolder holder){
	    if(drawThread==null){
	    	drawThread = new GameDrawThread(holder);
	    }
	    return drawThread;
	}

	@Override
	public void run() {
		super.run();
		while (this.gameStauts.equals(GameStatus.RUNING)) {
			try {
				Thread.sleep(50);
					canvas = holder.lockCanvas();
				synchronized (holder) {
					for (GameDrawTask task : drawTaskList) {
						task.draw(canvas);
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
		drawTaskList.add(gameThread);
		return this;
	}

}
