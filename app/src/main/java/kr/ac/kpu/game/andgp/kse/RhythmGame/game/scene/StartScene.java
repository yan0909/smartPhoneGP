package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.ui.Button;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.Ball;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.CityBackground;


public class StartScene extends GameScene {
    private static final String TAG = StartScene.class.getSimpleName();

    public enum Layer {
        bg, enemy, player, ui, COUNT
    }

    private Ball ball;
//    private ScoreObject scoreObject;
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
//            scoreObject.add(100);
            timer.reset();
        }
    }

    @Override
    public void enter() {
        super.enter();
        initObjects();
    }

    private void initObjects() {
        Random rand = new Random();
        int mdpi_100 = UiBridge.x(100);
        for (int i = 0; i < 10; i++) {
            int dx = rand.nextInt(2 * mdpi_100) - 1 * mdpi_100;
            if (dx >= 0) dx++;
            int dy = rand.nextInt(2 * mdpi_100) - 1 * mdpi_100;
            if (dy >= 0) dy++;
            ball = new Ball(mdpi_100, mdpi_100, dx, dy);
            gameWorld.add(Layer.enemy.ordinal(), ball);
        }
        gameWorld.add(Layer.bg.ordinal(), new CityBackground());
        int screenWidth = UiBridge.metrics.size.x;
        RectF rbox = new RectF(UiBridge.x(-52), UiBridge.y(20), UiBridge.x(-20), UiBridge.y(62));
//        scoreObject = new ScoreObject(R.mipmap.number_64x84, rbox);
//        gameWorld.add(Layer.ui.ordinal(), scoreObject);
        BitmapObject title = new BitmapObject(UiBridge.metrics.center.x, UiBridge.y(160), -150, -150, R.mipmap.rhythm_world_tile);
        gameWorld.add(Layer.ui.ordinal(), title);
        timer = new GameTimer(2, 1);

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;
//        y += UiBridge.y(100);
        gameWorld.add(Layer.ui.ordinal(), new Button(cx, y, R.mipmap.btn_tutorial, R.mipmap.blue_round_btn, R.mipmap.red_round_btn));
        y += UiBridge.y(100);
        Button button = new Button(cx, y, R.mipmap.btn_start_game, R.mipmap.blue_round_btn, R.mipmap.red_round_btn);
        button.setOnClickRunnable(false, new Runnable() {
            @Override
            public void run() {
                Chorus scene = new Chorus();
                scene.push();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), button);
        y += UiBridge.y(100);
        gameWorld.add(Layer.ui.ordinal(), new Button(cx, y, R.mipmap.btn_highscore, R.mipmap.blue_round_btn, R.mipmap.red_round_btn));
    }
}
