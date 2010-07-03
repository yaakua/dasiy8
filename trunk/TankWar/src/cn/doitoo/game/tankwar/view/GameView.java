package cn.doitoo.game.tankwar.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.enums.GameStatus;
import cn.doitoo.game.framework.event.ITouchEventHandler;
import cn.doitoo.game.framework.graphic.DoitooView;
import cn.doitoo.game.framework.thread.GameDrawThread;
import cn.doitoo.game.tankwar.event.GestureMoveEvent;
import cn.doitoo.game.tankwar.task.DrawControlLayerTask;
import cn.doitoo.game.tankwar.task.DrawMapTask;
import cn.doitoo.game.tankwar.task.tank.PlayerHeroTankTask;

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
        /*Task tankSpriteTask = new TankSpriteTask();
        Task.add(tankSpriteTask);*/

        //������Ϸ���߳�
        GameDrawThread gameDrawThread0 = GameDrawThread.getInstance(holder);

        //��ʼ���Ƶ�ͼtask
        DrawMapTask drawMapTask = new DrawMapTask();
        //�������Ӣ��̹��
        PlayerHeroTankTask playerHeroTankTask = new PlayerHeroTankTask();

        //���ƿ��ư�ť��
        DrawControlLayerTask drawControlLayerTask = new DrawControlLayerTask();

        //����������
        gameDrawThread0.add(drawMapTask).add(playerHeroTankTask).add(drawControlLayerTask);
        gameDrawThread0.setGameStauts(GameStatus.RUNING);
        gameDrawThread0.start();

        ITouchEventHandler.touchList.add(new GestureMoveEvent());
    }

    /**
     * ��Ϸ��Դ��ʼ��
     *
     * @param holder SurfaceHolder
     */
    public void gameInit(SurfaceHolder holder) {
        DisplayMetrics dm = getContext().getApplicationContext().getResources()
                .getDisplayMetrics();
        int screenHeight = dm.heightPixels;
        int screenWidth = dm.widthPixels;
        G.set("holder", holder);
        G.setContext(getContext());
        G.set("screenHeight", screenHeight);  //��ǰ��Ļ�߶�
        G.set("screenWidth", screenWidth);    //��ǰ��Ļ���
        G.set("tankElementWidth", 48);//̹��Ԫ�ػ������

    }
}
