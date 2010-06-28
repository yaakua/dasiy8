package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import cn.doitoo.game.framework.arithmetic.Dijkstra;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.task.DrawGraphicTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

import java.util.List;

public class DrawMapTask extends DrawGraphicTask {

    private DoitooMap map = null;

    private SurfaceHolder holder;

    /**
     * 地图分布图（第一关）
     */
    public static final byte[][] tank_map2 = {
            {1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 3, 1, 1, 3, 3, 3, 4, 3, 3, 1, 1, 1, 3, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 4, 1, 3, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {4, 1, 1, 3, 1, 4, 4, 4, 1, 4, 1, 4, 1, 3, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 1, 1, 3, 1, 4, 1, 4, 1, 4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 3, 1, 1, 1, 1, 1, 1, 1, 4, 3, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 1, 1, 3, 4, 4, 3, 3, 4, 4, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {4, 1, 1, 1, 4, 2, 2, 2, 4, 1, 1, 3, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {4, 1, 3, 1, 4, 2, 3, 2, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 4, 4, 4, 4, 1, 3, 3, 3, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 1, 1, 1, 3, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 1, 1, 1, 2, 4, 1, 1, 1, 3, 3, 3, 3, 2, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {4, 1, 1, 1, 4, 2, 2, 2, 4, 1, 1, 3, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {4, 1, 3, 1, 4, 2, 3, 2, 4, 1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 4, 4, 4, 4, 1, 3, 3, 3, 4, 4, 4, 4, 4, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 1, 1, 1, 3, 1, 1, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1},
            {3, 1, 1, 1, 1, 2, 4, 1, 1, 1, 3, 3, 3, 3, 2, 1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1}
    };

    public static final byte[][] tank_map1 = {
            {1, 1, 1, 1, 4, 1, 1, 4, 1, 1, 1, 1, 4, 1, 1,},
            {1, 3, 1, 1, 3, 3, 3, 4, 3, 3, 1, 1, 1, 3, 1,},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1,},
            {1, 1, 1, 3, 1, 1, 3, 1, 1, 1, 1, 4, 1, 3, 1,},
            {4, 1, 1, 3, 1, 4, 4, 4, 1, 4, 1, 4, 1, 3, 1,},
            {1, 1, 1, 3, 1, 4, 1, 4, 1, 4, 1, 1, 1, 3, 1,},
            {3, 3, 1, 1, 1, 1, 1, 1, 1, 4, 3, 1, 1, 1, 1,},
            {3, 1, 1, 1, 1, 1, 1, 1, 1, 4, 1, 1, 1, 4, 1,},
            {1, 1, 1, 3, 4, 4, 3, 3, 4, 4, 1, 1, 1, 4, 1,},
            {4, 1, 1, 1, 4, 2, 2, 2, 4, 1, 1, 3, 1, 4, 1,},
            {4, 1, 3, 1, 4, 2, 3, 2, 4, 1, 1, 1, 1, 4, 1,},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
            {3, 1, 4, 4, 4, 4, 1, 3, 3, 3, 4, 4, 4, 4, 4,},
            {3, 1, 1, 1, 1, 3, 1, 1, 4, 1, 1, 1, 1, 1, 1,},
            {3, 1, 1, 1, 1, 2, 4, 1, 1, 1, 3, 3, 3, 3, 2,},
            {4, 1, 1, 1, 4, 2, 2, 2, 4, 1, 1, 3, 1, 4, 1,},
            {4, 1, 3, 1, 4, 2, 3, 2, 4, 1, 1, 1, 1, 4, 1,},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
            {3, 1, 4, 4, 4, 4, 1, 3, 3, 3, 4, 4, 4, 4, 4,},
            {3, 1, 1, 1, 1, 3, 1, 1, 4, 1, 1, 1, 1, 1, 1,},
            {3, 1, 1, 1, 1, 2, 4, 1, 1, 1, 3, 3, 3, 3, 2,}
    };

    /**
     * 当前地图排列数组当中代表通道的类型
     * 凡是小于等于此值都为通道
     */
    public static final int passValue = 1;


    public DrawMapTask() {
        this.holder = (SurfaceHolder) G.get("holder");
        Context context = (Context) G.get("context");
        int[] resIds = {R.drawable.tile_regular2, R.drawable.tile_breakable, R.drawable.tile_breakable1
                , R.drawable.tile_trench1};
        map = new DoitooMap(tank_map1, resIds, passValue, context, 0, 0);
        //保存当前map对象至全局变量当中，以便其它类获取当前地图对象
        G.set(map.getClass().getName(), map);
        //将地图排列数组转成01矩阵
        PathSolver pathSolver = new Dijkstra();
        int[][] gameMap01Vector = Util.gameMap01Vector(tank_map1, passValue);
        G.set("gameMap01Vector", gameMap01Vector);
        G.set("pathSolver",pathSolver);

    }

    @Override
    public void draw(Canvas c) {
    	// 背景色
//		c.drawRGB(0, 0, 0);
        map.paint(c);
    }


}
