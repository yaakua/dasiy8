package cn.doitoo.game.framework.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by IntelliJ IDEA.
 * User: ����
 * Date: 2010-7-4
 * Time: 13:56:41
 */
public class ImageUtil {
    /**
     * ͼƬ͸���ȴ���
     *
     * @param sourceImg ԭʼͼƬ
     * @param number    ͸����
     * @return
     */
    public static Bitmap setAlpha(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// ���ͼƬ��ARGBֵ
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);// �޸����2λ��ֵ
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);
        return sourceImg;
    }

    /**
     * ͼƬ�����ŷ���
     *
     * @param bgimage   ��ԴͼƬ��Դ
     * @param newWidth  �����ź���
     * @param newHeight �����ź�߶�
     * @return
     */

    public Bitmap zoomImage(Bitmap bgimage, int newWidth, int newHeight) {

        // ��ȡ���ͼƬ�Ŀ�͸�
        int width = bgimage.getWidth();
        int height = bgimage.getHeight();

        // ��������ͼƬ�õ�matrix����
        Matrix matrix = new Matrix();

        // ���������ʣ��³ߴ��ԭʼ�ߴ�
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // ����ͼƬ����
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height,
                matrix, true);
        return bitmap;

    }
}
