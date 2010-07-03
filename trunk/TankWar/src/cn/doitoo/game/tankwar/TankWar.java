package cn.doitoo.game.tankwar;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import cn.doitoo.game.tankwar.view.GameView;

public class TankWar extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置成全屏模式..
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //强制为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.startview);

        Button startBtn = (Button) findViewById(R.id.start);
        Button settingBtn = (Button) findViewById(R.id.setting);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                switch (id) {
                    case R.id.start:
                        //进入游戏主界面
                        setContentView(new GameView(v.getContext()));
                        break;
                    case R.id.setting:
                        break;
                }
            }
        });
    }
}