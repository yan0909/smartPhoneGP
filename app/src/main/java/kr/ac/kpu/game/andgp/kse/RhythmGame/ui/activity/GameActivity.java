package kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.input.sensor.GyroSensor;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.view.GameView;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene.StartScene;


public class GameActivity extends AppCompatActivity {

    private static final long BACKKEY_INTERVAL_MSEC = 1000;

    public static GameActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UiBridge.setActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));

        if(instance == null)
        {
            instance = this;
        }

        new StartScene().run();
    }

    private long lastBackPressedOn;
    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        long elapsed = now - lastBackPressedOn;
        if (elapsed <= BACKKEY_INTERVAL_MSEC) {
            handleBackPressed();
            return;
        }
        Log.d("BackKey", "elapsed="+elapsed);
        Toast.makeText(this,
                "Press Back key twice quickly to exit",
                Toast.LENGTH_SHORT)
                .show();
        lastBackPressedOn = now;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (GyroSensor.isCreated()) {
            GyroSensor.get().register();
        }
    }

    @Override
    protected void onPause() {
        if (GyroSensor.isCreated()) {
            GyroSensor.get().unregister();
        }
        super.onPause();
    }

    public void handleBackPressed() {
        GameScene.getTop().onBackPressed();
    }
}
