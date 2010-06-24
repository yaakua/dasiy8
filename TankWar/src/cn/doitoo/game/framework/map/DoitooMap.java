package cn.doitoo.game.framework.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class DoitooMap {

	private int[][] mapRect;
	private int rows;
	private int cols;
	private Bitmap []bms;
	private int width;
	private int height;
	public DoitooMap(int[][]mapRect,int rows,int cols,Bitmap [] bms){
		this.rows = rows;
		this.cols = cols;
		this.mapRect = mapRect;
		this.bms = bms;
		width = bms[0].getWidth();
		height =bms[0].getHeight();
	}
	
    public void draw(Canvas c,float deltaX,float deltaY){
	   for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				float left= j*width+deltaX;
				float top = i*height+deltaY;
				c.drawBitmap(bms[mapRect[i][j]-1], left, top, null);
			}
			
		}
   }
	
}
