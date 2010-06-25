package cn.doitoo.game.tankwar.task;

import android.graphics.Canvas;
import android.util.Log;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.tankwar.R;

public class DrawMapTask extends DrawGraphicTask{

	private DoitooMap map =null;
	
	/**
	 * ��ͼ�ֲ�ͼ����һ�أ�
	 */
	public static final int[][] tank_map1 ={
		{ 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1 },
		{ 1, 3, 1, 1, 3, 3, 3, 4, 3, 3, 1, 1, 1, 3, 1 },
		{ 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1 },
		{ 1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 4, 1, 3, 1 },
		{ 4, 1, 1, 3, 1, 4, 4, 4, 1, 4, 1, 4, 1, 3, 1 },
		{ 1, 1, 1, 3, 1, 4, 1, 4, 1, 4, 1, 1, 1, 3, 1 },
		{ 3, 3, 1, 1, 1, 1, 1, 1, 1, 4, 3, 1, 1, 1, 1 },
		{ 3, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1 },
		{ 1, 1, 1, 3, 4, 4, 3, 3, 4, 4, 1, 1, 1, 4, 1 },
		{ 4, 1, 1, 1, 4, 2, 2, 2, 4, 1, 1, 3, 1, 4, 1 },
		{ 4, 1, 3, 1, 4, 2, 3, 2, 4, 1, 1, 1, 1, 4, 1 },
		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
		{ 3, 1, 4, 4, 4, 4, 1, 3, 3, 3, 4, 4, 4, 4, 4 },
		{ 3, 1, 1, 1, 1, 3, 1, 1, 4, 1, 1, 1, 1, 1, 1 },
		{ 3, 1, 1, 1, 1, 2, 4, 1, 1, 1, 3, 3, 3, 3, 2 }
		};
	
	
	public DrawMapTask() {
		int[] resIds = {R.drawable.tile_breakable,R.drawable.tile_breakable1
				,R.drawable.tile_regular2,R.drawable.tile_trench1};
		 map = new DoitooMap(tank_map1,resIds);
		 //���浱ǰmap������ȫ�ֱ������У��Ա��������ȡ��ǰ��ͼ����
		 this.set(this.getName(), map);
	}

	@Override
	public void draw() {
		Log.d("DrawMapTask", "draw");
		Canvas c =HOLDER.lockCanvas();
		map.draw(c);
		HOLDER.unlockCanvasAndPost(c);
		
	}

}
