package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.AnimObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;

public class ChorusMan_conductor extends AnimObject {

    private static final String TAG = ChorusMan_conductor.class.getSimpleName();
    private final FrameAnimationBitmap fabNormal;

    public ChorusMan_conductor(float x, float y) {
        super(x, y, 73 * 6, 107 * 6, R.mipmap.chorus_idle_conductor, 2, 3);

        fabNormal = fab;
    }

    @Override
    public void update() {
    }
}
