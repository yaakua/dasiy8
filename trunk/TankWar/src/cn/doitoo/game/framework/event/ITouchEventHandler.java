package cn.doitoo.game.framework.event;

import cn.doitoo.game.framework.context.GameContext;
import android.view.MotionEvent;

public abstract class ITouchEventHandler extends GameContext{

  public abstract void onTouchDown(MotionEvent event);

  public abstract void onTouchMove(MotionEvent event);

  public abstract void onTouchUp(MotionEvent event);
  
}
