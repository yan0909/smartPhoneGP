package kr.ac.kpu.game.andgp.kse.RhythmGame.game.obj;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.game.andgp.kse.RhythmGame.R;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.iface.Recyclable;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameObject;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameTimer;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.GameWorld2;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.MainWorld;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.main.UiBridge;
import kr.ac.kpu.game.andgp.kse.RhythmGame.framework.res.bitmap.FrameAnimationBitmap;

public class RobotAHead extends GameObject implements Recyclable {
    private static final int FRAME_COUNT = 1;
    private static final int FRAME_PER_SECOND = 6;
    private static float HEIGHT;

    private int halfSize;
    private FrameAnimationBitmap fab;
    private float x;
    private float y;
    private boolean isAssembled;


    private RobotAHead() {}


    public static RobotAHead get(float x, float y)
    {
//        GameWorld2 gw = GameWorld2.get();
//        RobotAHead robotAHead = (RobotAHead)gw.getRecyclePool().get(RobotAHead.class);
//        if(robotAHead == null) {
//            robotAHead = new RobotAHead();
//        }
        RobotAHead robotAHead = new RobotAHead();

        robotAHead.fab = new FrameAnimationBitmap(R.mipmap.robot_obja_head2, FRAME_PER_SECOND, FRAME_COUNT);
        robotAHead.halfSize = robotAHead.fab.getHeight() / 2;
        HEIGHT = robotAHead.fab.getHeight();
        robotAHead.x = x;
        robotAHead.y = y;
        robotAHead.isAssembled = false;
        return robotAHead;
    }



    @Override
    public void update()
    {
        int screenWidth = UiBridge.metrics.size.x;
        int screenHeight = UiBridge.metrics.size.y;


        int hh = fab.getHeight() / 2;
        if (y + hh > screenHeight - RobotALeg.getHeight()- RobotABody.getHeight())
        {
            isAssembled = true;
            return;
        }

        y += RobotA.FALLING_SPEED * GameTimer.getTimeDiffSeconds();

    }

    @Override
    public void draw(Canvas canvas) {
        fab.draw(canvas, x, y);
    }

    public void getBox(RectF rect) {
        int hw = fab.getWidth() / 2;
        int hh = fab.getHeight() / 2;
        rect.left = x - hw;
        rect.right = x + hw;
        rect.top = y - hh;
        rect.bottom = y + hh;

    }

    @Override
    public void recycle() {

    }

    public static float getHeight()
    {
        return HEIGHT;
    }

    public boolean isAssembled() {
        return isAssembled;
    }
    public void setAssembled(boolean _b) { isAssembled = _b; }

    public float getPosX()
    {
        return x;
    }
    public float getPosY()
    {
        return y;
    }

    public void setPosX(float _x) {
        x = _x;
    }
    public void setPosY(float _y) {
        y = _y;
    }

}
