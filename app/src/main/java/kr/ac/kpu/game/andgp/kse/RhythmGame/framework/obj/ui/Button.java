package kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.NinePatchDrawable;
import android.util.Log;
import android.view.MotionEvent;

import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Touchable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj.BitmapObject;


public class Button extends BitmapObject implements Touchable {
    private static final String TAG = Button.class.getSimpleName();
    private final NinePatchDrawable bgNormal;
    private final NinePatchDrawable bgPress;
    private boolean capturing, pressed;
    private Runnable onClickRunnable;
    private boolean runOnDown;
    private RectF bound;

    public Button(float x, float y, int resId, int bgNormalResId, int bgPressResId) {
        super(x, y, 0, 0, resId);

        this.bgNormal = (NinePatchDrawable) UiBridge.getResources().getDrawable(bgNormalResId);
        this.bgPress = (NinePatchDrawable) UiBridge.getResources().getDrawable(bgPressResId);
        int left = (int)this.x - this.width / 2, top = (int)this.y - this.height / 2;
        this.bgNormal.setBounds(left, top, left + this.width, top + this.height);
        this.bgPress.setBounds(left, top, left + this.width, top + this.height);

        bound = new RectF(this.bgNormal.getBounds());
    }

    public Button(float x, float y, int resId) {
        super(x, y, 0, 0, resId);
        this.bgNormal = null;
        this.bgPress = null;

        bound = new RectF();
        getBox(bound);
    }

    @Override
    public void draw(Canvas canvas) {
        NinePatchDrawable bg = pressed ? bgPress : bgNormal;
        if( bg != null) {
            bg.draw(canvas);
        }
        super.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (bound.contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "Down");
                    captureTouch();
                    capturing = true;
                    pressed = true;

                    if (onClickRunnable != null && runOnDown) {
                        onClickRunnable.run();
                    }

                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                pressed = bound.contains((int)e.getX(), (int)e.getY());
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Up");
                releaseTouch();
                capturing = false;
                pressed = false;
                if (bound.contains((int)e.getX(), (int)e.getY())) {
                    Log.d(TAG, "TouchUp Inside");
                    if (onClickRunnable != null && !runOnDown) {
                        onClickRunnable.run();
                    }
                }
                return true;
        }
        return false;
    }

    public void setOnClickRunnable(boolean runOnDown, Runnable runnable) {
        this.runOnDown = runOnDown;
        this.onClickRunnable = runnable;
    }
}
