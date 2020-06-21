package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import android.media.MediaPlayer;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.ui.Button;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.sound.SoundEffects;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.Ball;
import kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity.GameActivity;


public class MainScene extends GameScene {
    private static final String TAG = MainScene.class.getSimpleName();
    private MediaPlayer mp;

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

    @Override
    public void exit() {
        mp.release();
        super.exit();
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

        // 메인 화면
        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(UiBridge.metrics.size.x / 2, UiBridge.metrics.size.y / 2, UiBridge.metrics.size.x, UiBridge.metrics.size.y, R.mipmap.title_bg));

        // 타이틀
        gameWorld.add(Layer.ui.ordinal(), new BitmapObject(UiBridge.metrics.center.x, UiBridge.y(160), -150, -150, R.mipmap.rhythm_world_tile));
        timer = new GameTimer(2, 1);

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;

        y += UiBridge.y(100);

        // 코러스맨 버튼
        Button button = new Button(cx, y, R.mipmap.btn_start_chorus, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        button.setOnClickRunnable(false, new Runnable() {

            @Override
            public void run() {
                SoundEffects se = SoundEffects.get();
                se.play(R.raw.button);
                Chorus_intro scene = new Chorus_intro();
                scene.push();
                mp.pause();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), button);

        y += UiBridge.y(100);

        // 로봇 버튼
        Button button2 = new Button(cx, y, R.mipmap.btn_start_robot, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        // 로봇 인트로 넣기
        gameWorld.add(Layer.ui.ordinal(), button2);

        //----------------------------------------------------------------------------------

        // 사운드
        mp = MediaPlayer.create(GameActivity.instance, R.raw.main_bg);
        mp.setVolume(1.f, 1.f);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    public void resume() {
        super.resume();
        mp.start();
    }

    @Override
    public void pause() {
        super.pause();
        mp.pause();
    }
}
