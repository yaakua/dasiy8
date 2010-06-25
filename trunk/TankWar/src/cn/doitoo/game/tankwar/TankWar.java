package cn.doitoo.game.tankwar;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class TankWar extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //���ó�ȫ��ģʽ..
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //ǿ��Ϊ����
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //�ޱ���
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
       setContentView(new GameView(this));
       Log.d("TankWar","onCreate");
         //setContentView(R.layout.main);
    }
    
    
}