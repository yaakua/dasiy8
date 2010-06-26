package cn.doitoo.game.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * 常用工具类
 * @author Oliver O
 *
 */
public class Util {

	public static Bitmap getBitMapById(Context context,int id){
		return  BitmapFactory.decodeStream(context.getResources().openRawResource(id));
	}

    /**
     * 将地图排列数组转换成通道与障碍物类型的数组
     * @param map    地图排列数组
     * @param passValue   当前地图为通道类型的值，凡是小于等于此值都为通道
     * @return  0代表障碍物 1 代表通道
     */
	public static int[][] gameMap01Vector(int[][] map,int passValue){
        int[][] vector =new int[map.length][map[0].length];
        for(int i=0;i<map.length;i++){
            int[] x = new int[map[i].length];
            for(int j=0;j<map[i].length;j++){
                int y = map[i][j];
                y = (y<=passValue)?1:0;
                x[j] = y;
            }
            vector[i]=x;
        }
        return vector;
    }

}
