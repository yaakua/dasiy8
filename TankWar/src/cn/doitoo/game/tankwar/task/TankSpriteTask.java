package cn.doitoo.game.tankwar.task;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

public class TankSpriteTask extends DrawGraphicTask{
	private Bitmap spriteBm;
	private Bitmap startGame;
	public TankSpriteTask() {
		spriteBm = Util.getBitMapById(CONTEXT, R.drawable.loadmain);
		startGame = Util.getBitMapById(CONTEXT, R.drawable.btstart);
	}	

	@Override
	public void draw() {
		Log.d("TankSpriteTask", "draw");
		Canvas c =HOLDER.lockCanvas();
		c.drawBitmap(this.spriteBm, 0, 0,new Paint());
		c.drawBitmap(this.startGame, 150, 150,null);
		HOLDER.unlockCanvasAndPost(c);
	}
	
	
}
