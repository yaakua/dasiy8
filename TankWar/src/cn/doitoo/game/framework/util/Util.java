package cn.doitoo.game.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

import java.util.Random;


/**
 * 常用工具类
 * @author Oliver O
 *
 */
public class Util {
    private static Random random = new Random();
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

	/**
	 * 
	 * @param x begin at 0 游戏地图矩阵坐标，非屏幕坐标系中的坐标
	 * @param y begin at 0
	 * @return begin at 0
	 */
	public static int convertPoint2Node(int cols,int x,int y){
		return x+cols*y;
	}
	
	public static Point convertNode2Point(int node,int cols){
		return new Point(node%cols,node/cols);
	}
	/**
	 * 
	 * @param x 游戏地图矩阵坐标
	 * @param y
	 */
	public static Point getDestination(int gameMap01[][],int x,int y){
		if(gameMap01[x][y]==1)return new Point(x,y);
		int x1 = x;		
		int y1= y;		
		while(gameMap01[x1--][y1]==0&&x1>0);
		Point p1 = new Point(x1+1,y1);
		int d1 = Math.abs(x1-x);
		int d = d1;
		Point p = p1;
		x1 = x;
		while(gameMap01[x1++][y1]==0&&x1<gameMap01[0].length);
		Point p2 = new Point(x1+1,y1);
		int d2 = Math.abs(x1-x);
	    if(d2<d){
	    	d = d2;
	    	p = p2;
	    }
		while(gameMap01[x1][y1--]==0&&y1>0);
		Point p3 = new Point(x1+1,y1);
		int d3 = Math.abs(y1-y);
		if(d3<d){
			d = d3;
			p = p3;
		}
		y1 = y;
		while(gameMap01[x1][y1++]==0&&y1<gameMap01.length);
		Point p4 = new Point(x1+1,y1);
		int d4 = Math.abs(y1-y);
		if(d4<d){
			d = d4;
            p = p4;			
		}
		
		return p;
	}

    /**
	 *
	 * @return 随机获取一个指定范围内的值
	 */
	public static int getRandomDirect(int range){
		return random.nextInt(range);
	}
	
}
