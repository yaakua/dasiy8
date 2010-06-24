package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

public class TankSpriteTask extends DrawGraphicTask{
	private Bitmap spriteBm;
	private SurfaceHolder holder;
	private Bitmap startGame;
	
	public TankSpriteTask(SurfaceHolder holder, Context context) {
		spriteBm = Util.getBitMapById(context, R.drawable.loadmain);
		startGame = Util.getBitMapById(context, R.drawable.btstart);
	}	

	@Override
	public void draw() {
		Log.d("TankSpriteTask", "draw");
		Canvas c =this.holder.lockCanvas();
		c.drawBitmap(this.spriteBm, 0, 0,new Paint());
		c.drawBitmap(this.startGame, 150, 150,null);
		this.holder.unlockCanvasAndPost(c);
	}

	
	
}
