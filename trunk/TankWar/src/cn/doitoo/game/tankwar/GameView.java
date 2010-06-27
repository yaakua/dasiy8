package cn.doitoo.game.tankwar;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.graphic.DoitooView;
import cn.doitoo.game.framework.task.Task;
import cn.doitoo.game.tankwar.event.TankTouchEvent;
import cn.doitoo.game.tankwar.task.TankSpriteTask;

public class GameView extends DoitooView {
    public static final String TAG = "GameView";

    public GameView(Context context) {
        super(context);

        Log.d(TAG, "GameView");
    }

    @Override
    protected void initTasks(SurfaceHolder holder) {
        Log.d(TAG, "initTasks");
        gameInit(holder);
        Task tankSpriteTask = new TankSpriteTask();
        Task.add(tankSpriteTask);
    }

    /**
     * ��Ϸ��Դ��ʼ��
     * @param holder    SurfaceHolder
     */
    public void gameInit(SurfaceHolder holder) {
        DisplayMetrics dm = getContext().getApplicationContext().getResources()
                .getDisplayMetrics();
        float screenHeight = dm.heightPixels;
        float screenWidth = dm.widthPixels;
        G.set("holder", holder);
        G.set("context", getContext());
        G.set("screenHeight", screenHeight);  //��ǰ��Ļ�߶�
        G.set("screenWidth", screenWidth);    //��ǰ��Ļ���
        G.set("tankElementWidth",48);//̹��Ԫ�ػ������
        ITouchEventHandler.touchList.add(new TankTouchEvent());
    }
}
