package cn.doitoo.game.tankwar.task;

import android.content.Context;
import android.graphics.Canvas;
import cn.doitoo.game.framework.arithmetic.Dijkstra;
import cn.doitoo.game.framework.arithmetic.PathSolver;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.map.DoitooMap;
import cn.doitoo.game.framework.task.GameDrawTask;
import cn.doitoo.game.framework.util.Util;
import cn.doitoo.game.tankwar.R;

public class DrawMapTask implements GameDrawTask {

    private DoitooMap map = null;

    /**
     * ��ͼ�ֲ�ͼ����һ�أ�
     */
    public static final byte[][] tank_map1 = {{4, 4, 4, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
            {4, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}, {4, 1, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 3, 3, 3, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 3, 1, 1, 3, 3, 1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 3},
            {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3}, {1, 3, 1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3},
            {1, 3, 1, 1, 3, 3, 3, 3, 1, 3, 3, 3, 1, 3, 3, 1, 1, 1, 1, 3}, {1, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4},
            {1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 2, 4}, {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 4, 4},};

    /**
     * ��ǰ��ͼ�������鵱�д���ͨ�������� ����С�ڵ��ڴ�ֵ��Ϊͨ��
     */
    public static final int passValue = 1;

    public DrawMapTask() {
        Context context = G.getContext();
        int[] resIds = {R.drawable.tile_regular2, R.drawable.tile_trench1, R.drawable.tile_breakable, R.drawable.tile_breakable1};
        map = new DoitooMap(tank_map1, resIds, passValue, context, 0, 0);
        // ���浱ǰmap������ȫ�ֱ������У��Ա��������ȡ��ǰ��ͼ����
        G.setDoitooMap(map);
        // ����ͼ��������ת��01����
        PathSolver pathSolver = new Dijkstra();
        int[][] gameMap01Vector = Util.gameMap01Vector(tank_map1, passValue);
        G.set("gameMap01Vector", gameMap01Vector);
        G.set("pathSolver", pathSolver);

    }


    public void draw(Canvas canvas) {
        // ����ɫ
        canvas.drawRGB(0, 0, 0);
        map.paint(canvas);
    }

}
