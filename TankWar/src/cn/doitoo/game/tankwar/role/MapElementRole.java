package cn.doitoo.game.tankwar.role;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import cn.doitoo.game.framework.role.MovableRole;

public class MapElementRole extends MovableRole {

	private float width;
	private float height;
	
	private static List<Bitmap> AnimateBitMapList = new ArrayList<Bitmap>();
	
	
	public MapElementRole(float x,float y,Context context) {
		super(x, y);
		
		this.width = width;
		this.height = height;
	}

	@Override
	public void move() {

	}

	@Override
	public void paint(Canvas c) {

	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getWidth() {
		return 0;
	}

}
