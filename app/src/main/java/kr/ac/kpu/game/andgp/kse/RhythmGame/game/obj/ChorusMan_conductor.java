package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import android.view.MotionEvent;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Touchable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.AnimObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;

public class ChorusMan_conductor extends AnimObject  {

    private static final String TAG = ChorusMan_conductor.class.getSimpleName();
    private final FrameAnimationBitmap fabNormal;

//    private float base;
//    private GameTimer timer;

//    private const int touchAnimFPS = 12;
//    private const int touchAnimFrameCount = 4;
//    private const touchAnimPlayTime =

    public ChorusMan_conductor(float x, float y) {
        super(x, y, 73 * 6, 107 * 6, R.mipmap.chorus_idle_conductor, 2, 3);
//        base = y;
//        timer = new GameTimer(60, 1);
//
//
        fabNormal = fab;


    }


    @Override
    public void update() {

    }

//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        if (e.getAction() != MotionEvent.ACTION_DOWN) {
//            return false;
//        }
//        float tx = e.getX();
//
//        if (tx < UiBridge.metrics.center.x) {
//            // touch - sing
//
//                setAnimState(AnimState.touch);
//            }
//        } else {
//            // drag
//        }
//        return false;
//    }
}
