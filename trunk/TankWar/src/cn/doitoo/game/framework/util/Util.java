package cn.doitoo.game.framework.util;

import java.util.ArrayList;
import java.util.List;

import cn.doitoo.game.framework.arithmetic.Pathxy;
import cn.doitoo.game.framework.context.G;
import cn.doitoo.game.framework.math.Point;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * ���ù�����
 * @author Oliver O
 *
 */
public class Util {

	public static Bitmap getBitMapById(Context context,int id){
		return  BitmapFactory.decodeStream(context.getResources().openRawResource(id));
	}

    /**
     * ����ͼ��������ת����ͨ�����ϰ������͵�����
     * @param map    ��ͼ��������
     * @param passValue   ��ǰ��ͼΪͨ�����͵�ֵ������С�ڵ��ڴ�ֵ��Ϊͨ��
     * @return  0�����ϰ��� 1 ����ͨ��
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
	 * @param x begin at 0
	 * @param y begin at 0
	 * @return begin at 0
	 */
	public static int convertPoint2Node(int cols,int x,int y){
		return x+cols*y;
	}
	
	public static Point convertNode2Point(int node,int cols){
		int x = node%cols;
		int y = node/cols;
		Point p = new Point();
		p.x=x;
		p.y=y;
		return p;
	}
	
}
