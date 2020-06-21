package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import android.util.Log;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.AnimObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;

public class ChorusMan extends AnimObject {

    private static final String TAG = ChorusMan.class.getSimpleName();
    private final FrameAnimationBitmap fabIdle;
    private final FrameAnimationBitmap fabShort;
    private final FrameAnimationBitmap fabLong;
    private final FrameAnimationBitmap fabTogether;
    private final FrameAnimationBitmap fabWrong;

    private GameTimer timer;
    private AnimState myState;

    public ChorusMan(float x, float y) {
        super(x, y, 47 * 6, 82 * 6, R.mipmap.chorus_idle, 2, 2);
        timer = new GameTimer(1000, 1000);

        fabIdle = fab;
        fabShort = new FrameAnimationBitmap(R.mipmap.chorus_short, 12, 9);
        fabLong = new FrameAnimationBitmap(R.mipmap.chorus_long, 12, 24);
        fabTogether = new FrameAnimationBitmap(R.mipmap.chorus_together, 12, 9 );
        fabWrong = new FrameAnimationBitmap(R.mipmap.chorus_wrong, 3, 3 );

        myState = AnimState.IDLE;
    }

    public enum AnimState {
        NONE, IDLE, SHORT, LONG, TOGETHER, WRONG
    }
    public void SetAnimState(AnimState state) {
        if(state == AnimState.NONE)
            return;

        myState = state;
        switch (state) {
            case IDLE:
                fab = fabIdle;
                break;

            case SHORT:
                fab = fabShort;
                break;

            case LONG:
                fab = fabLong;
                break;

            case TOGETHER:
                fab = fabTogether;
                break;

            case WRONG:
                fab = fabWrong;
                break;
        }
        fab.reset();
        timer.reset();
    }

    public AnimState GetAnimState()
    {
        return this.myState;
    }

    @Override
    public void update() {
        // IDLE : 그냥 무한 반복
        // SHORT : 한번 재생하면 다시 IDLE로
        // LONG : ''
        // TOGETHER : ''

        if(myState == AnimState.IDLE) {
            // 아무것도 안 함
        }
        else if(myState == AnimState.SHORT)
        {
            if(fabShort.done())
                SetAnimState(AnimState.IDLE);
        }
        else if(myState == AnimState.LONG)
        {
            if(fabLong.done())
                SetAnimState(AnimState.IDLE);
        }
        else if(myState == AnimState.TOGETHER)
        {
            if(fabTogether.done())
                SetAnimState(AnimState.IDLE);
        }
        else if(myState == AnimState.WRONG)
        {
            Log.d(TAG, Integer.toString( timer.getRawIndex()));
            if(timer.getRawIndex() > 1000)
                SetAnimState(AnimState.IDLE);
        }
    }


}
