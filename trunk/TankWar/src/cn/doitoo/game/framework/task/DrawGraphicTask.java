package cn.doitoo.game.framework.task;



import android.os.Handler;
import android.os.Message;

public abstract class DrawGraphicTask extends Task{
 
	public DrawGraphicTask(){
		needThreadPool = false;
		setIntervalTime(1);
		
	}
	@Override
	protected void doTask() {
		//TODO: the drawing will be paused because of gc
		Message msg = new Message();
		msg.what=1;
		new DrawHandler().sendMessage(msg);//high speed drawing

		
	}
	
	class DrawHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			draw();
		}
	}

	public abstract void draw();
   
   
}
