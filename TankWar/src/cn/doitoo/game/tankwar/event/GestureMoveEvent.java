package cn.doitoo.game.tankwar.event;

import android.view.MotionEvent;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.math.LinearSolver;

public class GestureMoveEvent extends ITouchEventHandler{
	
	private float preX;
	private float preY;

	@Override
	public void onTouchDown(MotionEvent event) {
	     preX = event.getX();
	     preY = event.getY();
		
	}

	@Override
	public void onTouchMove(MotionEvent event) {
      float x =  event.getX();
      float y = event.getY();
      if(LinearSolver.distance(preX, preY, x, y)<2)return ;
      float deltaX = x-preX;
      float deltaY = y-preY;
      
      DoitooMap  map = (DoitooMap) G.get(DoitooMap.class.getName());
      map.setPosition(map.getX()+deltaX, map.getY()+deltaY);
      preX = x;
      preY = y;
		
	}

	@Override
	public void onTouchUp(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
