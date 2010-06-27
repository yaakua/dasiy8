package cn.doitoo.game.framework.task;



import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.context.G;

public abstract class DrawGraphicTask extends Task{
	private static List<DrawGraphicTask> dgTaskList = new ArrayList<DrawGraphicTask>();
	public DrawGraphicTask(){
		needThreadPool = false;
		setIntervalTime(1);
		dgTaskList.add(this);
	}
	@Override
	protected void doTask() {
		SurfaceHolder holder = (SurfaceHolder) G.get("holder");
		Canvas c =null;
		try{
		synchronized (holder) {
		 c = holder.lockCanvas();
			for(DrawGraphicTask task :dgTaskList){
				task.draw(c);
			}
			
		}
		}finally{
		  holder.unlockCanvasAndPost(c);
		}
		//TODO: the drawing will be paused because of gc
//		Message msg = new Message();
//		msg.what=1;
//		new DrawHandler().handleMessage(msg);//high speed drawing

		
	}
//	
//	class DrawHandler extends Handler {
//		@Override
//		public void handleMessage(Message msg) {
//			Log.d("draw", "drawing");
//			draw();
//		}
//	}
	

	@Override
	public void endTask() {		
		super.endTask();
		dgTaskList.remove(this);
	}

	public abstract void draw(Canvas c);
}
