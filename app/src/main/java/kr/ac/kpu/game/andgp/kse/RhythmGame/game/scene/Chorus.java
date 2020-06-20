package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import android.util.Log;
import android.view.View;

import java.util.Random;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Touchable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.bg.ImageScrollBackground;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.ui.Button;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.ChorusMan;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.ChorusMan_conductor;


public class Chorus extends GameScene {
    private static final String TAG = Chorus.class.getSimpleName();
    private int mdpi_100;

    public enum Layer {
        bg, platform, item, obstacle, player, ui, COUNT
    }

    private ChorusMan[] chorusMan;
    private ChorusMan_conductor chorusMan_conductor;
    private GameTimer timer;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());
        if (timer.done()) {
            pop();
        }
//        float dx = -2 * mdpi_100 * GameTimer.getTimeDiffSeconds();
//        for (int layer = Layer.platform.ordinal(); layer <= Layer.obstacle.ordinal(); layer++) {
//            ArrayList<GameObject> objects = gameWorld.objectsAtLayer(layer);
//            for (GameObject obj : objects) {
//                obj.move(dx, 0);
//            }
//        }
    }

    @Override
    public void enter() {
        super.enter();
//        GyroSensor.get();
        initObjects();

    }

    @Override
    public void exit() {
//        GyroSensor.get().destroy();
        super.exit();
    }


    private void initObjects() {
        timer = new GameTimer(60, 1);
        Random rand = new Random();
        mdpi_100 = UiBridge.x(300);
        Log.d(TAG, "mdpi_100: " + mdpi_100);
        int sw = UiBridge.metrics.size.x;
        int sh = UiBridge.metrics.size.y;
        int cx = UiBridge.metrics.center.x;
        int cy = UiBridge.metrics.center.y;
        chorusMan = new ChorusMan[3];
        chorusMan[0] = new ChorusMan(mdpi_100 - 400, sh - mdpi_100 - 300);
        chorusMan[1] = new ChorusMan(mdpi_100 - 150, sh - mdpi_100 - 250);
        chorusMan[2] = new ChorusMan(mdpi_100 + 100, sh - mdpi_100 - 200);
        chorusMan_conductor = new ChorusMan_conductor(250, sh - 550);
        for (int i=0; i<3; ++i)
            gameWorld.add(Layer.player.ordinal(), chorusMan[i]);
        gameWorld.add(Layer.player.ordinal(), chorusMan_conductor);

        Button btnShort = new Button(mdpi_100 + 100, sh - mdpi_100 + 200,
                R.mipmap.chorus_select_short);
        Button btnLong = new Button(mdpi_100 + 100, sh - mdpi_100 + 350,
                R.mipmap.chorus_select_long);
        Button btnTogether = new Button(mdpi_100 + 100, sh - mdpi_100 + 500,
                R.mipmap.chorus_select_together);

        btnShort.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                chorusMan[2].SetAnimState(ChorusMan.AnimState.SHORT);
            }
        });
        btnLong.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                chorusMan[2].SetAnimState(ChorusMan.AnimState.LONG);
            }
        });
        btnTogether.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                chorusMan[2].SetAnimState(ChorusMan.AnimState.TOGETHER);
            }
        });

        gameWorld.add(Layer.ui.ordinal(), btnShort);
        gameWorld.add(Layer.ui.ordinal(), btnLong);
        gameWorld.add(Layer.ui.ordinal(), btnTogether);
        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(mdpi_100 + 100, sh - mdpi_100 + 300, 400, 700, R.mipmap.chorus_select_bg));



        gameWorld.add(Layer.bg.ordinal(), new BitmapObject( 0, 0, 800, 600, R.mipmap.chorus_bg));
//        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1_2, ImageScrollBackground.Orientation.horizontal, -200));
//        gameWorld.add(Layer.bg.ordinal(), new ImageScrollBackground(R.mipmap.cookie_run_bg_1_3, ImageScrollBackground.Orientation.horizontal, -300));
    }
}
