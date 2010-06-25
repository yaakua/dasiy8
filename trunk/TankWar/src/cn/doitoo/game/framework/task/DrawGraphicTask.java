package cn.doitoo.game.framework.task;



import android.os.Handler;
import android.os.Message;
import android.util.Log;

public abstract class DrawGraphicTask extends Task{
	public DrawGraphicTask(){
		needThreadPool = false;
		setIntervalTime(1);
	}
	@Override
	protected void doTask() {
		
		draw();
		//TODO: the drawing will be paused because of gc
//		Message msg = new Message();
//		msg.what=1;
//		new DrawHandler().handleMessage(msg);//high speed drawing

		
	}
	
	class DrawHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Log.d("draw", "drawing");
			draw();
		}
	}

	public abstract void draw();
}
