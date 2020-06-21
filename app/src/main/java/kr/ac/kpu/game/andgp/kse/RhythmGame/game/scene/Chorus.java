package kr.ac.kpu.game.andgp.kse.RhythmGame.game.scene;

import android.media.MediaPlayer;

import java.util.HashMap;
import java.util.Random;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameScene;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.ui.Button;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.sound.SoundEffects;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.ChorusMan;
import kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj.ChorusMan_conductor;
import kr.ac.kpu.game.andgp.kse.RhythmGame.ui.activity.GameActivity;


public class Chorus extends GameScene {
    private static final String TAG = Chorus.class.getSimpleName();
    private int mdpi_100;

    public enum Layer {
        bg, player, ui, COUNT
    }

    private class PatternData
    {
        int resId_Sound;
        ChorusMan.AnimState anim_A;
        ChorusMan.AnimState anim_B;
        ChorusMan.AnimState anim_C;
        boolean isChecked;

        public PatternData(int resId, ChorusMan.AnimState anim_a, ChorusMan.AnimState anim_b)
        {
            this.resId_Sound = resId;
            this.anim_A = anim_a;
            this.anim_B = anim_b;
            this.isChecked = false;
        }

        public PatternData(ChorusMan.AnimState anim_c)
        {
            this.resId_Sound = 0;
            this.anim_A = ChorusMan.AnimState.NONE;
            this.anim_B = ChorusMan.AnimState.NONE;
            this.anim_C = anim_c;
            this.isChecked = false;
        }
    }


    private ChorusMan[] chorusMan;
    private ChorusMan_conductor chorusMan_conductor;
    private GameTimer timer;
    private MediaPlayer mp;
    private SoundEffects soundEffects;
    private HashMap<Integer, PatternData> patternMap;
    private int lastTick;

    @Override
    protected int getLayerCount() {
        return Layer.COUNT.ordinal();
    }

    @Override
    public void update() {
        super.update();
//        Log.d(TAG, "Score: " + timer.getRawIndex());
//        if (timer.done()) {
//            pop();
//        }
//        float dx = -2 * mdpi_100 * GameTimer.getTimeDiffSeconds();
//        for (int layer = Layer.platform.ordinal(); layer <= Layer.obstacle.ordinal(); layer++) {
//            ArrayList<GameObject> objects = gameWorld.objectsAtLayer(layer);
//            for (GameObject obj : objects) {
//                obj.move(dx, 0);
//            }
//        }

        //  사운드 패턴 재생
        int tick = timer.getRawIndex() - 200;
        for(Integer time : patternMap.keySet()) {
            PatternData data = patternMap.get(time);
            boolean isPattern = data.resId_Sound != 0;

            if(isPattern) {
                if(lastTick < time && time <= tick) {
                    soundEffects.play(data.resId_Sound);
                    chorusMan[0].SetAnimState(data.anim_A);
                    chorusMan[1].SetAnimState(data.anim_B);
                }
            }

            // 히트 타임을 놓친 경우
            if( !isPattern && !data.isChecked && time + 300 < tick )
            {
                if(chorusMan[0].GetAnimState() == ChorusMan.AnimState.IDLE)
                    chorusMan[0].SetAnimState(ChorusMan.AnimState.WRONG);
                if(chorusMan[1].GetAnimState() == ChorusMan.AnimState.IDLE)
                    chorusMan[1].SetAnimState(ChorusMan.AnimState.WRONG);
                data.isChecked = true;
            }
        }


        if(timer.getRawIndex() >= 92000)
            pop();


        lastTick = tick;
    }

    @Override
    public void enter() {
        super.enter();

        soundEffects = SoundEffects.get();


        lastTick = 0;

        patternMap = new HashMap<Integer, PatternData>();

        patternMap.put(11655, new PatternData(R.raw.chorus_short_a, ChorusMan.AnimState.SHORT, ChorusMan.AnimState.NONE));
        patternMap.put(12635, new PatternData(R.raw.chorus_short_b, ChorusMan.AnimState.NONE, ChorusMan.AnimState.SHORT));
        patternMap.put(13615, new PatternData(ChorusMan.AnimState.SHORT));

        patternMap.put(19405, new PatternData(R.raw.chorus_short_a, ChorusMan.AnimState.SHORT, ChorusMan.AnimState.NONE));
        patternMap.put(20385, new PatternData(R.raw.chorus_short_b, ChorusMan.AnimState.NONE, ChorusMan.AnimState.SHORT));
        patternMap.put(21345, new PatternData(ChorusMan.AnimState.SHORT));
        patternMap.put(21665, new PatternData(R.raw.chorus_conductor, ChorusMan.AnimState.NONE, ChorusMan.AnimState.NONE));
        patternMap.put(22645, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.TOGETHER, ChorusMan.AnimState.NONE));
        patternMap.put(22646, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.NONE, ChorusMan.AnimState.TOGETHER));
        patternMap.put(22647, new PatternData(ChorusMan.AnimState.TOGETHER));

        patternMap.put(27135, new PatternData(R.raw.chorus_short_a, ChorusMan.AnimState.SHORT, ChorusMan.AnimState.NONE));
        patternMap.put(28115, new PatternData(R.raw.chorus_short_b, ChorusMan.AnimState.NONE, ChorusMan.AnimState.SHORT));
        patternMap.put(29147, new PatternData(ChorusMan.AnimState.SHORT));

        patternMap.put(34890, new PatternData(R.raw.chorus_short_a, ChorusMan.AnimState.SHORT, ChorusMan.AnimState.NONE));
        patternMap.put(35870, new PatternData(R.raw.chorus_short_b, ChorusMan.AnimState.NONE, ChorusMan.AnimState.SHORT));
        patternMap.put(36850, new PatternData(ChorusMan.AnimState.SHORT));

        patternMap.put(38790, new PatternData(R.raw.chorus_long_a, ChorusMan.AnimState.LONG, ChorusMan.AnimState.NONE));
        patternMap.put(40690, new PatternData(R.raw.chorus_long_b, ChorusMan.AnimState.NONE, ChorusMan.AnimState.LONG));
        patternMap.put(42690, new PatternData(ChorusMan.AnimState.LONG));

        patternMap.put(44890, new PatternData(R.raw.chorus_conductor, ChorusMan.AnimState.NONE, ChorusMan.AnimState.NONE));
        patternMap.put(45850, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.TOGETHER, ChorusMan.AnimState.NONE));
        patternMap.put(45851, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.NONE, ChorusMan.AnimState.TOGETHER));
        patternMap.put(45852, new PatternData(ChorusMan.AnimState.TOGETHER));

        //
        patternMap.put(48763, new PatternData(R.raw.chorus_short_c, ChorusMan.AnimState.SHORT, ChorusMan.AnimState.NONE));
        patternMap.put(49423, new PatternData(R.raw.chorus_short_c, ChorusMan.AnimState.SHORT, ChorusMan.AnimState.NONE));
        patternMap.put(50755, new PatternData(R.raw.chorus_short_c, ChorusMan.AnimState.NONE, ChorusMan.AnimState.SHORT));
        patternMap.put(51425, new PatternData(R.raw.chorus_short_c, ChorusMan.AnimState.NONE, ChorusMan.AnimState.SHORT));
        patternMap.put(52747, new PatternData(ChorusMan.AnimState.SHORT));
        patternMap.put(53407, new PatternData(ChorusMan.AnimState.SHORT));

        patternMap.put(58013, new PatternData(R.raw.chorus_long_d, ChorusMan.AnimState.LONG, ChorusMan.AnimState.NONE));
        patternMap.put(58993, new PatternData(R.raw.chorus_long_e, ChorusMan.AnimState.NONE, ChorusMan.AnimState.LONG));
        patternMap.put(59973, new PatternData(ChorusMan.AnimState.LONG));

        patternMap.put(65878, new PatternData(R.raw.chorus_long_d, ChorusMan.AnimState.LONG, ChorusMan.AnimState.NONE));
        patternMap.put(66858, new PatternData(R.raw.chorus_long_e, ChorusMan.AnimState.NONE, ChorusMan.AnimState.LONG));
        patternMap.put(67838, new PatternData(ChorusMan.AnimState.LONG));

        patternMap.put(68298, new PatternData(R.raw.chorus_conductor, ChorusMan.AnimState.NONE, ChorusMan.AnimState.NONE));
        patternMap.put(69258, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.TOGETHER, ChorusMan.AnimState.NONE));
        patternMap.put(69259, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.NONE, ChorusMan.AnimState.TOGETHER));
        patternMap.put(70239, new PatternData(ChorusMan.AnimState.TOGETHER));

        patternMap.put(73608, new PatternData(R.raw.chorus_long_d, ChorusMan.AnimState.LONG, ChorusMan.AnimState.NONE));
        patternMap.put(74588, new PatternData(R.raw.chorus_long_e, ChorusMan.AnimState.NONE, ChorusMan.AnimState.LONG));
        patternMap.put(75568, new PatternData(ChorusMan.AnimState.LONG));

        patternMap.put(81300, new PatternData(R.raw.chorus_long_d, ChorusMan.AnimState.LONG, ChorusMan.AnimState.NONE));
        patternMap.put(82280, new PatternData(R.raw.chorus_long_e, ChorusMan.AnimState.NONE, ChorusMan.AnimState.LONG));
        patternMap.put(83260, new PatternData(ChorusMan.AnimState.LONG));

//        patternMap.put(87197, new PatternData(R.raw.chorus_conductor, ChorusMan.AnimState.NONE, ChorusMan.AnimState.NONE));
//        patternMap.put(88157, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.TOGETHER, ChorusMan.AnimState.NONE));
//        patternMap.put(88158, new PatternData(R.raw.chorus_together, ChorusMan.AnimState.NONE, ChorusMan.AnimState.TOGETHER));
        initObjects();

    }

    @Override
    public void exit() {
        mp.release();
        super.exit();
    }


    private void initObjects() {
        timer = new GameTimer(1000, 1000);
        Random rand = new Random();
        mdpi_100 = UiBridge.x(300);

        int sh = UiBridge.metrics.size.y;

        chorusMan = new ChorusMan[3];
        chorusMan[0] = new ChorusMan(mdpi_100 - 400, sh - mdpi_100 - 300);
        chorusMan[1] = new ChorusMan(mdpi_100 - 150, sh - mdpi_100 - 250);
        chorusMan[2] = new ChorusMan(mdpi_100 + 100, sh - mdpi_100 - 200);
        chorusMan_conductor = new ChorusMan_conductor(250, sh - 550);
        for (int i=0; i<3; ++i)
            gameWorld.add(Layer.player.ordinal(), chorusMan[i]);
        gameWorld.add(Layer.player.ordinal(), chorusMan_conductor);

//        Button btnShort = new Button(mdpi_100 + 100, sh - mdpi_100 + 200,
//                R.mipmap.chorus_select_short);
//        Button btnLong = new Button(mdpi_100 + 100, sh - mdpi_100 + 350,
//                R.mipmap.chorus_select_long);
//        Button btnTogether = new Button(mdpi_100 + 100, sh - mdpi_100 + 500,
//                R.mipmap.chorus_select_together);
        Button btnShort = new Button(mdpi_100 + 100, sh - mdpi_100 + 200, R.mipmap.btn_chorus_short, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        btnShort.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                chorusMan[2].SetAnimState(ChorusMan.AnimState.SHORT);
                soundEffects.play(R.raw.chorus_short_c);
                CheckHitTime(ChorusMan.AnimState.SHORT);
            }
        });

        Button btnLong = new Button(mdpi_100 + 100, sh - mdpi_100 + 350, R.mipmap.btn_chorus_long, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        btnLong.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                chorusMan[2].SetAnimState(ChorusMan.AnimState.LONG);
                soundEffects.play(R.raw.chorus_long_c);
                CheckHitTime(ChorusMan.AnimState.LONG);
            }
        });
        Button btnTogether = new Button(mdpi_100 + 100, sh - mdpi_100 + 500, R.mipmap.btn_chorus_together, R.mipmap.white_round_btn, R.mipmap.red_round_btn);
        btnTogether.setOnClickRunnable(true, new Runnable() {
            @Override
            public void run() {
                chorusMan[2].SetAnimState(ChorusMan.AnimState.TOGETHER);
                soundEffects.play(R.raw.chorus_together);
                CheckHitTime(ChorusMan.AnimState.TOGETHER);
            }
        });

        gameWorld.add(Layer.bg.ordinal(), new BitmapObject(UiBridge.metrics.size.x / 2, UiBridge.metrics.size.y / 2, UiBridge.metrics.size.x, UiBridge.metrics.size.y, R.mipmap.chorus_bg));
        gameWorld.add(Layer.ui.ordinal(), new BitmapObject(mdpi_100 + 110, sh - mdpi_100 + 310, 350, 650, R.mipmap.chorus_select_bg));
        gameWorld.add(Layer.ui.ordinal(), btnShort);
        gameWorld.add(Layer.ui.ordinal(), btnLong);
        gameWorld.add(Layer.ui.ordinal(), btnTogether);


        mp = MediaPlayer.create(GameActivity.instance, R.raw.chorus_bg);
        mp.setVolume(0.5f, 0.5f);
        mp.setLooping(false);
        mp.start();

    }


    private void CheckHitTime(ChorusMan.AnimState anim)
    {
        int tick = timer.getRawIndex() - 300;
        boolean isOutOfHitTime = true;
        for(Integer time : patternMap.keySet()) {
            PatternData data = patternMap.get(time);
            boolean isPattern = data.resId_Sound != 0;

            // 히트 타임인 경우
            if( !isPattern && time - 800 <= tick && tick <= time + 800  && data.anim_C == anim )
            {
                isOutOfHitTime = false;
                data.isChecked = true;
                break;
            }
        }

        if( isOutOfHitTime ) {
        if(chorusMan[0].GetAnimState() == ChorusMan.AnimState.IDLE)
            chorusMan[0].SetAnimState(ChorusMan.AnimState.WRONG);
        if(chorusMan[1].GetAnimState() == ChorusMan.AnimState.IDLE)
            chorusMan[1].SetAnimState(ChorusMan.AnimState.WRONG);
        }
    }


}
