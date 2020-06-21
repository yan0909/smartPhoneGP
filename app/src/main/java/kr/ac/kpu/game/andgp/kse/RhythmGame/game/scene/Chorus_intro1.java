package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import java.util.Random;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.ui.Button;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.sound.SoundEffects;

public class Chorus_intro1 extends GameScene {
    private GameTimer timer;

    public enum Layer {
        bg, ui, COUNT
    }

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    public void enter() {
        super.enter();
        initObjects();
    }



    private void initObjects() {
        timer = new GameTimer(1000, 1000);
        Random rand = new Random();

        int cx = UiBridge.metrics.center.x;
        int y = UiBridge.metrics.center.y;

        y += UiBridge.y(220);
        cx += UiBridge.x(100);
        Button button = new Button(cx, y, R.mipmap.btn_start, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        button.setOnClickRunnable(false, new Runnable() {
            @Override
            public void run() {
                SoundEffects se = SoundEffects.get();
                se.play(R.raw.button_start);
                pop();
                Chorus_intro2 scene = new Chorus_intro2();
                scene.push();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), button);

        cx -= UiBridge.x(200);
        Button button2 = new Button(cx, y, R.mipmap.btn_back, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        button2.setOnClickRunnable(false, new Runnable() {
            @Override
            public void run() {
                SoundEffects se = SoundEffects.get();
                se.play(R.raw.button);
                pop();
            }
        });
        gameWorld.add(Layer.ui.ordinal(), button2);

        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(UiBridge.metrics.size.x / 2, UiBridge.metrics.size.y / 2,
                UiBridge.metrics.size.x, UiBridge.metrics.size.y, R.mipmap.chorus_intro1));

    }
}
