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
import kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity.GameActivity;

public class Chorus_intro2 extends GameScene {
    private static final String TAG = Chorus_intro2.class.getSimpleName();;
    private int mdpi_100;
    private GameTimer timer;
    private MediaPlayer mp;

    public enum Layer {
        bg, ui, COUNT
    }

    @Override
    protected int getLayerCount() {
        return Chorus_intro.Layer.COUNT.ordinal();
    }

    public void enter() {
        super.enter();
        initObjects();
    }
    @Override
    public void exit() {
        mp.release();
        super.exit();
    }

    @Override
    public void update()
    {
        super.update();

        if(timer.getRawIndex() > 1000)
        {
            // 사운드
            if(mp == null) {
                mp = MediaPlayer.create(GameActivity.instance, R.raw.chorus_intro);
                mp.setVolume(1.f, 1.f);
                mp.setLooping(false);
                mp.start();
            }
        }

            if(timer.getRawIndex() > 4000)
        {
            timer.reset();
            pop();
            Chorus scene = new Chorus();
            scene.push();
        }
    }

    private void initObjects() {
        timer = new GameTimer(1000, 1000);
        Random rand = new Random();

        // 백그라운드
        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(UiBridge.metrics.size.x / 2, UiBridge.metrics.size.y / 2,
                UiBridge.metrics.size.x, UiBridge.metrics.size.y, R.mipmap.chorus_intro2));
    }
}
