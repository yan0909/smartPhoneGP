package kr.ac.kpu.game.andgp.kse.RhythmGame.framework.obj;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.SharedBitmap;

public class BitmapObject extends GameObject{
    private static final String TAG = BitmapObject.class.getSimpleName();
    protected SharedBitmap sbmp;
    protected final RectF dstRect;
    protected int width;
    protected int height;

    public BitmapObject(float x, float y, int width, int height, int resId) {
        sbmp = SharedBitmap.load(resId);
        this.x = x;
        this.y = y;
        this.dstRect = new RectF();
        if (width == 0) {
            width = UiBridge.x(sbmp.getWidth());
        } else if (width < 0) {
            width = UiBridge.x(sbmp.getWidth()) * -width / 100;
        }
        this.width = width;
        if (height == 0) {
            height = UiBridge.x(sbmp.getHeight());
        } else if (height < 0) {
            height = UiBridge.x(sbmp.getHeight()) * -height / 100;
        }
        this.height = height;
    }

    @Override
    public float getRadius() {
        return this.width / 2;
    }

    public void draw(Canvas canvas) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        canvas.drawBitmap(sbmp.getBitmap(), null, dstRect, null);
    }

    public void draw(Canvas canvas, int width, int height)
    {
        int halfWidth = width / 2;
        int halfHeight = height / 2;
        dstRect.left = x - halfWidth;
        dstRect.top = y - halfHeight;
        dstRect.right = x + halfWidth;
        dstRect.bottom = y + halfHeight;
        canvas.drawBitmap(sbmp.getBitmap(), null, dstRect, null);
    }

    public void getBox(RectF rect) {
        int hw = width / 2;
        int hh = height / 2;
        rect.left = x - hw;
        rect.top = y - hh;
        rect.right = x + hw;
        rect.bottom = y + hh;
    }
}
